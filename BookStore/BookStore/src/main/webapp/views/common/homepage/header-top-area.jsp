<%-- 
    Document   : header-top-area
    Created on : Oct 26, 2025, 3:52:17 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="header-top-area">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 col-md-6 col-12">
                <div class="language-area">
                    <ul>
                        <li><img src="${pageContext.request.contextPath}/views/img/flag/1.jpg" alt="flag" /><a href="#">English<i class="fa fa-angle-down"></i></a>
                            <div class="header-sub">
                                <ul>
                                    <li><a href="#"><img src="${pageContext.request.contextPath}/views/img/flag/2.jpg" alt="flag" />france</a></li>
                                    <li><a href="#"><img src="${pageContext.request.contextPath}/views/img/flag/3.jpg" alt="flag" />croatia</a></li>
                                </ul>
                            </div>
                        </li>
                        <li><a href="#">USD $<i class="fa fa-angle-down"></i></a>
                            <div class="header-sub dolor">
                                <ul>
                                    <li><a href="#">EUR €</a></li>
                                    <li><a href="#">USD $</a></li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-6 col-md-6 col-12">
                <div class="account-area text-end">
                    <ul>
                        <!-- Khi đã đăng nhập -->
                        <c:if test="${username != null}">
                            <!-- Người dùng thường -->
                            <c:if test="${fn:toLowerCase(username.getRole()) == 'user'}">
                                <li>
                                    <a href="${pageContext.request.contextPath}/dashboard">My Account</a>
                                </li>
                            </c:if>

                            <!-- Quản trị viên -->
                            <c:if test="${fn:toLowerCase(username.getRole()) == 'admin'}">
                                <li>
                                    <a href="${pageContext.request.contextPath}/admin/dashboard">Admin Panel</a>
                                </li>
                            </c:if>
                        </c:if>

                        <!-- Link checkout (hiện cho mọi đối tượng) -->
                        <li><a href="checkout.html">Checkout</a></li>

                        <!-- Khi chưa đăng nhập -->
                        <c:if test="${username == null}">
                            <li><a href="authen?action=login">Sign in</a></li>
                            <li><a href="authen?action=sign-up">Sign up</a></li>
                            </c:if>

                        <!-- Khi đã đăng nhập -->
                        <c:if test="${username != null}">
                            <li><a href="${pageContext.request.contextPath}/authen?action=log-out">Sign Out</a></li>
                            </c:if>
                    </ul>

                </div>
            </div>
        </div>
    </div>
</div>
