
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<html>
<head lang="en">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<meta http-equiv="x-ua-compatible" content="ie=edge">
	<title>PBP</title>

	<link href="<c:url value='/baiwa/libs/img/favicon.114x114.png' />" rel="apple-touch-icon" type="image/png" sizes="114x114"> 
	<link href="<c:url value='/baiwa/libs/img/favicon.72x72.png' />" rel="apple-touch-icon" type="image/png" sizes="72x72"> 
	<link href="<c:url value='/baiwa/libs/img/favicon.57x57.png' />" rel="apple-touch-icon" type="image/png"> 
	<link href="<c:url value='/baiwa/libs/img/favicon.png' />" rel="icon" type="image/png"> 
	<link href="<c:url value='/baiwa/libs/img/favicon.ico' />" rel="shortcut icon"> 


	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->

	<link rel="stylesheet" href="<c:url value='/baiwa/libs/css/lib/font-awesome/font-awesome.min.css' />">
	<link rel="stylesheet" href="<c:url value='/baiwa/libs/css/main.css' />">

</head>
<body>



    <div class="page-center">
        <div class="page-center-in">
            <div class="container-fluid">
				<form class="sign-box" action="<c:url value='/login.htm' />" method="POST">               
                    <div class="sign-avatar">
                        <img src="<c:url value='/baiwa/libs/img/kmitl_logo.png' />" alt="">
                    </div>
                    <header class="sign-title">เข้าสู่ระบบ</header>
                    <c:if test="${not empty error}">
									<div class="alert alert-danger alert-no-border alert-close alert-dismissible fade in" role="alert">
										<button type="button" class="close" data-dismiss="alert" aria-label="Close">
											<span aria-hidden="true">&times;</span>
										</button>
										${error}
									</div>
					</c:if>
                    <div class="form-group">
                        <input type="text" name="username" class="form-control" placeholder="ผู้ใช้งาน" value="ktpitak">
                    </div>
                    <div class="form-group">
                        <input type="password"  name="password" class="form-control" placeholder="รหัสผ่าน" value="password" />
                    </div>
                    
                    <div class="form-group">
                        <div class="checkbox float-left">
                            <input type="checkbox" id="signed-in"/>
                            <label for="signed-in">Keep me signed in</label>
                        </div>
                        
                    </div>
                    <button type="submit" class="btn btn-rounded">ตกลง</button>
<!--                     <button type="button" class="close">
                        <span aria-hidden="true">&times;</span>
                    </button> -->
                </form>
            </div>
        </div>
    </div><!--.page-center-->


<script src="<c:url value='/baiwa/libs/js/lib/jquery/jquery.min.js' />"></script>
<script src="<c:url value='/baiwa/libs/js/lib/tether/tether.min.js' />"></script>
<script src="<c:url value='/baiwa/libs/js/lib/bootstrap/bootstrap.min.js' />"></script>
<script src="<c:url value='/baiwa/libs/js/plugins.js' />"></script>
<script src="<c:url value='/baiwa/libs/js/app.js' />"></script>



</body>
</html>