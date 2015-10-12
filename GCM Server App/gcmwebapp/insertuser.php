<?php
include_once './db_functions.php';
//Create Object for DB_Functions clas
$db = new DB_Functions(); 
$emailID = $_POST["emailId"];
$regId = $_POST["regId"];
$res = $db->insertUser($emailID, $regId);
echo "Email Id ".$emailID." RegId ".$regId ;
if ($res) {
	echo "GCM Reg Id bas been shared successfully with Server";
} else {			 
	echo "Error occured while sharing GCM Reg Id with Server web app";			          
}
?>