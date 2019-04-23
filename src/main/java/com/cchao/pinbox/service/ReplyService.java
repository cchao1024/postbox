package com.cchao.pinbox.service;

import com.cchao.pinbox.bean.req.PageDTO;
import com.cchao.pinbox.bean.req.post.ReplyDTO;
import com.cchao.pinbox.bean.resp.RespBean;
import com.cchao.pinbox.bean.resp.post.ReplyVO;
import com.cchao.pinbox.constant.enums.Results;
import com.cchao.pinbox.dao.Comment;
import com.cchao.pinbox.dao.Reply;
import com.cchao.pinbox.dao.User;
import com.cchao.pinbox.exception.CommonException;
import com.cchao.pinbox.repository.ReplyRepository;
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
public class ReplyService {

    @Autowired
    ReplyRepository mReplyRepository;
    @Autowired
    CommentService mCommentService;
    @Autowired
    UserService mUserService;

    /**
     * 新的回复
     */
    public RespBean ReplyNew(ReplyDTO dto) {
        Comment comment = mCommentService.findById(dto.getToId());
        // comment 评论+1
        mCommentService.increaseReview(dto.getToId());

        Reply reply = new Reply();
        BeanUtils.copyProperties(dto, reply);

        reply.setPostId(comment.getPostId())
                .setCommentUserId(comment.getCommentUserId())
                .setReplyUserId(SecurityHelper.getUserId());

        mReplyRepository.save(reply);
        return RespBean.suc();
    }

    /**
     * 获取评论下的 部分回复
     *
     * @param dto
     * @return
     */
    public Page<ReplyVO> findReplyVoByComment(long commentId, PageDTO dto) {
        // 拿到comment 分页
        Page<Reply> commentPage = mReplyRepository.findByCommentId(commentId, dto.toPageable());
        // 转化成 VO
        Page<ReplyVO> result = commentPage.map(comment -> {
            // 获取用户信息
            User replyUser = mUserService.findUserById(comment.getReplyUserId());
            User commentUser = mUserService.findUserById(comment.getCommentUserId());

            // 封装 vo
            ReplyVO replyVO = new ReplyVO();
            BeanUtils.copyProperties(comment, replyVO);
            replyVO.setReplyUserId(replyUser.getId())
                    .setCommentUserId(commentUser.getId())
                    .setCommentUserName(commentUser.getNickName());
            return replyVO;
        });
        return result;
    }

    public Page<Reply> getReplyList(PageDTO dto) {
        return mReplyRepository.findAll(dto.toPageable());
    }

    /**
     * 添加喜欢，同时增长用户的like数量
     *
     * @param id id
     */
    public RespBean likeReply(Long id) {
        Optional<Reply> optional = mReplyRepository.findById(id);
        if (optional.isPresent()) {
            Reply reply = optional.get();
            mReplyRepository.save(reply.increaseLike());

            mUserService.increaseLike(reply.getReplyUserId());
            return RespBean.suc();
        } else {
            throw CommonException.of(Results.UN_EXIST_COMMENT);
        }
    }
}
