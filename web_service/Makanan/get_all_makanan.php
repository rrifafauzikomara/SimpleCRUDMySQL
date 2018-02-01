<?php 
	require_once('../koneksi.php');
	
	$sql = "SELECT * FROM makanan";
	
	$r = mysqli_query($con,$sql);
	
	$result = array();

	while($row = mysqli_fetch_array($r))
	{
		array_push($result,array("no"=>$row['no'],"nama_makanan"=>$row['nama_makanan']));
	}
	
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
