<?php
    //Include block
    INCLUDE("lib.php");

    session_start();

    $errors = [];

    //Initialize connection by parsing db login info from text file
	$lines=file('topsecret.txt');
	$dbhost=trim($lines[0]);
	$dbuser=trim($lines[1]);
	$dbpass=trim($lines[2]);
	$dbname=trim($lines[3]);
	$connection = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname);
	//End of connection init

	if(isset($_POST["register"]))
	{

        //Initialize username and password to user's input
		$username = $_POST["username"];
		$password = $_POST["password"];
        $password2 = $_POST["password2"];
        //End init

        //Check for empty input
        if(!isPresent($username) || !isPresent($password) || !isPresent($password2))
        {
            $errors['emptyregister'] = "Username and/or password fields are empty. Try again.";
        }
    
        else if(isPresent($username) && isPresent($password))
        {

            if(strcmp($password, $password2) == 0)
            {
                //Initialize a session variable username for a user
                $_SESSION['username'] = $username;

                $escapedName = mysqli_real_escape_string($connection, $username);
                $escapedPW = mysqli_real_escape_string($connection, $password);
                $safePW = md5($password);

                //Block to user information
                $query = "INSERT INTO user";
                $query .= "(userID, password)";
                $query .= "VALUES";
                $query .= "('$escapedName', '$safePW')";
                $result = mysqli_query($connection, $query);
                //End of block for user information insertion
            
                //Check if user info insertion was succcessful
                if($result)
                {
                    header('Location: menu.php');
                }
                else if (!$result)
                {
                    DIE("User information insertion failed <br>".
                    mysqli_error($connection).
                    "(".mysqli_errno($connection). ")"
                    );
                }

                /*
                if (headers_sent()) {
                die("Redirect failed. Please click on this link: <a href=...>");
                }
                else
                {
                exit(header("Location: /user.php"));
                }
                */
                //End of output buffering block
            }

            else if(strcmp($password, $password2) != 0)
            {
                $errors['failregister'] = "Passwords do not match. Registration failed. Try again.";
            }
        }//Username and Password are present
        
	}
    else 
    {
        $username = "";
        $password = "";
        $password2 = "";
    }

?>


<!DOCTYPE html>
<html>
<head>
    <title>Registration Page</title>
</head>
<!--Add BookStoreCSS-->
 <link rel="stylesheet" 
      type ="text/css"
      href = "BookStoreCSS.css" >
<meta charset="UTF-8">
<!--End CSS include-->

<body>

<form name ="frm1" method = "post" action = "register.php">
<a href="index.php"  class="myButton">Home</a>
<center>
<img style="vertical-align:middle" src="BookStore.png" alt="BookStore" title="BookStore Banner">

<fieldset><legend>User Registration</legend>
Username: <input type = "text" name = "username" /><br>
Password: <input type = "password" name = "password"/><br>
Confirm Password: <input type = "password" name = "password2"/><br>
</fieldset>
<br>

<?php display_errors_in_red('emptyregister', $errors);
?>
<?php display_errors_in_red('failregister', $errors);
?>

<br>
<input type = "submit" name = "register" value = "Register"/>
</form>
</center>

</body>
</html>