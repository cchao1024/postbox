package com.cchao.pinbox.service;

import com.cchao.pinbox.bean.req.PageDTO;
import com.cchao.pinbox.bean.req.post.PostDTO;
import com.cchao.pinbox.bean.resp.RespBean;
import com.cchao.pinbox.constant.enums.Results;
import com.cchao.pinbox.dao.Post;
import com.cchao.pinbox.exception.CommonException;
import com.cchao.pinbox.repository.PostRepository;
import com.cchao.pinbox.security.SecurityHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@Service
public class PostService {

    @Autowired
    PostRepository mPostRepository;
    @Autowired
    UserService mUserService;

    public Post findById(long id) {
        return mPostRepository.getOne(id);
    }

    public RespBean postNew(PostDTO dto) {

        Post post = new Post();
        BeanUtils.copyProperties(dto, post);

        post.setUserId(SecurityHelper.getUserId());
        post.setUserName(SecurityHelper.getUserName());

        mPostRepository.save(post);
        return RespBean.suc();
    }

    public Page<Post> getPostList(PageDTO dto) {
        return mPostRepository.findAll(dto.toPageable());
    }

    /**
     * 添加喜欢，同时增长用户的like数量
     * @param id id
     */
    public RespBean likePost(Long id) {
        Optional<Post> optional = mPostRepository.findById(id);
        if (optional.isPresent()) {
            Post post = optional.get();
            mPostRepository.save(post.increaseLike());

            // 用户 like +1
            mUserService.increaseLike(post.getUserId());
            return RespBean.suc();
        } else {
            throw CommonException.of(Results.UN_EXIST_POST);
        }
    }
}
