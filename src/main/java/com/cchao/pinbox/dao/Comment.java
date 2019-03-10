package com.cchao.pinbox.dao;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 一级评论
 *
 * @author : cchao
 * @version 2019-03-09
 */
@Entity
@Data
@Accessors(chain = true)
@DynamicUpdate
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    long postId;
    long postUserId;
    long commentUserId;

    String content;
    String images;

    int likeCount;
    Date createTime;
    Date updateTime;

    public Comment increaseLike() {
        likeCount++;
        return this;
    }
}
