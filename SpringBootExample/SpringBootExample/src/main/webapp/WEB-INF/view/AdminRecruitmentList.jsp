<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="Header.jsp"></jsp:include>
<jsp:include page="LeftMenu.jsp" />

<body>

<div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
		<h1>
			Recruitment List
		</h1>
		<ol class="breadcrumb">
			<li><a href="${pageContext.request.contextPath}/goToHomePage"><i class="fa fa-dashboard"></i> Home</a></li>
			<li class="active">Recruitment List</li>
		</ol>
	</section>

    <!-- Main content -->	
    <section class="content">
		<div class="box-body">
			<form action="${pageContext.request.contextPath}/adminrecruitmentList" id="adminRecruitmentList" name="adminRecruitmentList" method="post" >
			
				<div class="form-group col-sm-12" style="padding-right: 0px;">
					<div class="form-group col-sm-12" id="message">
						<h4><label style="color: green;"><b>${sessionScope.message}</b></label></h4>
					</div>
				</div>
		<table id="recruitmentListTable"
		class="table-checkbox table table-bordered table-striped" style="width: 100%;">
		<thead>
			<tr>
				<th width="50px" style="text-align: center;">SrNO</th>
				<th>Candidate Name</th>
				<th>Company Name</th>
				<th>Job Position</th>
				<th>Employment Type</th>
				<th>Status</th>
				<th>Location</th>
				

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${recruitmentDataList}" var="recruitment" varStatus="i">
				<tr>
					<td width="50px" style="text-align: center"><c:out
							value="${i.index + 1}"></c:out></td>
					<td>${recruitment.getCandidateMaster().getFull_name()}</td>
					<td>${recruitment.getCompanyMaster().getCompany_name()}</td>
					<td>${recruitment.getJobDetails().getJob_name()}</td>
					<td>${recruitment.getJobDetails().getEmployment_type()}</td>
					<td><c:if test="${recruitment.getStatus() eq 'P'}">
									<c:out value="Pending"></c:out>
								</c:if>
								<c:if test="${recruitment.getStatus() eq 'R'}">
									<c:out value="Rejected"></c:out>
								</c:if>
								<c:if test="${recruitment.getStatus() eq 'A'}">
									<c:out value="Accepted"></c:out>
								</c:if></td>
					<td>${recruitment.getJobDetails().getLocation()}</td>
					

				</tr>
			</c:forEach>
		</tbody>
	</table>
	    		</form>
    	
    	</div>
	</section>
    
</div>


<script type="text/javascript">
	$(document).ready(function(){
		if (document.getElementById('message').innerHTML != '')
	    	myVar = setInterval("hideMessage()", 10000);
	});
	
	
</script>
</body>
</html>