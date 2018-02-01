<?php 
	require_once('../koneksi.php');
	
	$sql = "SELECT * FROM k_pesan";
	
	$r = mysqli_query($con,$sql);
	
	$result = array();

	while($row = mysqli_fetch_array($r))
	{
		array_push($result,array("id"=>$row['id'],"fullname"=>$row['fullname']));
	}
	
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);


	