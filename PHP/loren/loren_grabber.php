<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2//EN">

<html>
<head>
    <title>Loren - Grabber</title>

<h2> Masse deilig data!</h2>
<h5><a href="loren_grabber_verdier.php">Tilbake</a></h5>

</head>

<body>

<?php
        set_include_path(dirname(__FILE__)."/../");
        include('simple_html_dom.php');
        include('lorensql/loren_php_sql.php');
        include('ut/loren_ut_func.php');
        echo get_include_path();


        // Skoledata
         $skoleid = $_POST["sklid"];
         $skolekode = $_POST["sklkd"];    
         $skoletype = $_POST["skltyp"];

        //Database
         $host = $_POST["host"]; 
         $brukernavn = $_POST["bruker"];  
         $passord = $_POST["pass"];  
         
         $database = $_POST["db"];  
         $tabell = $_POST["tabell"];  

        echo '<b>Skoleid: </b>' . $skoleid . '<br>';
        echo '<b>Skolekode: </b>' . $skolekode . '<br>';
        echo '<b>Type: </b>' . $skoletype . '<br>';
        echo '<b>Host: </b>' . $host . '<br>';
        echo '<b>Brukernavn: </b>' . $brukernavn . '<br>';
        echo '<b>Database: </b>' . $database . '<br>';
        echo '<b>Tabell: </b>' . $tabell . '<br>';

        $studentTabell = studentListArray();

        fiksetTabell($studentTabell);

        skapSkoleTabell($host, $brukernavn, $passord, $database, $tabell, $skoleid, $skolekode, $studentTabell);

        function returnRefKode(){
            global $skoleid, $skolekode;

            // Vi blir sendt videre av Novakarene fra Svenskelandet. For aa komme oss forbi denne stumpen tar vi med oss id'en og slikt og slikt
            // Det funker, ikke spor hvordan. Det bare gjor det!

        $htmla = file_get_html('http://www.novasoftware.se/webviewer/MZDesign1.aspx?schoolid=' . $skoleid.'&code=' . $skolekode);

              foreach($htmla->find('a') as $element) 
            echo '<b>Webview-href: </b>' . $element->href . '<br>';

        $htmla->clear();

        $idting = substr($element->href, 18, 24); //returnerer id'en vi er på utkikk etter! 
        echo '<b>Webview-ID: </b>' . $idting . '<br>';
        echo "<p>Seb</p><br>";

            return $idting;
        }


        


function studentListArray(){
            global $skoleid, $skolekode, $skoletype, $host, $brukernavn, $passord, $database, $tabell, $skoleid, $skolekode;

            $adresse = 'http://www.novasoftware.se/webviewer/(S(' . returnRefKode() . '))/MZDesign1.aspx?schoolid=' . $skoleid.'&code=' . $skolekode . '&type=' . $skoletype;
            $html = file_get_html($adresse);

            echo '<p> <b>Adresse: </b> <a href="' . $adresse . '">' . $adresse .'</a></p>';

            $opt = $html->find('option');
          
            $studentListe = array();    

            for ($i = 0; $i < count($opt); $i++) {
               $element = $opt[$i];
                $value = $element->value;
                $content = $element->innertext;

             if(strlen($value) > 36){

                // Fjerner brakkene fra ID'en
                $nValue = substr($value, 1, 36);

                    // Finner forste tomrom for aa skille mellom klasse og navn.
                    $pos = strpos($content, ' ');
                    if($pos === false){
                     //Vedkommende har ikke et navn, stor sansynelighet for en klasse eller et rom.
                       $klasse = $content;
                       $navn = "Navnfri";
                                 $studentListe[] = array(
                                    "student_id"=>$nValue,
                                    "student_klasse"=>$klasse,
                                    "student_navn"=>$navn);

                    }else{
                  
                     // Vedkommende tilhorer en klasse
                        if($content{$pos-1} != ','){
                             $klasse = substr($content, 0, $pos);
                             $navn = substr($content, $pos);
                                $studentListe[] = array(
                                    "student_id"=>$nValue,
                                    "student_klasse"=>$klasse,
                                    "student_navn"=>$navn);


                        } // Vedkommende tilhorer ingen klasse
                        else{
                             
                             $klasse = "Ukjent";
                             $navn = $content;
                                $studentListe[] = array(
                                    "student_id"=>$nValue,
                                    "student_klasse"=>$klasse,
                                    "student_navn"=>$navn);
                    }
                }
            }         
        }
        $html->clear();
        return $studentListe;
}

?>

</body>


<footer>
<center><h5> <a href="http://www.bevster.net/">Bevster</a> (c) 2012 </h5></center>
</footer>

</html>