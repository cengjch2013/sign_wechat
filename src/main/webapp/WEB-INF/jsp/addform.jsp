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
<title></title>

<script type="text/javascript">
$(function(){
	　　
	
	   //加载班级下拉列表
	   $.ajax({
		method: 'GET',
		url : '/user/classes',
		dataType: 'json',
		success:function(data){			
			classnames = []
			//classnames.push({"text": "全部", "id": "-1"})
			result = data; 
			if (result.status == 200){				
				list_names = result.classnames;
				if(list_names.length > 0){
					$.each(list_names,function(index,value){
						classnames.push({"text": value, "id": value});
					});
				}		
			}
			if (classnames.length > 0)
				$("#addclassname").combobox("loadData", classnames);
		},
      error : function() {
          alert('error');
      }
		
	   });
	})
	
	function saveuser(){
	
	name = $("#name").val();
	if (name == null || name == ""){
		$.messager.alert('系统提示', '请输入学生姓名！','error');
		return false;

	}
	nickname = $("#nickname").val();
	if(nickname == ""){
		$.messager.alert('系统提示', '请输入微信昵称！','error');
		return false;
	}
	classname = $("#addclassname").val();
	if(classname == ""){
		$.messager.alert('系统提示', '请输入或选择班级名称！','error');
		return false;
	}
	
	
	 param={
			  name: $("#name").val(),
			  nickname:$("#nickname").val(),
			  classname:$("#addclassname").val(),
			  wechatno:$('#wechatno').val()
	  }
	   
	
	
	 //加载班级下拉列表
	   $.ajax({
		method: 'POST',
		url : '/user/add',
		dataType: 'json',
		data: param,
		success:function(data){		
			result = data; 
			if (result.status == 200){
				alert('success');
			}
			
		},
	   error : function() {
	       alert('error');
	   }
		
	}); 
	
    }

   function dialog_close(){
	   //$(this).parent().parent("msgwindow").window('close');
	   
	  /*  div = $('#msgwindow');
	   div2 =$(this).parent("msgwindow");
	   alert($('#msgwindow'));
	   alert($(this).parent("msgwindow")); */
	   
	   $('#msgwindow').parent().dialog("close",true);  
	   $('#msgwindow').parent().window("close");
	   //$(this).parent("msgwindow").dialog('close')
	   
   } 
   
</script>

</head>
<body>
	<form method="post" url="/user/add" id ="adduser">
		<table class="dv-table"
			style="width: 100%; background: #fff; padding: 5px; margin-top: 5px; border-bottom: solid 1px #b7d2ff;">
			<tr>
				<td>姓名</td>
				<td><input id="name" name="name" class="easyui-validatebox"
					required="true"
					data-options="required:true, missingMessage:'请输入姓名'"></input></td>
				<td>班级名称</td>
				<td><input class="easyui-combobox" id="addclassname"
					name="classname" style="width: 335px"
					data-options="valueField:'id', textField:'text', panelHeight:'auto',
					required:true, missingMessage:'请输入班级名称'"></input></td>
			</tr>
			<tr>
				<td>微信昵称</td>
				<td><input name="nickname" id="nickname" class="easyui-validatebox"
					data-options="required:true, missingMessage:'请输入微信昵称'"></input></td>
				<td>微信号</td>
				<td><input name="wechatno" id="wechatno"></input></td>
			</tr>
		</table>
		<div style="padding: 5px 0; text-align: right; padding-right: 30px">
			<a href="javascript:void(0)" id="btnsave" class="easyui-linkbutton"
				iconCls="icon-save" plain="true" onclick="saveuser()">保存</a> <a
				href="javascript:void(0)" id="btnclose" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="dialog_close()">关闭</a>
		</div>
	</form>
</body>
</html>