package com.cchao.pinbox.bean.req.post;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@Data
@Accessors(chain = true)
public class PostDTO {

    @Length(min = 5, max = 1024, message = "大于于5，小于1024")
    String content;

    String images;
    String tags;
}
