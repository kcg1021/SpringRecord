<%@ page pageEncoding="utf-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>지도 API 테스트</title>
</head>
<body>
<h1>네이버 Maps API</h1>
<h2>주소검색</h2>
<form action="/naver/address/request" method="get">
    <input type="text" name="address" placeholder="주소" />
    <button>네이버 주소검색</button>
</form>
<h2>키워드검색</h2>
<form action="/naver/keyword/request" method="get">
    <input type="text" name="keyword" placeholder="키워드" />
    <button>네이버 키워드검색</button>
</form>
<hr />
<h1>카카오 Maps API</h1>
<h2>주소검색</h2>
<form action="/kakao/address/request" method="get">
    <input type="text" name="address" placeholder="주소" />
    <button>카카오 주소검색</button>
</form>
<h2>키워드검색</h2>
<form action="/kakao/keyword/request" method="get">
    <input type="text" name="keyword" placeholder="키워드" />
    <button>카카오 키워드검색</button>
</form>
<hr />
</body>
</html>