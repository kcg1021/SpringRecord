package com.mapservice.demo.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

// 서비스 Bean 생성
@Service
public class KakaoMapService implements MapService {

    // API 요청시 필요한 RestTemplate 선언
    private final RestTemplate restTemplate = new RestTemplate();

    // application.yml에 저장한 데이터중 일치한 Key의 데이터 자동주입
    @Value("${maps.kakao.api-key}")
    private String apiKey;

    // application.yml에 저장한 데이터중 일치한 Key의 데이터 자동주입
    @Value("${maps.kakao.js-api-key}")
    private String js_apiKey;

    // application.yml에 저장한 데이터중 일치한 Key의 데이터 자동주입
    @Value("${maps.kakao.address-request-uri}")
    private String addressRequesturi;

    // application.yml에 저장한 데이터중 일치한 Key의 데이터 자동주입
    @Value("${maps.kakao.keyword-request-uri}")
    private String keywordRequesturi;

    // JSON 데이터 Parsing 메소드
    private JSONObject parseData(String requestType, String jsonData) {
        // JSONObject형태로 반환할 데이터 선언
        JSONObject resData = new JSONObject();
        // resData에 use_api 이름의 값을 'Kakao Maps - {requestType}'로 설정
        resData.put("use_api", "Kakao Maps - " + requestType);

        try {
            // JSON을 Parsing해 줄 JSONParser 선언
            JSONParser parser = new JSONParser();
            // Parser를 이용해 jsonData를 파싱 후 JSONObject 객체에 저장
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
            // Json 형식에서 documents 키로 되어 있는 결과 값이 Array기 때문에 JSONArray 객체에 저장
            JSONArray res_arr = (JSONArray) jsonObject.get("documents");

            // res_arr의 결과 개수가 1개 이상일 때
            if (res_arr.size() > 0) {
                // Json 형식의 Index 0번에 있는 데이터가 Object기 때문에 JSONObject 객체에 저장
                JSONObject document_obj = (JSONObject) res_arr.get(0);
                // document_obj에서 Json 형식의 address 키로 되어 있는 데이터가 Object기 때문에 JSONObject 객체에 저장
                JSONObject address_obj = (JSONObject) document_obj.get("address");
                // Json 형식의 road_address 키로 되어 있는 데이터가 Object기 때문에 JSONObject 객체에 저장
                JSONObject road_address_obj = (JSONObject) document_obj.get("road_address");
                // resData에 result_x 이름의 값을 address_obj에서 'x' 키로 되어있는 데이터로 설정
                resData.put("result_x", address_obj.get("x"));
                // resData에 result_y 이름의 값을 address_obj에서 'y' 키로 되어있는 데이터로 설정
                resData.put("result_y", address_obj.get("y"));
                // resData에 address 이름의 값을 address_obj에서 'address_name' 키로 되어있는 데이터로 설정
                resData.put("address", address_obj.get("address_name"));
                // resData에 road_address 이름의 값을 road_address_obj에서 'address_name' 키로 되어있는 데이터로 설정
                resData.put("road_address", road_address_obj.get("address_name"));
                // resData에 building_name 이름의 값을 road_address_obj에서 'building_name' 키로 되어있는 데이터로 설정
                resData.put("building_name", road_address_obj.get("building_name"));
                // resData에 api_key 이름의 값을 '{js_apiKey}'로 설정
                resData.put("api_key", js_apiKey);
            } else {
                // resData에 exception 이름의 값을 '검색결과가 없습니다.'로 설정
                resData.put("exception", "검색 결과가 없습니다.");
            }
        } catch (Exception e) {
            // resData에 exception 이름의 값을 'JSON Parsing 도중 오류'로 설정
            resData.put("exception", "JSON Parsing 도중 오류");
            // 오류 데이터를 Console에 출력
            System.out.println("JSON Parsing 도중 오류가 발생하였습니다." + e);
        }
        // 결과 데이터 반환
        return resData;
    }

    // API 요청 데이터 생성 후 요청 -> 결과값 반환 메소드
    private ResponseEntity<String> getResponse(String uri, String query) {
        // Http Header에 붙일 수 있는 객체 선언
        HttpHeaders httpHeaders = new HttpHeaders();
        // ContentType을 Apllication/JSON 으로 설정
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // Header에서 Authorization 이름의 값을 'KakaoAK {apiKey}'로 설정
        httpHeaders.add("Authorization", "KakaoAK " + apiKey);

        // HttpHeader를 Entity 데이터로 묶음
        HttpEntity<?> headers = new HttpEntity<>(httpHeaders);

        // 쿼리 요청 URI 생성
        URI url = URI.create(uri + "?query=" + URLEncoder.encode(query, StandardCharsets.UTF_8));
        // RestTemplate를 이용해서 데이터 요청 반환형은 String으로 지정
        return restTemplate.exchange(url, HttpMethod.GET, headers, String.class);
    }

    // 주소 -> Data 반환 메소드
    public JSONObject getAddressInfo(String address) {
        // API 요청 메소드를 이용해 요청한 결과값을 Response로 받아옴
        ResponseEntity<String> response = getResponse(addressRequesturi, address);
        // response 변수의 Body 데이터를 JSON Parsing할 수 있게 메소드에 주입 후 호출
        return parseData("Address", response.getBody());
    }

    // 키워드 -> Data 반환 메소드
    public JSONObject getKeywordInfo(String keyword) {
        // API 요청 메소드를 이용해 요청한 결과값을 Response로 받아옴
        ResponseEntity<String> response = getResponse(keywordRequesturi, keyword);
        // response 변수의 Body 데이터를 JSON Parsing할 수 있게 메소드에 주입 후 호출
        return parseData("Keyword", response.getBody());
    }
}
