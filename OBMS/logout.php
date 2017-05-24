<?php
    session_start();
    ECHO "Goodbye " . $_SESSION['username']  . "!";
    ECHO "<h2><a href=\"index.php\">Home</a></h2>";
    //Delete session
    session_unset();
    session_destroy();
    //End of session deletion block 
?>
