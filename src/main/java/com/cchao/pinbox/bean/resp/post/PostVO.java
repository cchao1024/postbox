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
public class PostVO {

    long id;
    long postUserId;
    int likeCount;
    String content;
    String postUserName;
    String postUserAvatar;
    String images;
    Date updateTime;

    int curPage;
    int totalPage;
    List<CommentVO> list;
}
