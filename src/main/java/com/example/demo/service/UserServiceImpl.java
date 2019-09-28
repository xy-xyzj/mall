package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import com.example.demo.access.JwtTokenService;
import com.example.demo.vo.UserInfoVo;
import com.example.demo.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenService jwtTokenService;


    @Override
    public User getUserByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<User> userList = userMapper.selectByExample(example);
        if (userList != null && userList.size() > 0) {
            return userList.get(0);
        }
        return null;
    }

    @Override
    public User register(UserVo userVo) {
        // 验证账号是否存在
        User user = getUserByUsername(userVo.getUsername());
        if (user != null) {
            return null;
        }
        user = new User();
        BeanUtils.copyProperties(userVo, user);
        user.setPassword(passwordEncoder.encode(userVo.getPassword()));
        user.setCreateTime(new Date());
        userMapper.insert(user);
        return user;
    }

    @Override
    public String login(UserVo userVo) {
        System.out.println("userVo" + userVo);
        User user = getUserByUsername(userVo.getUsername());
        if (user == null) {
            return null;
        }
        if (!passwordEncoder.matches(userVo.getPassword(), user.getPassword())) {
            return null;
        }
        user.setLoginTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        String token = jwtTokenService.generateToken(user.getUsername());
        return token;
    }

    @Override
    public void logout() {
        // TODO
    }

    @Override
    public int updatePasswordByEmail(UserVo userVo) {
        UserExample example = new UserExample();
        example.createCriteria().andEmailEqualTo(userVo.getEmail());
        List<User> userList = userMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(userList)) {
            return 0;
        }
        User user = userList.get(0);
        user.setPassword(passwordEncoder.encode(userVo.getPassword()));
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updateUserInfoByUsername(String username, UserInfoVo userInfoVo) {
        User user = new User();
        BeanUtils.copyProperties(userInfoVo, user);// source -> target 忽略空值
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username);
        return userMapper.updateByExampleSelective(user, example);
    }
}
