package com.example.demo.controller;

import cn.hutool.core.convert.Convert;
import com.example.demo.base.Result;
import com.example.demo.dto.CategoryDto;
import com.example.demo.entity.Category;
import com.example.demo.facade.CategoryFacade;
import com.example.demo.vo.CategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "分类")
@RestController
@RequestMapping("/category")
@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class CategoryController {

    @Autowired
    private CategoryFacade categoryFacade;

    @ApiOperation("获取分类列表")
    @GetMapping("")
    public Result<List<CategoryVo>> list(@RequestParam(name = "id", defaultValue = "0") Long id) {
        // 全部读出 加速搜索
        List<Category> categoryList = categoryFacade.list();
        List<CategoryVo> categoryVoList = get(categoryList, id);
        return Result.success(categoryVoList);
    }

    @ApiOperation("添加分类")
    @PostMapping("")
    public Result<String> save(@Valid CategoryDto categoryDto) {
        categoryFacade.save(categoryDto);
        return Result.success();
    }

    @ApiOperation("修改分类")
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable("id") Long id, @Valid CategoryDto categoryDto) {
        categoryFacade.update(id, categoryDto);
        return Result.success();
    }

    @ApiOperation("删除分类")
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable("id") Long id) {
        categoryFacade.delete(id);
        return Result.success();
    }

    // 递归调用
    public List<CategoryVo> get(List<Category> categoryList, Long id) {
        List<CategoryVo> categoryVoList = getByPid(categoryList, id);
        categoryVoList.forEach(categoryVo -> categoryVo.setChildren(get(categoryList, categoryVo.getId())));
        return categoryVoList;
    }

    public List<CategoryVo> getByPid(List<Category> categoryList, Long id) {
        return categoryList.stream()
                .filter(category -> category.getPid().equals(id))
                .map(category -> Convert.convert(CategoryVo.class, category))
                .collect(Collectors.toList());
    }
}
