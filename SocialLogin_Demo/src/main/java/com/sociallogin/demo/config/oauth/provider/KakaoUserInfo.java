package com.sociallogin.demo.config.oauth.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KakaoUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes) {
        Map<String, Object> temp = (Map) attributes.get("kakao_account");
        this.attributes = new HashMap<>();
        this.attributes.put("id", attributes.get("id"));
        this.attributes.put("email", temp.get("email"));
        this.attributes.put("gender", temp.get("gender"));
        this.attributes.put("name", ((Map) temp.get("profile")).get("nickname"));
    }

    @Override
    public String getProviderId() {
        return attributes.get("id") + "";
    }

    @Override
    public String getName() {
        return attributes.get("name") + "";
    }

    @Override
    public String getGender() {
        String gender = attributes.get("gender") + "";
        if (gender.equals("male")) {
            return "M";
        } else if (gender.equals("female")) {
            return "F";
        } else {
            return "U";
        }
    }

    @Override
    public String getEmail() {
        return attributes.get("email") + "";
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

}
