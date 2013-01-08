<?php


$tabell_constant = "loren_tabell_";


//PDO LORT;
function skapSkoleTabell($host, $brukernavn, $passord, $database, $tabell, $skoleid, $skolekode, $liste){
global $tabell_constant;

$tabell_navn = $tabell_constant.$tabell;

if(!tableEkist($host, $brukernavn, $passord, $database, $tabell_navn)){
    echo "Tabell eksisterer ikke, skaper en ny med navn: " . $tabell_navn . "<br>";

// Aapne mot database
try{
$db_con = new PDO(sprintf('mysql:dbname=%s;host=%s', 
                  $database,
                  $host),
                  $brukernavn, 
                  $passord);

        // Turn on exceptions
        $db_con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $db_con->exec('CREATE TABLE ' . $tabell_navn . ' (keyID int NOT NULL AUTO_INCREMENT, PRIMARY KEY(keyID) ,StudentID varchar(36), Klasse varchar(45), Navn varchar(45))');
        
        skapSkoleListen($host, $brukernavn, $passord, $database, $tabell, $skoleid, $skolekode);
        addStudent($host, $brukernavn, $passord, $database, $tabell, $liste);

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    }
  }else{

    echo $tabell_navn . " esksisterer allerede, skaper ikke ny!<br>";
    skapSkoleListen($host, $brukernavn, $passord, $database, $tabell, $skoleid, $skolekode);
    addStudent($host, $brukernavn, $passord, $database, $tabell, $liste);

  }

}


function skapSkoleListen($host, $brukernavn, $passord, $database, $tabell, $skoleid, $skolekode){
global $tabell_constant;

$tabell_navn = $tabell_constant.$tabell;
$skoleListe = "loren_skoleliste";

$skapTabell = "CREATE TABLE " . $skoleListe .  " (keyID int NOT NULL AUTO_INCREMENT, PRIMARY KEY(keyID), Skole_tabellnavn varchar(45), SkoleID varchar(25), SkoleKode varchar(25))";
$settData = "INSERT INTO `" . $database . "`.`" . $skoleListe . "` (`keyID`, `Skole_tabellnavn`, `SkoleID`, `SkoleKode`) VALUES (NULL, '" . $tabell_navn . "', '" . $skoleid . "', '" . $skolekode . "');";

echo $settData . "<br>" . $skapTabell . "<br>";

try{
$db_con = new PDO(sprintf('mysql:dbname=%s;host=%s', 
                  $database,
                  $host),
                  $brukernavn, 
                  $passord);

        // Turn on exceptions
        $db_con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        if(!tableEkist($host, $brukernavn, $passord, $database, $skoleListe)){
            echo "Tabell eksisterer ikke, skaper en ny med navn: " . $skoleListe . "<br>";
            
            $db_con->exec($skapTabell);
            $db_con->exec($settData);

        }else{
            echo $skoleListe . " eksisterer allerede, skaper ikke ny!<br>";

           $db_con->exec($settData);

            echo "Lagt til " . $tabell_navn . " til skolelisten!<br>";

  }

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    }
}



function addStudent($host, $brukernavn, $passord, $database, $tabell, $liste){
global $tabell_constant;

$tabell_navn = $tabell_constant.$tabell;

$leggStudent = "INSERT INTO `" . $database . "`.`" . $tabell_navn . "` (`keyID`, `StudentID`, `Klasse`, `Navn`) VALUES (NULL, '" .$stu_id. "', '" .$stu_klasse. "', '" .$stu_navn. "');";


try{
$db_con = new PDO(sprintf('mysql:dbname=%s;host=%s', 
                  $database,
                  $host),
                  $brukernavn, 
                  $passord);

        // Turn on exceptions
        $db_con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        if(tableEkist($host, $brukernavn, $passord, $database, $tabell_navn)){
            echo "AddStudent: Fant tabellen: " . $tabell_navn . "<br>";
                       
           foreach ($liste as $rows => $row) {
           $i = 0;
            foreach ($row as $col => $cell) {
           if($i == 0) $stu_id = $cell;
           if($i == 1) $stu_klasse = $cell;
           if($i == 2) $stu_navn = $cell;
           $i = $i + 1;
             }   
             $leggStudent = "INSERT INTO `" . $database . "`.`" . $tabell_navn . "` (`keyID`, `StudentID`, `Klasse`, `Navn`) VALUES (NULL, '" .$stu_id. "', '" .$stu_klasse. "', '" .$stu_navn. "');";
           echo $leggStudent . "<br>";
           $db_con->exec($leggStudent);
          }   

        }else{
            echo $skoleListe . " eksisterer ikke, Merkelig! - AddStudent<br>";

            echo "Lagt til " . $tabell_navn . " til skolelisten!<br>";

  }

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    }
}



function tableEkist($host, $brukernavn, $passord, $database, $tabell){
global $tabell_constant;

$ekistere = true;

// Aapne mot database
try{
$db_con = new PDO(sprintf('mysql:dbname=%s;host=%s', 
                  $database,
                  $host),
                  $brukernavn, 
                  $passord);

        // Turn on exceptions
        $db_con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $db_con->query('SELECT 1 from ' . $tabell);

    } catch(PDOException $e) {
    //echo 'ERROR: ' . $e->getMessage();
    $ekistere = false;
    }

return $ekistere;
}

?>