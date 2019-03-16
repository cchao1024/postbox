package com.cchao.pinbox.service;

import com.cchao.pinbox.bean.req.PageDTO;
import com.cchao.pinbox.bean.req.post.CommentDTO;
import com.cchao.pinbox.bean.resp.RespBean;
import com.cchao.pinbox.bean.resp.post.CommentVO;
import com.cchao.pinbox.bean.resp.post.ReplyVO;
import com.cchao.pinbox.constant.enums.Results;
import com.cchao.pinbox.dao.Comment;
import com.cchao.pinbox.dao.Post;
import com.cchao.pinbox.dao.User;
import com.cchao.pinbox.exception.CommonException;
import com.cchao.pinbox.repository.CommentRepository;
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
public class CommentService {

    @Autowired
    CommentRepository mCommentRepository;
    @Autowired
    PostService mPostService;
    @Autowired
    ReplyService mReplyService;
    @Autowired
    UserService mUserService;

    public Comment findById(long id) {
        return mCommentRepository.getOne(id);
    }

    /**
     * 新的 评论
     */
    public RespBean CommentNew(CommentDTO dto) {
        Post post = mPostService.findById(dto.getPostId());
        // post 评论+1
        mPostService.reviewPost(dto.getPostId());

        Comment comment = new Comment();
        BeanUtils.copyProperties(dto, comment);

        comment.setPostUserId(post.getUserId())
                .setCommentUserId(SecurityHelper.getUserId());

        mCommentRepository.save(comment);
        return RespBean.suc();
    }

    /**
     * 获取评论下的 部分回复
     */
    public Page<CommentVO> findCommentVoByPost(long posId, PageDTO dto) {
        // 拿到comment 分页
        Page<Comment> commentPage = mCommentRepository.findByPostId(posId, dto.toPageable());
        // 转化成 VO
        Page<CommentVO> result = commentPage.map(comment -> {
            // 获取用户信息
            User postUser = mUserService.findUserById(comment.getPostUserId());
            User commentUser = mUserService.findUserById(comment.getCommentUserId());

            // 封装 CommentVo
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);

            // 获取 replyVo
            Page<ReplyVO> replyVO = mReplyService.findReplyVoByComment(comment.getId(), dto);
            commentVO.setPostId(postUser.getId())
                    .setPostUserAvatar(postUser.getAvatar())
                    .setPostUserName(postUser.getNickName())

                    // comment
                    .setCommentUserAvatar(commentUser.getAvatar())
                    .setCommentUserId(commentUser.getId())
                    .setCommentUserName(commentUser.getNickName())

                    // list reply
                    .setList(replyVO.getContent())
                    .setCurPage(dto.getPage())
                    .setTotalPage(replyVO.getTotalPages());
            return commentVO;
        });
        return result;
    }

    public Page<Comment> getCommentList(PageDTO dto) {
        return mCommentRepository.findAll(dto.toPageable());
    }

    /**
     * 添加喜欢，同时增长用户的like数量
     *
     * @param id id
     */
    public RespBean likeComment(Long id) {
        Optional<Comment> optional = mCommentRepository.findById(id);
        if (optional.isPresent()) {
            Comment comment = optional.get();
            mCommentRepository.save(comment.increaseLike());

            mUserService.increaseLike(comment.getCommentUserId());
            return RespBean.suc();
        } else {
            throw CommonException.of(Results.UN_EXIST_COMMENT);
        }
    }

    public RespBean increaseReview(Long id) {
        Optional<Comment> optional = mCommentRepository.findById(id);
        if (optional.isPresent()) {
            Comment comment = optional.get();
            mCommentRepository.save(comment.increaseReview());

            return RespBean.suc();
        } else {
            throw CommonException.of(Results.UN_EXIST_COMMENT);
        }
    }
}
