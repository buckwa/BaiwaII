<!DOCTYPE html>
<%@page import="baiwa.util.UserLoginUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>




<c:url var="contextLibs" value="/baiwa/" />
<c:url var="contextPath" value="/" />
<c:url var="ver" value="1.0.2" />

<html>
<head>
<title>PBP</title>
<base href="${contextLibs}">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="${contextLibs}libs/js/lib/jquery/jquery.min.js"></script>
<%--     <link href="${contextLibs}libs/img/favicon.114x114.png" rel="apple-touch-icon" type="image/png" sizes="114x114">  --%>
<%-- 	<link href="${contextLibs}libs/img/favicon.72x72.png" rel="apple-touch-icon" type="image/png" sizes="72x72">  --%>
<%-- 	<link href="${contextLibs}libs/img/favicon.57x57.png" rel="apple-touch-icon" type="image/png">  --%>
<%-- 	<link href="${contextLibs}libs/img/favicon.png" rel="icon" type="image/png">  --%>
<link href="${contextLibs}libs/img/favicon.ico" rel="shortcut icon">

<link rel="stylesheet"
	href="${contextLibs}libs/css/lib/clockpicker/bootstrap-clockpicker.min.css">
<link rel="stylesheet"
	href="${contextLibs}libs/css/lib/font-awesome/font-awesome.min.css">
<link rel="stylesheet" href="${contextLibs}libs/css/main.css">

<%--     <link rel="stylesheet" href="${contextLibs}libs/fonts/th_baijam.css">  --%>
<%--     <link rel="stylesheet" href="${contextLibs}libs/fonts/th_krub.css">  --%>
<link rel="stylesheet" href="${contextLibs}styles.css">

<!-- 1. Load libraries -->
<!-- Polyfill(s) for older browsers -->
<link rel="stylesheet" type="text/css"
	href="${contextLibs}libs/editor1.5.6/css/editor.dataTables.css">
<link rel="stylesheet"
	href="${contextLibs}libs/css/lib/datatables-net/datatables.min.css">
<script src="${contextLibs}node_modules/reflect-metadata/Reflect.js"></script>

<!-- Dev -->
<%-- <script src="${contextLibs}node_modules/core-js/client/shim.min.js"></script> --%>
<%-- <script src="${contextLibs}node_modules/zone.js/dist/zone.js"></script> --%>
<%-- <script src="${contextLibs}node_modules/systemjs/dist/system.src.js"></script> --%>
<%-- <script src="${contextLibs}systemjs.config.js"></script> --%>


<link rel="stylesheet" type="text/css"
	href="${contextLibs}libs/editor1.5.6/css/editor.dataTables.css">
<link rel="stylesheet"
	href="${contextLibs}libs/css/lib/font-awesome/font-awesome.min.css">
<link rel="stylesheet" href="${contextLibs}libs/css/main.css">
<link rel="stylesheet"
	href="${contextLibs}libs/css/lib/bootstrap-sweetalert/sweetalert.css" />
<link rel="stylesheet" href="${contextLibs}styles.css?v=${ver}">

<script>
    
    
//     System.import('app').catch(function(err){ console.error("run App", err); });//Dev
    
    const messages = "";
    const LOV_MASTER = "";
    const PAGE_ROLE_LIST = "";
    const contextPath = '${contextPath}';
