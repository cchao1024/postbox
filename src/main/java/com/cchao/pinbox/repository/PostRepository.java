package com.cchao.pinbox.repository;

import com.cchao.pinbox.dao.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : cchao
 * @version 2019-03-09
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
