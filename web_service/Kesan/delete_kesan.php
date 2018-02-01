<?php 
	$id = $_GET['id'];
	
	require_once('../koneksi.php');
	
	$sql = "DELETE FROM k_pesan WHERE id=$id;";
	
	if(mysqli_query($con,$sql)){
		echo 'Data Kesan & Pesan Berhasil dihapus';
	}else{
		echo 'oops! Mohon coba lagi!';
	}
	
	mysqli_close($con);