<%@page import="com.gxuwz.medical.domain.payRecord.PayRecord"%>
<%@page import="com.gxuwz.medical.domain.accountArchives.AccountArchives"%>
<%@page import="com.gxuwz.medical.domain.homearchives.Homearchives"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/base.jsp"%>


<!DOCTYPE html>
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">
<title>未缴费的家庭成员列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<link rel="stylesheet" href="css/styles.css">
<link rel="stylesheet" href="css/admin.css">
<script src="js/jquery.js"></script>
<script src="third/layer-v3.1.1/layer/layer.js">
<script src="js/admin.js"></script>
<script type="text/javascript">
   $(document).ready(function() {
		//重新绑定表单提交
		
		$("#add_btn").bind("click", function() {
			// 获取span标签中的缴费金额，并添加至input标签进行提交
			var payAccountStr = document.getElementById("amountSpan").innerHTML;
			var payAccount = payAccountStr.substring(payAccountStr.indexOf("￥")+1);
			$("#payAccount").attr("value", payAccount);
			if($("#invoiceNum").val() == null || $("#invoiceNum").val() == "") {
				alert("参合发票号不能为空！");
				return false;
			}
			 if($(":checkbox[name='ids']:checked").length==0){
	    		alert("至少勾选一个选项");
	   		 }else{
	   		 	$('form').submit();
	   		 	closeLayer();
	   		 	
	   		 }
	   
		});
		
		// 复选框的全选和全不选
	    $("#selectAll").click(function() {
	        $(":checkbox[name='ids']").attr("checked", this.checked); // this指代的你当前选择的这个元素的JS对象
	    });
	    calAccount();     
	});
    
  // 计算缴费总金额
  	function calAccount() {
	  	// 获取复选框选中的个数
	    var payNum = $(":checkbox[name='ids']:checked").length;
	   	var ids = document.getElementsByName("ids");
	    	var cardids = [];
	   	for(var i=0;i<ids.length;i++){
	   		if(ids[i].checked){
	   			cardids[i]=ids[i].value;
	   		}
	   	}		
	   	/*$("#cards").val(cardids); */
	    // 异步请求计算需缴费总金额
		$.ajax({
			type:"post",
			url:"<%=path%>/system/PayRecordServlet?m=calAccount&ids="+cardids,
			data:{payNum:payNum},
			success:function(data){
				var span = document.getElementById("amountSpan");
				span.innerHTML = "￥ " + data;
			}
		});
	}

// 关闭layer弹出层
	function closeLayer() {
		var index = parent.layer.getFrameIndex(window.name); // 获取父窗口索引
	 	parent.layer.close(index) // 关闭layer
	}
	
</script>

</head>
<body>
<form method="post" action="<%=path%>/system/PayRecordServlet?m=add">
	<div class="panel admin-panel">
		<div class="panel-head">
			<strong class="icon-reorder">&nbsp;&nbsp;未缴费的家庭成员列表</strong>
		</div>
		<div class="padding border-bottom">
				<ul class="search" style="padding-left:10px;">
					<li>
					  		<button id="add_btn" class="button bg-main icon-check-square-o" type="button" >缴费</button>
					 </li>
					 <!-- <li>
					 		<button id="exel_btn" class="button bg-main icon-check-square-o" type="button" >导出</button>
					 </li> -->
				</ul>
		</div>
		<table class="table table-hover text-center">
			<tr>	
				<th><input type="checkbox" id="selectAll" onchange="calAccount()" />序号</th>
				<th>家庭编号</th>
				<th>与户主关系</th>
				<th>身份证号</th>
				<th>参合证号</th>
				<th>姓名</th>
				<th>性别</th>
				<th>年龄</th>
				<th>健康状况</th>
				<th>联系电话</th>
			</tr>
			
	<% 
		List<String> homeAllPersonsid =(List)request.getAttribute("homeAllPersonsid");
		List<String> hasPayPersonsid =(List)request.getAttribute("hasPayPersonsid");
		homeAllPersonsid.removeAll(hasPayPersonsid);
		AccountArchivesDao dao = new AccountArchivesDao();
		
		for (int i = 0; i < homeAllPersonsid.size(); i++) {
		String sql = "select * from t_accountarchives where cardid=? order by cardid ";
		Object[] params = {homeAllPersonsid.get(i)};
		List<AccountArchives> list = dao.queryObject(sql, params);
		
	%>
	
	 <%
			for (int j = 0; j < list.size(); j++) {
			AccountArchives  model = (AccountArchives) list.get(j);
					
	%> 
	
		 	<tr>
				<td align="center"><input type="checkbox" id="checkbox" onchange="calAccount()" name="ids" value=" <%=model.getCardid() %> " /> <%=i+1 %> </td>
				<td> <%=model.getHomeid() %> </td>
				<td><%=model.getRelationship() %></td>
				<td><%=model.getCardid() %></td>
				<td> <%=model.getNongheCard() %></td>
				<td><%=model.getName() %></td>
				<% if(model.getSex().equals("0")){ %>
				<td>女 </td>
				<%}else if(model.getSex().equals("1")){ %>
				<td>男 </td>
				<%}else{ %>
				<td></td>
				<%}%>
				<td> <%=model.getAge() %> </td>
				<% if(model.getHealthstatus().equals("0")){ %>
				<td> 不健康 </td>
				<% }else{ %>
				<td> 健康</td>
				<%} %>
				<td> <%=model.getPhone() %></td>
			</tr>
		
	<%
		}
	%> 
	<%
		}
	%>
	</table> 
			<!-- 当家庭成员已全部完成参合缴费时,进行提示 -->
			
		<%
			if(homeAllPersonsid.size() == 0 || "0".equals(homeAllPersonsid)) {
		 %> 
			<h1 style="margin-left: 300px">家庭成员已全部完成参合缴费</h1>
		 <%
			}
		 %> 
		 <table class="table text-center" style="margin-top: 80px;">
			<tr>	
				<td>
					<input type="text" class="input w50" id="invoiceNum" name="invoiceNum" placeholder="请输入参合发票号" data-validate="required:请输入参合发票号 " />
					<span style="font-size: 16px">参合缴费总金额：<span id="amountSpan" style="font-size: 16px; color: red">  </span></span>
				</td>
			</tr>
		 </table>
	</div>
	</form> 
</body>
</html>