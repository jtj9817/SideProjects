<?php
    session_start();
	if(!isset($_SESSION['username']))
	{
        header ("Location: login.php");
        exit; 
    }
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
<h2>Welcome to the Main Menu</h2>
<?php include 'header.php' ;?>
    <ul>
        <li class="menu"><a href="addBook.php"  class="myButton">Add Book</a></li>
        <li class="menu"><a href="viewBooks.php"  class="myButton">View Books</a></li>
        <li class="menu"><a href="index.php"  class="myButton">Home</a></li>
		<li class="menu"><a href="search.php"  class="myButton">Search for a book</a></li>
		<li class="menu"><a href="logout.php"  class="myButton">Logout</a></li>
    </ul>
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