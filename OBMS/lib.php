<?php
function isPresent($var) 
{
	return isset($var) && trim($var) == true;
}

function PriceCheck($price)
{
	if(is_numeric($price))
	{
		if($price >0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	else
	{
		return false;
	}
}

function validPubDate($var)
{
	list($pmon, $pday, $pyear) = explode('/', $var);

	if(checkdate($pmon, $pday, $pyear))
	{
		return true;
	}
	else
	{
		return false;
	}

}

function match4($var1, $var2, $var3, $var4)
{
	if(isset($var1) && isset($var2) && isset($var3) && isset($var4))
	{
		if(strcasecmp(trim($var1), trim($var3) == 0 && $var2 == $var4))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	else
	{
		return false;
	}
}

function isbnCheck($isbn)
{

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
	$isbn2 = $isbn;
	$query = "SELECT *";
	$query .= "FROM BOOK ";
	$query .= "WHERE isbn = '$isbn2'";

	$result = mysqli_query($connection, $query);
	if (!$result) 
	{
    	DIE('Query failed to execute for some reason');
	}

	if(is_numeric($isbn))
	{
		if(strlen($isbn) > 13)
		{
			return false;
		}
		else if (strlen($isbn) <= 13)
		{
			if (mysqli_num_rows($result) > 0)
			{
					return false;
			}
			else
			{
					return true;
			}
		}
	}
	else
	{
		return false;
	}
}

function display_errors_in_red($errorMsg, $errorsArr)
{
	if (array_key_exists($errorMsg, $errorsArr))
	{
		 echo "<span style = 'color:red'> * ";
		 echo $errorsArr[$errorMsg];
		 echo "</span>";
	}		
}

class Book
{
    private $bookID;
    private $avgrating;

    public function __construct()
    {
        $this->bookID=0;
    }
        
    public function getBookID()
    {
        return $this->bookID;
    }

    public function setBookID($bID)
    {
        $this->bookID = $bID;
    }

    public function updateOverallRating()
    {
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


        $ratingq = "SELECT AVG(rating) FROM rating WHERE bookID = '$this->bookID'";
        $ratingres = mysqli_query($connection, $ratinq);
        $this->avgrating = $ratingres;

        $bookq = "UPDATE BOOK SET rating = '$this->avgrating' WHERE bookID = '$this->bookID'";
        $upbookrating = mysqli_query($connection, $bookq);
    }
}


?>