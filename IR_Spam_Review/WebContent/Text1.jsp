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
              
        	document.getElementById('div4').style.display = 'block';
        	document.getElementById('div2').style.display = 'none';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('div1').style.display = 'none';
        	document.getElementById('formid2').style.display ='none';
        	document.getElementById('formid1').style.display ='none';
        	
        
        	
        }
        function display2()
        {
        	document.getElementById('div2').style.display = 'block';
        	document.getElementById('div1').style.display = 'none';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('div4').style.display = 'none';
        	document.getElementById('formid2').style.display ='none';
        	document.getElementById('formid1').style.display ='none';
        	
        }
        
        function display3()
        {
        	document.getElementById('formid1').style.display = 'block';
        	document.getElementById('div4').style.display = 'none';
        	document.getElementById('formid2').style.display ='none';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('div1').style.display = 'none';
        	document.getElementById('div2').style.display = 'none';
        	
        	
        	
        }
        
        function display4() {
            
        	document.getElementById('formid2').style.display = 'block';
        	document.getElementById('div4').style.display = 'none';
        	document.getElementById('formid1').style.display ='none';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('div1').style.display = 'none';
        	document.getElementById('div2').style.display = 'none';
        	
        }
        
        function display5() {
            
        	document.getElementById('div2').style.display = 'none';
        	document.getElementById('div1').style.display = 'block';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('formid2').style.display = 'none';
        	document.getElementById('formid1').style.display = 'none';
        	document.getElementById('div4').style.display = 'none';
        	    	
        }
        
        
       function display6() {
            
    	document.getElementById('div1').style.display = 'none';
       	document.getElementById('div2').style.display = 'block';
       	document.getElementById('div3').style.display = 'none';
       	document.getElementById('formid2').style.display = 'none';
       	document.getElementById('formid1').style.display = 'none';
       	document.getElementById('div4').style.display = 'none';
        	
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
<h1>Text Analysis Part 1 </h1>
</center>
<div class="vertical-menu">
  <a href="Home.jsp" class="active">Home</a>
  <a href="#"   onclick="display1()" >Discard Reviews With Links</a>
  <a href="#"   onclick="display2()" >Discard Reviews With Question</a>
  <a href="#"   onclick="display3()" >Discard Short Length Reviews</a>
  <a href="#"   onclick="display4()" >Discard Reviews With CAPS</a>
  </div>

<center>
<form  action= "Text1.jsp" id = "formid1" style="display:none; position:absolute; top:180px; left:350px;">
<table>
<tr>
<td>
<label for="reviewLengthThreshold"> reviewLengthThreshold:</label></td>
<td><input type="text"   name="reviewLengthThreshold" ></td>
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


<form  action= "Text1.jsp" id = "formid2" style="display:none; position:absolute; top:180px; left:350px;">
<table>
<tr>
<td>
<label for="CAPSThreshold">CAPSThreshold:</label></td>
<td><input type="text"  name="CAPSThreshold" ></td>
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

<div id = "div1" >
<%
BufferedWriter outfile = new BufferedWriter(new FileWriter("outputfile.txt"));		
String inputFile = "C:\\Users\\user\\Desktop\\NeonWorkspace\\IR_GUI\\data.csv";
TextAnalysis text = new TextAnalysis(outfile);
String outputFile = "output.csv";

if(request.getParameter("reviewLengthThreshold") != null ){
int reviewLengthThreshold = Integer.parseInt(request.getParameter("reviewLengthThreshold"));

HashMap<String,Integer> op = text.discardShortLengthReviews(inputFile, outputFile,reviewLengthThreshold );

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
HashMap<String,Integer> op = text.discardReviewsWithQuestion(inputFile, outputFile);
for (String key : op.keySet())
{
	System.out.println(key + " : "+ op.get(key));
	out.println(key + " : "+ op.get(key));
	out.println("<br>");	
}
	
%>
</div>

<div id = "div3" >
<%

if(request.getParameter("CAPSThreshold") != null ){

	int CAPSThreshold = Integer.parseInt(request.getParameter("CAPSThreshold"));
	
	HashMap<String,Integer> op1 = text.discardReviewsInCAPS(inputFile, outputFile,CAPSThreshold);
	 
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
HashMap<String,Integer> op2 = text.discardReviewsWithLinks(inputFile, outputFile);
	for (String key : op2.keySet())
	{
		out.println(key + " : "+ op2.get(key));
		out.println("<br>");	
	}	
%>
</div>



</center>

<br><br>


</body>
</html>