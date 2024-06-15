package com.ini.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Entity 클래스와 기본 Entity Repository 는 함께 위치해야 한다.
 * (현 클래스는 Entity 클래스로 DB 접근하게 해줄 클래스)
 * 프로젝트 규모가 커지면 도메인별로 프로젝트를 분리해야 하며, 이 때 Entity 와 기본 Repository 는 함께 움직여야 하므로
 * 도메인 패키지에서 함께 관리한다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {

    /*
        SpringDataJPA 에서 제공하지 않는 메서드는 다음과 같이 쿼리로 작성해도 된다.
     */
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}