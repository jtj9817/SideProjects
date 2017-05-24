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

	//Block for PHP submit action 
	if(isset($_POST["submit"]))
	{
		$title = $_POST["title"];
		$author = $_POST["author"];
		$pubdate = $_POST["pubdate"];
		$isbn = $_POST["isbn"];
		$category = $_POST["category"];
		$price = $_POST["price"];
		$status = $_POST["status"];
		$rating = $_POST["rating"];
		$note = $_POST["note"];
		$userID =  $_SESSION['username'];

		//Block for PHP validation
		if(!isPresent($title))
		{
			$errors['title'] = "Error: Input Title.";
		}
		if(!isPresent($author))
		{
			$errors['author'] = "Error: Input Author.";
		}
		if(!isPresent($pubdate) || !validPubDate($pubdate))
		{
			$errors['pubdate'] = "Error: Input publish date in format mm/dd/yyyy.";
		}
		if(!isPresent($isbn))
		{
			$errors['isbn'] = "Error: Input ISBN.";
		}
		if(!isPresent($price) || !PriceCheck($price))
		{
			$errors['price'] = "Error: Input a valid value for price.";
		}
		if(!isbnCheck($isbn))
		{
			$errors['isbn'] = "Error: ISBN already exists. Input a valid one.";
		}

		if(isPresent($title) && isPresent($author) && isPresent($isbn) && isbnCheck($isbn) 
		&& isPresent($price) && PriceCheck($price) && isPresent($pubdate) && validPubDate($pubdate))
		{
			//Block to insert book information to book db
			$query = "INSERT INTO BOOK";
			$query .= "(title, author, publishDate, category, price, status, note, isbn) ";
			$query .= "VALUES";
			$query .= "('$title', '$author', '$pubdate', '$category', '$price', '$status', 	'$note', '$isbn')";
			$result = mysqli_query($connection, $query);
			//End block for book info insertion
	
			//Check if book info insertion was succcessful
			if (!$result)
			{
				DIE("Book information insertion failed <br>".
				mysqli_error($connection).
				"(".mysqli_errno($connection). ")"
				);
			}
			//End of block for book information insertion

			
			//Get bookID of latest book inserted
			$querymax = "SELECT MAX(bookID) FROM BOOK";
			$bookID = mysqli_query($connection, $querymax);
			$row = mysqli_fetch_row($bookID);
			//Solution to using a mysqli_result object for the next queries 
			$bookID2 = $row[0];
			
			
			//Block to insert rating information to book db
			$query = "INSERT INTO rating";
			$query .= "(userID, bookID, rating) ";
			$query .= "VALUES";
			$query .= "('$userID', '$bookID2', '$rating')";
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
			$ratingq = "SELECT AVG(rating) FROM rating WHERE bookID = '$bookID2'";
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

			$bookq = "UPDATE BOOK SET rating = '$avgrating' WHERE bookID = '$bookID2'";
        	$upbookrating = mysqli_query($connection, $bookq);
			if (!$upbookrating)
			{
				DIE("Book update rating failed <br>".
				mysqli_error($connection).
				"(".mysqli_errno($connection). ")"
				);
			}

			if($result2 && $upbookrating && $result)
			{
				ECHO "Book and rating successfully inserted!";
			}
			

		}
		//End of block for validation
	}
	//End of block for submit action

	//Block to initialize default values for variables
	else
	{
		$title = "";
		$author = "";
		$pubdate = "";
		$isbn = "";
		$category = "";
		$price = "";
		$rating = "";
		$status = "";
		$note = "";
	}
	//End of block for initialization

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

<!--End Menu-->
<h2>Enter Book Information</h2>
<!--Forms Field-->
<form name ="frm1" method = "post" action = "addBook.php">

<fieldset><legend>Add Book</legend>
Title: <input type = "text" name = "title" 
	value ="<?php ECHO htmlspecialchars($title); ?>">
	<?php display_errors_in_red('title', $errors);
	?>
<br>

Author(s): <input type = "text" name = "author" 
value ="<?php ECHO htmlspecialchars($author); ?>">
	<?php 
	display_errors_in_red('author', $errors); 
	?>
<br>

Publish Date: <input type = "text" name = "pubdate" 
value ="<?php ECHO htmlspecialchars($pubdate); ?>">
	<?php 
	display_errors_in_red('pubdate', $errors); 
	?>
<br>

ISBN: <input type = "text" name ="isbn" 
value ="<?php ECHO htmlspecialchars($isbn); ?>">
	<?php 
	display_errors_in_red('isbn', $errors);
	?>
<br>

Category:
<input type = "radio" name ="category" value = "Hardcover">Hardcover
<input type="radio" name = "category" value = 
"Paperback">Paperback
<input type="radio" name = "category" value = 
"eBook">eBook
</br>

Price($): <input type = "text" name ="price" 
value ="<?php ECHO htmlspecialchars($price); ?>">
	<?php display_errors_in_red('price', $errors);
	?>
<br>

Status:
<select name="status">
 	<option value="In-stock">In-stock</option>
	<option value="Out of stock">Out of stock</option>
	<option value="Pre-order">Pre-order</option>
	<option value="N/A">N/A</option>
<select>
<br>

Rating:
<select name="rating">
 	<option value="0">0</option>
	<option value="1">1</option>
	<option value="2">2</option>
	<option value="3">3</option>
	<option value="4">4</option>
	<option value="5">5</option>
<select><br>

Note:
<br>
<textarea name ="note" rows="4" cols="50">
</textarea>
<br>

<!--User action; submit or reset fields-->
<input type = "submit" name = "submit" value = "Submit"/>
<input type = "reset" name = "reset" value = "Reset"/>
<!--End user action-->

</fieldset>
<!--End form fields-->
</form>
<!--End forms field-->
</center>

<?php include 'footer.php' ;?>
</body>
</html>

<?php 
    //Close db connection
	mysqli_close($connection);
?>