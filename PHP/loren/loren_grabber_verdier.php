<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2//EN">

<html>
<head>
    <title>Loren - GrabberVerdier</title>
</head>

<body>
    <h3> Legg elevliste til database </h3>
<form action="loren_grabber.php" method="post">
    <br> <b>Skoledata</b> </br>
SkoleID: <input type="number" name="sklid" value="72150"><br>
SkoleKode: <input type="number" name="sklkd" value="546774"><br>
Type: <input type="number" name="skltyp" value="0"><br>
<br> <b>Database</b> </br>

Host: <input type="text" name="host" value="localhost"><br>
Brukernavn: <input type="text" name="bruker" value=""><br>
Passord: <input type="password" name="pass" value=""><br>

<br> <b>Databaseplassering</b> </br>
Database: <input type="text" name="db" value="_loren_db"><br>
Tabell: <input type="text" name="tabell" value="Skole"><br>

<input type="Submit" name="Submit"> <br>


<?php  

//include('lorensql/loren_config.php');
set_include_path(dirname(__FILE__)."/../");
include_once('ut/loren_ut_func.php');

// Config
$database = getDatabase();
$host = getHost();
$brukernavn = getUsername();
$passord = getPassword();


$tabell = "loren_skoleliste";

printFullTabell($tabell);
//printSearch("loren_tabell_lorenskog", "1", "Klasse");
printSpec("loren_tabell_lorenskog", "Navn");
//dynamic_args("loren_tabell_lorenskog", "StudentID", "Navn", "KeyID");

?>

</body>

</html>