package com.ini.book.springboot.web;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

//    @Test
//    public void helloDo가_리턴된다() throws Exception {
//        String name = "hello";
//        int amount = 1000;
//
//        mvc.perform(get("/hello/dto")
//                .param("name", name)
//                .param("amount", String.valueOf(amount))) // String 만 허용
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name", is(name))) // jsonPath : JSON 응답값을 필드별로 검증. $를 기준으로 필드명 명시
//                .andExpect(jsonPath("$.amount", is(amount)));
//    }

}
