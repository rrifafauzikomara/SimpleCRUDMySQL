<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

		$nama_makanan = $_POST['nama_makanan'];
		$asal_makanan = $_POST['asal_makanan'];
		$harga_makanan = $_POST['harga_makanan'];
		

				$sql = "INSERT INTO makanan (nama_makanan,asal_makanan,harga_makanan) VALUES ('$nama_makanan','$asal_makanan','$harga_makanan')";

				require_once('../koneksi.php');

				if(mysqli_query($con,$sql)) {
				echo 'Makanan Berhasil dimasukkan!';
				} else {
				echo 'Makanan Gagal dimasukkan!';
				}

		mysqli_close($con);
	}
