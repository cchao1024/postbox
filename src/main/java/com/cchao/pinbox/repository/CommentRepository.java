package com.cchao.pinbox.repository;

import com.cchao.pinbox.dao.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : cchao
 * @version 2019-03-09
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
