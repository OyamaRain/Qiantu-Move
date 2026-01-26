package com.hotaru.controller.admin;

import com.hotaru.annotation.Log;
import com.hotaru.dto.admin.MoverDTO;
import com.hotaru.dto.admin.MoverPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.result.Result;
import com.hotaru.service.admin.MoverService;
import com.hotaru.vo.admin.MoverVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/mover")
@Slf4j
public class AdminMoverController {

    @Autowired
    private MoverService moverService;

    @GetMapping("/page")
    @Tag(name = "搬家师傅管理")
    @Operation(summary = "搬家师傅分页查询")
    public Result<PageResult> page(@ParameterObject MoverPageQueryDTO moverPageQueryDTO) {
        log.info("分页查询搬家师傅信息:{}", moverPageQueryDTO);
        PageResult pageResult = moverService.pageQuery(moverPageQueryDTO);
        return Result.success(pageResult);
    }

    @Log("更改搬家师傅账号状态")
    @PostMapping("/status/{status}")
    @Tag(name = "搬家师傅管理")
    @Operation(summary = "更改搬家师傅账号状态")
    public Result EnableOrDisable(@PathVariable Integer status, @RequestParam Long id) {
        String statusText = switch (status) {
            case 0 -> "禁用";
            case 1 -> "空闲";
            case 2 -> "忙碌";
            default -> "未知";
        };
        log.info("搬家师傅ID:{},账号更改状态:{}", id, statusText);
        moverService.EnableOrDisable(status, id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Tag(name = "搬家师傅管理")
    @Operation(summary = "根据ID查询搬家师傅信息")
    public Result<MoverVO> getMoverById (@PathVariable Long id) {
        log.info("查询搬家师傅{}的信息", id);
        MoverVO moverVO = moverService.getMoverById(id);
        return Result.success(moverVO);
    }

    @Log("编辑搬家师傅信息")
    @PutMapping
    @Tag(name = "搬家师傅管理")
    @Operation(summary = "编辑搬家师傅信息")
    public Result update(@RequestBody MoverDTO moverDTO) {
        log.info("编辑搬家师傅信息:{}", moverDTO);
        moverService.update(moverDTO);
        return Result.success();
    }



}
