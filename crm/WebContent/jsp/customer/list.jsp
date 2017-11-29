<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>客户列表</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/default/easyui.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
<script type="text/javascript">
function getDictLevel(value,row,index){
	if(row.baseDictLevel){
		return row.baseDictLevel.dictItemName;
	}
}
function getDictSource(value,row,index){
	if(row.baseDictSource){
		return row.baseDictSource.dictItemName;
	}
}
function getDictIndustry(value,row,index){
	if(row.baseDictIndustry){
		return row.baseDictIndustry.dictItemName;
	}
}
//获取级别名称的
	function getLevelName(value,row,index){
		if(row.baseDictLevel){
			return row.baseDictLevel.dict_item_name;
		}
	}
	
	//获取来源名称的
	function getSourceName(value,row,index){
		if(row.baseDictSource){
			return row.baseDictSource.dict_item_name;
		}
	}
	
	//获取行业名称的
	function getIndustryName(value,row,index){
		if(row.baseDictIndustry){
			return row.baseDictIndustry.dict_item_name;
		}
	}
	$(function() {
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath }/baseDict/baseDict_findByTypeCode.action",
			data:"dictTypeCode=001",
			dataTpye:"json",
			success:function(msg){
				for(var i=0;i<msg.length;i++){
					$("#cust_industry").append("<option value='"+msg[i].dictId+"'>"+msg[i].dictItemName+"</option")
				}
			}
		})
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath }/baseDict/baseDict_findByTypeCode.action",
			data:"dictTypeCode=002",
			dataTpye:"json",
			success:function(msg){
				//msg是传入的json数据-->List<baseDict> 其中的每一个都是baseDict类型的数据
				for(var i=0;i<msg.length;i++){
					$("#cust_source").append("<option value='"+msg[i].dictId+"'>"+msg[i].dictItemName+"</option")
				}
			}
		})
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath }/baseDict/baseDict_findByTypeCode.action",
			data:"dictTypeCode=006",
			dataTpye:"json",
			success:function(msg){
				for(var i=0;i<msg.length;i++){
					$("#cust_level").append("<option value='"+msg[i].dictId+"'>"+msg[i].dictItemName+"</option")
				}
			}
		})
		$('#dg').datagrid(
						{
							url : '${pageContext.request.contextPath}/customer/customer_page.action',
							columns : [ [ {
								field : 'cust_name',
								title : '客户名称',
								width:100
							}, {
								field : 'baseDictLevel.dictItemName',
								formatter:getDictLevel,
								title : '客户级别',
								width:100
							}, {
								field : 'baseDictSource.dictItemName',
								formatter:getDictSource,
								title : '客户来源',
								width:100
							}, {
								field : 'baseDictIndustry.dictItemName',
								formatter:getDictIndustry,
								title : '所属行业',
								width:100
							}, {
								field : 'cust_address',
								title : '联系地址',
								width:100
							}, {
								field : 'cust_phone',
								title : '联系电话',
								width:100
							} ] ],
							fitColumns:true,
							pagination:true,
							pageList:[2,4,8],
							pageSize:4
						});
		
	
	});
	//点击搜索按钮执行的函数
	function doSearch() {
		$('#dg').datagrid('load', {
			'cust_name' : $('#cust_name').val(),
			'baseDictLevel.dictId' : $('#cust_level').val(),
			'baseDictSource.dictId' : $('#cust_source').val(),
			'baseDictIndustry.dictId' : $('#cust_industry').val()
		});
	}
	//编辑客户函数
	function editCustomer(){
		var row=$("#dg").datagrid('getSelected');
		if(row){
			$.messager.confirm("系统消息","你确定要修改这个记录吗",function(r){
				// 如果确认修改
				if(r){
					location.href="${pageContext.request.contextPath}/customer/customer_findById.action?cust_id="+row.cust_id;
						}
			})
		}
		
	}
	
	//删除客户函数
	function destroyCustomer(){
		//选中的行
		var row=$("#dg").datagrid('getSelected');
		if(row){
			$.messager.confirm("系统消息","你确定要删除这个记录吗",function(r){
				// 如果确认删除
				if(r){
					location.href="${pageContext.request.contextPath}/customer/customer_delete.action?cust_id="+row.cust_id;
						}
			})
		}
	}
</script>
</HEAD>
<BODY>
	<!-- 表格上方的工具栏 -->
	<div id="tb" style="padding: 3px">
		<!-- 操作栏：包括编辑、删除 -->
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCustomer()">编辑客户</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyCustomer()">删除客户</a> 
		<br>
		<!-- 搜索栏 -->
		<span>客户姓名:</span><input id="cust_name" type="text">
		<span>客户级别:</span>
		<select id="cust_level">
			<option value="">---请选择---</option>
		</select>
		<span>客户来源:</span>
		<select id="cust_source">
			<option value="">---请选择---</option>
		</select>
		<span>所属行业:</span>
		<select id="cust_industry">
			<option value="">---请选择---</option>
		</select>
		<!-- 搜索按钮 -->
		<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">筛选</a>
	</div>
	<!-- 显示数据的表格，toolbar属性：指定表格的工具栏 -->
	<table id="dg" toolbar="#tb" width="98%"></table>
</BODY>
</HTML>
