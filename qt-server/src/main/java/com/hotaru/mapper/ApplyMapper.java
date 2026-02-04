package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.admin.MoverApplyPageQueryDTO;
import com.hotaru.entity.MoverApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ApplyMapper {
    MoverApply selectByUserId(Long userId);

    void insert(MoverApply newApply);

    @Select("select * from mover_apply where apply_status = 0 order by create_time desc")
    Page<MoverApply> pageQuery(MoverApplyPageQueryDTO moverApplyPageQueryDTO);

    void updateStatus(Long id, Integer applyStatus, String rejectReason);
}