</script>
	


	<script src="${contextLibs}libs/js/lib/tether/tether.min.js"></script>
	<script src="${contextLibs}libs/js/lib/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${contextLibs}libs/js/lib/blockUI/jquery.blockUI.js"></script>
	<script src="${contextLibs}libs/js/plugins.js"></script>
	<script src="${contextLibs}libs/js/app.js"></script>
	<script
		src="${contextLibs}libs/js/lib/datatables-net/datatables.min.js"></script>
	<script
		src="${contextLibs}libs/js/lib/bootstrap-sweetalert/sweetalert.js"></script>
	<script type="text/javascript"
		src="${contextLibs}libs/editor1.5.6/js/dataTables.editor.js"></script>

	<script
		src="${contextLibs}libs/js/lib/clockpicker/bootstrap-clockpicker.min.js"></script>
	<script
		src="${contextLibs}libs/js/lib/clockpicker/bootstrap-clockpicker-init.js"></script>
	<script
		src="${contextLibs}libs/js/lib/daterangepicker/daterangepicker.js"></script>
	<script
		src="${contextLibs}libs/js/lib/bootstrap-select/bootstrap-select.min.js"></script>


	<link rel="stylesheet"
		href="${contextLibs}libs/kendo/styles/kendo.common.min.css">
	<link rel="stylesheet"
		href="${contextLibs}libs/kendo/styles/kendo.default.min.css">
	<link rel="stylesheet"
		href="${contextLibs}libs/kendo/styles/kendo.dataviz.min.css">
	<link rel="stylesheet"
		href="${contextLibs}libs/kendo/styles/kendo.dataviz.default.min.css">
	<script type="text/javascript"
		src="${contextLibs}libs/kendo/js/kendo.all.min.js"></script>
	<script type="text/javascript"
		src="${contextLibs}libs/kendo/js/angular.min.js"></script>

	<script type="text/javascript">
 		$( document ).ready(function() {    
		    $(window).scroll(function () {  
		    	var scl =  $('body').scrollTop();
		        if (scl < 50) {
		           $('#tophiddenbar,.kk-footer').fadeIn();
		        } else {
		           $('#tophiddenbar,.kk-footer').fadeOut();
		        }
		        
		    });
		});
 	</script>
