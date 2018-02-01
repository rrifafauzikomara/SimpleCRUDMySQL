<?php 
	$no = $_GET['no'];
	
	require_once('../koneksi.php');
	
	$sql = "SELECT * FROM makanan WHERE no=$no";
	
	$r = mysqli_query($con,$sql);
	
	$result = array();

	$row = mysqli_fetch_array($r);
	
	array_push($result,array("no"=>$row['no'], 
							"nama_makanan"=>$row['nama_makanan'], 
							"asal_makanan"=>$row['asal_makanan'], 
							"harga_makanan"=>$row['harga_makanan']));

	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
