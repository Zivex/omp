<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span12">
			<form class="form-inline" method="post" enctype="multipart/form-data" id="empForm"
				action="${pageContext.request.contextPath }/old/oldMatch/importInformation.shtml">
				<fieldset
					style="width: 300px; height: 210px; background: #fff; position: absolute; left: 50%; top: 20%; margin-left: -150px; border-radius: 4px;">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<p
						style="width: 80px; height: 40px; margin: 60px auto 30px; background: #ccc; text-align: center; line-height: 40px; position: relative; cursor: pointer; border-radius: 4px;">
						选择文件 <input type="file" onmouseleave="importEmp()" name="excelFile" id="excelFile"
							style="opacity: 0; position: absolute; left: 0; top: 0; width: 80px; height: 40px;">
					</p>
					<div id="buttonDiv" style="display: none">
						<p style="margin-left: 108px;">
							<a class="btn btn-default" href="#" onclick="UpladFile()" role="button">导入</a>
						</p>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">
	//Excel文件导入到数据库中
	function importEmp() {
		//检验导入的文件是否为Excel文件
		var excelPath = document.getElementById("excelFile").value;
		if (excelPath == null || excelPath == '') {
			// alert("请选择要上传的Excel文件");
			document.getElementById('buttonDiv').style.display = 'none';
			return;
		} else {
			var fileExtend = excelPath.substring(excelPath.lastIndexOf('.'))
					.toLowerCase();
				document.getElementById('buttonDiv').style.display = 'block';
// 			if (fileExtend == '.xls') {
// 			} else {
// 				alert("文件格式需为'.xls'格式");
// 				document.getElementById('buttonDiv').style.display = 'none';
// 				return;
// 			}
		}
	}
	function UpladFile() {
		$("#empForm").ajaxSubmit(
						{
							type : "POST",
							url : '${pageContext.request.contextPath }/old/oldMatch/importInformation.shtml',
							dataType : "json",
							success : function(data) {
								if (data == 1) {
									alert("导入成功!")
								} else {
									alert(data);
								}
								window.location.reload();
							}
						});
	}
</script>

