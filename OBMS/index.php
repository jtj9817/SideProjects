<pre>
<?php
	INCLUDE("lib.php");
	$errors = [];
	    	
	//Initialize connection by parsing db login info from text file
	$lines=file('topsecret.txt');
	$dbhost=trim($lines[0]);
	$dbuser=trim($lines[1]);
	$dbpass=trim($lines[2]);
	$dbname=trim($lines[3]);
	$connection = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname);
	//End of connection init

    //Check for connection errors
    if (mysqli_connect_errno())
	{
		DIE("Database connection failed: ".
			mysqli_connect_error().
			" ( ". mysqli_connect_errno(). " )"
		);
	} 
    //End connection error checking
?>
</pre>

<!DOCTYPE html>
<html>
<head>
	<title>Joshua's Bookstore </title>
</head>
<!--Add BookStoreCSS-->
 <link rel="stylesheet" 
      type ="text/css"
      href = "BookStoreCSS.css" >
<meta charset="UTF-8">
<!--End CSS include-->

<body>
<center>
<img style="vertical-align:middle" src="BookStore.png" alt="BookStore" title="BookStore Banner">
</center>
<!--Menu-->
<br>
<br>
<br>
<center>
    <ul>
        <li class="menu"><a href="register.php"  class="myButton">Register</a></li>
        <li class="menu"><a href="login.php"  class="myButton">Login</a></li>
    </ul>
<p>You have to login to use the site.
<br>
Please register first.</p>
</center>
</br>
<br>
<?php include 'footer.php' ;?>
<!--End Menu-->
</body>
</html>

<?php 
    //Close db connection
	mysqli_close($connection);
?>