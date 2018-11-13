<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@ page import= "com.project.*"  %>
<%@ page import= "java.io.*"  %>
<%@ page import= "java.util.*"  %>
<script type="text/javascript">
        function display1() {
              
        	document.getElementById('div4').style.display = 'none';
        	document.getElementById('div2').style.display = 'none';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('div1').style.display = 'none';
        	document.getElementById('formid2').style.display ='none';
        	document.getElementById('formid1').style.display ='block';
        	document.getElementById('formid3').style.display ='none';
        	document.getElementById('formid4').style.display ='none';
               	
        }
        function display2()
        {
        	document.getElementById('div2').style.display = 'none';
        	document.getElementById('div1').style.display = 'none';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('div4').style.display = 'none';
        	document.getElementById('formid2').style.display ='block';
        	document.getElementById('formid1').style.display ='none';
        	document.getElementById('formid3').style.display ='none';
        	document.getElementById('formid4').style.display ='none';
        	   	
        	}
        
        function display3()
        {
        	
        	document.getElementById('div2').style.display = 'none';
        	document.getElementById('div1').style.display = 'none';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('div4').style.display = 'none';
        	document.getElementById('formid2').style.display ='none';
        	document.getElementById('formid1').style.display ='none';
        	document.getElementById('formid3').style.display ='block';
        	document.getElementById('formid4').style.display ='none';
        	
        	
        }
        
        function display4() {
            
        	document.getElementById('div2').style.display = 'none';
        	document.getElementById('div1').style.display = 'none';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('div4').style.display = 'none';
        	document.getElementById('formid2').style.display ='none';
        	document.getElementById('formid1').style.display ='none';
        	document.getElementById('formid3').style.display ='none';
        	document.getElementById('formid4').style.display ='block';
        	
        }
        
        function display5() {
            
        	document.getElementById('div2').style.display = 'none';
        	document.getElementById('div1').style.display = 'block';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('div4').style.display = 'none';
        	document.getElementById('formid2').style.display ='none';
        	document.getElementById('formid1').style.display ='none';
        	document.getElementById('formid3').style.display ='none';
        	document.getElementById('formid4').style.display ='none';
        	
        	    	
        }
        
        
       function display6() {
            
    	document.getElementById('div1').style.display = 'none';
       	document.getElementById('div2').style.display = 'block';
       	document.getElementById('div3').style.display = 'none';
       	document.getElementById('div4').style.display = 'none';
       	document.getElementById('formid2').style.display ='none';
       	document.getElementById('formid1').style.display ='none';
       	document.getElementById('formid3').style.display ='none';
       	document.getElementById('formid4').style.display ='none';
        	
        }   
       
       
       function display7() {
           
    	   document.getElementById('div1').style.display = 'none';
          	document.getElementById('div3').style.display = 'block';
          	document.getElementById('div2').style.display = 'none';
          	document.getElementById('div4').style.display = 'none';
          	document.getElementById('formid2').style.display ='none';
          	document.getElementById('formid1').style.display ='none';
          	document.getElementById('formid3').style.display ='none';
          	document.getElementById('formid4').style.display ='none';
           }   
       
       
       function display8() {
           
    	    document.getElementById('div1').style.display = 'none';
         	document.getElementById('div4').style.display = 'block';
         	document.getElementById('div2').style.display = 'none';
         	document.getElementById('div3').style.display = 'none';
         	document.getElementById('formid2').style.display ='none';
         	document.getElementById('formid1').style.display ='none';
         	document.getElementById('formid3').style.display ='none';
         	document.getElementById('formid4').style.display ='none';
           	
           }   
       
        </script>
</head>
<body>
<style type="text/css">

