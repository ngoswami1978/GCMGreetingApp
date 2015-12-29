<!DOCTYPE html>
<html>
<head>
<title>PHP GET and POST Method Example</title>

<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>  
<link rel="stylesheet" href="/resources/demos/style.css">
<script>
  $(function() {
    $( "#datepicker" ).datepicker();
  });
  </script>

<!-- Include CSS  File Here-->
<link rel="stylesheet" href="style.css"/>
<!-- Include JavaScript File Here-->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="get_post.js"></script>
</head>
<body>
<div class="container">
<div class="main">
<form method="" action="first.php" id="form">
<h2>PHP GET and POST Method Example</h2>
<label>Select Form Method :</label>
<span><input type="radio" name="method" value="post"> POST
<input type="radio" name="method" value="get"> GET </span>

<label>Month</label>
<input type="text" name="mth" id="mth" />

<label>year :</label>
<input type="text" name="yr" id="yr" />

<label>owner_id :</label>
<input type="text" name="O_id" id="O_id" />

<label>flt_id :</label>
<input type="text" name="f_id" id="f_id" />

<label>amount :</label>
<input type="text" name="amt" id="amt" />


<label>paidby :</label>
<input type="text" name="pdby" id="pdby" />

<p>Date Picker: <input type="text" name="datepicker" id="datepicker"></p>

<input type="submit" name="submit" id="submit" value="Submit">
</form>
<?php 
	/* Output header */    
    //$json=$path;
	//header('Content-type: application/json');
	//echo json_encode($json);		
    include_once("ws_crud_maintainance.php");    
?>
<?php 
    include_once("datepicker.php");
?>

</div>
</div>
</body>
</html>