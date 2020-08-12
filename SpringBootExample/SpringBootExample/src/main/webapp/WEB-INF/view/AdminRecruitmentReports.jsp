<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="Header.jsp"></jsp:include>
<jsp:include page="LeftMenu.jsp"></jsp:include>

<body>
	 <div class="content-wrapper">
	    

	    <!-- Main content -->
	  
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>Recruitment Reports</h1>
			<ol class="breadcrumb">
				<li><a href="${pageContext.request.contextPath}/validUser"><i
						class="fa fa-dashboard"></i> Home</a></li>
				<li class="active"> Recruitment Reports</li>
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
					action="${pageContext.request.contextPath}/getRecruitmentDetails"
					id="recruitmentReportForm" name="recruitmentReportForm"
					method="post">
					<div class="form-group col-sm-12" style="padding-right: 0px;">
						<div class="form-group col-sm-12" id="message">
							<h4>
								<label style="color: green;"><b>${sessionScope.message}</b></label>
							</h4>
						</div>
					</div>

					<div class="form-group">
					<div class="col-sm-2">
							<label class="control-label">Candidate Full Name</label> <select
								name="candidate_id" id="candidate_id" class="form-control"
								style="width: 100%;">
								<option value="0">----Select----</option>
								<c:forEach items="${candidateList}" var="candidate">
									<option value="${candidate.candidate_id}">${candidate.full_name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<label class="control-label">Company</label> <select
								name="company_id" id="company_id" class="form-control"
								style="width: 100%;" onchange="getJobTitle(this.value);">
								<option value="0">----Select----</option>
								<c:forEach items="${companyList}" var="company">
									<option value="${company.company_id}">${company.company_name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-3">
							<label class="control-label">Job Name</label> <select
								name="job_details_id" id="job_details_id" class="form-control"
								style="width: 100%;">
								<option value="0">-----Select-----</option>
								<c:forEach items="${jobList}" var="job">
									<option value="${job.job_details_id}">${job.job_name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<label class="control-label">Employment Type</label> <select
								name="job_details_id" id="job_details_id" class="form-control"
								style="width: 100%;">
								<option value="0">-----Select-----</option>
								<c:forEach items="${jobList}" var="job">
									<option value="${job.job_details_id}">${job.employment_type}</option>
								</c:forEach>
							</select>
						</div>
						<div class="col-sm-2">
							<label class="control-label">Location</label> <select
								name="job_details_id" id="job_details_id" class="form-control"
								style="width: 100%;">
								<option value="0">-----Select-----</option>
								<c:forEach items="${jobList}" var="job">
									<option value="${job.job_details_id}">${job.location}</option>
								</c:forEach>
							</select>
						</div>
					</div> <label>&nbsp;</label>
					<button type="button" id="search" class="btn btn-block btn-primary" onclick="recruitmentReport();" style="width: 80px;">Search</button>
					<br/><br/>
					<br/><br/>
					<div id="recruitmentReportDetails"></div>
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
			document.recruitmentReportForm.action = "${pageContext.request.contextPath}/getRecruitmentDetails?company_id="
					+ company_id + "&job_deatils_id=" + job_deatils_id + "candidate_id=" + candidate_id;
			document.recruitmentReportForm.submit();
		}

		/* function getJobTitle(company) {
			//alert(company);
			$.get(
					'${pageContext.request.contextPath}/getJobTitleByCompanyId?company_id='
							+ company +'&job_details_id=' + job_details_id + "candidate_id="+ candidate_id , null, function(resp) {
						$('#job_details_id').empty()
						$('#job_details_id').append(resp.trim());
					});
		} */
		function recruitmentReport(){
			var company_id=document.getElementById("company_id").value;
			var job_details_id=document.getElementById("job_details_id").value;
			var candidate_id=document.getElementById("candidate_id").value;
			
			$.get(
					'${pageContext.request.contextPath}/getRecruitmentReport?company_id='+ company_id +'&job_details_id=' +job_details_id +
					"candidate_id=" + candidate_id  , null, function(resp) {
						console.log(resp.trim());
						if(resp.trim() != 'expired') {
					 		$("#recruitmentReportDetails").empty();
							$('#recruitmentReportDetails').html(resp.trim());
							$("#test").hide();
							 $('#recruitmentDetailsTable').DataTable({
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