<html>
<head><title>View Users</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
 <link rel="stylesheet" type="text/css" href="/Apartment/Login.css" media="screen" />
<style>
body {
	font: normal medium/1.4 sans-serif;
	background-image: url('img/bg_1.jpg');
    background-size: cover;  
}

div.greetblock, div.serverresponse {
  border-collapse: collapse;
  width: 900px;
  margin-left: auto;
  margin-right: auto;
  align: center;  
}

div#norecord{
	margin-top:10px;
	width: 15%;
	margin-left: auto;
	margin-right: auto;
}
input,select{
	cursor: pointer;
}
img{
	margin-top: 10px;
	height: 200px;
	width: 300px;
}
select{
width: 200px
}

div.leftdiv{
	width: 400px;
	padding: 0 10px;
	float: left;
	border: 1px solid #ccc;
	margin: 5px;
	height: 320px;
	text-align:center;
	overflow:auto;
}

div.rightdiv{
	width: 400px;
	padding: 0 10px;
	float: right;
	border: 1px solid #ccc;
	margin: 5px;
	height: 320px;
	text-align:center;
}
hidediv{
display: none;
}
	p.header{
	height: 40px;
	background-color: #EB5038;
	padding: 10px;
	color: #fff;
	text-align:center;
	margin: 0;
	margin-bottom: 10px;
}
textarea{
	font-size: 25px;
	font-weight: bold;
}

table.imagetable {
	font-family: verdana,arial,sans-serif;	
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
}
table.imagetable th {
	background:#b5cfd2 url('img/cell-blue.jpg');
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
}
table.imagetable td {
	background:#dcddc0 url('img/cell-grey.jpg');
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #999999;
}

/*CSS for dropdown box*/
select
{
	width: 200px;
	height: 29px;
	border-radius: 3px;
	border: 1px solid #CCC;
	font-weight: 200;
	font-size: 15px;
	font-family: Verdana;
	box-shadow: 1px 1px 5px #CCC;
}
select: hover
{
	width: 200px;
	height: 29px;
	border-radius: 3px;
	border: 1px solid #CCC;
	font-weight: 200;
	font-size: 15px;
	font-family: Verdana;
	box-shadow: 1px 1px 5px #CCC;
}

/*CSS for textarea*/
textarea
{
	width: 300px;
	height: 120px;
	border-radius: 3px;
	border: 1px solid #CCC;
	padding: 8px;
	font-weight: 200;
	font-size: 15px;
	font-family: Verdana;
	box-shadow: 1px 1px 5px #CCC;
}
textarea:hover
{
	width: 300px;
	height: 120px;
	border-radius: 3px;
	border: 1px solid #aaa;
	padding: 8px;
	font-weight: 200;
	font-size: 15px;
	font-family: Verdana;
	box-shadow: 1px 1px 5px #CCC;
}

#overLeft
{
	border: 1px solid #ccc;
    position:absolute;
    width:180px;
    height:440px;
    text-align: center; /*handles the horizontal centering*/	
	left:5px;
	top:5px;
	z-index:-1;
}
#overRight
{
	border: 1px solid #ccc;
    position:absolute;
    width:180px;
    height:440px;
    text-align: center; /*handles the horizontal centering*/	
	right:5px;
	top:5px;
	z-index:-1;
}
#overBottomRight
{
	border: 1px solid #ccc;
    position:absolute;
    width:180px;
    height:440px;
    text-align: center; /*handles the horizontal centering*/	
	right:5px;
	bottom:5px;
	z-index:-1;
}
#overBottomLeft
{
	border: 1px solid #ccc;
    position:absolute;
    width:180px;
    height:440px;
    text-align: center; /*handles the horizontal centering*/	
	left:5px;
	bottom:5px;
	z-index:-1;
}
.Centered
{
    display: inline-block;
    vertical-align: middle;
	margin: auto auto;
	-webkit-filter: blur(1px);
    filter: blur(1px);
}


