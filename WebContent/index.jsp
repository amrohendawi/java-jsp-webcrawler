<html>
<link rel="stylesheet" href="css/styles.css" type="text/css">
<style>
input[type=text], select {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

input[type=submit] {
  width: 100%;
  background-color: #4CAF50;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #45a049;
}

div {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
}

.center {
  text-align: center;
  border: 3px solid green;
}

h2 {
  text-align: center;
  text-transform: uppercase;
  color: #4CAF50;
}

p {
  text-indent: 50px;
  text-align: justify;
  letter-spacing: 3px;
}

a {
  text-decoration: none;
  color: #008CBA;
}

.footer {
  position: fixed;
  left: 0;
  bottom: 19;
  width: 100%;
  background-color: #4CAF50;
  color: white;
  text-align: center; 
  padding:5px 10px 20px 10px;
  border:1px solid #4CAF50; 
  margin: -30px 0 -60px 0
  
}

</style>

<body>

<div class="center">
<h2>Welcome to the almighty Webcrawler</h2>

<h3>The time on server is <%= new java.util.Date() %></h3>

<div>

<form action="webCrawlerServlet">
<div class="center">
	<table>
		<tr>
			<td>please Enter valid URL</td>
			<td><input type="text" name="url"/></td>
			<td>Searchword</td>			
			<td><input type="text" name="keyword"/></td>
			<td>max number of results</td>			
			<td><input type="number" name="size"/></td>
		</tr>
		<tr>
			<td><input type="submit"/></td>
		</tr>
		
	</table>
</div>
</form>
</div>
</div>

</body>

<div class="footer">
  <p>Desgined by Amro Hendawi</p>
</div>

</html>