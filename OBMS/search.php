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
<img src="BookStore.png" alt="BookStore" title="BookStore Banner">
<br>
<?php include 'header.php' ;?>
<!--Menu-->
<br>
<br>
<br>
    <ul class="menu">
        <li class="menu"><a href="index.php"  class="myButton">Home</a></li>
        <li class="menu"><a href="menu.php"  class="myButton">Menu</a></li>
		<li class="menu"><a href="logout.php" class="myButton">Logout</a></li>
    </ul>
</br>
<br>

<form name ="frm1" method = "post" action = "search.php">
<fieldset><legend>Enter book title</legend>
Search: <input type = "text" name = "usrquery" /><br>
<input type = "submit" name = "submit" value = "Search"/>
<input type = "reset" name = "reset" value = "Reset"/>
<br>
<pre>
<?php
	//Block for PHP submit action 
	if(isset($_POST["submit"]))
	{
		$usrquery = $_POST["usrquery"];

		//Block for PHP validation
		if(!isPresent($usrquery))
		{
			$errors['usrquery'] = "Error: Search parameters empty. Try again";
        }
		else if(isPresent($usrquery))
		{
			//Block to search book
			$query = "SELECT * FROM BOOK WHERE title LIKE '$usrquery%' ORDER BY rating DESC";
            $result = mysqli_query($connection, $query);
 			//End block for book info insertion
	
			//Check if search query was succcessful
			if (!$result)
			{
				DIE("Search query failed <br>".
				mysqli_error($connection).
				"(".mysqli_errno($connection). ")"
				);
			}
            else if ($result)
            {
                ECHO "Search result(s)";
                //Table implementation
                ECHO "<br>";
                ECHO "<table> ";

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
            }
            //End of block for search query check
        }
	}//End of block for submit action

	//Block to initialize default values for variables
	else
	{
		$usrquery = "";
	}
	//End of block for initialization

;?>
</pre>

</fieldset>
<br>
<?php display_errors_in_red('usrquery', $errors);
?>

<br>
</form>
</center>

<?php include 'footer.php' ;?>
</body>
</html>

<?php 
    //Close db connection
	mysqli_close($connection);
?>