</head>
<!-- 3. Display the application -->
<body>


	<header class="site-header">
		<div class="container-fluid">
			<a href="#" class="site-logo"> <img class="hidden-md-down"
				src="${contextLibs}libs/img/Logo02.png" alt=""> <img
				class="hidden-lg-up" src="${contextLibs}libs/img/Logo02.png" alt="">

			</a>
			<!-- 	        <button class="hamburger hamburger--htla"> -->
			<!-- 	            <span>toggle menu</span> -->
			<!-- 	        </button> -->
			<div class="site-header-content">
				<div class="site-header-content-in">
					<div class="site-header-shown">

						<div class="dropdown user-menu">
							<button class="dropdown-toggle" id="dd-user-menu" type="button"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">
								<img src="${contextLibs}libs/img/avatar-2-64.png" alt="">

							</button>


							<div class="dropdown-menu dropdown-menu-right"
								aria-labelledby="dd-user-menu">
								<div class="dropdown-header">เวอร์ชั่น : ${ver}</div>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="#/home"><span
									class="font-icon glyphicon glyphicon-user"></span>หน้าแรก</a> <a
									class="dropdown-item" href="#/userManual"><span
									class="font-icon glyphicon glyphicon-question-sign"></span>คู่มือ</a>
								<div class="dropdown-divider"></div>
								<a class="dropdown-item" href="../logout.htm"><span
									class="font-icon glyphicon glyphicon-log-out"></span>Logout</a>
							</div>
						</div>

						<!-- 	<label style="color: #adb7be;"> </label> -->
						<!-- 	<label style="color: #adb7be;     padding-left: 70px;">  การศึกษา 2558 </label> -->
						<!-- 	<label style="color: #adb7be;     padding-left: 70px;">  รอบการประเมิน: 1 ส.ค. 2558 - 31 ก.ค. 2559 </label> -->


						<button type="button" class="burger-right">
							<i class="font-icon-menu-addl"></i>
						</button>
					</div>
					<!--.site-header-shown-->

					<div class="mobile-menu-right-overlay"></div>

					<div class="site-header-collapsed">
						<div class="site-header-collapsed-in" style="width: 100%;">
							<sec:authorize access="!hasAuthority('ROLE_ADMIN')">
								<div class="dropdown dropdown-typical">
									<a class="dropdown-toggle no-arr" id="dd-header-marketing"
										data-target="#/home" href="#/home"> <span
										class="font-icon"><i class="fa fa-home"></i> หน้าแรก</span>
									</a>
								</div>

								<div class="dropdown dropdown-typical">
									<a class="dropdown-toggle" data-target="#" href=""
										data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="true"> <span class="font-icon"><i
											class="fa fa-tasks"></i>&nbsp;รายการผลงาน</span>
									</a>
									<div class="dropdown-menu"
										aria-labelledby="dd-header-form-builder">
										<a class="dropdown-item" href="#/AcademicWork"><i
											class="fa fa-book"></i>&nbsp;&nbsp;ผลงานประจำปี</a> <a
											class="dropdown-item" href="#/ListByWorkType"><i
											class="fa fa-upload"></i>&nbsp;&nbsp;นำเข้าผลงาน</a>
										<sec:authorize
											access="!hasAuthority('ROLE_HEAD')&&!hasAuthority('ROLE_DEAN')">
											<!-- 		                                <a class="dropdown-item" href="#/work"><i class="fa fa-edit"></i>&nbsp;&nbsp;แก้ไขผลงาน</a> -->
										</sec:authorize>
									</div>
								</div>

								<div class="dropdown dropdown-typical">

									<a class="dropdown-toggle" data-target="#" href=""
										data-toggle="dropdown" aria-haspopup="true"
										aria-expanded="true"> <span class="font-icon"><i
											class="fa fa-check-square-o"></i>&nbsp;รายงานคะแนน</span>
									</a>
									<div class="dropdown-menu"
										aria-labelledby="dd-header-form-builder">
										<a class="dropdown-item" href="#/personReportInit"><span
											class="font-icon"><i class="fa fa-flag"></i></span>คะแนนประจำปี</a>
										<a class="dropdown-item" href="#/personReport"><span
											class="font-icon font-icon-cart"></span>ระดับคะแนนในภาควิชา</a> <a
											class="dropdown-item" href="#/workTypeBarChart"><span
											class="font-icon font-icon-speed"></span>ระดับคะแนนแต่ละด้านในภาควิชา</a>
										<a class="dropdown-item" href="#/personYearReport"><span
											class="font-icon"><i class="fa fa-download"></i></span>Download
											รายงานประจำปี</a>

									</div>
								</div>

								<div class="dropdown dropdown-typical">
									<a class="dropdown-toggle no-arr" id="dd-header-form-builder"
										href="#/anonymous"> <span class="font-icon"><i
											class="fa fa-balance-scale"></i>&nbsp;กฎระเบียบ</span>
									</a>
								</div>

								<div class="dropdown dropdown-typical">
									<a class="dropdown-toggle no-arr" id="dd-header-form-builder"
										href="#/personTimeTable"> <span class="font-icon"><i
											class="fa fa-calendar"></i>&nbsp;ตารางสอน</span>
									</a>
								</div>


								<sec:authorize
									access="hasAuthority('ROLE_HEAD')&&!hasAuthority('ROLE_DEAN')">
									<div class="dropdown dropdown-typical">
										<a class="dropdown-toggle" id="dd-header-form-builder"
											data-target="#" href="" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false"> <span
											class="font-icon"><i class="fa fa-sitemap"></i>&nbsp;หัวหน้าภาควิชา</span>
										</a>
										<div class="dropdown-menu"
											aria-labelledby="dd-header-form-builder">
											<a class="dropdown-item" href="#/initApproveByKPI/1"><span
												class="font-icon font-icon-users"></span>อนุมัติผลงาน</a> <a
												class="dropdown-item" href="#/initApprove"><span
												class="font-icon font-icon-users"></span>อนุมัติผลงานรายบุคคล</a>
											<a class="dropdown-item" href="#/PreviousAcademicWork"><span
												class="font-icon font-icon-users"></span>ดูผลงานย้อนหลัง</a>
											<a class="dropdown-item" href="#/AssignHead"><span
												class="font-icon font-icon-users"></span>กำหนดผู้ช่วยอนุมัติ</a>
											<a class="dropdown-item" href="#/markDepartmentRecalInit"><span
												class="font-icon font-icon-users"></span>คำนวนคะแนนให้เป็นปัจจุบัน</a>
											<a class="dropdown-item" href="#/barchart"><span
												class="font-icon font-icon-users"></span>ระดับคะแนนในภาควิชา</a>
											<a class="dropdown-item" href="#/headWorkTypeBarChart"><span
												class="font-icon font-icon-users"></span>ระดับคะแนนแต่ละด้านในภาควิชา</a>
											<!-- 	                                <a class="dropdown-item" href="#/initApproveByKPI"><span class="font-icon font-icon-users"></span> กำลังทดสอบ</a> -->
										</div>
									</div>
								</sec:authorize>
								<sec:authorize access="hasAuthority('ROLE_HEAD_ASSIST')">
									
								<div class="dropdown dropdown-typical">
									<a class="dropdown-toggle no-arr" id="dd-header-form-builder"
										href="#/initApproveByKPI/1"> <span class="font-icon"><i
											class="fa fa-balance-scale"></i>&nbsp;อนุมัติผลงาน</span>
									</a>
								</div>
								<div class="dropdown dropdown-typical">
									<a class="dropdown-toggle no-arr" id="dd-header-form-builder"
										href="#/initApprove"> <span class="font-icon"><i
											class="font-icon font-icon-users"></i>&nbsp;อนุมัติผลงานรายบุคคล</span>
									</a>
								</div>
								
								</sec:authorize>

								<sec:authorize access="hasAuthority('ROLE_DEAN')">
									<div class="dropdown dropdown-typical">
										<a class="dropdown-toggle" id="dd-header-form-builder"
											data-target="#" href="" data-toggle="dropdown"
											aria-haspopup="true" aria-expanded="false"> <span
											class="font-icon"><i class="fa fa-university"></i>&nbsp;คณบดี</span>
										</a>
										<div class="dropdown-menu"
											aria-labelledby="dd-header-form-builder">
											<a class="dropdown-item" href="#/facultyReport"><span
												class="font-icon font-icon-users"></span>รายงานคะแนนทั้งคณะ</a>
											<a class="dropdown-item" href="#/deanBarChart"><span
												class="font-icon font-icon-users"></span>รายงานระดับคะแนนแต่ล่ะภาควิชา</a>
											<a class="dropdown-item" href="#/deanWorkTypeBarChart"><span
												class="font-icon font-icon-users"></span>รายงานระดับคะแนนแต่ล่ะภาควิชา
												แต่ล่ะด้าน</a> <a class="dropdown-item"
												href="#/workTypeCompareBarChart"><span
												class="font-icon font-icon-users"></span>รายงานเปรียบเทียบระดับคะแนนแต่ละภาควิชา
												แต่ละด้าน</a> <a class="dropdown-item"
												href="#/departmentBarChart"><span
												class="font-icon font-icon-users"></span>ระดับคะแนนในภาควิชา</a>
												
												<a class="dropdown-item" href="#/ReportKpi2"><span
											class="font-icon font-icon-users"></span>
											 ระดับคะแนนตามตัวชี้วัด</a>	
											
										<a class="dropdown-item" href="#/ReportKpi1"><span
											class="font-icon font-icon-users"></span>
											จำนวนผลงานตามตัวชี้วัด</a>	
										</div>
									</div>
								</sec:authorize>


							</sec:authorize>
							<sec:authorize access="hasAuthority('ROLE_ADMIN')">

								<div class="dropdown dropdown-typical">
									<a class="dropdown-toggle no-arr" id="dd-header-marketing"
										data-target="#/home" href="#/home"> <span
										class="font-icon"><i class="fa fa-home"></i> หน้าแรก</span>
									</a>
								</div>

								<div class="dropdown dropdown-typical">
									<a class="dropdown-toggle" id="dd-header-form-builder"
										data-target="#" href="" data-toggle="dropdown"
										aria-haspopup="true" aria-expanded="false"> <span
										class="font-icon"><i class="fa fa-university"></i>&nbsp;สถาบัน
											&บุคลากร</span>
									</a>

									<div class="dropdown-menu"
										aria-labelledby="dd-header-form-builder">
										<a class="dropdown-item" href="#/AdminAcademicYear"><span
											class="font-icon font-icon-users"></span>ปีการศึกษา</a> 
											<!-- <a
											class="dropdown-item" href="#/AdminEvaluateRoundinit"><span
											class="font-icon font-icon-users"></span>รอบการประเมิน</a> -->
											 <a
											class="dropdown-item" href="#/AdminFaculty"><span
											class="font-icon font-icon-users"></span>หน่วยงาน</a> <a
											class="dropdown-item" href="#/AdminChainOfCommandinit"><span
											class="font-icon font-icon-users"></span>สายบังคับบัญชา</a>
											
