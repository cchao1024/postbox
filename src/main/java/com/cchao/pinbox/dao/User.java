package com.cchao.pinbox.dao;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@Accessors(chain = true)
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickName;

    private String password;
    private int gender;
    private int age;
    private int getLike;
    private Date updateTime;
    private Date createTime;

    public User increaseLike() {
        getLike++;
        return this;
    }
}
