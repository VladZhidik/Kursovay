<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html;
    charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>main</title>

    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="locale" var="loc"/>
    <fmt:message bundle="${loc}" key="main.lable" var="lable"/>
    <fmt:message bundle="${loc}" key="main.lable.viewingDishes" var="viewingDishes"/>
    <fmt:message bundle="${loc}" key="main.lable.addDish" var="addDish"/>
    <fmt:message bundle="${loc}" key="main.lable.makeOrder" var="makeOrder"/>
    <fmt:message bundle="${loc}" key="main.lable.viewOrders" var="viewOrders"/>
    <fmt:message bundle="${loc}" key="main.lable.personalaccount" var="personalaccount"/>
    <fmt:message bundle="${loc}" key="main.lable.userList" var="userList"/>
    <fmt:message bundle="${loc}" key="main.lable.rating" var="rating"/>
    <fmt:message bundle="${loc}" key="main.lable.rating.restoran" var="ratingRestoran"/>
    <fmt:message bundle="${loc}" key="main.lable.rating.button" var="ratingButton"/>
    <fmt:message bundle="${loc}" key="main.lable.feedback" var="feedBack"/>
</head>
<body>

<jsp:include page="part/header.jsp"/>

<!-- Title Page -->
<section class="bg-title-page flex-c-m p-t-160 p-b-80 p-l-15 p-r-15"
         style="background-image: url(${pageContext.request.contextPath}/static/img/bg-title-page-02.jpg);">
    <h2 class="tit6 t-center">
        ${lable}
    </h2>

</section>
<br/><br/>

<section>
    <div class="container" id="catalog">

        <c:if test="${sessionScope.userRole == 'ADMIN'}">
            <form action="Controller" method="post">
                <a href="Controller?command=gotopersonalaccountpage" class="txt4">${personalaccount}</a><br/><br/>
                <a href="Controller?command=gotomenupage" class="txt4">${viewingDishes}</a><br/>
                <a href="Controller?command=gotoadddishpage" class="txt4">${addDish}</a><br/><br/>
                <a href="Controller?command=gotoalluserorderspage" class="txt4">${viewOrders}</a><br/><br/>
                <a href="Controller?command=gotouserspage" class="txt4">${userList}</a><br/><br/>
            </form>
            <br/>
        </c:if>
        <c:if test="${sessionScope.userRole == 'USER'}">
            <form action="Controller" method="post">
                <a href="Controller?command=gotopersonalaccountpage" class="txt4">${personalaccount}</a><br/><br/>
                <a href="Controller?command=gotomenupage" class="txt4">${viewingDishes}</a><br/>
                <a href="Controller?command=gotomakeorderpage" class="txt4">${makeOrder}</a><br/><br/>
                <a href="Controller?command=gotouserorderspage" class="txt4">${viewOrders}</a><br/><br/>
                <a href="Controller?command=gotoFeedBackPage" class="txt4">${feedBack}</a><br/><br/>
            </form>
        </c:if>
        <br><br>
        <c:if test="${sessionScope.rating != null}">
            <section>
            <span class="tit2 t-left">
                ${ratingRestoran}: ${sessionScope.rating}
            </span>
            </section>
        </c:if>

        <br><br>
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="setRating"/>
            <label>
                ${rating}
                <input type="number" name="rating"
                       min="1" max="10" pattern="\d [0-9]">
            </label>
            <button class="btn btn-success delete2" type="submit">
                ${ratingButton}
            </button>
        </form>
        <br><br>
    </div>
</section>

<jsp:include page="part/footer.jsp"/>
</body>
</html>
