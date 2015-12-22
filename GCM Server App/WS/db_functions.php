<?php
//if(!defined('INCLUDE_CHECK')) die('You are not allowed to execute this file directly');
 
class DB_Functions {
 
    private $db;
 
   function __construct() {
        include_once './db_connect.php';
        // Connect to database
        $this->db = new DB_Connect();
        $this->db->connect();
    }
    // destructor
   function __destruct() {
 
    }
    /**
     * Insert new user
     * 
     */
    public function insertUser($emailId, $gcmRegId) {
        // Insert user into database
        $result = mysql_query("INSERT INTO gcmusers (emailid,gcmregid) VALUES('$emailId','$gcmRegId')");        
        if ($result) {
            return true;
        } else {             
            return false;                      
        }
    }
	
    /**
     * Select all user
     * 
     */
    public function getAllUsers() {
        $result = mysql_query("select Owner_name,email FROM members");
        return $result;
    }
	
	/**
     * Select Maintainance Master to check Month and Year enrty is exists or not
     * 
     */
	public function getMaintainanceMaster($month,$year)
	{
		$result = mysql_query("select payid from MaintainanceMaster where month='$month' and year='$year'");
        return $result;
	}
	
	/**
     * Select Maintainance Detail to check flatid and owner id and Month and Year enrty is exists or not
     * 
     */
	public function getMaintainanceDetail($flt_id,$owner_id,$month,$year)
	{
		$result = mysql_query("select dtl_Id from MaintainanceDetail where flt_id='$flt_id' and OwnerId='$owner_id' and month='$month' and year='$year'");
        return $result;
	}
	/**
     * Insert Maintainance Master 
     * 
     */
	public function insertMaintainanceMaster($month,$year)
	{
		$result = mysql_query("INSERT INTO MaintainanceMaster (MONTH,YEAR) VALUES ('$month','$year')");
		
		$newID = mysql_insert_id();
		return $newID;		
	}
	
	/**
     * Insert Maintainance Detail
     * 
     */
	public function insertMaintainanceDetail($resultNewID,$month,$year,$owner_id,$flt_id,$amount,$paidby,$datepicker)
	{
		$result = mysql_query("INSERT INTO MaintainanceDetail (payid,MONTH,YEAR,OwnerId,flt_id,Amount,Paidby,Entrydt) VALUES ('$resultNewID','$month','$year','$owner_id','$flt_id','$amount','$paidby','$datepicker')");
		
		$newID = mysql_insert_id();
		return $newID;		
	}
	
	/**
     * Update Maintainance Detail
     * 
     */
	public function UpdateMaintainanceDetail($resultDetailID,$amount,$paidby,$datepicker)
	{
		$result = mysql_query("UPDATE MaintainanceDetail set Amount='$amount' , Paidby='$paidby' ,Entrydt='$datepicker' where  dtl_Id='$resultDetailID' ");
		
		$newID = mysql_insert_id();
		return $newID;		
	}
	
	
    /**
     * Get GCMRegId
     * 
     */
    public function getGCMRegID($usr){
         $result = mysql_query("SELECT gcmregid FROM members WHERE usr= "."'$usr'");
         return $result;
    }
}
?>