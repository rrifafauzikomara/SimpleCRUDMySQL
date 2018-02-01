<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

		$fullname = $_POST['fullname'];
		$gender = $_POST['gender'];
		$address = $_POST['address'];
		$kesan = $_POST['kesan'];

				$sql = "INSERT INTO k_pesan (fullname,gender,address,kesan) VALUES ('$fullname','$gender','$address','$kesan')";

				require_once('../koneksi.php');

				if(mysqli_query($con,$sql)) {
				echo 'Kesan & Pesan Berhasil dilakukan';
				} else {
				echo 'Maaf Kesan & Pesan Gagal dilakukan!';
				}

		mysqli_close($con);
	}