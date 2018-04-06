<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.coetool.servlet.model.Application" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%
  Application appli = (Application)request.getAttribute("editDataApplication");
%>
<html>
<head>
<title>Search Result</title>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/style.css">

<script src="js/jquery-1.11.1.js"></script>
<script src="js/jquery-ui.js"></script>
<script>
$(function() {
	  $( "#tabs" ).tabs();
	});
function backToHome()
{
	document.getElementById("editApplicationForm").action="ApplicationController?action=backToHome";
	document.getElementById("editApplicationForm").method="post";
	document.getElementById("editApplicationForm").submit();
}	
function updateApplication()
{
	var accIdVal = document.getElementById("accId").value;
	//alert("==accIdVal=="+accIdVal);
	var contactPersonVal = document.getElementById("contactPerson").value;
	var licsnNoVal = document.getElementById("applicenseNo").value;
	
	 $.ajax({  
		    type: "POST",  
		    url: "ApplicationController",  
		    data: {"action":"updateApplication",
		    	"contactPerson":contactPersonVal,"appFte":licsnNoVal,"account":accIdVal},  
		    success: function(data,jqXHR){  
		    	$("#applicationMessageDiv").html(data);
		    }, 
		    error: function(data,jqXHR){  
		    	$("#applicationMessageDiv").html(data);
		    }, 
		    beforeSend: function() {
		            // setting a timeout
		            
		    },
		    complete: function()
		    {
		    	
		    }
		  }); 
	
}	
	
/* function updateAccount()
{
	var accIdVal = document.getElementById("accId").value;
	//alert("==accIdVal=="+accIdVal);
	var accContactVal = document.getElementById("accountContact").value;
	var ilCdVal = document.getElementById("ilcCode").value;
	var licsnNoVal = document.getElementById("licenseNo").value;
	var iotVal = document.getElementById("iot").value;
	 $.ajax({  
		    type: "POST",  
		    url: "AccountController",  
		    data: {"action":"updateAccount",
		    	"accountContact":accContactVal,"ilcCode":ilCdVal,"licenseNo":licsnNoVal,"iot":iotVal,"accId":accIdVal},  
		    success: function(data,jqXHR){  
		    	$("#accMessageDiv").html(data);
		    }, 
		    error: function(data,jqXHR){  
		    	$("#accMessageDiv").html(data);
		    }, 
		    beforeSend: function() {
		            // setting a timeout
		            
		    },
		    complete: function()
		    {
		    	
		    }
		  }); 
	
} */
</script>
</head>
<body>
<table align="center">
<tr>
<td valign="top"><img src="images/IBM.jpg" width="100px" height="54px"></td>
<td valign="top"><h1 align="center">CAST License Tracker</h1>

<div class="wrapper">
		<div class="container">
			<div id="tabs">
				<ul>
					<li><a href="#EditApplicationResult"  onclick="reset(); return false;">Edit Application Result</a></li>
					
				</ul>
				<div id="EditApplicationResult">
				
					<div id="applicationMessageDiv"></div></br>
					<form method="post" action="" name="editApplicationForm" id="editApplicationForm">
						 
						<label for="appfte">No. of Licenses:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="applicenseNo" id="applicenseNo" value=<%=appli.getNumOfLicenses() %>> <br />
						<label for="contactperson">Contact Person:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="contactPerson" id="contactPerson" value=<%=appli.getContactPerson() %> > <br />
						<input type="hidden" id="accId" name="accId" value=<%=appli.getAccountId()%>>
						
						<br /> <input type="button" value="Update Application Details" onclick="updateApplication();">
						<input type="button" value="Back To Home" onclick="backToHome();">
						
					</form>
				</div>
			</div>
		</div>
</div>
</td>
	<td valign="top"><img src="images/CAST.jpg" width="100px" height="54px"></td>
</tr>
</table>						
</body>
</html>