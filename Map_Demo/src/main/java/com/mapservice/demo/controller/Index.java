package com.mapservice.demo.controller;

import com.mapservice.demo.service.KakaoMapService;
import com.mapservice.demo.service.NaverMapService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class Index {
    // 네이버 지도 서비스 ( NaverMapService 클래스와 연결 )
    @Autowired
    NaverMapService naverMapService;

    // 카카오 지도 서비스 ( KakaoMapService 클래스와 연결 )
    @Autowired
    KakaoMapService kakaoMapService;

    @GetMapping("")
    public String index() {
        return "Index";
    }

    @GetMapping("/naver/address/request")
    public ModelAndView request_address_naver(HttpServletRequest request) throws UnsupportedEncodingException {
        // 카카오 지도 서비스 ( 받아온 데이터의 Encoding을 UTF-8로 변환 )
        request.setCharacterEncoding("utf-8");
        // request 데이터에서 address를 받아와 NaverMapService에서 getAddressInfo 메소드에 주입 후 호출 -> 결과 값 result에 저장
        JSONObject result = naverMapService.getAddressInfo(request.getParameter("address"));
        // ModelAndView 객체를 선언하여 view는 result로 지정
        ModelAndView mav = new ModelAndView("result");
        // ModelAndView 객체에 result 변수 데이터를 result 이름으로 추가
        mav.addObject("result", result);
        // ModelAndView 반환
        return mav;
    }

    @GetMapping("/naver/keyword/request")
    public @ResponseBody ModelAndView request_keyword_naver(HttpServletRequest request) throws UnsupportedEncodingException {
        // 카카오 지도 서비스 ( 요청하는 데이터의 Encoding을 UTF-8로 변환 )
        request.setCharacterEncoding("utf-8");
        // request 데이터에서 keyword를 받아와 NaverMapService에서 getKeywordInfo 메소드에 주입 후 호출 -> 결과 값 result에 저장
        JSONObject result = naverMapService.getKeywordInfo(request.getParameter("keyword"));
        // ModelAndView 객체를 선언하여 view는 result로 지정
        ModelAndView mav = new ModelAndView("result");
        // ModelAndView 객체에 result 변수 데이터를 result 이름으로 추가
        mav.addObject("result", result);
        // ModelAndView 반환
        return mav;
    }

    @GetMapping("/kakao/address/request")
    public ModelAndView request_address_kakao(HttpServletRequest request) throws UnsupportedEncodingException {
        // 카카오 지도 서비스 ( 요청하는 데이터의 Encoding을 UTF-8로 변환 )
        request.setCharacterEncoding("utf-8");
        // request 데이터에서 address를 받아와 KakaoMapService에서 getAddressInfo 메소드에 주입 후 호출 -> 결과 값 result에 저장
        JSONObject result = kakaoMapService.getAddressInfo(request.getParameter("address"));
        // ModelAndView 객체를 선언하여 view는 result로 지정
        ModelAndView mav = new ModelAndView("result");
        // ModelAndView 객체에 result 변수 데이터를 result 이름으로 추가
        mav.addObject("result", result);
        // ModelAndView 반환
        return mav;
    }

    @GetMapping("/kakao/keyword/request")
    public @ResponseBody ModelAndView request_keyword_kakao(HttpServletRequest request) throws UnsupportedEncodingException {
        // 카카오 지도 서비스 ( 요청하는 데이터의 Encoding을 UTF-8로 변환 )
        request.setCharacterEncoding("utf-8");
        // request 데이터에서 keyword를 받아와 KakaoMapService에서 getKeywordInfo 메소드에 주입 후 호출 -> 결과 값 result에 저장
        JSONObject result = kakaoMapService.getKeywordInfo(request.getParameter("keyword"));
        // ModelAndView 객체를 선언하여 view는 result로 지정
        ModelAndView mav = new ModelAndView("result");
        // ModelAndView 객체에 result 변수 데이터를 result 이름으로 추가
        mav.addObject("result", result);
        // ModelAndView 반환
        return mav;
    }
}
