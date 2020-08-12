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
			Language Proficiency Details List
		</h1>
		<ol class="breadcrumb">
			<li><a href="${pageContext.request.contextPath}/goToHomePage"><i class="fa fa-dashboard"></i> Home</a></li>
			<li class="active">Language Proficiency Details List</li>
		</ol>
	</section>

    <!-- Main content -->	
    <section class="content">
		<div class="box-body">
			<form action="${pageContext.request.contextPath}/addLanguageDetails" id="AddLanguage" name="AddLanguage" method="post" >
			
				<div class="form-group col-sm-12" style="padding-right: 0px;">
					<div class="form-group col-sm-12" id="message">
						<h4><label style="color: green;"><b>${sessionScope.message}</b></label></h4>
					</div>
				</div>
			
				<div class="form-group col-sm-12" align="right" style="padding-right: 0px; padding-left: 0px;">
							<div class="col-sm-12" style=" float: left;">
									<button type="submit" class="btn btn-primary"  style="width: 35px; height: 35px; padding-left: 6px; color: white; font-size: 18px;">
										<i class="fa fa-fw fa-plus" title="Add Job"></i>
									</button>
							</div>
					</div>
					
				<table id="languageDetailsListTable" class="table-checkbox table table-bordered table-striped" style="width:100%;" >
					<thead>
	                	<tr>
							<th width="50px" style="text-align: center;">SrNo</th>
							<th>Language</th>
							<!-- <th>Proficiency Level</th> -->
							<th>Read</th>
							<th>Write</th>
							<th>Speak</th>
						<!-- 	<th colspan="2">Action</th> -->
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${candidateLanguageDetailsList}" var="language" varStatus="i">
							<tr>
								<td width="50px" style="text-align: center"><c:out value="${i.index + 1}"></c:out></td>
								<td>${language.getLanguageMaster().getLanguage()}</td>
								<%-- <td>${language.getLanguage_proficiency_level()}</td> --%>
								<td><c:if test="${language.getRd() eq 'Y'}">
									<c:out value="Yes"></c:out>
								</c:if>
								<c:if test="${language.getRd() eq 'N'}">
									<c:out value="No"></c:out>
								</c:if>
								</td>
								<td><c:if test="${language.getWr() eq 'Y'}">
									<c:out value="Yes"></c:out>
								</c:if>
								<c:if test="${language.getWr() eq 'N'}">
									<c:out value="No"></c:out>
								</c:if>
								</td>
								<td>
								<c:if test="${language.getSpeak() eq 'Y'}">
									<c:out value="Yes"></c:out>
								</c:if>
								<c:if test="${language.getSpeak() eq 'N'}">
									<c:out value="No"></c:out>
								</c:if>
								</td> 
								<%-- <td>*<a href="${pageContext.request.contextPath}/editJobDetails?job_details_id=${job.getJob_details_id()}">
								<button class="fa  fa-pencil" type="button" id="editJobDetails" ></button></a></td>
								 <td><button class="fa  fa-trash-o" type="reset" onclick=""></button></td> --%>
							
							</tr>
	    				</c:forEach>
	    			</tbody>
	    		</table>
	    		</form>
    	
    	</div>
	</section>
    
</div>
<script src="bower_components/datatables.net/js/jquery.dataTables.min.js"></script>
<script src="bower_components/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script> 
<script src="js/dataTables.buttons.min.js"></script>
<script src="js/buttons.flash.min.js"></script>
<script src="js/jszip.min.js"></script>
<script src="js/pdfmake.min.js"></script>
<script src="js/vfs_fonts.js"></script>
<script src="js/buttons.html5.min.js"></script>
<script src="js/buttons.print.min.js"></script>
<script>
  $(function () {
	    $('.table1').DataTable({
	    	/* "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
	    	"iDisplayLength": 10 */
		        dom: 'Bfrtip',
		        "iDisplayLength": 10,
		    	lengthMenu: [
		            [ 10, 25, 50, -1 ],
		            [ '10 rows', '25 rows', '50 rows', 'Show all' ]
		        ],
				 buttons: [
			        	{
			        		extend:'pageLength',
			        		text: 'Page Length <i class="fa fa-sort-down"></i>'
			        	}
			        	]
	   })
	   $('.table2').DataTable({
	     'paging'      : false,
	     'lengthChange': false,
	     'searching'   : false,
	     'ordering'    : true,
	     'info'        : false,
	     'autoWidth'   : false,
	     "scrollX": true,
		  "scrollY": 300,
	   })
	   $('.table3').DataTable({
	    	/* "lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
	    	"iDisplayLength": -1,
	    	"aaSorting": [[2, 'asc']] */
	    	 dom: 'Bfrtip',
	    	 "aaSorting": [[2, 'asc']],
	    	 "iDisplayLength": -1,
		    	lengthMenu: [
		            [ 10, 25, 50, -1 ],
		            [ '10 rows', '25 rows', '50 rows', 'Show all' ]
		        ],
				 buttons: [
			        	{
			        		extend:'pageLength',
			        		text: 'Page Length <i class="fa fa-sort-down"></i>'
			        	}
			        	]
	   })
	   /*  $('.table-checkbox').DataTable({
	    	"lengthMenu": [[10, 25, 50, -1], [10, 25, 50, "All"]],
	    	"iDisplayLength": -1,
	    	"aaSorting": [[2, 'asc']],
			 "scrollY": 400
	     }) */
	     $('.table-checkbox').DataTable({
	    	 "scrollX": true,
	    	 "iDisplayLength": -1,
		     "aaSorting": [[2, 'asc']],
		        dom: 'Bfrtip',
		        "scrollY": 400,
		        "scrollX": true,
		        "autoWidth": false,
		    	lengthMenu: [
		            [ 10, 25, 50, -1 ],
		            [ '10 rows', '25 rows', '50 rows', 'Show all' ]
		        ],
				 buttons: [
			        	{
			        		extend:'pageLength',
			        		text: 'Page Length <i class="fa fa-sort-down"></i>'
			        	}
			        	]
		     })
	    $('.only-Search').DataTable({
	     'paging'      : false,
	     'lengthChange': false,
	     'searching'   : true,
	     'ordering'    : true,
	     'info'        : false,
	     'autoWidth'   : false,
	     "scrollX": true,
		  "scrollY": 400,
		  "aaSorting": [[1, 'asc']]
	   })
	   $('.export-data').DataTable({
    	"scrollY":  400,
        "scrollX": true,
        'dom': 'Bfrtip',
        'buttons' : [ 'copy', 'csv', 'excel','pdf','print' ]
    })
  })
</script>

<script type="text/javascript">
	$(document).ready(function(){
		if (document.getElementById('message').innerHTML != '')
	    	myVar = setInterval("hideMessage()", 10000);
	});
	
	
</script>
</body>
</html>