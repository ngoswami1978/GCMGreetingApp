<?php

define('HOST','myandroidng.fatcowmysql.com');
define('USER','sa');
define('PASS','$db$@$');
define('DB','myandroidng');
 
 if (!$link = mysql_connect(HOST,USER,PASS,DB)) {
    echo 'Could not connect to mysql';
    exit;
}

if (!mysql_select_db('myandroidng', $link)) {
    echo 'Could not select database';
    exit;
}


//$sql    = 'SELECT  members.* ,  flat_master.* FROM flat_master A LEFT OUTER JOIN members B ON A.flt_id = B.flt_id  WHERE  members.flt_id =  flat_master.flt_id';

	
$sql    = 'SELECT A.flt_id,A.flt_no,A.flt_type ,IFNULL(B.Owner_name,"Not Registered") as Owner_name ,IFNULL(B.id,0) as id,IFNULL(B.usr,"Not Registered") as usr,B.pass,B.email,B.regIP,B.dt,B.gcmregid
,B.approve_status,B.Owner_contact,B.Renter_name,B.Renter_contact FROM flat_master A
LEFT OUTER JOIN members B ON A.flt_id = B.flt_id';

	


$result = mysql_query($sql, $link);

$result_array = array();

if (!$result) {
    echo "DB Error, could not query the database\n";
    echo 'MySQL Error: ' . mysql_error();
    exit;
}

while ($row = mysql_fetch_assoc($result)) {    	
	array_push($result_array,
	
	array(
	'flt_id'=>$row['flt_id'],
	'flt_no'=>$row['flt_no'],
	'flt_type'=>$row['flt_type'],
	'id'=>$row['id'],
	'Owner_name'=>$row['Owner_name'],	
	'usr'=>$row['usr'],
	'pass'=>$row['pass'],
	'email'=>$row['email'],
	'regIP'=>$row['regIP'],
	'dt'=>$row['dt'],
	'gcmregid'=>$row['gcmregid'],
	'approve_status'=>$row['approve_status'],
	'flt_id'=>$row['flt_id'],	
	'Owner_contact'=>$row['Owner_contact'],
	'Renter_name'=>$row['Renter_name'],
	'Renter_contact'=>$row['Renter_contact'],
	));
}

echo json_encode(array("register_member"=>$result_array));

mysql_close($link);
?>