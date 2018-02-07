<?php 
	$no = $_GET['no'];
	
	require_once('../koneksi.php');
	
	$sql = "SELECT * FROM minuman WHERE no=$no";
	
	$r = mysqli_query($con,$sql);
	
	$result = array();

	$row = mysqli_fetch_array($r);
	
	array_push($result,array("no"=>$row['no'], 
							"nama_minuman"=>$row['nama_minuman'], 
							"perusahaan"=>$row['perusahaan'],
							"netto"=>$row['netto'],
							"sedotan"=>$row['sedotan'],
							"tempat"=>$row['tempat'],
							"nama_pemesan"=>$row['nama_pemesan']));

	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
