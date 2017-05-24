<?php
	session_start();
	if(!isset($_SESSION['username']))
	{
        header ("Location: login.php");
        exit; 
    }
	INCLUDE("lib.php");
	$userID =  $_SESSION['username'];
	$bookID = $_SESSION['bookID'];

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

	//Block for PHP submit action 
	if(isset($_POST["submit"]))
	{
		$rating = $_POST["rating"];

		//Perform a query first to check if user already rated the book 
		$query = "SELECT userID FROM rating WHERE bookID = '$bookID'";
		$result = mysqli_query($connection, $query);
		$temp1 = mysqli_fetch_row($result);
        if ($temp1)
            ECHO "UserID is " . $temp1[0];
		$verify = $temp1[0];

		//Conditional statement to UPDATE/INSERT rating
		if($verify == $userID)
		{
			//Rating update
			$updatequery = "UPDATE rating SET rating = '$rating' WHERE bookID = '$bookID'";
        	$ratingupdate = mysqli_query($connection, $updatequery);
			if (!$ratingupdate)
			{
				DIE("Rating update failed <br>".
				mysqli_error($connection).
				"(".mysqli_errno($connection). ")"
				);
			}

			//Book rating updates
			$ratingq = "SELECT AVG(rating) FROM rating WHERE bookID = '$bookID'";
			$ratingres = mysqli_query($connection, $ratingq);
			$temprating = mysqli_fetch_row($ratingres);
			$avgrating = $temprating[0];
			if (!$ratingres)
			{
				DIE("Rating average query failed <br>".
				mysqli_error($connection).
				"(".mysqli_errno($connection). ")"
				);
			}

			$bookq = "UPDATE BOOK SET rating = '$avgrating' WHERE bookID = '$bookID'";
        	$upbookrating = mysqli_query($connection, $bookq);
			if (!$upbookrating)
			{
				DIE("Book update rating failed <br>".
				mysqli_error($connection).
				"(".mysqli_errno($connection). ")"
				);
			}

			if($ratingupdate && $upbookrating)
			{
	            ECHO "<center>";
				ECHO "<h2>Successful rating update!</h2>";
                ECHO "</center>";
			}
		}

		else if($verify != $userID)
		{
			//Block to insert rating information to book db
			$query = "INSERT INTO rating";
			$query .= "(userID, bookID, rating) ";
			$query .= "VALUES";
			$query .= "('$userID', '$bookID', '$rating')";
			$result2 = mysqli_query($connection, $query);
			//End block for rating info insertion
			if (!$result2)
			{
				DIE("Rating information insertion failed <br>".
				mysqli_error($connection).
				"(".mysqli_errno($connection). ")"
				);
			}

			//Book rating updates
			$ratingq = "SELECT AVG(rating) FROM rating WHERE bookID = '$bookID'";
			$ratingres = mysqli_query($connection, $ratingq);
			$temprating = mysqli_fetch_row($ratingres);
			$avgrating = $temprating[0];
			if (!$ratingres)
			{
				DIE("Rating average query failed <br>".
				mysqli_error($connection).
				"(".mysqli_errno($connection). ")"
				);
			}

			$bookq = "UPDATE BOOK SET rating = '$avgrating' WHERE bookID = '$bookID'";
        	$upbookrating = mysqli_query($connection, $bookq);
			if (!$upbookrating)
			{
				DIE("Book update rating failed <br>".
				mysqli_error($connection).
				"(".mysqli_errno($connection). ")"
				);
			}
			if($upbookrating)
			{
                ECHO "<center>";
				ECHO "<h2>Successful rating update!</h2>";
                ECHO "</center>";
			}
		}
	}//End of if statement for SUBMIT action

?>


<!DOCTYPE html>
<html>
<head>
	<title>Rating Update </title>
</head>
<!--Add BookStoreCSS-->
 <link rel="stylesheet" 
      type ="text/css"
      href = "BookStoreCSS.css" >
<meta charset="UTF-8">
<!--End CSS include-->

<body>
<!--Menu-->
<br>
<br>
<br>
<center>
<br>
<?php include 'header.php' ;?>
    <ul>
        <li class="menu"><a href="addBook.php"  class="myButton">Add Book</a></li>
        <li class="menu"><a href="viewBooks.php"  class="myButton">View Books</a></li>
        <li class="menu"><a href="index.php"  class="myButton">Home</a></li>
		<li class="menu"><a href="search.php"  class="myButton">Search for a book</a></li>
		<li class="menu"><a href="<logo></logo>ut.php"  class="myButton">Logout</a></li>
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