package com.ini.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
스프링 시큐리티에선 권한 코드 앞에 항상 ROLE_ 을 붙여야 한다.
 */
@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
