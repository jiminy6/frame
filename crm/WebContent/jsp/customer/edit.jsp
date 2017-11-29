<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>编辑客户</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css"
	type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css"
	type=text/css rel=stylesheet>
<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/default/easyui.css">
<link type="text/css" rel="stylesheet"
	href="${pageContext.request.contextPath}/css/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery.easyui.min.js"></script>
<script type="text/javascript">
	function getDictLevel(value, row, index) {
		if (row.baseDictLevel) {
			return row.baseDictLevel.dictItemName;
		}
	}
	function getDictSource(value, row, index) {
		if (row.baseDictSource) {
			return row.baseDictSource.dictItemName;
		}
	}
	function getDictIndustry(value, row, index) {
		if (row.baseDictIndustry) {
			return row.baseDictIndustry.dictItemName;
		}
	}
	$(function() {
		$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath }/baseDict/baseDict_findByTypeCode.action",
					data : "dictTypeCode=001",
					dataTpye : "json",
					success : function(msg) {
						for (var i = 0; i < msg.length; i++) {
							if (msg[i].dictId == <s:property value="viewCustomer.baseDictIndustry.dictId"/>) {
								$("#cust_industry").append(
										"<option value='"+msg[i].dictId+"'selected='selected'>"
												+ msg[i].dictItemName
												+ "</option")
							} 
							else {
								$("#cust_industry").append(
										"<option value='"+msg[i].dictId+"'>"
												+ msg[i].dictItemName
												+ "</option")
							}
						}
					}
				})
		$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath }/baseDict/baseDict_findByTypeCode.action",
					data : "dictTypeCode=002",
					dataTpye : "json",
					success : function(msg) {
						//msg是传入的json数据-->List<baseDict> 其中的每一个都是baseDict类型的数据
						for (var i = 0; i < msg.length; i++) {
							if (msg[i].dictId == '<s:property value="viewCustomer.baseDictSource.dictId"/>') {
								$("#cust_source").append(
										"<option value='"+msg[i].dictId+"'selected='selected'>"
												+ msg[i].dictItemName
												+ "</option")
							} 
							else {
								$("#cust_source").append(
										"<option value='"+msg[i].dictId+"'>"
												+ msg[i].dictItemName
												+ "</option")
							}
						}
					}
				})
		$.ajax({
					type : "post",
					url : "${pageContext.request.contextPath }/baseDict/baseDict_findByTypeCode.action",
					data : "dictTypeCode=006",
					dataTpye : "json",
					success : function(msg) {
						for (var i = 0; i < msg.length; i++) {
							if (msg[i].dictId == '<s:property value="viewCustomer.baseDictLevel.dictId"/>') {
								$("#cust_level").append(
										"<option value='"+msg[i].dictId+"'selected='selected'>"
												+ msg[i].dictItemName
												+ "</option")
							} else {
								$("#cust_level").append(
										"<option value='"+msg[i].dictId+"'>"
												+ msg[i].dictItemName
												+ "</option")
							}
						}
					}
				})

	});
</script>
</HEAD>

<BODY>
	<FORM id=form1 name=form1 action="${pageContext.request.contextPath }/customer/customer_update.action"
		method=post enctype="multipart/form-data">
		<input type="hidden" name="cust_id" value="${viewCustomer.cust_id}">
		<!-- 卧槽!你这个坑害的我好惨 -->
<table cellSpacing=0 cellPadding=5  border=0>
		<TR>
			<td>客户名称：</td>
			<td><INPUT class=textbox id="cust_name" value="<s:property value="viewCustomer.cust_name"/>" style="WIDTH: 180px"
				maxLength=50 name="cust_name"></td>
			<td>所属行业 ：</td>
			<td><select name="baseDictIndustry.dictId" class=textbox
				id="cust_industry" style="WIDTH: 180px">
					<option value="">---请选择---</option>
			</select></td>
		</TR>
		<TR>
			<td>信息来源 ：</td>
			<td><select name="baseDictSource.dictId" class=textbox
				id="cust_source" style="WIDTH: 180px">
					<option value="">---请选择---</option>
			</select></td>
			<td>客户级别：</td>
			<td><select name="baseDictLevel.dictId" class=textbox
				id="cust_level" style="WIDTH: 180px">
					<option value="">---请选择---</option>
			</select></td>
		</TR>
		<TR>
			<td>联系地址 ：</td>
			<td><INPUT class=textbox id=sChannel2 style="WIDTH: 180px"
				maxLength=50 name="cust_address" value="<s:property value="viewCustomer.cust_address"/>"></td>
			<td>联系电话 ：</td>
			<td><INPUT class=textbox id=sChannel2 style="WIDTH: 180px"
				maxLength=50 name="cust_phone" value="<s:property value="viewCustomer.cust_phone"/>"></td>
		</TR>
			<tr>
				<td><font>资质图片</font></td>
			<td colspan="3">
			<s:property value="viewCustomer.cust_filename"/>
			<input name="upload" type="file">
			</td>
			</tr>
			<!-- 搜索按钮 -->
			<a href="#" class="easyui-linkbutton" plain="true"
				onclick="doSearch()">筛选</a>
			<tr>
				<td rowspan=2><INPUT class=button type=submit value="保存">
				</td>
			</tr>
		</table>
	</FORM>
</BODY>
</HTML>
