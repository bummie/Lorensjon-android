<html>
<head>
	<title>Loren - Ut</title>

    	<?php include_once("googstat.php") ?>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />

</head>
<body>

<?php
set_include_path(dirname(__FILE__)."/../");
include_once('loren_ut_func.php');

// Informasjon fra URL
$LOR_FUNCTION = $_GET['function'];
$LOR_TABLE = $_GET['table'];
$LOR_TYPE = $_GET['type'];
$LOR_SEARCH = $_GET['search'];

// Funksjoner
$LOR_FUNC_PRINTFULL = "printfull";
$LOR_FUNC_SPEC = "spec";
$LOR_FUNC_SEARCH = "search";


if($LOR_FUNCTION == $LOR_FUNC_PRINTFULL){
	printFullTabell($LOR_TABLE);
}
if($LOR_FUNCTION == $LOR_FUNC_SPEC){
	printSpec($LOR_TABLE, $LOR_TYPE);

}
if($LOR_FUNCTION == $LOR_FUNC_SEARCH){
	printSearch($LOR_TABLE, $LOR_SEARCH, $LOR_TYPE);
}

?>

</body>
</html>