</style>
<script>
function sendMsg(){
var msgLength = $.trim($("textarea").val()).length;
var checkedCB = $("input[type='checkbox']:checked").length;
if( checkedCB == 0){
	alert("You must select atleast one User to send message");
}else if(msgLength == 0){
	alert("You left the message field blank, please fill it");
}else{
	var formData = $(".wrapper").find("input").serialize() + "&imgurl="+ $("#festival").val() + "&message=" + $("textarea").val();	
	$.ajax({type: "POST",data: formData, url: "processmessage.php", success:function(res){
		$(".greetblock").slideUp(1000);
		$(".serverresponse").prepend(res).hide().fadeIn(2000);
	}});
}
}
$(function(){
	$(".serverresponse").hide()
	$("input[type='checkbox']").click(function(){
		if($(this).is(':checked')){
			$(this).parent().css("border","3px solid red");
		}else{
			$(this).parent().css("border","0px");
		}
	});
	
	$("div.leftdiv, div.rightdiv").hover(function(){
		$(this).css("background","#FAFAFA");
	},function(){
		$(this).css("background","#fff");
	});
	
	$("#festival").change(function(){
		$("img").attr("src",$(this).val());
	});
	
	$("#sendmsg").click(function(){
		$(".serverresponse").fadeOut(300,function(){
			$(".greetblock").fadeIn(1000);
		});		
	});
});
</script>
</head>
<body>

<?php
	define('INCLUDE_CHECK',true);
	session_name('tzLogin');
	session_set_cookie_params(2*7*24*60*60);
	session_start();
?>

<?php
     include_once 'db_functions.php';
     $db = new DB_Functions();
     $users = $db->getAllUsers();
     if ($users != false)
         $no_of_users = mysql_num_rows($users);
     else
         $no_of_users = 0;
?>

<?php
    if ($no_of_users > 0) {
?>

<div class="greetblock">
<div class="leftdiv">
<p class="header">Select Users to whom you want to send Greeting message
</p>

	<table class="imagetable">
		<tr id="header">
               <th>Send Message?</th>
               <th>Member</th>
               <th>Email</th>               
        </tr>
	<?php
		while ($row = mysql_fetch_array($users)) {
	?> 
	<tr>
	<td><span class="wrapper"><input type="checkbox" name="sendmsg[]" value="<?php echo $row["usr"] ?>"/></span></td>
	<td><span><?php echo $row["usr"] ?></span></td>
	<td><span><?php echo $row["email"] ?></span></td>
	</tr>
	<?php } ?>
	</table>

</div>
<div class="rightdiv">
<p class="header">Select Greeting Card
</p>
<select id="festival">
<option value="http://www.myandroidng.com/Apartment/greeting/img/diwali.png">Diwali</option>
<option value="http://www.myandroidng.com/Apartment/greeting/img/pongal.png">Pongal</option>
<option value="http://www.myandroidng.com/Apartment/greeting/img/christmas.png">Christmas</option>
<option value="http://www.myandroidng.com/Apartment/greeting/img/ramzan.png">Ramadan</option>
<option value="http://www.myandroidng.com/Apartment/greeting/img/birthday.png">Birthday</option>

</select>
<br/>
<img src="http://www.myandroidng.com/Apartment/greeting/img/diwali.png"/>
</div>
<div class="leftdiv">
<p class="header">Type your message
</p>
<textarea cols="20" rows="5" value="txtarea">

</textarea>
</div>
<div class="rightdiv">
<p class="header">Send your customized message to Members
</p>
<center>
<button onclick="sendMsg()">Send Message</button>
</center>
</div>
</div>
<div class="serverresponse hidediv">
<center><button id="sendmsg">Send Message Again</button></center>
</div>
<?php }
else{ 
?>
<div id="norecord">
Members not yet registered
</div>
<?php } ?>

</body>
</html>
                          
    