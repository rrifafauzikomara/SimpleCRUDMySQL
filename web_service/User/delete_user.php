<?php 
	$id = $_GET['id'];
	
	require_once('../koneksi.php');
	
	$sql = "DELETE FROM users WHERE id=$id;";
	
	if(mysqli_query($con,$sql)){
		echo 'Data user berhasil dihapus';
	}else{
		echo 'oops! Mohon coba lagi!';
	}
	
	mysqli_close($con);