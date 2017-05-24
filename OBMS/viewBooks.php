<?php
	session_start();
	if(!isset($_SESSION['username']))
	{
        header ("Location: login.php");
        exit; 
    }
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

    //Perform database query
	$query = "SELECT *";
	$query .= "FROM BOOK ORDER BY rating DESC";
    $result = mysqli_query($connection, $query);
    //End of db query block
?>

<!DOCTYPE html>
<html>
<head>
  <title> Book Catalog </title>
  <style>
    th{
		 border: solid 2px blue;
	  }
  </style>
</head>

<!--Add BookStoreCSS-->
 <link rel="stylesheet" 
      type ="text/css"
      href = "BookStoreCSS.css" >
<meta charset="UTF-8">
<!--End CSS include-->

<body>

<center>
<img src="BookCatalog.png" alt="BookCatalogue" title="BookStore Catalogue">
<br>
<?php include 'header.php' ;?>

    <ul>
        <li class="menu"><a href="menu.php"  class="myButton">Menu</a></li>
        <li class="menu"><a href="logout.php"  class="myButton">Logout</a></li>
        <li class="menu"><a href="index.php"  class="myButton">Home</a></li>
    </ul>

    <pre>
    <?php

    //Table implementation
	ECHO "<br>";
	ECHO "<table> ";

	ECHO "<caption><h2>Book Catalog</h2> </caption>";
	ECHO "<tr>";

	ECHO "<th> Book Title </th>";
	ECHO "<th> Book Author </th>";
	ECHO "<th> Publish Date </th>";
	ECHO "<th> View Book Information</th>";
	ECHO "</tr>";


	while ($row = mysqli_fetch_assoc($result))
	{
		$rbid = $row["bookID"];
		ECHO "<tr>";
		ECHO "<td>".$row["title"]."</td>";
		ECHO "<td>".$row["author"]."</td>";
		ECHO "<td>".$row["publishDate"]."</td>";
		ECHO "<td><a href =\"viewDetails.php?bookID=$rbid\">View Details<a/></td>";
		ECHO "</tr>";
	}
	ECHO "</tr>";
	ECHO "</table>";
    //End of table implementation
   ?>
   </pre>

   <?php 
   //Release query results to prevent errors
	mysqli_free_result($result);
   ?>

</center>
</body>
</html>

<?php 
    //Close BOOK db connection
	mysqli_close($connection);
?>