<!-- 											<a class="dropdown-item" href="javascript:void(0);"> -->
<!-- 											<span class="font-icon font-icon-users"></span>นำเข้าบุคลากร</a>  -->
											
											<a class="dropdown-item" href="#/AdminWorkUser">
											<span class="font-icon font-icon-users"></span>ข้อมูลบุคลากร</a> 
											
<!-- 											<a class="dropdown-item" href="javascript:void(0);"> -->
<!-- 											<span class="font-icon font-icon-users"></span>ข้อความ</a> -->


									</div>
								</div>

								<div class="dropdown dropdown-typical">
									<a class="dropdown-toggle" id="dd-header-form-builder"
										data-target="#" href="" data-toggle="dropdown"
										aria-haspopup="true" aria-expanded="false"> <span
										class="font-icon"><i class="fa fa-university"></i>&nbsp;ภาระงาน
									</span>
									</a>
									<div class="dropdown-menu"
										aria-labelledby="dd-header-form-builder">
<!-- 										<a class="dropdown-item" href="javascript:void(0);"><span -->
<!-- 											class="font-icon font-icon-users"></span>เกณฑ์คะแนน</a>  -->
											<!-- <a
											class="dropdown-item" href="javascript:void(0);t"><span
											class="font-icon font-icon-users"></span>หน่วยนับ</a>  -->
											<!-- <a
											class="dropdown-item" href="#/AdminpBPWorkTypeinit"><span
											class="font-icon font-icon-users"></span>ประเภทภาระงาน</a> -->
											 <a
											class="dropdown-item" href="#/AdminAcademicKPI/0/0/0"><span
											class="font-icon font-icon-users"></span>ภาระงานประจำปี</a>
									</div>
								</div>

