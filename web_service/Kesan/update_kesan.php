<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){

		$id = $_POST['id'];
		$fullname = $_POST['fullname'];
		$gender = $_POST['gender'];
		$address = $_POST['address'];
		$kesan = $_POST['kesan'];
		
		require_once('../koneksi.php');

		$sql = "UPDATE k_pesan SET fullname = '$fullname', gender = '$gender', address = '$address', kesan = '$kesan' WHERE id = $id;";
		
		if(mysqli_query($con,$sql)) {
				echo "Kesan & Pesan Berhasil diupdate";
				} else {
				echo 'Maaf Kesan & Pesan Gagal dilakukan!';
				}
		
		mysqli_close($con);
	}