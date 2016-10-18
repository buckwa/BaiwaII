<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>




<c:url var="contextLibs" value="/baiwa/"/>
<c:url var="contextPath" value="/"/>

<html>
  <head>
    <title>Angular QuickStart</title>
    <base href="${contextLibs}home.htm">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <link href="${contextLibs}libs/img/favicon.114x114.png" rel="apple-touch-icon" type="image/png" sizes="114x114"> 
	<link href="${contextLibs}libs/img/favicon.72x72.png" rel="apple-touch-icon" type="image/png" sizes="72x72"> 
	<link href="${contextLibs}libs/img/favicon.57x57.png" rel="apple-touch-icon" type="image/png"> 
	<link href="${contextLibs}libs/img/favicon.png" rel="icon" type="image/png"> 
	<link href="${contextLibs}libs/img/favicon.ico" rel="shortcut icon"> 
    
    <link rel="stylesheet" href="${contextLibs}styles.css">
    
	<link rel="stylesheet" href="${contextLibs}libs/css/lib/font-awesome/font-awesome.min.css">
	<link rel="stylesheet" href="${contextLibs}libs/css/main.css">

    <!-- 1. Load libraries -->
    <!-- Polyfill(s) for older browsers -->
    <link rel="stylesheet" type="text/css" href="${contextLibs}libs/editor1.5.6/css/editor.dataTables.css">
        <link rel="stylesheet" href="${contextLibs}libs/css/lib/datatables-net/datatables.min.css">
    <script src="${contextLibs}node_modules/core-js/client/shim.min.js"></script>
    <script src="${contextLibs}node_modules/zone.js/dist/zone.js"></script>
    <script src="${contextLibs}node_modules/reflect-metadata/Reflect.js"></script>
    <script src="${contextLibs}node_modules/systemjs/dist/system.src.js"></script>
    <!-- 2. Configure SystemJS -->
    <script src="${contextLibs}systemjs.config.js"></script>
    
    <script>
    "use strict";
    
    System.import('app').catch(function(err){ console.error("run App", err); });
    
    const messages = "";
    const LOV_MASTER = "";
    const PAGE_ROLE_LIST = "";
    const contextPath = '${contextPath}';
    </script>
  </head>
  <!-- 3. Display the application -->
  <body>
  
  
	<header class="site-header">
	    <div class="container-fluid">
	        <a href="#" class="site-logo">
	        
	            <img class="hidden-md-down" src="${contextLibs}libs/img/logo-2.png" alt="">
	            <img class="hidden-lg-up" src="${contextLibs}libs/img/logo-2-mob.png" alt="">
	            
	        </a>
	        <button class="hamburger hamburger--htla">
	            <span>toggle menu</span>
	        </button>
	        <div class="site-header-content">
	            <div class="site-header-content-in">
	                <div class="site-header-shown">
	                    <div class="dropdown dropdown-notification notif">
	                        <a href="#"
	                           class="header-alarm dropdown-toggle active"
	                           id="dd-notification"
	                           data-toggle="dropdown"
	                           aria-haspopup="true"
	                           aria-expanded="false">
	                            <i class="font-icon-alarm"></i>
	                        </a>
	                        <div class="dropdown-menu dropdown-menu-right dropdown-menu-notif" aria-labelledby="dd-notification">
	                            <div class="dropdown-menu-notif-header">
	                                Notifications
	                                <span class="label label-pill label-danger">4</span>
	                            </div>
	                            <div class="dropdown-menu-notif-list">
	                                <div class="dropdown-menu-notif-item">
	                                    <div class="photo">
	                                        <img src="img/photo-64-1.jpg" alt="">
	                                    </div>
	                                    <div class="dot"></div>
	                                    <a href="#">Morgan</a> was bothering about something
	                                    <div class="color-blue-grey-lighter">7 hours ago</div>
	                                </div>
	                                <div class="dropdown-menu-notif-item">
	                                    <div class="photo">
	                                        <img src="img/photo-64-2.jpg" alt="">
	                                    </div>
	                                    <div class="dot"></div>
	                                    <a href="#">Lioneli</a> had commented on this <a href="#">Super Important Thing</a>
	                                    <div class="color-blue-grey-lighter">7 hours ago</div>
	                                </div>
	                                <div class="dropdown-menu-notif-item">
	                                    <div class="photo">
	                                        <img src="img/photo-64-3.jpg" alt="">
	                                    </div>
	                                    <div class="dot"></div>
	                                    <a href="#">Xavier</a> had commented on the <a href="#">Movie title</a>
	                                    <div class="color-blue-grey-lighter">7 hours ago</div>
	                                </div>
	                                <div class="dropdown-menu-notif-item">
	                                    <div class="photo">
	                                        <img src="img/photo-64-4.jpg" alt="">
	                                    </div>
	                                    <a href="#">Lionely</a> wants to go to <a href="#">Cinema</a> with you to see <a href="#">This Movie</a>
	                                    <div class="color-blue-grey-lighter">7 hours ago</div>
	                                </div>
	                            </div>
	                            <div class="dropdown-menu-notif-more">
	                                <a href="#">See more</a>
	                            </div>
	                        </div>
	                    </div>
	
	                  

	
	                    <div class="dropdown user-menu">
	                        <button class="dropdown-toggle" id="dd-user-menu" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                            <img src="${contextLibs}libs/img/avatar-2-64.png" alt="">
	                        </button>
	                        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dd-user-menu">
	                            <a class="dropdown-item" href="#"><span class="font-icon glyphicon glyphicon-user"></span>Profile</a>
	                            <a class="dropdown-item" href="#"><span class="font-icon glyphicon glyphicon-cog"></span>Settings</a>
	                            <a class="dropdown-item" href="#"><span class="font-icon glyphicon glyphicon-question-sign"></span>Help</a>
	                            <div class="dropdown-divider"></div>
	                            <a class="dropdown-item" href="logout.htm"><span class="font-icon glyphicon glyphicon-log-out"></span>Logout</a>
	                        </div>
	                    </div>
	
	                    <button type="button" class="burger-right">
	                        <i class="font-icon-menu-addl"></i>
	                    </button>
	                </div><!--.site-header-shown-->
	
	                <div class="mobile-menu-right-overlay"></div>
	                <div class="site-header-collapsed">
	                    <div class="site-header-collapsed-in" style=" width: 100%;">
	                    
	                        <div class="dropdown dropdown-typical">
	                            <div class="dropdown-menu" aria-labelledby="dd-header-sales">
	                                <a class="dropdown-item" href="#"><span class="font-icon font-icon-home"></span>Quant and Verbal</a>
	                                <a class="dropdown-item" href="#"><span class="font-icon font-icon-cart"></span>Real Gmat Test</a>
	                                <a class="dropdown-item" href="#"><span class="font-icon font-icon-speed"></span>Prep Official App</a>
	                                <a class="dropdown-item" href="#"><span class="font-icon font-icon-users"></span>CATprer Test</a>
	                                <a class="dropdown-item" href="#"><span class="font-icon font-icon-comments"></span>Third Party Test</a>
	                            </div>
	                        </div>
	                        
	                        
	                        
	                        <div class="dropdown dropdown-typical">
	                            <a class="dropdown-toggle no-arr" id="dd-header-marketing" data-target="#/home" href="#/home" >
	                                <span class="font-icon font-icon-cogwheel">หน้าแรก</span>
	                               
	                            </a>
	                        </div>
	                        
	                        
	                      
	                        <div class="dropdown dropdown-typical">
	                            <a href="#/ListByWorkType" class="dropdown-toggle no-arr">
	                                <span class="font-icon font-icon-page">นำเข้าผลงาน</span>
	                            </a>
	                        </div>
	                        <div class="dropdown dropdown-typical">
	                            <a class="dropdown-toggle no-arr" id="dd-header-form-builder"  href="#/AcademicWork" >
	                                <span class="font-icon font-icon-pencil">ผลงานประจำปี</span>
	                            </a>
	                        </div>
	                        
	                        <div class="dropdown dropdown-typical">
	                            <a class="dropdown-toggle no-arr"  id="dd-header-form-builder" href="#/anonymous" >
	                                <span class="font-icon font-icon-pencil">กฎระเบียบ</span>
	                            </a>
	                        </div>
	                        
	                       	<div class="dropdown dropdown-typical">
	                            <a class="dropdown-toggle no-arr" id="dd-header-form-builder" data-target="#" href="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	                                <span class="font-icon font-icon-pencil">รายงานคะแนน</span>
	                            </a>
									<div class="dropdown-menu" aria-labelledby="dd-header-form-builder">

	                                <a class="dropdown-item" href="#/personReportInit"><span class="font-icon font-icon-home"></span>คะแนนประจำปี</a>
	                                <a class="dropdown-item" href="#/personReport"><span class="font-icon font-icon-cart"></span>ระดับคะแนนในภาควิชา</a>
	                                <a class="dropdown-item" href="#/workTypeBarChart"><span class="font-icon font-icon-speed"></span>ระดับคะแนนแต่ละด้านในภาควิชา</a>
	                                <a class="dropdown-item" href="#/personYearReport"><span class="font-icon font-icon-users"></span>Download รายงานประจำปี</a>
	                            

	                            </div>
	                        </div>

	                        <div class="dropdown dropdown-typical">
	                            <a class="dropdown-toggle no-arr" id="dd-header-form-builder" href="#/personTimeTable" >
	                                <span class="font-icon font-icon-pencil">ตารางสอน</span>
	                            </a>
	                        </div>
 							<div class="dropdown dropdown-typical">
	                            <a class="dropdown-toggle no-arr" id="dd-header-form-builder"  href="#/work"  >
	                                <span class="font-icon font-icon-pencil">แก้ไขผลงาน</span>
	                            </a>
	                        </div>
	                        <div class="dropdown dropdown-typical">
	                            <a class="dropdown-toggle no-arr" id="dd-header-form-builder" href="#/userManual">
	                                <span class="font-icon font-icon-pencil">คู่มือ</span>
	                            </a>
	                        </div>




	                    </div><!--.site-header-collapsed-in-->
	                </div><!--.site-header-collapsed-->
	            </div><!--site-header-content-in-->
	        </div><!--.site-header-content-->
	    </div><!--.container-fluid-->
	</header><!--.site-header-->
	
	
	<div class="page-content">
		<div class="container-fluid">

    		<my-app>Loading...</my-app>
     	</div>
    </div>
     
     
     
     
     
     
     
     
