<?php

include_once './db_functions.php';
date_default_timezone_set('Asia/Kolkata');

	//echo "The time is " . date("h:i:sa");
	//echo "The date time is " . date("Y-m-d H:i:s");

if($_SERVER['REQUEST_METHOD'] == "POST"){	

		//Create Object for DB_Functions class
		$db = new DB_Functions(); 
		
		// Get data
		$month = isset($_POST["mth"]) ? mysql_real_escape_string($_POST["mth"]) : "";    
		$year = isset($_POST["yr"]) ? mysql_real_escape_string($_POST["yr"]) : "";     
		$owner_id= isset($_POST["O_id"]) ? mysql_real_escape_string($_POST["O_id"]) : "";  
		$flt_id= isset($_POST["f_id"]) ? mysql_real_escape_string($_POST["f_id"]) : "";    
		$amount= isset($_POST["amt"]) ? mysql_real_escape_string($_POST["amt"]) : "";    
		$paidby= isset($_POST["pdby"]) ? mysql_real_escape_string($_POST["pdby"]) : "";    		
		$datepicker = isset($_POST["datepicker"]) ? mysql_real_escape_string($_POST["datepicker"]) : "";
				
		//$dateArray = explode('/', $datepicker);
		//$date = $dateArray[2].'-'.$dateArray[0].'-'.$dateArray[1];
		
		$date = date("Y-m-d H:i:s");
		
		$json= "Month- ".$month." Year- ".$year."Ownerid- ".$owner_id."flt id- ".$flt_id."amount- ".$amount."paidby- ".$paidby."datepicker- ".$datepicker."Date format- ".$date ; 
				
		
		//Generate the sql query based on month and year 
		//Execute the query
		$resultMaster = $db->getMaintainanceMaster($month,$year);
		
		$result_array = array();

		if (!$resultMaster) {
			echo "DB Error, could not query the database\n";
			echo 'MySQL Error: ' . mysql_error();
			exit;
		}

		//Get the rowcount of Maintainance Master table 
		$rowcount= mysql_num_rows($resultMaster);			
		array_push($result_array,array('Rowcount Maintainance Master '=>$rowcount));		
		
		 
		//if the count is 0 i.e. no matching rows are found. create a new record in master table (maintainance master) with Month and Year
		if($rowcount==0)
		{			
			//Query to insert Manitainance Master table
			$resultMasterNewID=$db->insertMaintainanceMaster($month,$year);		
			
			if (!$resultMasterNewID) {				 
				 array_push($result_array,array('Result '=>0));
				 echo "DB Error, could not query the database\n";
				 echo 'MySQL Error: ' . mysql_error();
				 exit;				 
			} else {				
				array_push($result_array,array('New Inserted Id '=>$resultMasterNewID));
				//Query to insert Manitainance details Table
				$resultdetailNewID=$db->insertMaintainanceDetail($resultMasterNewID,$month,$year,$owner_id,$flt_id,$amount,$paidby,$date);
			 }			 
		}	
		else 
		{ //Else there is an maintainance details flatid,ownerid,year and month with the given credentials
			array_push($result_array,array('Details Insert '=>1));
			
			// $row = mysql_fetch_row($resultMaster);
			// $resultMasterID = $row['payid'];
			
			while ($row = mysql_fetch_assoc($resultMaster)) 
			{    	
				$resultMasterID = $row['payid'];			
			}
				
		 			
			//Check in Maintainance Details table for flat and Owner entry if exists please update the same else insert the New detail 
			//Execute the query
			$resultdetail = $db->getMaintainanceDetail($flt_id,$owner_id,$month,$year);
			$result_array = array();

			if (!$resultdetail) {
				echo "DB Error, could not query the database\n";
				echo 'MySQL Error: ' . mysql_error();
				exit;
			}

			//Get the rowcount
			$rowcount= mysql_num_rows($resultdetail);
			array_push($result_array,array('Rowcount Maintainance Details  '=>$rowcount));

			if( $rowcount==0 )
			{			
				if ($resultMasterID>0)
				{
					//Insert Child Maintainance details Table Query to insert Manitainance Master table
					$resultdetailNewID=$db->insertMaintainanceDetail($resultMasterID,$month,$year,$owner_id,$flt_id,$amount,$paidby,$date);
					if (!$resultdetailNewID) {
						echo "DB Error, could not query the database\n";
						echo 'MySQL Error: ' . mysql_error();
						exit;
					}			
				}
				else
				{
					array_push($result_array,array('Maintainance Master  ID '=>$resultMasterID));					
					echo "DB Error, You are inserting the detail table without payid , Please check from Admin\n";
				}
			}
			else			
			{
				// $rowdlts = mysql_fetch_row($resultdetail);
				// $resultDetailID = $rowdlts['dtl_Id'];			
				while ($row = mysql_fetch_assoc($resultdetail)) 
				{    	
					$resultDetailID = $row['dtl_Id'];				
				}
				
				array_push($result_array,array('Maintainance Master  ID '=>$resultMasterID));					
				array_push($result_array,array('Maintainance Details  ID '=>$resultDetailID ));					
				
				// Update Child Maintainance details Table Query to insert Manitainance Master table
				$resultdetail=$db->UpdateMaintainanceDetail($resultDetailID,$amount,$paidby,$date);				
			}
		}	 
}	 
else
{
	$json1=$_SERVER['REQUEST_METHOD'];
}
	/* Output header */
	header('Content-type: application/json');
	echo json_encode($json);
	echo json_encode(array("result"=>$result_array));

?>