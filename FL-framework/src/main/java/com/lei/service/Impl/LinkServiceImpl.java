package com.lei.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.constants.SystemConstants;
import com.lei.domain.entity.Category;
import com.lei.domain.entity.Link;
import com.lei.domain.entity.ResponseResult;
import com.lei.domain.vo.LinkVo;
import com.lei.domain.vo.PageVo;
import com.lei.mapper.LinkMapper;
import com.lei.mapper.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import com.lei.service.LinkService;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author 是大大大橙子噢
 * @since 2022-05-31 21:29:00
 */


@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    /**
     * @description <实现前端查询友联的功能,友联功能为已通过的友联信息 >
     * @author XiaoFan DaWang
     * @time 2022/6/1 12:51
     * @return ResponseResult
    */
    @Override
    public ResponseResult getAllLink() {

        //查询出status为通过的友联
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Link::getStatus, SystemConstants.Link_STATUS_REVIEWED_PASS);
        List<Link> linkList = list(wrapper);

        //Vo优化
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);

        return ResponseResult.okResult(linkVos);
    }

    @Override
    public ResponseResult getLinkList(Integer pageNum, Integer pageSize, LinkVo linkVo) {
        LambdaQueryWrapper<Link> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(linkVo.getName()),Link::getName,linkVo.getName());
        wrapper.eq(linkVo.getStatus()!=null,Link::getStatus,linkVo.getStatus());
        Page<Link> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);
        return ResponseResult.okResult(new PageVo(BeanCopyUtils.copyBeanList(page.getRecords(),LinkVo.class),page.getTotal()));
    }

    @Override
    public ResponseResult addLink(LinkVo linkVo) {
        if (ObjectUtils.isEmpty(linkVo)) {
            return ResponseResult.errorResult(250,"内容不能为空！");
        }
        save(BeanCopyUtils.copyBean(linkVo, Link.class));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLinkById(Long id) {
        Link link = getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(link,LinkVo.class));

    }

    @Override
    public ResponseResult updateLink(LinkVo linkVo) {
        if (ObjectUtils.isEmpty(linkVo)) {
            return ResponseResult.errorResult(250,"内容不能为空！");
        }
        updateById(BeanCopyUtils.copyBean(linkVo,Link.class));
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteLink(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }
}

