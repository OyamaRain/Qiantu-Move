package com.hotaru.service.admin;

import com.hotaru.dto.admin.MoverDTO;
import com.hotaru.dto.admin.MoverPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.admin.MoverVO;

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
