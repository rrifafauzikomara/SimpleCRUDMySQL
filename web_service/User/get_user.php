<?php 
	$id = $_GET['id'];
	
	require_once('../koneksi.php');
	
	$sql = "SELECT * FROM users WHERE id=$id";
	
	$r = mysqli_query($con,$sql);
	
	$result = array();
	$row = mysqli_fetch_array($r);
	array_push($result,array(
			"id"=>$row['id'],
			"fullname"=>$row['fullname'],
			"email"=>$row['email'],
			"username"=>$row['username'],
			"password"=>$row['password'],
			"gender"=>$row['gender'],
			"address"=>$row['address'],
			"telp"=>$row['telp']
		));

	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);