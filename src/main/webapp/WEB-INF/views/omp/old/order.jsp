<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta http-equiv="refresh"
	content="30;url=${pageContext.request.contextPath}/old/oldMatch/list.shtml?name=&idCard=&zjNumber=&county=&street=&community=&isGenerationOrder=&creationTime=">
<div class="panel-footer">

	<div align="center">
		<c:if test="${message > 0 }">
			<c:out value="$message"></c:out>
		</c:if>
		<div align="center">
			<input id="autoBack" type="button"
				onclick="location='${pageContext.request.contextPath}/old/oldMatch/list.shtml?name=&idCard=&zjNumber=&county=&street=&community=&isGenerationOrder=&creationTime='"
				value="30秒后自动返回列表/点击返回" />
		</div>
	</div>

</div>



<!-- Script	-->
<SCRIPT type="text/javascript">
	
</SCRIPT>
