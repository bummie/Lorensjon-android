<?php 
set_include_path(dirname(__FILE__)."/../");
include_once('lorensql/loren_config.php');
include_once('lorensql/loren_php_sql.php');

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

function printSpecTable($tabell, $type){
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

        return $result;

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    echo "printFullTabell: En eller annen feil her, sjekk det ut!<br>";
    }

}

function printSearch($tabell, $search, $type){
global $database, $host, $brukernavn, $passord;

// Lortefiks for aa fikse de nordiske bokstavene
$fixed_str = str_replace('æ', '&#230;', $search);
$fixed_str = str_replace('Æ', '&#198;', $fixed_str);
$fixed_str = str_replace('ø', '&#248;', $fixed_str);
$fixed_str = str_replace('Ø', '&#216;', $fixed_str);
$fixed_str = str_replace('å', '&#229;', $fixed_str);
$fixed_str = str_replace('Å', '&#197;', $fixed_str);

$fixed_str = str_replace('aelig', '&#230;', $fixed_str);
$fixed_str = str_replace('AElig', '&#198;', $fixed_str);
$fixed_str = str_replace('oslash', '&#248;', $fixed_str);
$fixed_str = str_replace('Oslash', '&#216;', $fixed_str);
$fixed_str = str_replace('aring', '&#229;', $fixed_str);
$fixed_str = str_replace('Aring', '&#197;', $fixed_str);

 addSearchResult($host, $brukernavn, $passord, $database, "loren_result_search", $search, $type);

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

function printSearchTable($tabell, $search, $type){
global $database, $host, $brukernavn, $passord;

// Lortefiks for aa fikse de nordiske bokstavene
$fixed_str = str_replace('æ', '&#230;', $search);
$fixed_str = str_replace('Æ', '&#198;', $fixed_str);
$fixed_str = str_replace('ø', '&#248;', $fixed_str);
$fixed_str = str_replace('Ø', '&#216;', $fixed_str);
$fixed_str = str_replace('å', '&#229;', $fixed_str);
$fixed_str = str_replace('Å', '&#197;', $fixed_str);

$fixed_str = str_replace('&aelig;', '&#230;', $fixed_str);
$fixed_str = str_replace('&AElig;', '&#198;', $fixed_str);
$fixed_str = str_replace('&oslash;', '&#248;', $fixed_str);
$fixed_str = str_replace('&Oslash;', '&#216;', $fixed_str);
$fixed_str = str_replace('&aring;', '&#229;', $fixed_str);
$fixed_str = str_replace('&Aring;', '&#197;', $fixed_str);
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

        return $result;

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    echo "printSearch: En eller annen feil her, sjekk det ut!<br>";
    }
}

function updateAntall($tabell){
global $database, $host, $brukernavn, $passord;

try{
$db_con = new PDO(sprintf('mysql:dbname=%s;host=%s', 
                  $database,
                  $host),
                  $brukernavn, 
                  $passord);

        // Turn on exceptions
        $db_con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $db_con->exec(("UPDATE " . $tabell . " SET antall = antall + 1"));
        

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    echo 'Apekatt';
    }
}  

function printFullTabell($tabell){
global $database, $host, $brukernavn, $passord;


if($tabell == "loren_motd") updateAntall("loren_antall_besokne");

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

      if($tabell == "loren_antall_besokne"){
        fiksetAntall($result);

      }else{
          fiksetTabell($result);
        } 

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    echo "printFullTabell: En eller annen feil her, sjekk det ut!<br>";
    }

}

function printFullTabellTable($tabell){
global $database, $host, $brukernavn, $passord;


if($tabell == "loren_motd") updateAntall("loren_antall_besokne");

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

      if($tabell == "loren_antall_besokne"){
        return $result;

      }else{
          return $result;
        } 

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    echo "printFullTabell: En eller annen feil her, sjekk det ut!<br>";
    }

}

function fiksetAntall($liste){
        foreach ($liste as $rows => $row) {
            foreach ($row as $col => $cell) {
            echo "<font size='24'>MOTD har blitt lastet: " . $cell/2 . "</font>";
        }   
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