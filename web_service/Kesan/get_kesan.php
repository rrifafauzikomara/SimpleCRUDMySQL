<?php 
	$id = $_GET['id'];
	
	require_once('../koneksi.php');
	
	$sql = "SELECT * FROM k_pesan WHERE id=$id";
	
	$r = mysqli_query($con,$sql);
	
	$result = array();

	$row = mysqli_fetch_array($r);
	
	array_push($result,array("id"=>$row['id'], 
							"fullname"=>$row['fullname'], 
							"gender"=>$row['gender'], 
							"address"=>$row['address'],
							"kesan"=>$row['kesan']));

	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);