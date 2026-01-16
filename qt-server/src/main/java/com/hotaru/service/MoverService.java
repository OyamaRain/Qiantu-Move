package com.hotaru.service;

import com.hotaru.dto.MoverDTO;
import com.hotaru.dto.MoverPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.MoverVO;

public interface MoverService {
    // 分页查询
    PageResult pageQuery(MoverPageQueryDTO moverPageQueryDTO);

    // 启用或禁用
    void EnableOrDisable(Integer status, Long id);

    // 根据ID查询搬家师傅信息
    MoverVO getMoverById(Long id);

    // 编辑搬家师傅信息
    void update(MoverDTO moverDTO);
}