body {background-color:#66CDAA}
h1 {
    font-size: 40px;
}
</style>
<style>
.vertical-menu {
    width: 200px;
}

.vertical-menu a {
    background-color: #eee;
    color: black;
    display: block;
    padding: 12px;
    text-decoration: none;
}

.vertical-menu a:hover {
    background-color: #ccc;
}

.vertical-menu a.active {
    background-color: #4CAF50;
    color: white;
}
</style>
<center>
<h1>Text Analysis Part 2 </h1>
</center>
<div class="vertical-menu">
  <a href="Home.jsp" class="active">Home</a>
  <a href="#"   onclick="display1()" >Discard  Similar Reviews </a>
  <a href="#"   onclick="display2()" >Discard Reviews With Deviated Rating</a>
  <a href="#"   onclick="display3()" >Discard Reviews With Deviated Rating</a>
  <a href="#"   onclick="display4()" >Discard Reviews With High Objectivity</a>
  </div>

<center>
<form  action= "Text2.jsp" id = "formid1" style="display:none; position:absolute; top:180px; left:350px;">
<table>
<tr>
<td>
<label for="similarityThreshold"> similarityThreshold:</label></td>
<td><input type="text"   name="similarityThreshold" ></td>
</tr> 
<tr align ="center">
<td style="text-align: center">
<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<div align="center">
<input type="submit"   value="Get Result"  onclick="display5()" style="height:30px;  width:200px; margin: 0 auto;">
</div>
</td>
</tr>
</table>
</form>


<form  action= "Text2.jsp" id = "formid2" style="display:none; position:absolute; top:180px; left:350px;">
<table>
<tr>
<td>
<label for="deviationThreshold">deviationThreshold:</label></td>
<td><input type="text"  name="deviationThreshold" ></td>
</tr>

<tr>
<td>
<label for="limitExceedThreshold">limitExceedThreshold:</label></td>
<td><input type="text"  name="limitExceedThreshold" ></td>
</tr>
<tr>
<td>
<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<input type="submit"   value="Get Result"  onclick="display6()" style="height:30px; width:200px;">
</td>
</tr>
</table>
</form>






<form  action= "Text2.jsp" id = "formid3" style="display:none; position:absolute; top:180px; left:350px;">
<table>
<tr>
<td>
<label for="deviationThreshold">deviationThreshold:</label></td>
<td><input type="text"  name="deviationThreshold" ></td>
</tr>
<tr>
<td>
<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<input type="submit"   value="Get Result"  onclick="display7()" style="height:30px; width:200px;">
</td>
</tr>
</table>
</form>

<form  action= "Text2.jsp" id = "formid4" style="display:none; position:absolute; top:180px; left:350px;">
<table>
<tr>
<td>
<label for="objectivityThreshold">objectivityThreshold:</label></td>
<td><input type="text"  name="objectivityThreshold" ></td>
</tr>
<tr>
<td>
<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<input type="submit"   value="Get Result"  onclick="display8()" style="height:30px; width:200px;">
</td>
</tr>
</table>
</form>



<div id = "div1" >
<%
BufferedWriter outfile = new BufferedWriter(new FileWriter("outputfile.txt"));		
String inputFile = "C:\\Users\\user\\Desktop\\NeonWorkspace\\IR_GUI\\data.csv";
TextAnalysis text = new TextAnalysis(outfile);
String outputFile = "C:\\Users\\user\\Desktop\\NeonWorkspace\\IR_GUI\\output.csv";

if(request.getParameter("similarityThreshold") != null ){
double similarityThreshold = Double.parseDouble(request.getParameter("similarityThreshold"));

HashMap<String,Integer> op = text.discardSimilarReviews(inputFile, outputFile,similarityThreshold);
for (String key : op.keySet())
{
	out.println(key + " : "+ op.get(key));
	out.println("<br>");	
}
	
}
%>
</div>

<div id = "div2"  style="display: none" >
<%
if(request.getParameter("deviationThreshold") != null  && request.getParameter("limitExceedThreshold") != null){
double similarityThreshold = Double.parseDouble(request.getParameter("deviationThreshold"));

double limitExceedThreshold = Double.parseDouble(request.getParameter("limitExceedThreshold"));

HashMap<String,Integer> op = text.discardReviewsWithDeviatedRating(inputFile, outputFile,similarityThreshold,limitExceedThreshold);
for (String key : op.keySet())
{
	System.out.println(key + " : "+ op.get(key));
	out.println(key + " : "+ op.get(key));
	out.println("<br>");	
}

}
%>
</div>

<div id = "div3" >
<%

if(request.getParameter("deviationThreshold") != null ){

	double deviationThreshold = Double.parseDouble(request.getParameter("deviationThreshold"));
	
	HashMap<String,Integer> op1 = text.discardReviewsWithDeviatedRating(inputFile, outputFile,deviationThreshold );
	 
	for (String key : op1.keySet())
	{
		out.println(key + " : "+ op1.get(key));
		out.println("<br>");	
	}	
	
}
	
%>
</div>

<div id = "div4" style="display: none" >
<%

if(request.getParameter("objectivityThreshold") != null ){

	double objectivityThreshold = Double.parseDouble(request.getParameter("objectivityThreshold"));
	
	HashMap<String,Integer> op1 = text.discardReviewsWithHighObjectivity(inputFile, outputFile,objectivityThreshold);

	for (String key : op1.keySet())
	{
		System.out.println(key  + op1.get(key));
		out.println(key + " : "+ op1.get(key));
		out.println("<br>");	
	}	
}	
%>
</div>



</center>

<br><br>


</body>
</html>