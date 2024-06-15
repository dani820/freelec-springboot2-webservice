package com.ini.book.springboot.service.posts;

import com.ini.book.springboot.domain.posts.Posts;
import com.ini.book.springboot.domain.posts.PostsRepository;
import com.ini.book.springboot.web.dto.PostsListResponseDto;
import com.ini.book.springboot.web.dto.PostsResponseDto;
import com.ini.book.springboot.web.dto.PostsSaveRequestDto;
import com.ini.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. Id="+ id));

        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        return new PostsResponseDto(entity);
    }

    /*
        readOnly = true 속성 부여 시 트랜잭션 범위는 유지하되 조회 기능만 남겨두어 조회 속도가 개선된다.
        따라서 등록, 수정, 삭제 기능이 전혀 없는 서비스 메서드에서 사용하는 것을 추천한다.
     */
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

}
