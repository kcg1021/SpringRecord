<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<head>
    <title>회원가입 페이지</title>
</head>
<body>
    <h1>회원가입 페이지</h1>
    <hr />
    <form action="/joinProc" method="post">
        <input type="text" name="username" placeholder="UserName" /><br />
        <input type="password" name="password" placeholder="Password" /><br />
        <input type="email" name="email" placeholder="Email" /><br />
        <button>회원가입</button>
    </form>
</body>
</html>