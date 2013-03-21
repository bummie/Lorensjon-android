<?php

set_include_path(dirname(__FILE__)."/../");
include_once('loren_grabber.php');

$tabell_constant = "loren_tabell_";


//PDO LORT;
function oppdaterTabellene($host, $brukernavn, $passord, $database, $tabellen, $skoid, $skokode){
global $tabell_constant;

$tabell_loren_navn;
$tabell_loren_skoleid;
$tabell_loren_skolekode;
$loren_type_antall = 7;

$tabell_navn = $tabell_constant.$tabellen;

$tabell_skolenavn = printFullTabellTable("loren_skoleliste");


print_r($tabell_skolenavn);

if($tabellen == null || $tabellen == "" ){
            echo "<b>Oppdaterer alle tabellene!</b><br>";

        foreach ($tabell_skolenavn as $rows => $row) {
              echo "<b>[TABELL_START]</b>  <br>";
            if($tabell_loren_navn != null){
              dropTable($host, $brukernavn, $passord, $database, $tabell_loren_navn);
              for ($k=0; $k < $loren_type_antall; $k++) { 

                if($tabell_loren_navn == "loren_tabell_raelingen" || $tabell_loren_navn == "raelingen" || $tabell_loren_navn == "loren_tabell_mailand" || $tabell_loren_navn == "mailand"){
                   addStudent($host, $brukernavn, $passord, $database, $tabell_loren_navn, studentListArray(returnRefKode($tabell_loren_skoleid, $tabell_loren_skolekode), $tabell_loren_skoleid, $tabell_loren_skolekode, 0), false);
                    break;
                 }else{
                   addStudent($host, $brukernavn, $passord, $database, $tabell_loren_navn, studentListArray(returnRefKode($tabell_loren_skoleid, $tabell_loren_skolekode), $tabell_loren_skoleid, $tabell_loren_skolekode, $k), false);
                }

              }
            }
            $i = 0;
            foreach ($row as $col => $cell) {
              echo "<b>Celle: </b>" . $cell . $i . "<br>";
            if($i == 1){$tabell_loren_navn = $cell;
              echo "<b>Tabellnavn: </b>" . $tabell_loren_navn. "<br>";

            }
            if($i == 2){$tabell_loren_skoleid = $cell;
              echo "<b>SkoleID: </b>" . $tabell_loren_skoleid. "<br>";
            }
            if($i == 3){$tabell_loren_skolekode = $cell;
              echo "<b>Skolekode: </b>" . $tabell_loren_skolekode. "<br>";
            }

            $i++; 
        }
    } 
  }else{
            echo "<b>Oppdaterer</b>: " . $tabell_navn . "<br>";

            if($tabell_navn != null){
              dropTable($host, $brukernavn, $passord, $database, $tabell_navn);
               for ($k=0; $k < $loren_type_antall; $k++) { 
                if($tabell_loren_navn == "loren_tabell_raelingen" || $tabell_loren_navn == "raelingen"){
                   addStudent($host, $brukernavn, $passord, $database, $tabell_loren_navn, studentListArray(returnRefKode($tabell_loren_skoleid, $tabell_loren_skolekode), $tabell_loren_skoleid, $tabell_loren_skolekode, 0), false);
                    break;
                 }else{
                   addStudent($host, $brukernavn, $passord, $database, $tabell_loren_navn, studentListArray(returnRefKode($tabell_loren_skoleid, $tabell_loren_skolekode), $tabell_loren_skoleid, $tabell_loren_skolekode, $k), false);
                }
              }
  }
 }
}

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
        $db_con->exec('CREATE TABLE ' . $tabell_navn . ' (keyID int NOT NULL AUTO_INCREMENT, PRIMARY KEY(keyID), StudentID varchar(36), Klasse varchar(45), Navn varchar(45))');
        
        skapSkoleListen($host, $brukernavn, $passord, $database, $tabell, $skoleid, $skolekode);
        addStudent($host, $brukernavn, $passord, $database, $tabell, $liste, true);

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    }
  }else{

    echo $tabell_navn . " esksisterer allerede, skaper ikke ny!<br>";
   // skapSkoleListen($host, $brukernavn, $passord, $database, $tabell, $skoleid, $skolekode);
    addStudent($host, $brukernavn, $passord, $database, $tabell, $liste, true);

  }

}

function dropTable($host, $brukernavn, $passord, $database, $tabell){

// Aapne mot database
try{
$db_con = new PDO(sprintf('mysql:dbname=%s;host=%s', 
                  $database,
                  $host),
                  $brukernavn, 
                  $passord);

        // Turn on exceptions
        $db_con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        $db_con->exec("TRUNCATE " . $tabell);

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    }

    echo "<b>Droppet: </b><i>" . $tabell . "</i><br>";

 
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



function addStudent($host, $brukernavn, $passord, $database, $tabell, $liste, $bool){
global $tabell_constant;

if($bool){
  $tabell_navn = $tabell_constant.$tabell;
}else{
  $tabell_navn = $tabell;
}
$leggStudent = "INSERT INTO `" . $database . "`.`" . $tabell_navn . "` (`keyID`, `StudentID`, `Klasse`, `Navn`) VALUES (NULL, '" .$stu_id. "', '" .$stu_klasse. "', '" .$stu_navn. "');";

$etAntall = 0;

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
           $etAntall++; //echo $leggStudent . "<br>";
           $db_con->exec($leggStudent);
          }   

        }else{
            echo $skoleListe . " eksisterer ikke, Merkelig! - AddStudent<br>";

            echo "Lagt til " . $tabell_navn . " til skolelisten!<br>";

  }

  echo "<b>[STUDENTER]</b>: " . $etAntall . "<br>";

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


function addSearchResult($host, $brukernavn, $passord, $database, $tabell, $search, $type, $sTabell, $suks){

try{
$db_con = new PDO(sprintf('mysql:dbname=%s;host=%s', 
                  $database,
                  $host),
                  $brukernavn, 
                  $passord);

        // Turn on exceptions
        $db_con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
        if(tableEkist($host, $brukernavn, $passord, $database, $tabell)){

            echo "AddSearchResult: Fant tabellen: " . $tabell . "<br>";
             
             $searchQ = "INSERT INTO `" . $database . "`.`" . $tabell . "` (`Search`, `Type`, `Skole`, `Suks`, `Tidspunkt`) VALUES ('" . $search . "', '" . $type . "', '" . $sTabell . "', '" . $suks . "', CURRENT_TIMESTAMP);";
             $db_con->exec($searchQ);
          } else{
            echo $tabell . " eksisterer ikke, Merkelig! - AddStudent<br>";

  }

    } catch(PDOException $e) {
    echo 'ERROR: ' . $e->getMessage();
    }
}


?>