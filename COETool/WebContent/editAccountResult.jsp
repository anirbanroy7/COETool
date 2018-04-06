<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="com.coetool.servlet.model.Account" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%
  Account acc = (Account)request.getAttribute("editDataAccount");
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
	document.getElementById("editAccountForm").action="AccountController?action=backToHome";
	document.getElementById("editAccountForm").method="post";
	document.getElementById("editAccountForm").submit();
}
function updateAccount()
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
	
}
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
					<li><a href="#EditAccountResult"  onclick="reset(); return false;">Edit Account Result</a></li>
					
				</ul>
				<div id="EditAccountResult">
				
					<div id="accMessageDiv"></div></br>
					<form method="post" action="" name="editAccountForm" id="editAccountForm">
						 
						<label for="accountContact">Account Contact:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="accountContact" id="accountContact" value=<%=acc.getAccContact() %> > <br />
						<label for="ilcCode">ILC Code:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="ilcCode" id="ilcCode"  value=<%=acc.getIlcCode() %>> <br />
						<label for="licenseNo">License No:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="licenseNo" id="licenseNo"  value=<%=acc.getLicenseNo() %>> <br />
						<label for="iot">IOT:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="iot" id="iot" value=<%=acc.getIot() %> > <br />
						<input type="hidden" id="accId" name="accId" value=<%=acc.getAccId()%>>
						
						<br /> <input type="button" value="Update Account Details" onclick="updateAccount();">
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