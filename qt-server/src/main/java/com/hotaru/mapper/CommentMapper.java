package com.hotaru.mapper;

import com.github.pagehelper.Page;
import com.hotaru.dto.user.CommentPageQueryDTO;
import com.hotaru.entity.Order;
import com.hotaru.entity.OrderComment;
import com.hotaru.vo.user.CommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {
    Page<CommentVO> PageQuery(CommentPageQueryDTO commentPageQueryDTO);
}
