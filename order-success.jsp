<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Thanh Toán Thành Công</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            .container {
                background-color: white;
                padding: 40px;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                text-align: center;
            }
            h2 {
                color: #28a745;
                margin-bottom: 20px;
            }
            .success-icon {
                font-size: 50px;
                color: #28a745;
                margin-bottom: 20px;
            }
            .btn-home {
                display: inline-block;
                padding: 10px 20px;
                background-color: #007bff;
                color: white;
                text-decoration: none;
                border-radius: 5px;
                margin-top: 20px;
                transition: background-color 0.3s;
            }
            .btn-home:hover {
                background-color: #0056b3;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <div class="success-icon">✅</div>
            <h2>THANH TOÁN THÀNH CÔNG!</h2>
            <p>Đơn hàng của bạn đã được ghi nhận và đang chờ xử lý.</p>
            <p>Xin cảm ơn bạn đã mua hàng tại cửa hàng của chúng tôi!</p>
            
            <a href="home.jsp" class="btn-home">Quay về Trang Chủ</a>
        </div>
    </body>
</html>