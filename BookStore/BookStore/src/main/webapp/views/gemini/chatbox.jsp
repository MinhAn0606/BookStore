<%-- 
    Document   : gemini
    Created on : Nov 1, 2025, 6:40:05 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/views/gemini/chatbox.css"/>
    </head>
    <body>

        <!-- Chatbot Floating Button -->
        <div id="chatbot-container">
            <button id="chat-toggle-btn">
                💬
            </button>

            <div id="chatbox" class="hidden">
                <div id="chat-header">
                    <span>Gemini Assistant</span>
                    <button id="close-chat">×</button>
                </div>

                <div id="chat-messages"></div>

                <div id="chat-input-area">
                    <input type="text" id="user-input" placeholder="Nhập tin nhắn..." />
                    <button id="send-btn">➤</button>
                </div>
            </div>
        </div>
        <script src="${pageContext.request.contextPath}/views/gemini/chatbox.js"></script>
    </body>
</html>
