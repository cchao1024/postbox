package com.cchao.pinbox.service;

import com.cchao.pinbox.bean.req.PageDTO;
import com.cchao.pinbox.bean.req.post.ReplyDTO;
import com.cchao.pinbox.bean.resp.RespBean;
import com.cchao.pinbox.constant.enums.Results;
import com.cchao.pinbox.dao.Comment;
import com.cchao.pinbox.dao.Reply;
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

    public RespBean ReplyNew(ReplyDTO dto) {
        Comment comment = mCommentService.findById(dto.getCommentId());

        Reply reply = new Reply();
        BeanUtils.copyProperties(dto, reply);

        reply.setPostId(comment.getPostId())
                .setCommentUserId(comment.getCommentUserId())
                .setCommentUserName(comment.getCommentUserName())
                .setReplyUserId(SecurityHelper.getUserId())
                .setReplyUserName(SecurityHelper.getUserName());

        mReplyRepository.save(reply);
        return RespBean.suc();
    }

    public Page<Reply> getReplyList(PageDTO dto) {
        return mReplyRepository.findAll(dto.toPageable());
    }

    /**
     * 添加喜欢，同时增长用户的like数量
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
