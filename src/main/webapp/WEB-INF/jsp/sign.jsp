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
		　　//初始加载，表格宽度自适应
		    $(document).ready(function(){
		        fitCoulms();
		    });
		　　//浏览器窗口大小变化后，表格宽度自适应
		    $(window).resize(function(){
		        fitCoulms();
		    });
		})

	//表格宽度自适应，这里的#dg是datagrid表格生成的div标签
	function fitCoulms(){
	    $('#tt').datagrid({
	        fitColumns:true,
	        scrollbarSize:0,
	        method : 'get',
	        fit: true,
	        loadMsg: '正在加载中，请稍等... ',
	        nowrap:true,//允许换行	        
	        onLoadSuccess : function(data) {// Fires when data is
	          
	        }
	    });
	    $('#tt').datagrid('getPager').pagination({//分页栏下方文字显示
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
		/* alert($("input[name='end']").val());
		alert($("input[name='begin']").val()); */
		
		var opts = $("#tt").datagrid("options");
	    opts.url = "/wechat/list";
		$('#tt').datagrid('load', {
			begin: $("input[name='begin']").val(), //$('#begin').text(),
			end: $("input[name='end']").val(), //$('#end').text(),
			name: $('#name').val(),
			mode: $('#mode').val()
		});
	}
	 function formatMode(val,row){
	        if (val == 1){
	            return '签退';
	        } else {
	            return '签到';
	        }
	  }
	 
	 function ww3(date){  
         var y = date.getFullYear();  
         var m = date.getMonth()+1;  
         var d = date.getDate();  
         var h = date.getHours();  
         var min = date.getMinutes();  
         var sec = date.getSeconds();  
         var str=y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(min<10?('0'+min):min)+':'+(sec<10?('0'+sec):sec);  
         return str;  
     }  
     function w3(s){  
         if (!s) return new Date();  
         var y = s.substring(0,4);  
         var m =s.substring(5,7);  
         var d = s.substring(8,10);  
         var h = s.substring(11,13);  
         var min = s.substring(14,16);  
         var sec = s.substring(17,19);  
         if (!isNaN(y) &&!isNaN(m)&& !isNaN(d) && !isNaN(h) && !isNaN(min)&&!isNaN(sec)){  
             return new Date(y,m-1,d,h,min,sec);  
         } else {  
             return new Date();  
         }  
     }  
</script>
</head>
<body>
	<div style="width: 95%">
		<table style="width:100%;height:auto;">
			<tr>
				<td>
					<div id="tb" style="padding: 3px; height: auto; width: 100%;background-color: white;">

						<div style = "margin:10px 10px 10px 10px;">
							起始时间: <input class="easyui-datetimebox"
							style="width: 160px;" id="begin" name="begin" 
								data-options="formatter:ww3,parser:w3">
								 &nbsp;&nbsp;&nbsp;&nbsp;
								 终止时间:<input class="easyui-datetimebox"
								style="width: 160px" id="end" name="end" data-options="formatter:ww3,parser:w3">
								&nbsp;&nbsp;&nbsp;&nbsp;学生姓名:<input class="easyui-textbox"
								style="width: 180px;" id="name">
								&nbsp;&nbsp;&nbsp;&nbsp;打卡状态:
							<select  id="mode" class="easyui-combobox" name="mode" style="width:180px;" panelHeight="auto">
							  <option value="">所有</option>
							  <option value="0">签到</option>
							  <option value="1">签退</option>
							  
							</select>
							&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:doSearch()" id="btn_query"
								class="easyui-linkbutton" iconCls="icon-search">查询</a>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<table id="tt" class="easyui-datagrid"
						style="width: 100%; height: 490px;" toolbar="#tb" title="签到列表"
						iconCls="icon-save" rownumbers="true" pagination="true">
						<thead>
							<tr>
								<th field="name" width="80">学生姓名</th>
								<th field="mode" width="150" data-options="formatter:formatMode">打卡状态</th>
								<th field="time" width="80" align="right" sortable="true">打卡时间</th>
								<!-- <th field="unitcost" width="80" align="right"
									formatter="formatPrice">Unit Cost</th>
								<th field="attr1" width="150">Attribute</th>
								<th field="status" width="60" align="center">Stauts</th> -->
							</tr>
						</thead>
					</table>
				</td>
			</tr>
		</table>
	</div>
</body>

</html>