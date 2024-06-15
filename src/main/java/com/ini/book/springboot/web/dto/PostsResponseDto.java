package com.ini.book.springboot.web.dto;

import com.ini.book.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;

    /**
     * @param entity
     * Entity 필드 중 일부만 사용하므로 생성자로 Entity를 받아 필드에 값을 넣는다.
     */
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
