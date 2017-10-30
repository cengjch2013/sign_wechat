<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css"
	href="/jquery-easyui-1.5.3/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="/jquery-easyui-1.5.3/themes/icon.css" />
<script type="text/javascript" src="/jquery-easyui-1.5.3/jquery.min.js"></script>
<script type="text/javascript"
	src="/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
<title></title>
<script type="text/javascript">


$(function(){
	　　//初始化配置数据
	    $(document).ready(function(){
	    	//配置信息
	 	   $.ajax({
	 		method: 'GET',
	 		url : '/config/list',
	 		dataType: 'json',
	 		success:function(data){
	 			result = data; 
	 			if (result.status == 200){
	 				map = result.data;
	 				ip = map.ip;
	 				$("#ip").attr("value",map.ip);
	 				$("#projectname").val(map.projectname);
	 			}
	 			
	 		},
	       error : function() {
	           alert('error');
	       }
	 		
	 	   });
	    });   
	   
	});
	
	function saveConfig(){
		ip = $("#ip").val();
		if (ip == null || ip == ""){
			$.messager.alert('系统提示', '请输入签到机IP！','error');
			return false;

		}
		projectname = $("#projectname").val();
		if(projectname == ""){
			$.messager.alert('系统提示', '请输入机构名称！','error');
			return false;
		}
		param = {
				ip:ip,
				projectname:projectname
		}
		 $.ajax({
		 		method: 'POST',
		 		url : '/config/save',
		 		dataType: 'json',
		 		data: param,
		 		success:function(data){
		 			result = data; 
		 			if (result.status == 200){
		 				$.messager.alert('系统提示', '保存成功！','info');
		 			}else{
		 				$.messager.alert('系统提示', result.msg ,'error');
		 			}		 			
		 		},
		       error : function() {
		           alert('error');
		       }
		 		
		 	   });
	}

</script>

<style type="text/css">
.textbox {
  position: relative;
  border: 1px solid #95B8E7;
  background-color: #fff;
  vertical-align: middle;
  display: inline-block;
  overflow: hidden;
  white-space: nowrap;
  margin: 0;
  padding: 4px;
  -moz-border-radius: 5px 5px 5px 5px;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
}


</style>
</head>
<body>
	<table class="dv-table"
		style="width: 400px; background: #fff; padding: 5px; margin-top: 5px;">
		<tr>
			<td align = "right">签到机IP:</td>
			<td align = "left"><input id="ip" name="ip" class="textbox"></input></td>

		</tr>
		<tr>
			<td align = "right">机构名称:</td>
			<td align = "left"><input name="projectname" id="projectname"
				class="textbox"></input></td>

		</tr>
		<tr>
			<td></td>
			<td align = "left"><div
					style="padding: 5px 0; padding-right: 30px">
					<a href="javascript:void(0)" id="btnsave" class="easyui-linkbutton"
						iconCls="icon-save" onclick="saveConfig()">保存</a>
				</div></td>
		</tr>
	</table>

</body>
</html>