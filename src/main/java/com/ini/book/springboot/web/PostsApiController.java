package com.ini.book.springboot.web;

import com.ini.book.springboot.service.posts.PostsService;
import com.ini.book.springboot.web.dto.PostsResponseDto;
import com.ini.book.springboot.web.dto.PostsSaveRequestDto;
import com.ini.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    /**
     * 빈 주입 방식
     * 1. Autowired
     * 2. setter
     * 3. 생성자
     *
     * 생성자를 통해 주입받는 방식을 가장 추천하며, 여기서는 @RequiredArgsConstructor 를 통해 생성자를 생성하고 있다.
     * 롬복 어노테이션을 사용함으로써 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속해서 수정하는 번거로움을 덜 수 있다.
     */
    private final PostsService postsService;

//    @PutMapping("/api/v1/posts")
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }
}
