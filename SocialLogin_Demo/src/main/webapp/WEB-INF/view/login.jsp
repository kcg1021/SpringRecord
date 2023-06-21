<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<head>
    <title>로그인 페이지</title>
</head>
    <body>
        <h1>로그인 페이지</h1>
        <hr />
        <form action="/loginProc" method="post">
            <input type="text" name="username" placeholder="UserName" /><br />
            <input type="password" name="password" placeholder="Password" /><br />
            <button>로그인</button><br />
            <a href="/join">회원가입</a>
        </form>

        <h1>Social Login</h1>
        <br />
        <a href="/oauth2/authorization/naver">
            <img src="/Image/naverBtn.png"
                 alt="naver" width="185px" height="50px">
        </a>
        <a href="/oauth2/authorization/kakao">
            <img src="/Image/kakaoBtn.png"
                 alt="kakao" width="185px" height="50px">
        </a>
        <br />
    </body>
</html>