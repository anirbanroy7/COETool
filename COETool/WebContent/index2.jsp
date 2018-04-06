<%@ page import="java.sql.*" %>
<%@ page
language="java"
contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"
%>
<%ResultSet resultset =null;
Connection connection = null;
Statement statement = null;
%>
<html>
<head>
<title>Account and Application Registration </title>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/style.css">

<script src="js/jquery-1.11.1.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/application.js" type="text/javascript"></script>

<script>
$(function() {
  $( "#tabs" ).tabs();
});
function rowEdit(keyVal)
{
	
	document.getElementById("rowDataId").value = keyVal;
	
	document.getElementById("editForm").action = "AccountController?action=editAccount"; 
	document.getElementById("editForm").method = "post";
	document.getElementById("editForm").submit(); 	
	
}
function serachAccount1()
{
	document.getElementById("accountForm").action = "AccountController?action=searchAccount"; 
	document.getElementById("accountForm").method = "post";
	document.getElementById("accountForm").submit(); 
}
function searchAccount()
{
	 $.ajax({  
		    type: "POST",  
		    url: "AccountController",  
		    data: {"action":"searchAccount"}, 
		    
		    success: function(data,jqXHR){  
		    	//$("#accMessageDiv").html(data);
		    	
		    	//alert(data[0]);
		    	
		    	 var col = [];
		         for (var i = 0; i < data.length; i++) {
		             for (var key in data[i]) {
		                 if (col.indexOf(key) === -1) {
		                     col.push(key);
		                 }
		             }
		         } 
		        /*  for (var i = 0; i < col.length; i++) {
		             col[i] = col[i].toUpperCase();
		         }  */

		         col.push("Actions");	
		         // CREATE DYNAMIC TABLE.
		        var table = document.createElement("table");

		         // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

		         var tr = table.insertRow(-1);                   // TABLE ROW.

		          for (var i = 0; i < col.length; i++) {
		             var th = document.createElement("th");      // TABLE HEADER.
		             th.style.backgroundColor = "#c1c1c1";
		             th.innerHTML = col[i].toUpperCase();
		             tr.appendChild(th);
		         } 

		         // ADD JSON DATA TO THE TABLE AS ROWS.
		          for (var i = 0; i < data.length; i++) {

		             tr = table.insertRow(-1);

		             for (var j = 0; j < col.length; j++) {
		                 var tabCell = tr.insertCell(-1);
		                 //alert("==data[i]=="+i+"==[col[j]]=="+j+"==="+data[i][col[j]]);
		                 var primKey = data[i][col[0]] ;
		                 if(data[i][col[j]] == undefined && j==8)
		                 {
		                	// tabCell.innerHTML = "";
		                	 //tabCell.innerHTML = '<a href=\"edit.jsp?accId='+primKey+'\">EDIT</a>';
		                	 tabCell.innerHTML = '<a href=\"javascript:rowEdit('+primKey+')\">EDIT</a>';
		                	 
		                 }
		                 else if(data[i][col[j]] == undefined)
		                 {
		                	 tabCell.innerHTML = "";
		                 }
		                 else
		                	 tabCell.innerHTML = data[i][col[j]];
		             }
		         } 

		         // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
		         var divContainer = document.getElementById("searchData");
		         divContainer.innerHTML = "";
		         divContainer.appendChild(table); 
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
function updateAccount()
{
	var accName = document.getElementById("accountName").value;
	var accContact = document.getElementById("accountContact").value;
	var projId = document.getElementById("projectId").value;
	var projName = document.getElementById("projectName").value;
	var ilCd = document.getElementById("ilcCode").value;
	var licsnNo = document.getElementById("licenseNo").value;
	var iot = document.getElementById("iot").value;
	 $.ajax({  
		    type: "POST",  
		    url: "AccountController",  
		    data: {"action":"createAccount",
		    	"accountName":accName,"accountContact":accContact,"projectId":projId,"projectName":projName,"ilcCode":ilCd,"licenseNo":licsnNo,"iot":iot,},  
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
function calculateFTE(){  

	  //$("#loading").hide();

	  var accVersion = document.getElementById("rowDataIdAccountVersion").value;
	  
	  //var optionValue = accVersion.options[accVersion.selectedIndex].value;
	  
	  alert("==optionValue=="+accVersion);
	  
	  $.ajax({  
	    type: "POST",  
	    url: "CalculateFTE",  
	    data: {"action":"calculatefte",
	    	"account_version":accVersion},  
	    success: function(data,jqXHR){
	    	alert("==data[1]==="+data[1]);
	    	$("#fteCount").val(data);
	    	
	    }, 
	    beforeSend: function() {
	            // setting a timeout
	            
	    },
	    complete: function()
	    {
	    	
	    }
	  }); 
	}  
	function submitFTE(){  

	 var ftecountVal = document.getElementById("fteCount").value;
	  //alert("==ftecount=="+ftecountVal);
	  $.ajax({  
	    type: "POST",  
	    url: "CalculateFTE",  
	    data: {"action":"submitfte",
	    	"fteCountVal":ftecountVal},  
	    success: function(data,jqXHR){  
	    	$("#somediv").html(data);
	    }, 
	    error: function(data,jqXHR){  
	    	$("#somediv").html(data);
	    },
	    beforeSend: function() {
            // setting a timeout
            
    	},
	    complete: function()
	    {
	    	
	    }                
	  }); 
	} 
function reset(){
    $('input[type=text]').val('');  
    $('#textarea').val(''); 
    $('input[type=select]').val('');
    $('input[type=radio]').val('');
    $('input[type=checkbox]').val('');  
}
function rowEditApplication(keyVal)
{
	
	document.getElementById("rowDataIdApplication").value = keyVal;
	
	document.getElementById("editApplicationForm").action = "ApplicationController?action=editApplication"; 
	document.getElementById("editApplicationForm").method = "post";
	document.getElementById("editApplicationForm").submit(); 	
	
}

function searchApplication()
{
	 $.ajax({  
		    type: "POST",  
		    url: "ApplicationController",  
		    data: {"action":"searchApplication"}, 
		    
		    success: function(data,jqXHR){  
		    	//$("#accMessageDiv").html(data);
		    	
		    	//alert(data[0]);
		    	
		    	 var col = [];
		         for (var i = 0; i < data.length; i++) {
		             for (var key in data[i]) {
		                 if (col.indexOf(key) === -1) {
		                     col.push(key);
		                 }
		             }
		         } 
		        /*  for (var i = 0; i < col.length; i++) {
		             col[i] = col[i].toUpperCase();
		         }  */

		         col.push("Actions");	
		         // CREATE DYNAMIC TABLE.
		        var table = document.createElement("table");

		         // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

		         var tr = table.insertRow(-1);                   // TABLE ROW.

		          for (var i = 0; i < col.length; i++) {
		             var th = document.createElement("th");      // TABLE HEADER.
		             th.style.backgroundColor = "#c1c1c1";
		             th.innerHTML = col[i].toUpperCase();
		             tr.appendChild(th);
		         } 

		         // ADD JSON DATA TO THE TABLE AS ROWS.
		          for (var i = 0; i < data.length; i++) {

		             tr = table.insertRow(-1);

		             for (var j = 0; j < col.length; j++) {
		                 var tabCell = tr.insertCell(-1);
		                 //alert("==data[i]=="+i+"==[col[j]]=="+j+"==="+data[i][col[j]]);
		                 var primKey = data[i][col[3]] ;
		                 if(data[i][col[j]] == undefined && j==4)
		                 {
		                	// tabCell.innerHTML = "";
		                	 //tabCell.innerHTML = '<a href=\"edit.jsp?accId='+primKey+'\">EDIT</a>';
		                	 tabCell.innerHTML = '<a href=\"javascript:rowEditApplication('+primKey+')\">EDIT</a>';
		                	 
		                 }
		                 else if(data[i][col[j]] == undefined)
		                 {
		                	 tabCell.innerHTML = "";
		                 }
		                 else
		                	 tabCell.innerHTML = data[i][col[j]];
		             }
		         } 

		         // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
		         var divContainer = document.getElementById("searchDataApplication");
		         divContainer.innerHTML = "";
		         divContainer.appendChild(table); 
		    }, 
		    error: function(data,jqXHR){  
		    	$("#appMessageDiv").html(data);
		    }, 
		    beforeSend: function() {
		            // setting a timeout
		            
		    },
		    complete: function()
		    {
		    	
		    }
		  });
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
		    	"contactPerson":contactPersonVal,"applicenseNo":licsnNoVal,"accId":accIdVal},  
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
function enable()
{
	document.getElementById("fteCount").readOnly=false;
	document.getElementById("calculateLic").disabled=false;
	document.getElementById("updateLic").disabled=false;
	document.getElementById("allUpdate").style.display="none";
}
function disable()
{
	document.getElementById("fteCount").readOnly=true;
	document.getElementById("calculateLic").disabled=true;
	document.getElementById("updateLic").disabled=true;
	document.getElementById("allUpdate").style.display="block";
}
function getApplication(val)
{
	$("#accVerMessageDiv").html("");
	
	 var accVersion = document.getElementById("account_version").value;
	// var optionValue = accVersion.options[accVersion.selectedIndex].value;
	  alert("==val=="+accVersion);
	if(accVersion == "0"){
		alert();
		disable();
	}
	else
	{
		enable();
	}
	document.getElementById("rowDataIdAccountVersion").value=accVersion;
	 $.ajax({  
		    type: "POST",  
		    url: "CalculateFTE",  
		    data: {"action":"searchData",
		    	"account_version":accVersion},  
		    success: function(data,jqXHR){
		    	$("#accVerMessageDiv").html("");
		    	 var col = [];
		         for (var i = 0; i < data.length; i++) {
		             for (var key in data[i]) {
		                 if (col.indexOf(key) === -1) {
		                     col.push(key);
		                 }
		             }
		         } 
		        /*  for (var i = 0; i < col.length; i++) {
		             col[i] = col[i].toUpperCase();
		         }  */

		         //col.push("Actions");	
		         // CREATE DYNAMIC TABLE.
		        var table = document.createElement("table");

		         // CREATE HTML TABLE HEADER ROW USING THE EXTRACTED HEADERS ABOVE.

		         var tr = table.insertRow(-1);                   // TABLE ROW.

		          for (var i = 0; i < col.length; i++) {
		             var th = document.createElement("th");      // TABLE HEADER.
		             th.style.backgroundColor = "#c1c1c1";
		             th.innerHTML = col[i].toUpperCase();
		             tr.appendChild(th);
		         } 

		         // ADD JSON DATA TO THE TABLE AS ROWS.
		          for (var i = 0; i < data.length; i++) {

		             tr = table.insertRow(-1);

		             for (var j = 0; j < col.length; j++) {
		                 var tabCell = tr.insertCell(-1);
		                 //alert("==data[i]=="+i+"==[col[j]]=="+j+"==="+data[i][col[j]]);
		                 var primKey = data[i][col[3]] ;
		                /*  if(data[i][col[j]] == undefined && j==4)
		                 {
		                	// tabCell.innerHTML = "";
		                	 //tabCell.innerHTML = '<a href=\"edit.jsp?accId='+primKey+'\">EDIT</a>';
		                	 tabCell.innerHTML = '<a href=\"javascript:rowEditApplication('+primKey+')\">EDIT</a>';
		                	 
		                 }
		                 else */ if(data[i][col[j]] == undefined)
		                 {
		                	 tabCell.innerHTML = "";
		                 }
		                 else
		                	 tabCell.innerHTML = data[i][col[j]];
		             }
		         } 

		         // FINALLY ADD THE NEWLY CREATED TABLE WITH JSON DATA TO A CONTAINER.
		         var divContainer = document.getElementById("searchDataAccountVersion");
		         divContainer.innerHTML = "";
		         divContainer.appendChild(table); 

		    	
		    }, 
		    error:function(data,jqXHR)
		    {
		    	alert("==error=="+data);
		    	$("#accVerMessageDiv").html("No Data Found.");
		    	//$("#accMessageDiv").html(data);
		    },
		    beforeSend: function() {
		            // setting a timeout
		            
		    },
		    complete: function()
		    {
		    	
		    }
		  }); 
}

function submitAllFTE()
{
	 
	  //alert("==ftecount=="+ftecountVal);
	  $.ajax({  
	    type: "POST",  
	    url: "CalculateFTE",  
	    data: {"action":"submitAllfte"},  
	    success: function(data,jqXHR){  
	    	$("#somediv").html(data);
	    	enable();
	    }, 
	    error: function(data,jqXHR){  
	    	$("#somediv").html(data);
	    	enable();
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
<%
    try{
	      Class.forName("org.postgresql.Driver").newInstance();
	      connection = DriverManager.getConnection
            ("jdbc:postgresql://localhost:2280/CASTCOE?user=operator&password=CastAIP");

         statement = connection.createStatement() ;
         resultset =statement.executeQuery("select acc_id,acc_name,project_id from account") ;
    }
catch(Exception e)
{
     out.println("wrong entry"+e);
}
%>
<body>
<table align="center">
<tr>
<td valign="top"><img src="images/IBM.jpg" width="100px" height="54px"></td>
<td valign="top"><h1 align="center">CAST License Tracker</h1>
	<div class="wrapper">
		<div class="container">
			<div id="tabs">
				<ul>
					<li><a href="#Account"  onclick="reset(); return false;">Account Registration</a></li>
					<li><a href="#Application" onclick="reset(); return false;">Application Registration</a></li>
					<li><a href="#FTECalculation" onclick="reset(); return false;">License Calculator</a></li>
				</ul>
				<div id="Account">
				
					<fieldset>
						<legend>Account Creation</legend>
						
						 <div id="accMessageDiv"></div></br>	
					<% 
  					if((String)session.getAttribute("error") != null){ %>
					<h4><%=session.getAttribute("error")%></h4>
					<%}else if((String)session.getAttribute("message") != null){ %>
					<h4><%=session.getAttribute("message")%></h4>
					<%} %>
					<form method="post" action="" name="accountForm" id="accountForm">
						<label for="accountName">Account Name:</label> &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="accountName" id="accountName" /> <br /> 
						<label for="accountContact">Account Contact:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="accountContact" id="accountContact" /> <br />
						<label for="projectId">Project Id:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="projectId" id="projectId" /> <br />
						<label for="projectName">Project Name:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="projectName" id="projectName" /> <br />
						<label for="ilcCode">Account Id:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="ilcCode" id="ilcCode" /> <br />
						<label for="licenseNo">License Key:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="licenseNo" id="licenseNo" /> <br />
						<label for="iot">IOT:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="iot" id="iot" /> <br />
						
						<br /> <input type="button" value="Create Account" onclick="updateAccount();">
						<input type="button" value="Search Account" onclick="searchAccount();">
					</form>
					</fieldset>
					<div id="searchDataContainer">
					<fieldset>
					<legend>Search Result:</legend>
					</br></br>
						<div id="searchData"></div>
						<form name="editForm" id="editForm">
							<input type="hidden" id="rowDataId" name="rowDataId" value="" />
						</form>
					</fieldset>
					</div>
				</div>
				<div id="Application">
					<fieldset>
						<legend>Application Creation</legend>
						 <div id="appMessageDiv"></div></br>
					<% 
  	if((String)session.getAttribute("errorReg") != null){ %>
					<h4><%=session.getAttribute("errorReg") %></h4>
					<%} %>
					<form method="post" action="ApplicationController">
						<label for="appName">Application Name:</label> &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="appName" id="appName" /> <br /> 
						<label for="appfte">No. of Licenses:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="appFte" id="appFte" /> <br />
						<label for="contactperson">Contact Person:</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="text" name="contactPerson" id="contactPerson" /> <br />
						<label for="account">Account To Be Selected:</label>&nbsp;&nbsp;&nbsp;&nbsp;<select name ="account">
        				<%
        					StringBuilder str1 = null;
        					while(resultset.next()){
        						str1 = new StringBuilder(100);
    				        	str1.append(resultset.getString(2)).append(" - ").append(resultset.getString(3));
        						
        				%>
        					<option value="<%=resultset.getInt(1)%>"><%=str1.toString()%></option>
                        <%}
        					resultset = null;
                        %>           
                         </select>  						
						<br /> <input type="submit" value="Create Application">
						<input type="button" value="Search Application" onclick="searchApplication();">
					</form>
					</fieldset>
					<div id="searchDataContainer">
						<fieldset>
						<legend>Search Result:</legend>
							</br>
								
							</br>
							<div id="searchDataApplication"></div>
							<form name="editApplicationForm" id="editApplicationForm">
								<input type="hidden" id="rowDataIdApplication" name="rowDataIdApplication" value="" />
							</form>
						</fieldset>
					</div>
				</div>
				<div id="FTECalculation">
					<form method ="post" action="CalculateFTE">
						<fieldset>
							<legend>Application Selection:</legend>
							 <div id="somediv"></div>					 
					    		</br></br>
						
								<label for="account">Application License Calculator:</label>&nbsp;&nbsp;&nbsp;&nbsp;
								<select name ="account_version" id ="account_version" onchange="getApplication();">
								<option value="">Select</option>
								<option value="0">All</option>
        <%
				        try
				        {
				        	  statement = connection.createStatement() ;
				              resultset =statement.executeQuery("select a.acc_id,a.acc_name,a.project_name,b.app_name  from Account a, Application b where a.acc_id = b.acc_id;") ;
				        }
				        catch(Exception e)
				        {
				        	
				        }
				        StringBuilder str = null;
				        
				        while(resultset.next()){
				        	str = new StringBuilder(100);
				        	str.append(resultset.getString(2)).append(" - ").append(resultset.getString(3));
        %>
        
       					 <option value="<%=resultset.getInt(1)%>"><%=str.toString()%></option>
        <%}%>           
                         </select>  						
						<br />
							
						
						
						</fieldset>
					    <div id="searchDataContainer">
							<fieldset>
							<legend>Search Result:</legend>
								<div id="accVerMessageDiv"></div>
								<div id="searchDataAccountVersion"></div>
								
								<input type="hidden" id="rowDataIdAccountVersion" name="rowDataIdAccountVersion" value="" />
								<br>
								<input type="button" value="Calculate License" name="calculateLic" id="calculateLic" onClick="calculateFTE();">
							</fieldset>
						</div> 
						<div id="anotherSection">
					        <fieldset>
					            <legend>Final License Count</legend>
					                 <div id="ajaxResponse">
					                 <label for="finalCount">License Count:</label> &nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="fteCount" id="fteCount" /> &nbsp;&nbsp;
					                 <input type="button" value="Update License" name="updateLic" id="updateLic" onClick="submitFTE();">
					                 </div>
					        </fieldset>
					    </div>
					    <div id="allUpdate" style="display:none">
					    	<input type="button" value="Update All Licenses" name="updateAllLic" id="updateAllLic" onClick="submitAllFTE();">
					    </div>
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