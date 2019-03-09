package com.cchao.pinbox.bean.req;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author : cchao
 * @version 2019-03-09
 */
@Data
@Accessors(chain = true)
public class PageDTO {
    int page;
    int pageSize;

    public Pageable toPageable() {
        return PageRequest.of(page, pageSize);
    }
}
