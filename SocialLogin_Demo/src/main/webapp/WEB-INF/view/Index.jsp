<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>로그인 API 연동</title>
    </head>
    <body>
        <h1>로그인 API 연동</h1>
        <a href="${pageContext.request.contextPath}/login">로그인</a>
        <a href="/join">회원가입</a>

        <c:if test="${model != null}">
            <h1>연동 로그인 정보</h1>
            <p>Provider(연동): ${model.get("provider")}</p>
            <p>이름: ${model.name}</p>
            <p>성별: ${model.gender}</p>
            <p>이메일: ${model.email}</p>
            <p>권한: ${model.role}</p>
        </c:if>
    </body>
</html>