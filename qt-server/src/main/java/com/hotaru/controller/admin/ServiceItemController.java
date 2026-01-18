package com.hotaru.controller.admin;

import com.hotaru.annotation.Log;
import com.hotaru.constant.StatusConstant;
import com.hotaru.dto.ServiceItemDTO;
import com.hotaru.dto.ServiceItemPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.ServiceItemService;
import com.hotaru.vo.ServiceItemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/serviceItem")
@Slf4j
public class ServiceItemController {

    @Autowired
    private ServiceItemService serviceItemService;

    @GetMapping("/page")
    @Tag(name = "服务项管理")
    @Operation(summary = "服务分页查询")
    public Result<PageResult> page(@ParameterObject ServiceItemPageQueryDTO serviceItemPageQueryDTO){
        log.info("服务分页查询:{}",serviceItemPageQueryDTO);
        PageResult pageResult = serviceItemService.pageQuery(serviceItemPageQueryDTO);
        return Result.success(pageResult);
    }

    @Log("修改服务项")
    @PutMapping
    @Tag(name = "服务项管理")
    @Operation(summary = "修改服务项")
    public Result update(@RequestBody ServiceItemDTO serviceItemDTO){
        log.info("修改服务:{}",serviceItemDTO);
        serviceItemService.update(serviceItemDTO);
        return Result.success();
    }

    @Log("批量删除服务项")
    @DeleteMapping
    @Tag(name = "服务项管理")
    @Operation(summary = "批量删除服务项")
    public Result delete(@RequestParam("ids") List<Long> ids){
        log.info("批量删除服务:{}",ids);
        serviceItemService.delete(ids);
        return Result.success();
    }

    @Log("开启或关闭服务项")
    @PostMapping("/status/{status}")
    @Tag(name = "服务项管理")
    @Operation(summary = "开启或关闭服务项")
    public Result EnableOrDisable(@PathVariable Integer status, @RequestParam Long id) {
        log.info("服务ID{}状态:{}", id, status == StatusConstant.ENABLE ? "开启" : "关闭");
        serviceItemService.EnableOrDisable(status, id);
        return Result.success();
    }

    @Log("新增服务项")
    @PostMapping
    @Tag(name = "服务项管理")
    @Operation(summary = "新增服务项")
    public Result add(@RequestBody ServiceItemDTO serviceItemDTO) {
        log.info("新增服务:{}", serviceItemDTO);
        serviceItemService.add(serviceItemDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Tag(name = "服务项管理")
    @Operation(summary = "根据ID查询服务项")
    public Result<ServiceItemVO> getById(@PathVariable Long id){
        log.info("根据ID查询服务:{}",id);
        ServiceItemVO serviceItemVO = serviceItemService.getById(id);
        return Result.success(serviceItemVO);
    }



}