<!-- 								<div class="dropdown dropdown-typical"> -->
<!-- 									<a class="dropdown-toggle" id="dd-header-form-builder" -->
<!-- 										data-target="#" href="" data-toggle="dropdown" -->
<!-- 										aria-haspopup="true" aria-expanded="false"> <span -->
<!-- 										class="font-icon"><i class="fa fa-university"></i>&nbsp;นำเข้าตารางสอน -->
<!-- 									</span> -->
<!-- 									</a> -->
<!-- 									<div class="dropdown-menu" -->
<!-- 										aria-labelledby="dd-header-form-builder"> -->
<!-- 										<a class="dropdown-item" href="javascript:void(0);"><span -->
<!-- 											class="font-icon font-icon-users"></span> (Webservice)รายบุคล -->
<!-- 										</a> <a class="dropdown-item" href="javascript:void(0);"><span -->
<!-- 											class="font-icon font-icon-users"></span> หน่วยนับ </a> <a -->
<!-- 											class="dropdown-item" href="javascript:void(0);"><span -->
<!-- 											class="font-icon font-icon-users"></span>แก้ไขตารางสอน</a> <a -->
<!-- 											class="dropdown-item" href="javascript:void(0);"><span -->
<!-- 											class="font-icon font-icon-users"></span>คำนวณคะแนน</a> -->
<!-- 									</div> -->
<!-- 								</div> -->


								<div class="dropdown dropdown-typical">
									<a class="dropdown-toggle" id="dd-header-form-builder"
										data-target="#" href="" data-toggle="dropdown"
										aria-haspopup="true" aria-expanded="false"> <span
										class="font-icon"><i class="fa fa-university"></i>&nbsp;รายงาน
									</span>
									</a>
									<div class="dropdown-menu"
										aria-labelledby="dd-header-form-builder">
										<a class="dropdown-item" href="javascript:void(0);"><span
											class="font-icon font-icon-users"></span>
											รายงานคะแนนบุคลากรทั้งสถาบัน </a>
										<a class="dropdown-item" href="#/ReportKpi2"><span
											class="font-icon font-icon-users"></span>
											 ระดับคะแนนตามตัวชี้วัด</a>	
											
										<a class="dropdown-item" href="#/ReportKpi1"><span
											class="font-icon font-icon-users"></span>
											จำนวนผลงานตามตัวชี้วัด</a>	
											
											 <!-- <a class="dropdown-item"
											href="javascript:void(0);"><span
											class="font-icon font-icon-users"></span> หน่วยนับ Access Log
										</a> -->
									</div>
								</div>

							</sec:authorize>


							<!-- 	                        <div class="dropdown dropdown-typical"> -->
							<!-- 	                            <a class="dropdown-toggle no-arr" id="dd-header-form-builder" href="#/userManual"> -->
							<!-- 	                                <span class="font-icon"> &nbsp;คู่มือ</span> -->
							<!-- 	                            </a> -->
							<!-- 	                        </div> -->




						</div>
						<!--.site-header-collapsed-in-->

						<%-- 	                 <label style="color: #adb7be;    text-align: right; ">  รอบการประเมิน: 1 ส.ค. 2558 - 31 ก.ค. 2559 |  การศึกษา ${year} | ${username} </label> --%>


					</div>
					<!--.site-header-collapsed-->

				</div>
				<!--site-header-content-in-->

			</div>
			<!--.site-header-content-->
		</div>
		<!--.container-fluid-->
	</header>
	<!--.site-header-->
	<div id="tophiddenbar" class="nav-down">
		<!-- 		<span style="float: left;">&nbsp;&nbsp;&nbsp;รอบการประเมิน : 1 ส.ค. 2558 - 31 ก.ค. 2559</span> -->
		<span style="float: right;"> <%=UserLoginUtil.getCurrentFullThaiName()%>&nbsp;&nbsp;
			ปีการศึกษา : <%=UserLoginUtil.getCurrentAcademicYear()%>&nbsp;&nbsp;
		</span>
	</div>

	<div class="page-content">

		<div class="container-fluid">
			<my-app></my-app>
		</div>

	</div>



	<div class="kk-footer">
		<span> พัฒนาโดย คณะวิศวกรรมศาสตร์
			สถาบันเทคโนโลยีพระจอมเกล้าเจ้าคุณทหารลาดกระบัง.</span> &nbsp;&nbsp;&nbsp; <span>
			(หากมีข้อสงสัยในการใช้งาน สอบถามได้ที่  3272 คุณเมธิน)</span>
	</div>





		<script src="${contextLibs}dist/shim.min.js"></script>
		<script src="${contextLibs}dist/zone.js"></script>
		<script src="${contextLibs}dist/bundle.min.js"></script>
</body>
</html>
