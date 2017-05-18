function lendon(){
	//市
	$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",function(data){
		for(var i = 0;i<data.length;i++){
			$("#city").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
		}
	});
	
	
	
	
	$("#city").change(function(){
		$("#county option:not(:first)").remove();
		$("#street option:not(:first)").remove();
		$("#community option:not(:first)").remove();
		var id = $("#city").val();
		$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:id},function(data){
			for(var i = 0;i<data.length;i++){
				$("#county").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
		});
	});
	$("#county").change(function(){
		$("#street option:not(:first)").remove();
		$("#community option:not(:first)").remove();
		var id = $("#county").val();
		$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id:id},function(data){
			for(var i = 0;i<data.length;i++){
				$("#street").append("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
			}
		});
	});

	$("#street").change(function(){
		$("#community option:not(:first)").remove();
		var id = $("#street").val();
		$.post("<%=request.getContextPath() %>/old/oldMatch/getRegionById.shtml",{id : id},function(data) {
				for (var i = 0; i < data.length; i++) {
					$("#community").append("<option value='"+data[i].id+"'>"+ data[i].name+ "</option>");
				}
			});
		});
}
			