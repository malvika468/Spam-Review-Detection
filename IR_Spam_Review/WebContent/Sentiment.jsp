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
              
        	document.getElementById('div1').style.display = 'block';
        }
        function display2()
        {
        	document.getElementById('formid').style.display = 'block';
        	document.getElementById('div1').style.display = 'none';
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
<h1>Sentiment Analysis</h1>
</center>
<div class="vertical-menu">
  <a href="Home.jsp" class="active">Home</a>
  <a href="#"  onclick="display2()" >Discard Deviated Score</a>
 </div>



<center>
<form  action= "Sentiment.jsp" id = "formid" style="display:none; position:absolute; top:180px; left:350px;">
<table>
<tr>
<td>
<label for="deviationThreshold"> DeviationThreshold:</label></td>
<td><input type="text"  id ="deviation" name="deviationThreshold" ></td>
</tr>
<tr align ="center">
<td style="text-align: center">
<br><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<div align="center">
<input type="submit"   value="Get Result"  onclick="display1()" style="height:30px;  width:200px; margin: 0 auto;">
</div>
</td>
</tr>
</table>
</form>

<div id = "div1"  >
<%
BufferedWriter outfile = new BufferedWriter(new FileWriter("outputfile.txt"));		
AFFINSentimentAnalysis senti = new AFFINSentimentAnalysis(outfile);
String inputFile = "C:\\Users\\user\\Desktop\\NeonWorkspace\\IR_GUI\\data.csv";

if(request.getParameter("deviationThreshold") != null){	
double deviationThreshold = Double.parseDouble(request.getParameter("deviationThreshold"));
senti.storeAfinnScores(inputFile,"reviewsWithAfinnScore.csv");
senti.computeAfinnAverage("reviewsWithAfinnScore.csv","reviewsWithAfinnScoreAndProductWiseAverage.csv");
HashMap<String,Integer> op = senti.discardDeviatedScores("reviewsWithAfinnScoreAndProductWiseAverage.csv","reviewsAfterAfinn.csv",deviationThreshold);

for (String key : op.keySet())
{
	out.println(key +  + op.get(key));
	out.println("<br>");	
}
}
%>
</div>
</center>

</body>




</html>