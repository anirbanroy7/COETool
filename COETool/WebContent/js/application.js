function rowEditApplication(keyVal)
{
	
	document.getElementById("rowDataId").value = keyVal;
	
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
		                 var primKey = data[i][col[0]] ;
		                 if(data[i][col[j]] == undefined && j==8)
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