<div class="" style="
	text-align: center;
    padding: 18px 0 10px 10px;
    background: #fff;
    color: #fff;
    font-family: THSarabun, 'Thai Sans Lite', sans-serif;

    bottom: 0px;
    font-size: 13px;
    width: 100%;
">
     <span style="color: black;"> พัฒนาโดย คณะวิศวกรรมศาสตร์ สถาบันเทคโนโลยีพระจอมเกล้าเจ้าคุณทหารลาดกระบัง.</span> &nbsp;&nbsp;&nbsp;
    <span style="color: black;"> (หากมีข้อสงสัยในการใช้งาน สอบถามได้ที่ 3278 คุณ นิสากร สีนวลแตง)</span> 
        
</div>
     
     
     
     <script src="${contextLibs}libs/js/lib/jquery/jquery.min.js"></script>
	 <script src="${contextLibs}libs/js/lib/tether/tether.min.js"></script>
	 <script src="${contextLibs}libs/js/lib/bootstrap/bootstrap.min.js"></script>
	 <script src="${contextLibs}libs/js/plugins.js"></script>
	 <script src="${contextLibs}libs/js/app.js"></script>
	 <script src="${contextLibs}libs/js/lib/datatables-net/datatables.min.js"></script>
      <script src="${contextLibs}libs/js/lib/bootstrap-sweetalert/sweetalert.js"></script>
      <script type="text/javascript" src="${contextLibs}libs/editor1.5.6/js/dataTables.editor.js"></script>
   
   
  </body>
</html>
