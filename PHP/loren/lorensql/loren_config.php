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