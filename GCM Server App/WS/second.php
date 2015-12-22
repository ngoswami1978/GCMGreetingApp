<!--  This code will execute when form method is set to POST  -->
<?php
	if(isset($_POST['mth']))
	{
		$month = $_POST['mth'];
		$year = $_POST['yr'];
		
		echo "<span class='success'>Form Submitted By <b>POST METHOD</b></span><br/>";
		echo "Month : ".$month."<br/>Year : ".$year;
	}
	?>
	<!--  This code will execute when form method is set to GET  -->
	<?php
	if(isset($_GET['mth']))
	{
		$month = $_GET['mth'];
		$year = $_GET['yr'];
		echo "<span class='success'>Form Submitted By <b>GET METHOD</b></span><br/>";
		echo "Month : ".$month."<br/>Year : ".$year;
	}
	
	$json=$_SERVER['REQUEST_METHOD'];	
	/* Output header */
	header('Content-type: application/json');
	echo json_encode($json);	
	
?>