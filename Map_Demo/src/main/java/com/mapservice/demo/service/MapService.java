package com.mapservice.demo.service;

import org.json.simple.JSONObject;

import java.util.Map;

// MapService의 기본 구조 확립
public interface MapService {
    Map<String, String> getAddressInfo(String address);
    JSONObject getKeywordInfo(String keyword);
}