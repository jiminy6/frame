<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>添加客户</TITLE> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<LINK href="${pageContext.request.contextPath }/css/Style.css" type=text/css rel=stylesheet>
<LINK href="${pageContext.request.contextPath }/css/Manage.css" type=text/css rel=stylesheet>
<META content="MSHTML 6.00.2900.3492" name=GENERATOR>
</HEAD>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
$(function(){
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
})
</script>
<script type="text/javascript">
window.load=function(){
	alert("hello world")
}
</script>
<BODY>
	<FORM id=form1 name=form1 action="${pageContext.request.contextPath }/customer/customer_save.action" method=post enctype="multipart/form-data">
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_019.jpg" border=0></TD>
					<TD width="100%" background="${pageContext.request.contextPath }/images/new_020.jpg" height=20></TD>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_021.jpg" border=0></TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15 background="${pageContext.request.contextPath }/images/new_022.jpg">
						<IMG src="${pageContext.request.contextPath }/images/new_022.jpg" border=0>
					</TD>
					<TD vAlign=top width="100%" bgColor=#ffffff>
						<TABLE cellSpacing=0 cellPadding=5 width="100%" border=0>
							<TR>
								<TD class=manageHead>当前位置：客户管理 &gt; 添加客户</TD>
							</TR>
							<TR>
								<TD height=2></TD>
							</TR>
						</TABLE>						
						<TABLE cellSpacing=0 cellPadding=5  border=0>
						<tr>
						<td colspan="4">
						<font color="red">
						<s:fielderror></s:fielderror>
						</font>
						</td>
						</tr>
							<TR>
								<td>客户名称：</td>
								<td>
									<INPUT class=textbox id="cust_name" style="WIDTH: 180px" maxLength=50 name="cust_name">
								</td>
								<td>所属行业 ：</td>
								<td>
									<select name="baseDictIndustry.dictId" class=textbox id="cust_industry" style="WIDTH: 180px">
										<option value="">---请选择---</option>
									</select>
								</td>
							</TR>							
							<TR>	
								<td>信息来源 ：</td>
								<td>
									<select name="baseDictSource.dictId" class=textbox id="cust_source" style="WIDTH: 180px">
										<option value="">---请选择---</option>
									</select>
								</td>
								<td>客户级别：</td>
								<td>
									<select name="baseDictLevel.dictId" class=textbox id="cust_level" style="WIDTH: 180px">
										<option value="">---请选择---</option>
									</select>							
								</td>
							</TR>
							<TR>
								<td>联系地址 ：</td>
								<td>
									<INPUT class=textbox id=sChannel2 style="WIDTH: 180px" maxLength=50 name="cust_address">
								</td>
								<td>联系电话 ：</td>
								<td>
									<INPUT class=textbox id=sChannel2 style="WIDTH: 180px" maxLength=50 name="cust_phone">
								</td>
							</TR>
							<tr>
							<td>资质图片</td>
							
							<td colspan="3">
							<input type="file" name="upload">
							</td>
							</tr>
							<tr>
								<td rowspan=2>
									<INPUT class=button  type=submit value="保存" >
								</td>
							</tr>
						</TABLE>
					</TD>
					<TD width=15 background="${pageContext.request.contextPath }/images/new_023.jpg">
						<IMG src="${pageContext.request.contextPath }/images/new_023.jpg" border=0>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<TABLE cellSpacing=0 cellPadding=0 width="98%" border=0>
			<TBODY>
				<TR>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_024.jpg" border=0></TD>
					<TD align="center" width="100%" background="${pageContext.request.contextPath }/images/new_025.jpg" height=15></TD>
					<TD width=15><IMG src="${pageContext.request.contextPath }/images/new_026.jpg"	border=0></TD>
				</TR>
			</TBODY>
		</TABLE>
	</FORM>
</BODY>
</HTML>
