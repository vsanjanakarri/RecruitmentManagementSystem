<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="Header.jsp"></jsp:include>
<jsp:include page="LeftMenu.jsp"></jsp:include>
<head>
<meta charset="ISO-8859-1">
<title>Language Proficiency Details</title>
</head>
<body>
	 <div class="content-wrapper">
	     <section class="content-header">
	      <h1>
	        Language Proficiency Details
	      </h1>
	      <ol class="breadcrumb">
	        <li><a href="${pageContext.request.contextPath}/goToDashboard"><i class="fa fa-dashboard"></i>Home</a></li>
	        <li class="active">Language Proficiency Details</li>
	      </ol>
	    </section>
	    
	    <!-- Main content -->
	    <section class="content">
			<div class="box box-info">
				<div class="box-header with-border">
					<div class="col-sm-1" align="left" style="padding-left: 0px;">
						<a href="${pageContext.request.contextPath}/getCandidateLanguageDetails">
							<button onclick="areaList()" >
								<i class="fa fa-fw fa-hand-o-left" title="Back"></i>
							</button>
						</a>
					</div>
					<div class="col-sm-10 form-group col-sm-6" id="message" style="margin-bottom: 0px;">
						<label style="color: green; margin-bottom: 0px; margin-top: 0px; height: 15px;"><b>${message}</b></label>
						<label style="color: red; margin-bottom: 0px; margin-top: 0px; height: 15px;"><b>${errorMessage}</b></label>
					</div>
	            </div>
	            <form:form class="form-horizontal" name="saveLanguageDetailForm" modelAttribute="form" method="post">
					<form:hidden  id="candidate_language_detail_id" path="candidate_language_detail_id"/>
					<form:hidden  id="candidate_id" path="candidate_id"/>
					<div class="box-body">
						<div class="form-group">
						<label class="col-sm-2 control-label">Language</label>
							<div class="col-sm-3">
								<form:select path="language_id" id="language_id" class="form-control" style="width: 100%;">
									<form:option value="0">-----Select-----</form:option>
									<form:options items="${languageList}" itemValue="language_id" itemLabel="language"></form:options>
								</form:select>
							</div>
							</div>
						<%-- <div class="form-group">
							<label class="col-sm-2 control-label">Proficiency Level</label>
							<div class="col-sm-3">
								<form:input path="Proficiency Level" id="Proficiency Level" class="form-control"/>
							</div>
						</div>
						 --%>
						 <div class="form-group">
							<label class="col-sm-2 control-label">Read</label>
							<div class="col-sm-3">
								<form:checkbox path="rd" id="rd" value="N" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Write</label>
							<div class="col-sm-3">
								<form:checkbox path="wr" id="wr" value="N"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">Speak</label>
							<div class="col-sm-3">
								<form:checkbox path="speak" id="speak" value="N" />
							</div>
						</div>
					<div class="box-footer" style="text-align: center;">
						<div class="col-sm-6">
							<div class="col-sm-6" align="right" style="padding-right: 5px;">
								<c:if test="${candidate_language_detail_id == null}">
									<button type="button" id="saveAreaButton" class="btn btn-block btn-primary" onclick="validDetails()" style="width: 80px;">ADD</button>
								</c:if>
								<c:if test="${candidate_language_detail_id != null}">
									<button type="button" id="saveAreaButton" class="btn btn-block btn-primary" onclick="validDetails()" style="width: 80px;">UPDATE</button>
								</c:if>
							</div>
							<div class="col-sm-6" align="left" style="padding-left: 5px;">
								<button type="button" class="btn btn-primary" style="width: 80px;" onclick="clearAllData()">CLEAR ALL</button>
							</div>
						</div>
					</div>
					</div>
					
				</form:form>
			</div>
	</section>
	<!-- /.content -->
</div>
		<script>
		function validDetails() {
			var isValid = true;
			if (isValid) {
				document.saveLanguageDetailForm.action = "${pageContext.request.contextPath}/saveLanguageDetails";
				document.saveLanguageDetailForm.submit();
			}
		}
		
          function clearAllData() {
        	  document.saveLanguageDetailForm.action = "${pageContext.request.contextPath}/addLanguageDetails";
				document.saveLanguageDetailForm.submit();
                      
                  }

	</script>
</body>
</html>