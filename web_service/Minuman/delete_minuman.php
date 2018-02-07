<?php 
	$no = $_GET['no'];
	
	require_once('../koneksi.php');
	
	$sql = "DELETE FROM minuman WHERE no=$no;";
	
	if(mysqli_query($con,$sql)){
		echo 'Minuman Berhasil dihapus';
	}else{
		echo 'Galat!';
	}
	
	mysqli_close($con);
