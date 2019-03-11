package com.cchao.pinbox.bean.resp.post;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author : cchao
 * @version 2019-03-11
 */
@Data
@Accessors(chain = true)
public class CommentVO {

    long id;
    long postId;
    long postUserId;
    String postUserName;
    String postUserAvatar;

    long commentUserId;
    String commentUserName;
    String commentUserAvatar;

    int likeCount;
    String content;
    String images;
    Date updateTime;

    int curPage;
    int totalPage;
    List<ReplyVO> list;
}
