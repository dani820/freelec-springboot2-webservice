package com.ini.book.springboot.domain.posts;

import com.ini.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 어노테이션 순서 Tip
 * 주요 어노테이션을 클래스에 가깝게 둔다.
 *
 * 롬복은 코드를 단순화시켜주지만 필수 어노테이션은 아니므로 다음과 같이 작성
 * + 롬복이 더이상 필요없을 경우 쉽게 삭제 가능하다.
 */
@Getter // 롬복 어노테이션
@NoArgsConstructor // 롬복 어노테이션 - 기본 생성자 자동 추가
@Entity // JPA 어노테이션 - 실제 DB 테이블과 매칭될 Entity 클래스. 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름 매칭(기본값)
public class Posts extends BaseTimeEntity {

    /**
     * Entity 의 PK 는 Long 타입의 Auto_increment 지정할 것을 추천
     *
     * 주민등록번호 같은 비즈니스상 유니크 키나 여러 키를 조합한 복합키로 Pk 를 잡을 경우,
     * 1. FK 맺을 때 다른 테이블에서 복합키 전부를 갖고 있거나 중간 테이블을 하나 더 둬야 하는 상황 발생
     * 2. 인덱스에 좋지 않은 영향
     * 3. 유니크한 조건이 변경될 경우 PK 전체를 수정해야 할 수도 있다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 생성규칙
    private Long id;

    /**
     * @Column
     * 굳이 선언하지 않더라도 클래스의 필드는 모두 컬럼이 된다.
     * 사용하는 이유는 기본값 이외 추가로 변경이 필요한 옵션이 있을 때 사용하기 위함이다.
     * ex) length = 500 -> 문자열 기본값(VARCHAR(255))을 변경하고 싶을 때,
     *     columnDefinition = "TEXT" -> 타입을 "TEXT"로 변경하고 싶을 때 사용
     */
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 해당 클래스의 빌더 패턴 클래스 생성, 생성자 상단에 선언 시 생성자에 포함된 필드만 빌더에 포함
    public Posts(Long id, String title, String content, String author) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

/**
 * Setter 메서드가 없는 이유
 *
 * 게터 세터를 무작정 생성하면 해당 클래스의 인스턴스 값들이 언제 어디서 변해야하는지 코드상으로 명확한 구분을 하기가 어려워,
 * 차후 기능 변경 시 상당히 복잡해질 수 있다.
 * 때문에 Entity 클래스에서는 절대 Setter 메서드를 만들지 않으며 해당 필드의 값 변경이 필요하다면
 * 명확히 그 목적과 의도를 나타낼 수 있는 메서드를 추가해야 한다.
 */