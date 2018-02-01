<?php 
	$no = $_GET['no'];
	
	require_once('../koneksi.php');
	
	$sql = "DELETE FROM makanan WHERE no=$no;";
	
	if(mysqli_query($con,$sql)){
		echo 'Makanan Berhasil dihapus';
	}else{
		echo 'Galat!';
	}
	
	mysqli_close($con);
