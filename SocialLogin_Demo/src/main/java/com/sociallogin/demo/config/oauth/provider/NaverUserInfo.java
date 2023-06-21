package com.sociallogin.demo.config.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        attributes.forEach((key, value) -> {
            System.out.println(key + " -> " + value);
        });
        this.attributes = attributes;
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
    public String getEmail() {
        return attributes.get("email") + "";
    }

    @Override
    public String getGender() {
        return attributes.get("gender") + "";
    }

    @Override
    public String getProvider() {
        return "naver";
    }

}
