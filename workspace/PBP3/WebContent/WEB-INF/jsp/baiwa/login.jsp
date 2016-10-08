<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>TMB Bank Public Company Limited</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- LIB -->
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" href="<c:url value='/tmb/libs/css/lib/bootstrap-sweetalert/sweetalert.css' />">
<link rel="stylesheet" href="<c:url value='/tmb/libs/css/lib/font-awesome/font-awesome.min.css' />">
<link rel="stylesheet" href="<c:url value='/tmb/libs/css/main.css' />">
<link rel="stylesheet" href="<c:url value='/tmb/styles.css' />">
<script type="text/javascript" src="<c:url value='/tmb/libs/js/lib/jquery/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/tmb/libs/js/lib/tether/tether.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/tmb/libs/js/lib/bootstrap/bootstrap.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/tmb/libs/js/lib/bootstrap-sweetalert/sweetalert.min.js'/>"></script>
</head>


<!-- 3. Display the application -->
<body class="bg-login">
<c:choose>
  <c:when test="${not empty pageContext.request.userPrincipal.name and empty warning}">   
		<script type="text/javascript">
				window.location.href="<c:url value='/tmb/home.htm' />";
		</script>
  </c:when>
  <c:otherwise>
<!--   			Not Login -->
  </c:otherwise>
</c:choose>

<div class="page-content">
<div class="container-fluid">
<div class="row">
	<div class="col-sm-12 col-md-8 col-xs-8 col-lg-6  col-md-offset-2">
		<img alt="logo" src="<c:url value='/tmb/libs/img/nonssl_logo2.png' />">
		<section class="card">
			<header class="card-header card-header-lg"> Login (ver. 30/09/2559) </header>
			<div class="card-block">
				<p class="card-text">
				<c:if test="${not empty warning}"> 
					<!-- Change Code Here!!  -->
					  <div class="form-bottom hidden">
						<div class="error-signin">${warning}</div>
						<form id="confirmloginform" action="<c:url value='/confirm-login.htm' />" method="POST" class="login-form">
							<button type="submit">Continue Login</button>
						</form>
						<form id="logoutform" action="<c:url value='/logout.htm' />" method="POST" class="login-form">
							<button type="submit">Cancel</button>
						</form>
					</div>
			
				</c:if>
<%-- 				<c:if test="${empty warning}"> --%>
					<form action="<c:url value='/login.htm' />" method="POST">
						<div class="row">
							<div class="col-sm-12">
								<c:if test="${not empty error}">
									<div class="alert alert-danger alert-no-border alert-close alert-dismissible fade in" role="alert">
										<button type="button" class="close" data-dismiss="alert" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										${error}
									</div>
									<!--  <div class="error-signin">${error}</div> -->
								</c:if>
								<div class="form-group">
									<div class="form-control-wrapper form-control-icon-left login">
										<input type="text" name="username" class="form-control" placeholder="User Name" />
										<i class="fa fa-user color-blue"></i>
									</div>
									<div class="form-control-wrapper form-control-icon-left login">
										<input type="password" name="password" class="form-control" placeholder="Password" />
										<i class="fa fa-unlock-alt color-blue"></i>
									</div>
								</div>
							</div>
						</div>
						<div class="text-center">
							<button type="submit" class="btn btn-inline tmb-button">Login</button>
						</div>
					</form>
<%-- 				</c:if> --%>
				</p>
			</div>
		</section>
	</div>
</div>
</div>
<!--.container-fluid-->
</div>
<!--.page-content-->


<c:if test="${not empty warning}">
		
	<script type="text/javascript" >
					$(function() {
						
							swal({
								title:"Are you sure?",
								text: "Duplicate User Login. Click \"Yes\" to continue, \"No\" to back to Login screen.",
								type: "info",
								showCancelButton: true,
								cancelButtonClass: "btn-default",
								cancelButtonText: "Yes", // swap No->Yes
								confirmButtonText: "No",// swap Yes->No
								confirmButtonClass: "btn-default",
								allowEscapeKey: false,
								animation: false

		
							},function(isConfirm) {
								//swap No->Yes, Yes->No
								isConfirm = !isConfirm;
								
								if(isConfirm){
									$("#confirmloginform").submit();
								}else{
									$("#logoutform").submit();
								}

							});
							
							setTimeout(function() {
								jQuery(".sweet-alert").focus();
// 								console.log("OK");
							}, 500);
						
								});
										
	</script>
	
</c:if>
</body>
</html>
