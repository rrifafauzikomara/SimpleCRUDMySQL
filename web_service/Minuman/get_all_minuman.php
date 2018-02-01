<?php 
	require_once('../koneksi.php');
	
	$sql = "SELECT * FROM minuman";
	
	$r = mysqli_query($con,$sql);
	
	$result = array();

	while($row = mysqli_fetch_array($r))
	{
		array_push($result,array("no"=>$row['no'],
			"nama_minuman"=>$row['nama_minuman']
			));
	}
	
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
