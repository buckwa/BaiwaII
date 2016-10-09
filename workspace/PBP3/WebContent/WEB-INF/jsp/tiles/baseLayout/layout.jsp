<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html lang="en">
    <head>
 
 
	      
  
    </head>
   
    
    <body  >
 
        
            <tiles:insertAttribute name="header" />
            <div class="container" id="main">
            <%@include file="/WEB-INF/jsp/tiles/baseLayout/pageInfo.jsp" %>
            <tiles:insertAttribute name="body" />
     		 </div><!--/main-->
            <tiles:insertAttribute name="footer" />
       
    </body>
</html>