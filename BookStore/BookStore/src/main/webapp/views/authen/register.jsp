<%-- 
    Document   : register
    Created on : Oct 29, 2025, 9:12:29 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">


    <!-- Mirrored from htmldemo.net/koparion/koparion/register.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 23 Feb 2024 17:30:57 GMT -->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Koparion – Book Shop HTML5 Template</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="img/favicon.png">

        <!-- all css here -->
        <!-- bootstrap v3.3.6 css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
        <!-- animate css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/animate.css">
        <!-- meanmenu css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/meanmenu.min.css">
        <!-- owl.carousel css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.carousel.css">
        <!-- font-awesome css -->
        <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/font-awesome.min.css">-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css" integrity="sha512-+L4yy6FRcDGbXJ9mPG8MT/3UCDzwR9gPeyFNMCtInsol++5m3bk2bXWKdZjvybmohrAsn3Ua5x8gfLnbE1YkOg==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!-- flexslider.css-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/flexslider.css">
        <!-- chosen.min.css-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/chosen.min.css">
        <!-- style css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
        <!-- responsive css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/responsive.css">
        <!-- modernizr css -->
        <script src="${pageContext.request.contextPath}/assets/js/vendor/modernizr-2.8.3.min.js"></script>
    </head>

    <body class="register">
        <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

        <!-- Add your site or application content here -->
        <!-- header-area-start -->
        <header>
            <!-- header-top-area-start -->
            <jsp:include page="../common/homepage/header-top-area.jsp"></jsp:include>
                <!-- header-top-area-end -->
                <!-- header-mid-area-start -->
            <jsp:include page="../common/homepage/header-mid-area.jsp"></jsp:include>
                <!-- header-mid-area-end -->
                <!-- main-menu-area-start -->
            <jsp:include page="../common/homepage/main-menu-area.jsp"></jsp:include>
                <!-- main-menu-area-end -->
                <!-- mobile-menu-area-start -->
            <jsp:include page="../common/homepage/mobile-menu-area.jsp"></jsp:include>
                <!-- mobile-menu-area-end -->
            </header>
            <!-- header-area-end -->
            <!-- breadcrumbs-area-start -->
        <jsp:include page="../common/homepage/breadcrumbs-area.jsp"></jsp:include>
            <!-- breadcrumbs-area-end -->
            <!-- user-login-area-start -->
            <div class="user-login-area mb-70">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="login-title text-center mb-30">
                                <h2>Sign Up</h2>
                                <p>doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo<br>inventore veritatis et quasi architecto beat</p>
                            </div>
                        </div>
                        <div class="offset-lg-2 col-lg-8 col-md-12 col-12">
                            <div class="billing-fields">
                                <div class="single-register">
                                    <form action="authen?action=sign-up" method="POST" id="signUpForm">
                                        <!-- Username -->
                                        <div class="form-group">
                                            <label>Username <span style="color:red">*</span></label>
                                            <input type="text" class="form-control" placeholder="Enter username" name="username" required />
                                        </div>

                                        <!-- Password -->
                                        <div class="form-group">
                                            <label>Password <span style="color:red">*</span></label>
                                            <input type="password" class="form-control" placeholder="Enter password" name="password" required />
                                        </div>

                                        <!-- Full name -->
                                        <div class="form-group">
                                            <label>Full name <span style="color:red">*</span></label>
                                            <input type="text" class="form-control" placeholder="Enter your full name" name="fullname" required />
                                        </div>

                                        <!-- Email -->
                                        <div class="form-group">
                                            <label>Email <span style="color:red">*</span></label>
                                            <input type="email" class="form-control" placeholder="Enter your email" name="email" required />
                                        </div>

                                        <!-- Role -->
                                        <!--                                        <div class="form-group">
                                                                                    <label>Role <span style="color:red">*</span></label>
                                                                                    <select class="form-control" name="role" required>
                                                                                        <option value="">-- Select role --</option>
                                                                                        <option value="user">User</option>
                                                                                        <option value="admin">Admin</option>
                                                                                    </select>
                                                                                </div>-->

                                        <!-- Error message -->
                                        <div class="form-group">
                                            <span style="color:red">${error}</span>
                                    </div>

                                    <!-- Submit -->

                                </form>

                            </div>
                            <!-- Submit -->
                            <div class="single-register">
                                <a href="#"
                                   onclick="document.querySelector('#signUpForm').submit()">Register</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- user-login-area-end -->
        <!-- footer-area-start -->
        <jsp:include page="../common/homepage/footer.jsp"></jsp:include>
            <!-- footer-area-end -->


            <!-- all js here -->
            <!-- jquery latest version -->
            <script src="${pageContext.request.contextPath}/assets/js/vendor/jquery-1.12.4.min.js"></script>


        <!-- bootstrap js -->
        <script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
        <!-- owl.carousel js -->
        <script src="${pageContext.request.contextPath}/assets/js/owl.carousel.min.js"></script>
        <!-- meanmenu js -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.meanmenu.js"></script>
        <!-- wow js -->
        <script src="${pageContext.request.contextPath}/assets/js/wow.min.js"></script>
        <!-- jquery.parallax-1.1.3.js -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.parallax-1.1.3.js"></script>
        <!-- jquery.countdown.min.js -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.countdown.min.js"></script>
        <!-- jquery.flexslider.js -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.flexslider.js"></script>
        <!-- chosen.jquery.min.js -->
        <script src="${pageContext.request.contextPath}/assets/js/chosen.jquery.min.js"></script>
        <!-- jquery.counterup.min.js -->
        <script src="${pageContext.request.contextPath}/assets/js/jquery.counterup.min.js"></script>
        <!-- waypoints.min.js -->
        <script src="${pageContext.request.contextPath}/assets/js/waypoints.min.js"></script>
        <!-- plugins js -->
        <script src="${pageContext.request.contextPath}/assets/js/plugins.js"></script>
        <!-- main js -->
        <script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
    </body>


    <!-- Mirrored from htmldemo.net/koparion/koparion/register.html by HTTrack Website Copier/3.x [XR&CO'2014], Fri, 23 Feb 2024 17:30:57 GMT -->
</html>
