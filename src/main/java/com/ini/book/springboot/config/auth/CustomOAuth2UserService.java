package com.ini.book.springboot.config.auth;

import com.ini.book.springboot.config.auth.dto.OAuthAttributes;
import com.ini.book.springboot.config.auth.dto.SessionUser;
import com.ini.book.springboot.domain.user.User;
import com.ini.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        /*
         registrationId : 현재 로그인 진행 중인 서비스를 구분하는 코드(ex. 네이버 로그인인지? 구글 로그인인지?)
         userNameAttributeName : OAuth2 로그인 진행 시 키가 되는 필드 값. (=Primary Key) 구글의 기본 코드는 "sub"(네이버, 카카오 등은 기본 코드 제공 X)
         */
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        /*
         OAuthAttributes : OAuth2UserService 를 통해 가져온 OAuth2User 의 attriubte 를 담을 클래스
         */
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        /*
         SessionUser : 세션에 사용자 정보를 저장하기 위한 Dto 클래스

         ## 왜 User 클래스를 쓰지 않는가?
         먼저 User 클래스에는 직렬화가 구현되어 있지 않다. 그렇다고 엔티티 클래스에 직렬화 코드를 넣는다면,
         @OneToMany 나 @ManyToMany 등 자식 엔티티를 갖고 있는 경우 직렬화 대상이 이들도 포함되기 때문에
         성능 이슈나 부수 효과가 발생할 확률이 높다.
         따라서 직렬화 기능을 가진 세션 Dto 를 하나 추가로 만드는 것이 운영 및 유지보수 때 많은 도움이 된다.
         */
        User user = saveOrUpdate(attributes);
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    /*
     구글 사용자 정보가 업데이트 되었을 때를 대비하여 update 기능도 같이 구현하여 User 엔티티에 반영되도록 함
     */
    private User saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}
