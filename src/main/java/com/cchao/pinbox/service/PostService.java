package com.cchao.pinbox.service;

import com.cchao.pinbox.bean.req.PageDTO;
import com.cchao.pinbox.bean.req.post.PostDTO;
import com.cchao.pinbox.bean.resp.RespBean;
import com.cchao.pinbox.bean.resp.post.CommentVO;
import com.cchao.pinbox.bean.resp.post.PostListVO;
import com.cchao.pinbox.bean.resp.post.PostVO;
import com.cchao.pinbox.constant.enums.Results;
import com.cchao.pinbox.dao.Post;
import com.cchao.pinbox.dao.User;
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
    @Autowired
    CommentService mCommentService;

    public Post findById(long id) {
        return mPostRepository.getOne(id);
    }

    public RespBean postNew(PostDTO dto) {

        Post post = new Post();
        BeanUtils.copyProperties(dto, post);

        post.setUserId(SecurityHelper.getUserId());

        mPostRepository.save(post);
        return RespBean.suc();
    }

    /**
     * 获取评论下的 部分回复
     *
     * @param postId postId
     * @param dto    page
     */
    public PostVO findPostVo(long postId, PageDTO dto) {
        Optional<Post> optional = mPostRepository.findById(postId);
        if (!optional.isPresent()) {
            throw CommonException.of(Results.UN_EXIST_POST);
        }
        Post post = optional.get();
        // copy到vo
        PostVO postVO = new PostVO();
        BeanUtils.copyProperties(post, postVO);

        // 获取用户信息
        User postUser = mUserService.findUserById(post.getUserId());

        // 获取 CommentVo，
        Page<CommentVO> commentVO = mCommentService.findCommentVoByPost(post.getId(), dto);

        postVO.setPostUserId(postUser.getId())
                .setPostUserAvatar(postUser.getAvatar())
                .setPostUserName(postUser.getNickName())
                // list comment
                .setList(commentVO.getContent())
                .setCurPage(dto.getPage())
                .setTotalPage(commentVO.getTotalPages());
        return postVO;
    }

    public Page<PostVO> getIndex(PageDTO dto) {
        Page<PostVO> result = mPostRepository.findAll(dto.toPageable()).map(post -> {
            return findPostVo(post.getId(), PageDTO.of(0, 10));
        });
        return result;
    }

    public Page<PostListVO> findPostList(PageDTO dto) {
        Page<PostListVO> result = mPostRepository.findAll(dto.toPageable()).map(post -> {
            User user = mUserService.findUserById(post.getUserId());

            // postListVO
            PostListVO postListVO = new PostListVO();
            BeanUtils.copyProperties(post, postListVO);
            postListVO.setPostUserAvatar(user.getAvatar())
                    .setPostUserName(user.getNickName())
                    .setPostUserId(user.getId());

            return postListVO;
        });
        return result;
    }

    /**
     * 添加喜欢，同时增长用户的like数量
     *
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

    /**
     * 添加评论
     *
     * @param id id
     */
    public RespBean reviewPost(Long id) {
        Optional<Post> optional = mPostRepository.findById(id);
        if (optional.isPresent()) {
            Post post = optional.get();
            mPostRepository.save(post.increaseReview());
            return RespBean.suc();
        } else {
            throw CommonException.of(Results.UN_EXIST_POST);
        }
    }
}
