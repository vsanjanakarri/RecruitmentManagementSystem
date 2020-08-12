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
			Company List
		</h1>
		<ol class="breadcrumb">
			<li><a href="${pageContext.request.contextPath}/goToHomePage"><i class="fa fa-dashboard"></i> Home</a></li>
			<li class="active">Company List</li>
		</ol>
	</section>

    <!-- Main content -->	
    <section class="content">
		<div class="box-body">
			<form action="${pageContext.request.contextPath}/admincompanyList" id="admincompanyList" name="admincompanyList" method="post" >
			
				<div class="form-group col-sm-12" style="padding-right: 0px;">
					<div class="form-group col-sm-12" id="message">
						<h4><label style="color: green;"><b>${sessionScope.message}</b></label></h4>
					</div>
				</div>
		<table id="companyListTable"
		class="table-checkbox table table-bordered table-striped" style="width: 100%;">
		<thead>
			<tr>
				<th width="50px" style="text-align: center;">SrNO</th>
				<th>Company Name</th>
				<th>Registration Number</th>
				<th>Email</th>
				<th>Telephone No</th>
				<th>State</th>
				<th>City</th>
				<th>Active/Inactive</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${companyDataList}" var="company" varStatus="i">
				<tr>
					<td width="50px" style="text-align: center"><c:out
							value="${i.index + 1}"></c:out></td>
					<td>${company.getCompany_name()}</td>
					<td>${company.getCompany_reg_no()}</td>
					<td>${company.getEmail_id()}</td>
					<td>${company.getCompanyBasicDetails().telephone_no}</td>
					<td>${company.getCompanyBasicDetails().getStateMaster().getState()}</td>
					<td>${company.getCompanyBasicDetails().getCityMaster().getCity()}</td>
					<td><c:if test="${company.getActive_inactive() eq 'Y'}">
									<c:out value="Yes"></c:out>
								</c:if>
								<c:if test="${company.getActive_inactive() eq 'N'}">
									<c:out value="No"></c:out>
								</c:if></td>

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