package com.hotaru.service.admin;

import com.hotaru.dto.admin.MoverApplyPageQueryDTO;
import com.hotaru.dto.admin.MoverApplyRejectReasonDTO;
import com.hotaru.dto.admin.MoverDTO;
import com.hotaru.dto.admin.MoverPageQueryDTO;
import com.hotaru.result.PageResult;
import com.hotaru.vo.admin.MoverVO;

public interface AdminMoverService {
    // 分页查询
    PageResult pageQuery(MoverPageQueryDTO moverPageQueryDTO);

    // 启用或禁用
    void EnableOrDisable(Integer status, Long id);

    // 根据ID查询搬家师傅信息
    MoverVO getMoverById(Long id);

    // 编辑搬家师傅信息
    void update(MoverDTO moverDTO);

    // 分页查询搬家师傅申请信息
    PageResult getApplyPage(MoverApplyPageQueryDTO moverApplyPageQueryDTO);

    // 通过申请
    void approveApply(Long id);

    // 拒绝申请
    void rejectApply(Long id, MoverApplyRejectReasonDTO moverApplyRejectReasonDTO);
}
