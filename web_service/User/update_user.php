<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){
		$id = $_POST['id'];
		$fullname = $_POST['fullname'];
		$email = $_POST['email'];
		$username = $_POST['username'];
		$password = $_POST['password'];
		$gender = $_POST['gender'];
		$address = $_POST['address'];
		$telp = $_POST['telp'];
		
		require_once('../koneksi.php');
		$sql = "UPDATE users SET fullname = '$fullname', email = '$email', username = '$username', password = '$password', gender = '$gender', address = '$address', telp = '$telp' WHERE id = $id;";
		if(mysqli_query($con,$sql)){
			echo 'Data user berhasil diupdate';
		}else{
			echo 'oops! Mohon coba lagi!';
		}
		
		mysqli_close($con);
	}