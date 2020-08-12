<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="Header.jsp"></jsp:include>
<jsp:include page="LeftMenu.jsp"></jsp:include>
<jsp:include page="DefaultJSCall.jsp"></jsp:include>
<body>
	 <div class="content-wrapper">
	    

	    <!-- Main content -->
	  
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>Candidate Reports</h1>
			<ol class="breadcrumb">
				<li><a href="${pageContext.request.contextPath}/validUser"><i
						class="fa fa-dashboard"></i> Home</a></li>
				<li class="active"> Candidate Reports</li>
			</ol>
		</section>
		<!-- Main content -->
		<section class="content">
			<div class="box box-info">
				<div class="box-header with-border">
					<!-- <h3 class="box-title">Add Area</h3> -->
					<div class="col-sm-1" align="left" style="padding-left: 0px;">
						<a href="${pageContext.request.contextPath}/validUser">
							<button onclick="areaList()" >
								<i class="fa fa-fw fa-hand-o-left" title="Back"></i>
							</button>
						</a>
					</div>
					<div class="col-sm-10 form-group col-sm-6" id="message" style="margin-bottom: 0px;">
						<label style="color: green; margin-bottom: 0px; margin-top: 0px; height: 15px;"><b>${message}</b></label>
						<label style="color: red; margin-bottom: 0px; margin-top: 0px; height: 15px;"><b>${errorMessage}</b></label>
					</div>
				<form
					action="${pageContext.request.contextPath}/getCandidateDetailsReport"
					id="candidateReportform" name="candidateReportform"
					method="post">
					<div class="form-group col-sm-12" style="padding-right: 0px;">
						<div class="form-group col-sm-12" id="message">
							<h4>
								<label style="color: green;"><b>${sessionScope.message}</b></label>
							</h4>
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-3">
							<label class="control-label">Candidate Full Name</label> <select
								name="candidate_id" id="candidate_id" class="form-control"
								style="width: 100%;">
								<option value="0">----Select----</option>
								<c:forEach items="${candidateList}" var="candidate">
									<option value="${candidate.candidate_id}">${candidate.full_name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-3">
							<label class="control-label">City</label> <select
								name="city_id" id="city_id" class="form-control"
								style="width: 100%;">
								<option value="0">-----Select-----</option>
								<c:forEach items="${cityList}" var="city">
									<option value="${city.city_id}">${city.city}</option>
								</c:forEach>
							</select>
						</div>
							<div class="col-sm-3">
							<label class="control-label">State</label> <select
								name="state_id" id="state_id" class="form-control"
								style="width: 100%;">
								<option value="0">-----Select-----</option>
								<c:forEach items="${stateList}" var="state">
									<option value="${state.state_id}">${state.state}</option>
								</c:forEach>
							</select>
						</div>
					</div> <label>&nbsp;</label>
					<button type="button" id="search" class="btn btn-block btn-primary" onclick="searchCandidateReport();" style="width: 80px;">Search</button>
					<br/><br/>
					<br/><br/>
					<div id="candidateReportDiv"></div>
				</form>
			</div>
		</section>
	</div>
			
	    
    <!-- /.content -->


	<script type="text/javascript">
		$(document).ready(function() {
			if (document.getElementById('message').innerHTML != '')
				myVar = setInterval("hideMessage()", 10000);
		});

		function applyJob() {
			document.candidateReportform.action = "${pageContext.request.contextPath}/getCandidateDetailsReport?candidate_id="+ candidate_id +'&city_id=' + city_id +
				'&state_id=' + state_id;
			document.candidateReportform.submit();
		}
		function searchCandidateReport(){
			var candidate_id=document.getElementById("candidate_id").value;
			var state_id=document.getElementById("state_id").value;
			var city_id=document.getElementById("city_id").value;
			$.get(
					'${pageContext.request.contextPath}/getCandidateReportbyfull_name?candidate_id='+ candidate_id +'&city_id=' +city_id +
							'&state_id=' + state_id , null, function(resp) {
						console.log(resp.trim());
						if(resp.trim() != 'expired') {
					 		$("#candidateReportDiv").empty();
							$('#candidateReportDiv').html(resp.trim());
							$("#test").hide();
							 $('#candidateReportTable').DataTable({
								  "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
								 "iDisplayLength": 10,  
								// "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
								// "iDisplayLength": "All", 
								 "scrollX": true,
								 "ordering": false,
								 paging: false,
							        dom: 'Bfrtip',
							        buttons: [
							        	{
							                extend: 'copy',
							                title: 'Attendance Register'
						            	}, 
							        	{
							                extend: 'csv',
							                title: 'Attendance Register'
						            	}, 
							        	{
							                extend: 'excel',
							                title: 'Attendance Register'
						            	}, 
						            	 {
							                extend: 'pdf',
							                title: 'Stockist List'
						            	}, 
						            	{
							                extend: 'print',
							                title: 'Attendance Register'
						            	}
									]
								});
								$("#test").hide();
						}
					 	else
					 		window.location('${pageContext.request.contextPath}/login');
						});
		}
		
	</script>
	
</body>
</html>