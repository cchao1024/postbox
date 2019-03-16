package com.cchao.pinbox.controller;

import com.cchao.pinbox.bean.req.PageDTO;
import com.cchao.pinbox.bean.req.post.PostDTO;
import com.cchao.pinbox.bean.resp.RespBean;
import com.cchao.pinbox.bean.resp.RespListBean;
import com.cchao.pinbox.bean.resp.post.PostListVO;
import com.cchao.pinbox.bean.resp.post.PostVO;
import com.cchao.pinbox.service.PostService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    PostService mPostService;

    @RequestMapping("/new")
    @RequiresAuthentication
    public RespBean postNew(@Valid PostDTO params) {
        return mPostService.postNew(params);
    }

    @RequestMapping("/like")
    @RequiresAuthentication
    public RespBean like(Long id) {
        return mPostService.likePost(id);
    }

    /**
     * 获取默认第一页
     */
    @RequestMapping("/index")
    public RespListBean<PostVO> getIndex(@RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        Page<PostVO> pageObj = mPostService.getIndex(PageDTO.of(0, pageSize));
        return RespListBean.of(pageObj, 0);
    }

    @RequestMapping("/list")
    public RespListBean<PostListVO> getPostByPage(@RequestParam(value = "page", defaultValue = "1") int page
            , @RequestParam(value = "pageSize", defaultValue = "30") int pageSize) {

        Page<PostListVO> pageObj = mPostService.findPostList(PageDTO.of(page - 1, pageSize));
        return RespListBean.of(pageObj, page);
    }

    @RequestMapping("/detail")
    public RespBean getPostById(Long id) {
        PostVO postVO = mPostService.findPostVo(id, PageDTO.of(0, 10));
        return RespBean.suc(postVO);
    }
}
