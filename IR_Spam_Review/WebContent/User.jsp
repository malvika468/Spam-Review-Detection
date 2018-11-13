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
        function display() {
              
        	document.getElementById('div1').style.display = 'block';
        }
        function display2()
        {
        	document.getElementById('formid2').style.display = 'block';
        	document.getElementById('div1').style.display = 'none';
        	document.getElementById('formid3').style.display = 'none';
        	document.getElementById('formid4').style.display = 'none';
        	document.getElementById('div2').style.display = 'none';
        	document.getElementById('div3').style.display = 'none';
        }
        
        function display3()
        {
        	document.getElementById('formid3').style.display = 'block';
        	document.getElementById('div2').style.display = 'none';
        	document.getElementById('formid2').style.display = 'none';
        	document.getElementById('formid4').style.display = 'none';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('div1').style.display = 'none';
        	
        	
        	
        }
        
        function display4() {
            
        	document.getElementById('div2').style.display = 'block';
        	document.getElementById('div1').style.display = 'none';
        	
        }
        
        function display5() {
            
        	document.getElementById('div2').style.display = 'none';
        	document.getElementById('div1').style.display = 'none';
        	document.getElementById('div3').style.display = 'none';
        	document.getElementById('formid2').style.display = 'none';
        	document.getElementById('formid3').style.display = 'none';
        	document.getElementById('formid4').style.display = 'block';
        	
        }
        
        
       function display6() {
            
        	document.getElementById('div2').style.display = 'none';
        	document.getElementById('div1').style.display = 'none';
        	document.getElementById('div3').style.display = 'block';     	
        	
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
<h1>User Analysis</h1>
</center>
<div class="vertical-menu">
  <a href="Home.jsp" class="active">Home</a>
  <a href="#"   onclick="display2()" >Discard Per Day Basis</a>
  <a href="#"   onclick="display3()" >Discard Extreme Rating Review</a>
  <a href="#"   onclick="display5()" >Discard Avg Review Length</a>
 </div>

<center>
<form  action= "User.jsp" id = "formid2" style="display:none; position:absolute; top:180px; left:350px;">
<table>
<tr>
<td>
<label for="PerDayThreshold"> PerDayThreshold:</label></td>
<td><input type="text"  id ="perday" name="PerDayThreshold" ></td>
</tr>
<tr>
<td>
<label for="limitExceedThreshold">
LimitExceedThreshold:
</label>
</td>
<td>
<input type="text"  id = "limit" name="limitExceedThreshold" ></td>
</tr> 
<tr align ="center">
<td style="text-align: center">
<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<div align="center">
<input type="submit"   value="Get Result"  onclick="display()" style="height:30px;  width:200px; margin: 0 auto;">
</div>
</td>
</tr>
</table>
</form>

<form  action= "User.jsp" id = "formid3" style="display:none; position:absolute; top:180px; left:350px;">
<table>
<tr>
<td>
<label for="extremeReview">ExtremeReviewThreshold:</label></td>
<td><input type="text"  id ="extreme" name="extremeReview" ></td>
</tr>
<tr>
<td>
<label for="maxRating">
maxRating:
</label>
</td>
<td>
<input type="text"  id = "max" name="maxRating" ></td>
</tr> 
<tr>
<td>
<label for="minRating">
minRating:
</label>
</td>
<td>
<input type="text"  id = "min" name="minRating" ></td>
</tr> 
<tr>
<td>
<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

<input type="submit"   value="Get Result"  onclick="display4()" style="height:30px; width:200px;">
</td>
</tr>
</table>
</form>


<form  action= "User.jsp" id = "formid4" style="display:none; position:absolute; top:180px; left:350px;">
<table>
<tr>
<td>
<label for="ReviewLengthThreshold"> ReviewLengthThreshold:</label></td>
<td><input type="text"  id ="Rev" name="ReviewLength" ></td>
</tr>
<tr>
<td>
<label for="AvgReviewLength">
AvgReviewLength:
</label>
</td>
<td>
<input type="text"  id = "AvgR" name="AvgReviewLength" ></td>
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
ReviewerAnalysis reviewer = new ReviewerAnalysis(outfile);
String outputFile = "output.csv";

if(request.getParameter("PerDayThreshold") != null && request.getParameter("limitExceedThreshold") != null){
int PerDayThreshold = Integer.parseInt(request.getParameter("PerDayThreshold"));
double limitExceedThreshold = Double.parseDouble(request.getParameter("limitExceedThreshold"));
HashMap<String,Integer> op = reviewer.discardPerDayBasis(inputFile, outputFile,PerDayThreshold ,limitExceedThreshold);

for (String key : op.keySet())
{
	out.println(key + " : "+ op.get(key));
	out.println("<br>");	
}
	
}
%>

</div>


<div id = "div2" >
<%

if(request.getParameter("extremeReview") != null && request.getParameter("minRating") != null  &&  request.getParameter("maxRating")!=null ){

double extremeReview = Double.parseDouble(request.getParameter("extremeReview"));
int minRating = Integer.parseInt(request.getParameter("minRating"));
int maxRating = Integer.parseInt(request.getParameter("maxRating"));

HashMap<String,Integer> op = reviewer.discardExtremeRatingReviewBasis(inputFile, outputFile, extremeReview,minRating,maxRating);
 
for (String key : op.keySet())
{
	System.out.println(key + " : "+ op.get(key));
	out.println(key + " : "+ op.get(key));
	out.println("<br>");	
}
}	

%>
</div>
<div id = "div3"  >
<%

if(request.getParameter("ReviewLength") != null && request.getParameter("AvgReviewLength") != null){

	int ReviewLengthThreshold = Integer.parseInt(request.getParameter("ReviewLength"));
	double AvgReviewLengthLimitThreshold = Double.parseDouble(request.getParameter("AvgReviewLength"));
	HashMap<String,Integer> op = reviewer.discardAvgReviewLengthBasis(inputFile, outputFile, ReviewLengthThreshold, AvgReviewLengthLimitThreshold);
	 
	for (String key : op.keySet())
	{
		out.println(key + " : "+ op.get(key));
		out.println("<br>");	
	}
}	
%>
</div>
</center>

<br><br>
</body>
</html>