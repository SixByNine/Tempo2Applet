<?php
$par=$_POST['par'];
$tim=$_POST['tim'];
if(isset($_POST['psr'])){
	$psr=$_POST['psr'];
}
if(isset($_POST['jmp'])){
	$jmp=$_POST['jmp'];
}
if(isset($_POST['srt'])){
	$srt=$_POST['srt'];
}
if(isset($_POST['fsh'])){
	$fsh=$_POST['fsh'];
}
if(isset($_POST['nofit'])){
	$nofit=$_POST['nofit'];
}
@$sock = fsockopen("rubicon.atnf.csiro.au",21001) or die("\nCould not open connection to TEMPO2 Server");
fwrite_stream($sock,"EXPECT_PAR##END##");
$ret=fread_4($sock);
if($ret!="OK##")die("\nRecieved error from server ".$ret);
fwrite_stream($sock,"$par##END##");
$ret=fread_4($sock);
if($ret!="OK##")die("\nRecieved error from server ".$ret);
fwrite_stream($sock,"EXPECT_TIM##END##");
$ret=fread_4($sock);
if($ret!="OK##")die("\nRecieved error from server ".$ret);
fwrite_stream($sock,"$tim##END##");
$ret=fread_4($sock);
if($ret!="OK##")die("\nRecieved error from server ".$ret);
if(isset($psr)){
	fwrite_stream($sock,"EXPECT_PSR##END##");
	$ret=fread_4($sock);
	print $ret;
	fwrite_stream($sock,"$psr##END##");
	$ret=fread_4($sock);
	print $ret;
}
if(isset($jmp)){
	fwrite_stream($sock,"EXPECT_JMP##END##");
	$ret=fread_4($sock);
	print $ret;
	fwrite_stream($sock,"$jmp##END##");
	$ret=fread_4($sock);
	print $ret;
}
if(isset($srt)){
	fwrite_stream($sock,"EXPECT_SRT##END##");
	$ret=fread_4($sock);
	print $ret;
	fwrite_stream($sock,"$srt##END##");
	$ret=fread_4($sock);
	print $ret;
}
if(isset($fsh)){
	fwrite_stream($sock,"EXPECT_FNS##END##");
	$ret=fread_4($sock);
	print $ret;
	fwrite_stream($sock,"$fsh##END##");
	$ret=fread_4($sock);
	print $ret;
}
if(isset($nofit)){
	fwrite_stream($sock,"NOFIT##END##");
	$ret=fread_4($sock);
	print $ret;
}


fwrite_stream($sock,"FIT##END##");
$ret=fread_4($sock);
if($ret!="OK##")die("\nRecieved error from server ".$ret);

/* NOW we should recieve the results! */
$ret=fread_stream($sock);
print "'$ret'";
@fclose($sock);


function fwrite_stream($fp, $string) {
	for ($written = 0; $written < strlen($string); $written += $fwrite) {
		$fwrite = fwrite($fp, substr($string, $written));
		if (!$fwrite) {
			return $fwrite;
		}
	}
	return $written;
}
function fread_4($handle){
	$contents="";
	$contents = fread($handle,4);
	return $contents;
}
function fread_stream($handle){
	$contents="";
	while(!feof($handle)){
		$contents.=fread($handle,1024);
	}
	return $contents;
}
?>
