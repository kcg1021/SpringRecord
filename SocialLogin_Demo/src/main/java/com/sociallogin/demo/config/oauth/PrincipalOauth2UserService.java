package com.sociallogin.demo.config.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sociallogin.demo.config.auth.PrincipalDetails;
import com.sociallogin.demo.config.oauth.provider.KakaoUserInfo;
import com.sociallogin.demo.config.oauth.provider.NaverUserInfo;
import com.sociallogin.demo.config.oauth.provider.OAuth2UserInfo;
import com.sociallogin.demo.enums.RoleType;
import com.sociallogin.demo.model.User;
import com.sociallogin.demo.repository.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); // 회원 조회
//        System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration()); // code를 통해 구성한 정보
//        System.out.println("oAuth2User : " + oAuth2User); // token을 통해 응답받은 회원정보

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();
        if (provider.equals("naver")) {
            System.out.println("[★] 네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        } else if (provider.equals("kakao")) {
            System.out.println("[★] 카카오 로그인 요청");
            Set<String> keys = oAuth2User.getAttributes().keySet();
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            System.out.println("[!] 지원하지 않는 로그인 요청");
        }

        Optional<User> userOptional = userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());

        User user;
        if (userOptional.isPresent()) {
            // 유저가 존재하면
            user = userOptional.get();
            userRepository.save(user);
        } else {
            // 유저가 존재하지 않으면
            user = User.builder()
                    .username(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .email(oAuth2UserInfo.getEmail())
                    .role(RoleType.ROLE_USER)
                    .gender(oAuth2UserInfo.getGender())
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
