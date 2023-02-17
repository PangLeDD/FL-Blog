package com.lei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lei.domain.entity.Link;
import org.apache.ibatis.annotations.Mapper;

/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2022-05-31 21:28:58
 */
@Mapper
public interface LinkMapper extends BaseMapper<Link> {

}

