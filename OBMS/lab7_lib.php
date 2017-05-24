<?php
    class DB
    {
            private $dbHost;
            private $dbUser;
            private $dbPassword;
            private $dbName;
            private $dbConnection;
            private $dbError;
            private $lines;

            public function __construct()
            {
                $lines=file('/home/bti320_163c30/secret/topsecret.txt');
                $dbHost=trim($lines[0]);
                $dbUser=trim($lines[1]);
                $dbPassword=trim($lines[2]);
                $dbName =trim($lines[3]);
            }
                
            public function connectDB()
            {
                $this->dbConnection = new mysqli($dbHost, $dbUser, $dbPassword, $dbName);
                if ($this->dbConnection->connect_errno)
                {
                    die("Database connection failed: ".
                        $dbConnection->connect_error.
                        " ( ". $dbConnection->connect_errno. " )"
                    );
                    $this->dbError = "Failed connection.";
                } 
            }

            public function query($queryStr)
            {
                $this->queryres =  mysqli_query($dbConnection, $queryStr);
                return queryres;
                if (!$this->queryres) 
                {
                    DIE('Query failed to execute for some reason');
                    $dbError = "Query failed.";
                }
                if (mysqli_num_rows($this->queryres) == 0)
                { 
                    $this->dbError = "No results found";
                }
            }

            public function getErrorMsg()
            {
                return $this->dbError;
            }

            public function __destruct()
            {
                if ($this->dbConnection)
                {
                    mysqli_close($this->dbConnection);
                }
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
            $dbquery = new DB;
            $ratingq = "SELECT AVG(rating) FROM rating WHERE bookID = '$this->bookID'";
            $ratingres = $dbquery->query($ratingq);
            $this->avgrating = $ratingres;
            $bookq = "UPDATE BOOK SET rating = '$this->avgrating' WHERE bookID = '$this->bookID'";
            $upbookrating = $dbquery->query($bookq);
        }
    }


?>