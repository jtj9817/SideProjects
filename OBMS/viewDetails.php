<?php
    session_start();
	if(!isset($_SESSION['username']))
	{
        header ("Location: login.php");
        exit; 
    }
	INCLUDE("lib.php");
	$errors = [];
	$bID = $_GET["bookID"];

	//Set a session bookID depending on the book viewed
	//Use this session variable for rating updates
	if($bID)
		$_SESSION['bookID'] = $bID;
	    	
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
?>

<!DOCTYPE html>
<html>
<head>
	<title>View Book Details </title>
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
<a href="index.php"  class="myButton">Home</a>
<a href="menu.php"  class="myButton">Menu</a>
<a href="logout.php"  class="myButton">Logout</a>


	<?php
		$query = "SELECT * FROM BOOK WHERE bookID = $bID";
		$result = mysqli_query($connection, $query);	
			while ($row = mysqli_fetch_assoc($result))
			{
				ECHO "<fieldset><legend>Book Information</legend>";
				ECHO "<p style='text-align:left'>";
				ECHO "BookID: " .$row["bookID"] . "<br>";
				ECHO "Title: " .$row["title"] . "<br>";
				ECHO "Author: " .$row["author"] ."<br>";
				ECHO "Publish Date: " .$row["publishDate"] . "<br>";
				ECHO "ISBN: " .$row["isbn"] . "<br>";
				ECHO "Category: " .$row["category"] . "<br>";
				ECHO "Price($): " .$row["price"] . "<br>";
				ECHO "Status: " .$row["status"] . "<br>";
				ECHO "Rating: " .$row["rating"] . "<br>";
				ECHO "Note: " .$row["note"] . "<br>";
				ECHO "</p>";
			}
	;?>

<form name ="frm1" method = "post" action = "rateupdate.php" style='text-align: left'>
Book Rating Update<br>
Rating:
<select name="rating">
 	<option value="0">0</option>
	<option value="1">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
	<option value="4">4</option>
	<option value="5">5</option>
</select><br>
<input type = "submit" name = "submit" value = "Submit"/>

</fieldset>
</form>

</center>
</br>
<br>
<?php include 'footer.php' ;?>
<!--End Menu-->
</body>
</html>