<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<%=path%>/style/new.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/style/ul.css" rel="stylesheet" type="text/css">
<script src="<%=basePath%>script/My97DatePicker/WdatePicker.js"></script>
<script src="<%=path%>/script/jquery-1.9.1.min.js"></script>
<script src="<%=path%>/script/common.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<jsp:include page="/index/top.jsp"></jsp:include>
		<div>
			<form id="form1" action="MeetingTrainingServlet" method="post">
				<label for="roomID">会议室:</label> 
				<select name="_roomID" style="width:260px;">
					<option value="">请选择会议室</option>
					<c:forEach items="${mrs }" var="mr">
						<option value="${mr.id }">${mr.building }${mr.room }</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp; 
				<label for="org">部门:</label> 
				<select id="org" name="org"
					class="Wdate" style="height: 20px;">
					<option value="">-------------</option>
					<c:forEach items="${orgs }" var="o">
						<option value="${o[0] }"
							<c:if test="${org == o[0] }">selected="selected"</c:if>>${o[1]
							}</option>
					</c:forEach>
				</select>
				&nbsp;&nbsp;&nbsp;&nbsp; 
				<label for="content">培训类容:</label> 
				<input id="content" name="content" type="text" value="${content }" size="35"><br/><br/> 
				<label for="starttime">开始日期:</label> <input class="Wdate" id="st" type="text" name="starttime"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'st2\')}'})" value="" /> 
				<label for="starttime2">&nbsp;&nbsp;至:&nbsp;&nbsp;</label>
				<input class="Wdate" id="st2" type="text" name="starttime2"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'st\')}'})" value="" />
					&nbsp;&nbsp;&nbsp;&nbsp; <label for="endtime">结束日期:</label> 
					<input class="Wdate" id="et" type="text" name="endtime"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'et2\')}'})" value="" /> 
					<label for="endtime2">&nbsp;&nbsp;至:&nbsp;&nbsp;</label>
				<input class="Wdate" id="et2" type="text" name="endtime2"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'et\')}'})" value="" /> &nbsp;&nbsp;
					<input style="COLOR: #44606B; background-color: #FFFFFF" type="submit" value="搜索">&nbsp;&nbsp;
					<input style="COLOR: #44606B; background-color: #FFFFFF" type="button" value="清空" onclick="clearForm(this.form)">  
					<input type="hidden" name="ctrl" value="list" />
					<input type="hidden" id="roomName" name="roomName" value="" />
					<script type="text/javascript">
						$(function(){
							var rn = $("#child optin:selected").text();
							$("#roomName").val(rn);
						});
				</script>
			</form>
			<br style="clear"/>
		</div>
		<div style="text-align: center;margin: 0 auto;">
			<table class="dtable" align="center" cellspacing="1">
				<tr>
					<th width="15%" align="center">日期</th>
					<th width="10%" align="center">课程名称</th>
					<th width="5%" align="center">教员</th>
					<th width="15%" align="center">参加单位/人员</th>
					<th width="10%" align="center">有关说明</th>
					<th width="10%" align="center">预定教室</th>
					<th width="10%" align="center">分配教室</th>
					<th width="10%" align="center">状态</th>
					<th width="12%" align="center">申请部门<br />日期</th>
					<th width="3%" align="center">操作</th>
				</tr>
				<c:forEach items="${meetings }" var="mp">
					<tr>
						<td width="10%" align="center"><fmt:formatDate
								value="${mp.starttime }" pattern="E" /><br /> <fmt:formatDate
								value="${mp.starttime }" pattern="yyyy-MM-dd HH:mm" /><br />
							<fmt:formatDate value="${mp.endtime }"
								pattern="yyyy-MM-dd HH:mm" /></td>
						<td width="10%" align="center">${mp.content }</td>
						<td width="5%" align="center">${mp.presider }</td>
						<td width="10%" align="center">${mp.depart }</td>
						<td width="10%" align="center">${mp.remark }</td>
						<td width="10%" align="center">${mp.reserve_address }</td>
						<td width="10%" align="center">${mp.address1 }</td>
						<td width="10%" align="center"><c:if
								test="${mp.status =='0' }">
								<font color="">未处理</font>
							</c:if> <c:if test="${mp.status =='1' }">
								<font color="green">已审批</font>
							</c:if> <c:if test="${mp.status =='2' }">
								<font color="red">拒批</font>
							</c:if> <c:if test="${mp.status =='3' }">
								<font color="">已安排</font>
							</c:if> <c:if test="${mp.status =='4' }">
								<font color="">退回</font>
							</c:if></td>
						<td width="12%" align="center">${mp.commitdepart }<br />
							<fmt:formatDate value="${mp.committime }"
								pattern="yyyy-MM-dd HH:mm" /></td>
					<td width="3%" align="center">
							<c:if test="${from =='m' }">
								<a href="MeetingTrainingServlet?ctrl=toUpdate&show=all&id=${mp.id }">修改</a>
							</c:if> 
							<a href="javascript:confirmDelete('MeetingServlet?ctrl=del&id=${mp.id }')">删除</a>
						</td> 
					</tr>
				</c:forEach>
				<tr>
					<td align="right" colspan="11">${tag }</td>
				</tr>
			</table>
			<br class="clear"/>
			<br class="clear"/>
		</div>
	</div>
	<jsp:include page="/index/bottom.jsp"/>
</body>
</html>