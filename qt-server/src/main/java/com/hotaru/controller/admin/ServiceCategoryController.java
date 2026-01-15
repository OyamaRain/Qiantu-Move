package com.hotaru.controller.admin;

import com.hotaru.constant.StatusConstant;
import com.hotaru.dto.ServiceCategoryDTO;
import com.hotaru.dto.ServiceCategoryPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.ServiceCategoryService;
import com.hotaru.vo.ServiceCategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class ServiceCategoryController {

    @Autowired
    private ServiceCategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    @Tag(name = "分类管理")
    @Operation(summary = "修改分类")
    public Result update(@RequestBody ServiceCategoryDTO serviceCategoryDTO){
        log.info("修改分类：{}", serviceCategoryDTO);
        categoryService.update(serviceCategoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @Tag(name = "分类管理")
    @Operation(summary = "分页查询分类")
    public Result<PageResult> page(@ParameterObject ServiceCategoryPageQueryDTO serviceCategoryPageQueryDTO){
        log.info("分页查询分类：{}", serviceCategoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(serviceCategoryPageQueryDTO);
        return Result.success(pageResult);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/status/{status}")
    @Tag(name = "分类管理")
    @Operation(summary = "启用、禁用分类")
    public Result EnableOrDisable(@PathVariable Integer status,@RequestParam Long id){
        log.info("服务分类ID{}：{},", id, status == StatusConstant.ENABLE ? "启用" : "禁用");
        categoryService.EnableOrDisable(status,id);
        return Result.success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Tag(name = "分类管理")
    @Operation(summary = "新增分类")
    public Result add(@RequestBody ServiceCategoryDTO serviceCategoryDTO){
        log.info("新增分类：{}", serviceCategoryDTO);
        categoryService.add(serviceCategoryDTO);
        return Result.success();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    @Tag(name = "分类管理")
    @Operation(summary = "根据ID删除分类")
    public Result delete(@RequestParam Long id){
        log.info("删除分类：{}", id);
        categoryService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Tag(name = "分类管理")
    @Operation(summary = "根据ID查询分类")
    public Result<ServiceCategoryVO> getByCategoryId(@PathVariable Long id){
        log.info("根据ID查询分类：{}", id);
        ServiceCategoryVO serviceCategoryVO = categoryService.getByCategoryId(id);
        return Result.success(serviceCategoryVO);
    }


}
