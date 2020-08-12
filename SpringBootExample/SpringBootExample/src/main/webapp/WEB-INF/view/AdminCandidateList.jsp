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
			Candidate List
		</h1>
		<ol class="breadcrumb">
			<li><a href="${pageContext.request.contextPath}/goToHomePage"><i class="fa fa-dashboard"></i> Home</a></li>
			<li class="active">Candidate List</li>
		</ol>
	</section>

    <!-- Main content -->	
    <section class="content">
		<div class="box-body">
			<form action="${pageContext.request.contextPath}/admincandidateList" id="admincandidateList" name="admincandidateList" method="post" >
			
				<div class="form-group col-sm-12" style="padding-right: 0px;">
					<div class="form-group col-sm-12" id="message">
						<h4><label style="color: green;"><b>${sessionScope.message}</b></label></h4>
					</div>
				</div>
			
			<table id="admincandidateListTable"
		class="table-checkbox table table-bordered table-striped" style="width: 100%;">
		<thead>
			<tr>
				<th width="50px" style="text-align: center;">SrNO</th>
				<th>Candidate Name</th>
				<th>Email</th>
				<th>Gender</th>
				<th>Date of Birth</th>
				<th>Active/Inactive</th>
				<th>Mobile No</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${candidateList}" var="candidate" varStatus="i">
				<tr>
					<td width="50px" style="text-align: center"><c:out
							value="${i.index + 1}"></c:out></td>
					<td>${candidate.getCandidateMaster().getFull_name()}</td>
					<td>${candidate.getCandidateMaster().getEmail_id()}</td>
					<td><c:if test="${candidate.getGender() eq 'F'}">
									<c:out value="Female"></c:out>
								</c:if>
								<c:if test="${candidate.getGender() eq 'M'}">
									<c:out value="Male"></c:out>
								</c:if></td>
					<td>${candidate.getDate_of_birth()}</td>
					<td><c:if test="${candidate.getCandidateMaster().getActive_inactive() eq 'Y'}">
									<c:out value="Yes"></c:out>
								</c:if>
								<c:if test="${candidate.getCandidateMaster().getActive_inactive() eq 'N'}">
									<c:out value="No"></c:out>
								</c:if></td>
					<td>${candidate.getMobile_no()}</td>
					
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