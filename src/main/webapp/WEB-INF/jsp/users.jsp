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
	src="/jquery-easyui-1.5.3/jquery.edatagrid.js"></script>
<script type="text/javascript"
	src="/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
<title></title>
<script type="text/javascript">

	$(function(){
		　　//初始加载，表格宽度自适应
		    $(document).ready(function(){
		        fitCoulms();
		    });
		　　//浏览器窗口大小变化后，表格宽度自适应
		    $(window).resize(function(){
		        fitCoulms();
		    });
		
		   //加载班级下拉列表
		   $.ajax({
			method: 'GET',
			url : '/user/classes',
			dataType: 'json',
			success:function(data){
				
				classnames = []
				classnames.push({"text": "全部", "id": "-1"})
				result = data; 
				if (result.status == 200){
					list_names = result.classnames;
					if(list_names.length > 0){
						$.each(list_names,function(index,value){
							classnames.push({"text": value, "id": value});
						});
					}					
				}
				$("#classname").combobox("loadData", classnames);
			},
	        error : function() {
	            alert('error');
	        }
			
		   });
		   
		   //加载数据
		   doSearch();
		})

	//表格宽度自适应，这里的#dg是datagrid表格生成的div标签
	function fitCoulms(){
	    $('#dg').datagrid({
	        fitColumns:true,
	        scrollbarSize:0,
	        method : 'get',
	        fit: true,
	        loadMsg: '正在加载中，请稍等... ',
	        nowrap:true,//允许换行	        
	        onLoadSuccess : function(data) {// Fires when data is
	          
	        }
	    });
	    $('#dg').datagrid('getPager').pagination({//分页栏下方文字显示
	    	beforePageText: '第',//页数文本框前显示的汉字  
	    	afterPageText: '页    共 {pages} 页', 
            displayMsg:'当前显示从第{from}条到{to}条 共{total}条记录',
            onBeforeRefresh:function(pageNumber, pageSize){
            $(this).pagination('loading');
             $(this).pagination('loaded');
          }
           });
	}
	function doSearch() {
		var opts = $("#dg").datagrid("options");
	    opts.url = "/user/list";
	    
		$('#dg').datagrid('load', {			
			nickname : $('#nickname').val(),
			name: $('#name').val(),
			classname: $('#classname').val()
			
		});
	}
	 
	 function ww3(date){  
         var y = date.getFullYear();  
         var m = date.getMonth()+1;  
         var d = date.getDate();  
         var h = date.getHours();  
         var min = date.getMinutes();  
         var sec = date.getSeconds();  
         var str=y+'/'+(m<10?('0'+m):m)+'/'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(sec<10?('0'+sec):sec);  
         return str;  
     }  
     function w3(s){  
         if (!s) return new Date();  
         var y = s.substring(0,4);  
         var m =s.substring(5,7);  
         var d = s.substring(8,10);  
         var h = s.substring(11,14);  
         var min = s.substring(15,17);  
         var sec = s.substring(18,20);  
         if (!isNaN(y) &&!isNaN(m)&& !isNaN(d) && !isNaN(h) && !isNaN(min)&&!isNaN(sec)){  
             returnnewDate(y,m-1,d,h,min,sec);  
         } else {  
             return new Date();  
         }  
     }
     
     function formatOper(val,row,index){  
    	    return '<a href="#" onclick="editUser('+row.id+')">修改</a>&nbsp;&nbsp; <a href="#" onclick="alert('+ row.id +')">删除</a>';  
    	}
     
   //url：窗口调用地址，title：窗口标题，width：宽度，height：高度，shadow：是否显示背景阴影罩层
     function showMessageDialog(url, title, width, height, shadow) {
         var content = '<iframe src="' + url + '" width="100%" height="99%" frameborder="0" scrolling="no" ></iframe>';
         //var boarddiv = '<div id="msgwindow" title="' + title + '"></div>'//style="overflow:hidden;"可以去掉滚动条
         //$(document.body).append(boarddiv);
         msgwindow = $('#msgwindow');
         msgwindow.title = title;
         var win = msgwindow.dialog({
             content: content,
             width: width,
             height: height,
             modal: shadow,
             title: title,
             onClose: function () {
                 //$(this).dialog('destroy');//后面可以关闭后的事件
             }
         });
       /* //点击保存,假定保存成功  
         $('btnsave').click(function(){  
             $.messager.alert("提示信息","操作成功!","info");  
             //这里在后面加true,将会绕过onBeforeClose事件,弹出框直接关闭  
             $('#msgwindow').dialog("close",true);  
         });  
           
         //点击关闭  
         $('#btnclose').click(function(){  
             $('#msgwindow').dialog("close");  
         });   */
         
         win.dialog('open');
     }
   
   function addUser(){
	   url = "/user/addform";
	   title = "新增";
	   width = 700;
	   height = 400;
	   shadow = true;
	   showMessageDialog(url, title, width, height, shadow);
   }
     
    
</script>
</head>
<body>
	<div style="width: 95%">
		<table id="dg" title="用户列表" style="width: 100%; height: 490px" class="easyui-datagrid"
			toolbar="#query" idField="id" rownumbers="true" fitColumns="true" pagination="true"  
			singleSelect="true">
			<thead>
				<tr>
					<th field="name" width="50">学生姓名</th>
					<th field="classname" width="50">班级名称</th>
					<th field="nickname" width="50">微信昵称</th>					
					<th field="wechatno" width="50">微信号</th>
					<th data-options="field:'_operate',width:80,align:'center',formatter:formatOper">操作</th>  
				</tr>
			</thead>
		</table>
		<div id="query" style="background-color: white;">
			<div style="padding: 3px; height: auto; width: 100%;">
				学生姓名: <input class="easyui-textbox" style="width: 180px;" id="name">
				&nbsp;&nbsp;&nbsp;&nbsp; 微信昵称: <input id="nickname"
					class="easyui-textbox" name="nickname" style="width: 180px;">
				&nbsp;&nbsp;&nbsp;&nbsp; 班级名称: <input class="easyui-combobox"
					id="classname" name="classname" style="width: 335px"
					data-options="valueField:'id', textField:'text', panelHeight:'auto'">
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:doSearch()"
					id="btn_query" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			</div>
			<div style="border-top: 1px solid #95b8e7">
				<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
					onclick="javascript:addUser();">新增</a>
					<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
					onclick="javascript:$('#dg').edatagrid('saveRow')">修改</a> <a href="#"
					class="easyui-linkbutton" iconCls="icon-remove" plain="true"
					onclick="javascript:$('#dg').edatagrid('destroyRow')">删除</a> --><a
					href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true"
					onclick="javascript:$('#dg').edatagrid('cancelRow')">打印</a>
			</div>
		</div>
		

	</div>
<div id="msgwindow"  ></div>
</body>
</html>