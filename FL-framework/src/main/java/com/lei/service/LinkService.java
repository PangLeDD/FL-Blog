package com.lei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.domain.entity.Link;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.LinkVo;


/**
 * 友链(Link)表服务接口
 *
 * @author 是大大大橙子噢
 * @since 2022-05-31 21:29:00
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    ResponseResult getLinkList(Integer pageNum, Integer pageSize, LinkVo linkVo);

    ResponseResult addLink(LinkVo linkVo);

    ResponseResult getLinkById(Long id);

    ResponseResult updateLink(LinkVo linkVo);

    ResponseResult deleteLink(Long id);
}

