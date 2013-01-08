<?php 
set_include_path(dirname(__FILE__)."/../");
include('lorensql/loren_config.php');

$database = getDatabase();
$host = getHost();
$brukernavn = getUsername();
$passord = getPassword();


function printSpec($tabell, $type){
global $database, $host, $brukernavn, $passord;

try{
$db_con = new PDO(sprintf('mysql:dbname=%s;host=%s', 
                  $database,
                  $host),
                  $brukernavn, 
                  $passord);

        // Turn on exceptions
        $db_con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sth = $db_con->prepare("SELECT " . $type . " FROM " . $tabell);
        $sth->execute();
        $result = $sth->fetchAll(PDO::FETCH_ASSOC);

        fiksetTabell($result);

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    echo "printFullTabell: En eller annen feil her, sjekk det ut!<br>";
    }

}

function printSearch($tabell, $search, $type){
global $database, $host, $brukernavn, $passord;

// Lortefiks for aa fikse de nordiske bokstavene
$fixed_str = str_replace('æ', '&#230;', $search);
$fixed_str = str_replace('Æ', '&#230;', $fixed_str);
$fixed_str = str_replace('ø', '&#248;', $fixed_str);
$fixed_str = str_replace('Ø', '&#248;', $fixed_str);
$fixed_str = str_replace('å', '&#229;', $fixed_str);
$fixed_str = str_replace('Å', '&#229;', $fixed_str);

try{
$db_con = new PDO(sprintf('mysql:dbname=%s;host=%s', 
                  $database,
                  $host),
                  $brukernavn, 
                  $passord);

        // Turn on exceptions
        $db_con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $sth = $db_con->prepare("SELECT * FROM " . $tabell .  " WHERE  `" . $type . "` LIKE  '%" . $fixed_str . "%'");

        $sth->execute();
        $result = $sth->fetchAll(PDO::FETCH_ASSOC);

        fiksetTabell($result);

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    echo "printSearch: En eller annen feil her, sjekk det ut!<br>";
    }
}

function printFullTabell($tabell){
global $database, $host, $brukernavn, $passord;

try{
$db_con = new PDO(sprintf('mysql:dbname=%s;host=%s', 
                  $database,
                  $host),
                  $brukernavn, 
                  $passord);

        // Turn on exceptions
        $db_con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
       	$sth = $db_con->prepare("SELECT * FROM " . $tabell);
		    $sth->execute();
		    $result = $sth->fetchAll(PDO::FETCH_ASSOC);

        fiksetTabell($result);

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    echo "printFullTabell: En eller annen feil her, sjekk det ut!<br>";
    }

}


  function fiksetTabell($liste){
        echo "<table border='1'>";
        foreach ($liste as $rows => $row) {
            echo "<tr>";
            foreach ($row as $col => $cell) {

            echo "<td>" . $cell . "</td>";
        }   
            echo "</tr>";
    }   
            echo "</table>";
}

?>