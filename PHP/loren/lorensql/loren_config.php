<?php


// Config - Bevster 2013

//Database
$LOR_HOST = "localhost";
$LOR_DATABASE = "";
$LOR_USERNAME = "";
$LOR_PASSWORD = "";

//Getters
function getHost(){
	global $LOR_HOST;

	return $LOR_HOST;
}

function getDatabase(){
	global $LOR_DATABASE;

	return $LOR_DATABASE;
}

function getUsername(){
	global $LOR_USERNAME;

	return $LOR_USERNAME;
}

function getPassword(){
	global $LOR_PASSWORD;

	return trim(hexToStr($LOR_PASSWORD));
}


//Simpel kryptering
function hexToStr($hex)
{
    $string='';
    for ($i=0; $i < strlen($hex)-1; $i+=2)
    {
        $string .= chr(hexdec($hex[$i].$hex[$i+1]));
    }
    return $string;
}

?>





\nLorenplan startet som et lite prosjekt i slutten av 2012. Timeplanstøtten for mobile enheter var relativt dårlig, så som et læringsprosjekt tenkte jeg at jeg kunne skape en selv. Dette ble dermed gjort og etter en stund har det nå blitt til noe brukbart. Appklikasjonen er stadig under arbeid og alt som sees kan blir forandret.\nSliter du med å komme i gang?\nOm du sliter med å komme i gang og ikke er av den eksperimentelle personen, eller at du rett og slett ikke har all verden av tid kan du sjekke ut siden "Komme i gang" for informasjon om nettop dette!\nEksisterer ikke skolen din i systemet?\nFinner du ikke skolen din i systemet? Frykt ikke kjære deg, sjekk ut "Mangler skolen din?" siden for informasjon om timeplanen din er støttet!\nKontaktgrunnlag\nOm du av en eller annen grunn ikke ønsker å være i databasen må du for all del ta kontakt og dette vil bli fikset på. Føl også friheten til å foreslå endringer som kan bli gjort. Om du skulle komme over noen feil vill det også settes stor pris på om det ble meldt i fra om!\nKontaktopplysninger\nMail: seebzei@gmail.com \nITL: sebaber\n