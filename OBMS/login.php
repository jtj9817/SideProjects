<?php
    session_start();
    //Refer to match4 and isPresent functions in lib.php to when checking validation code
    INCLUDE("lib.php");
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

    //Initialize errors array
    $errors = [];



	if(isset($_POST["login"]))
	{

        //Initialize username and password to user's input
		$username = $_POST["username"];
		$password = $_POST["password"];
        //End init
 
        //Set session variables
        $_SESSION['username'] = $username;
		$_SESSION['password'] = $password;

        //Validation block
        if(!isPresent($username) || !isPresent($password))
        {
            $errors['emptylogin'] = "Username and/or password fields are empty. Try again.";
        }
        else
        {
            $pass = md5($password);
            //Query for user information
            //Perform database query
            $query = "SELECT count(*) FROM user WHERE userID = '$username' AND password = '$pass'";
            $result = mysqli_query($connection, $query);
            //End of db query block

            //User authenticated
            if(mysqli_num_rows($result) > 0)
            {
                if(isset($_POST['savelogin'])) 
                {   
                        //Set cookies if remember username and password option is checked
                        setcookie("username", $username, time()+60*60*24*7, "/");
                        setcookie("password", $password, time()+60*60*24*7, "/");
                        //End of cookies init block
                }
                                
               else if(isset($_POST['clearcookies'])) 
                {
                    setcookie("username", "", time() - 3600);
                    setcookie("password", "", time() - 3600);
                     //unset($_COOKIE["username"]);
                    //unset($_COOKIE["password"]);
                }

                //Output buffering block
                ob_start();
                //Header to redirect to another page
                header ('Location: menu.php');
                exit;
                ob_end_flush();
                //End of output buffering block
            }
            else if(mysqli_num_rows($result) == 0)
            {
                $errors['incorrectlogin'] = "Username or password is incorrect. Try again!" . $result;
            }
        }//End of validation block
	}
    else 
    {
        $username = "";
        $password = "";
    }

?>


<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
</head>

<!--Add BookStoreCSS-->
 <link rel="stylesheet" 
      type ="text/css"
      href = "BookStoreCSS.css" >
<meta charset="UTF-8">
<!--End CSS include-->

<body>


<form name ="frm1" method = "post" action = "login.php">
<a href="index.php"  class="myButton">Home</a>
<center>
<img style="vertical-align:middle" src="BookStore.png" alt="BookStore" title="BookStore Banner">

<fieldset><legend>User Login</legend>
Username: <input type = "text" name = "username" /><br>
Password: <input type = "password" name = "password"/><br>
<input type="checkbox" name="savelogin" value="save">Remember Me?<br>
<input type="checkbox" name="clearcookies" value="clear">Forget Me?<br>
<input type = "submit" name = "login" value = "Login"/>
</fieldset>
<br>
<?php display_errors_in_red('login', $errors);
?>
<?php display_errors_in_red('emptylogin', $errors);
?>
<?php display_errors_in_red('incorrectlogin', $errors);
?>
<br>
</form>
</center>
</body>
</html>

<?php 
    //Close db connection
	mysqli_close($connection);
?>