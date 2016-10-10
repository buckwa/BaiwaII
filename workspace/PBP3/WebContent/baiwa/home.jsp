<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url var="contextLibs" value="/baiwa/"/>
<c:url var="contextPath" value="/"/>


<html>
  <head>
    <title>Angular QuickStart</title>
    <base href="${contextLibs}home.htm">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    
    
    <link rel="stylesheet" href="${contextLibs}styles.css">


    <!-- 1. Load libraries -->
    <!-- Polyfill(s) for older browsers -->
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
    <my-app>Loading...</my-app>
     <script type="text/javascript" src="${contextLibs}libs/th_datepicker/jquery-1.4.4.min.js"></script>
   
  </body>
  
      
</html>
