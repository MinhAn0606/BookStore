<%-- 
    Document   : dashboard
    Created on : Oct 27, 2025, 9:53:25 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>SB Admin - Dashboard</title>

        <!-- Custom fonts for this template-->
        <link href="${pageContext.request.contextPath}/assets/vendor-admin/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <!-- Page level plugin CSS-->
        <link href="${pageContext.request.contextPath}/assets/vendor-admin/datatables/dataTables.bootstrap4.css" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="${pageContext.request.contextPath}/assets/css/sb-admin.css" rel="stylesheet">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/colReorder-bootstrap4.css">

        <style>
            .error{
                color:red;
            }
        </style>

    </head>

    <body id="page-top">

        <nav class="navbar navbar-expand navbar-dark bg-dark static-top">

            <a class="navbar-brand mr-1" href="index.html">Start Bootstrap</a>

            <button class="btn btn-link btn-sm text-white order-1 order-sm-0" id="sidebarToggle" href="#">
                <i class="fas fa-bars"></i>
            </button>

            <!-- Navbar Search -->
            <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search for..." aria-label="Search"
                           aria-describedby="basic-addon2">
                    <div class="input-group-append">
                        <button class="btn btn-primary" type="button">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>

            <!-- Navbar -->
            <jsp:include page="../common/admin/navbar.jsp"></jsp:include>

            </nav>

            <div id="wrapper">

                <!-- Sidebar -->
            <jsp:include page="../common/admin/sidebar.jsp"></jsp:include>

                <div id="content-wrapper">

                    <div class="container-fluid">

                        <!-- Breadcrumbs-->
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item">
                                <a href="#">Dashboard</a>
                            </li>
                            <li class="breadcrumb-item active">Overview</li>
                            <li class="breadcrumb-item ml-auto">
                                <button type="button" class="btn btn-success" data-toggle="modal" data-target="#addModal">
                                    Add Product
                                </button>
                            </li>
                        </ol>


                        <!-- Icon Cards-->
                    <jsp:include page="../common/admin/iconcard.jsp"></jsp:include>

                        <!-- Area Chart Example-->
                        <div class="card mb-3">
                            <div class="card-header">
                                <i class="fas fa-chart-area"></i>
                                Area Chart Example
                            </div>
                            <div class="card-body">
                                <canvas id="myAreaChart" width="100%" height="30"></canvas>
                            </div>
                            <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
                        </div>

                        <!-- DataTables Example -->
                        <div class="card mb-3">
                            <div class="card-header">
                                <i class="fas fa-table"></i>
                                Data Table Example
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>Book_id</th>
                                                <th>Title</th>
                                                <th>Category</th>
                                                <th>Description</th>
                                                <th>Price</th>
                                                <th>Stock</th>
                                                <th>Url_image</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                        <c:forEach items="${listBook}" var="lb">
                                            <tr>
                                                <td name="id">${lb.getBookId()}</td>
                                                <td name="name">${lb.getTitle()}</td>
                                                <td name="author" hidden>${lb.getAuthor()}</td>
                                                <td name="category">${lb.getCategory()}</td>
                                                <td name="description">${lb.getDescription()}</td>
                                                <td name="price">${lb.getPrice()}</td>
                                                <td name="quantity">${lb.getStock()}</td>
                                                <td name="image"><img src="${pageContext.request.contextPath}/${lb.getImageUrl()}" alt="alt" width="100" height="100"/></td>
                                                <td>
                                                    <button type="button" class="btn btn-primary"
                                                            data-toggle="modal" 
                                                            data-target="#editProductModal"
                                                            onclick="editProductModal(this)">
                                                        Edit
                                                    </button>
                                                    <button type="button" class="btn btn-danger" 
                                                            data-toggle="modal" data-target="#delete-product-modal"
                                                            onclick="deleteProductModal(${lb.getBookId()})">
                                                        Delete
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="card-footer small text-muted">Updated yesterday at 11:59 PM</div>
                    </div>

                </div>
                <!-- /.container-fluid -->

                <!-- Sticky Footer -->
                <jsp:include page="../common/admin/footer.jsp"></jsp:include>
                

                </div>
                <!-- /.content-wrapper -->

            </div>
            <!-- /#wrapper -->

            <!-- Scroll to Top Button-->
            <a class="scroll-to-top rounded" href="#page-top">
                <i class="fas fa-angle-up"></i>
            </a>

            <!-- Logout Modal-->
        <jsp:include page="../common/admin/logoutmodel.jsp"></jsp:include>

        <jsp:include page="addBook.jsp"></jsp:include>
        <jsp:include page="deleteBook.jsp"></jsp:include>
        <jsp:include page="editBook.jsp"></jsp:include>

        <!-- Bootstrap core JavaScript-->
        <script src="${pageContext.request.contextPath}/assets/vendor-admin/jquery/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor-admin/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="${pageContext.request.contextPath}/assets/vendor-admin/jquery-easing/jquery.easing.min.js"></script>

        <!-- Page level plugin JavaScript-->
        <script src="${pageContext.request.contextPath}/assets/vendor-admin/chart.js/Chart.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor-admin/datatables/jquery.dataTables.js"></script>
        <script src="${pageContext.request.contextPath}/assets/vendor-admin/datatables/dataTables.bootstrap4.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="${pageContext.request.contextPath}/assets/js/admin/sb-admin.min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/admin/colReorder-bootstrap4-min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/admin/colReorder-dataTables-min.js"></script>

        <!-- Demo scripts for this page-->
        <script src="${pageContext.request.contextPath}/assets/js/admin/demo/datatables-demo.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/admin/demo/chart-area-demo.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/admin/colReorder-dataTables-min.js"></script>
        <script src="${pageContext.request.contextPath}/assets/js/admin/colReorder-bootstrap4-min.js"></script>

    </body>

</html>
