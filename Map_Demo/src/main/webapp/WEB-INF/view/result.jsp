<%@ page pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <meta charset="utf-8">
        <title>지도 결과</title>
    </head>
    <body>
        <h1>지도 결과</h1>
        <hr />
        <p>사용된 Maps API: ${result['use_api']}</p>
        <c:choose>

            <c:when test = "${result['use_api'] eq 'Naver Maps - Address'}">
                <!-- Naver Maps API -->
                <c:if test="${result['exception'] eq null}" var="res">
                    <div id="map" style="width: 500px; height: 400px;"></div>
                    <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${result['api_key']}"></script>
                    <script>
                        var mapOptions = {
                            center: new naver.maps.LatLng(
                                "${result['result_y']}",
                                "${result['result_x']}"
                            ),
                            zoom: 17,
                        };

                        var map = new naver.maps.Map('map', mapOptions);
                    </script>
                </c:if>
                <c:if test="${res == false}">
                    지도를 표시할 수 없습니다.
                    메시지: ${result['exception']}
                </c:if>
            </c:when>

            <c:when test = "${result['use_api'] eq 'Kakao Maps - Address' or result['use_api'] eq 'Kakao Maps - Keyword'}">
                <c:if test="${result['exception'] eq null}" var="res">
                    <div id="map" style="width: 500px; height: 400px;"></div>
                    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=${result['api_key']}&libraries=services"></script>
                    <script>
                        // 지도 DOM 영역
                        var container = document.getElementById('map');
                        var mapCoord = new kakao.maps.LatLng(
                            "${result['result_y']}",
                            "${result['result_x']}"
                        );
                        var options = { //지도를 생성할 때 필요한 기본 옵션
                            center: mapCoord, //지도의 중심좌표
                            level: 3 //지도의 레벨(확대, 축소 정도)
                        };

                        // 지도 생성
                        var map = new kakao.maps.Map(container, options);
                    </script>
                </c:if>
                <c:if test="${res == false}">
                    지도를 표시할 수 없습니다.
                    메시지: ${result['exception']}
                </c:if>
            </c:when>

            <c:otherwise>
                오류가 발생하였습니다.<br />
                오류 메시지: ${result['exception']}
            </c:otherwise>
        </c:choose>
    </body>
</html>