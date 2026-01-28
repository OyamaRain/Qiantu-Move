package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.admin.MoverPageQueryDTO;
import com.hotaru.entity.Mover;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface MoverMapper {
    Page<Mover> pageQuery(MoverPageQueryDTO moverPageQueryDTO);

    @Select("select * from mover where id = #{id}")
    Mover getById(Long id);

    void update(Mover mover);

    List<Mover> findMover(Mover mover);

    List<Mover> findMovers();

    void updateRating(Long moverId, BigDecimal newRating, int orderCount);
}
