<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

		$nama_minuman = $_POST['nama_minuman'];
		$perusahaan = $_POST['perusahaan'];
		$netto = $_POST['netto'];
		$sedotan = $_POST['sedotan'];
		$tempat = $_POST['tempat'];
		$nama_pemesan = $_POST['nama_pemesan'];
		

				$sql = "INSERT INTO minuman (nama_minuman,perusahaan,netto,sedotan,tempat,nama_pemesan) VALUES ('$nama_minuman','$perusahaan','$netto','$sedotan','$tempat','$nama_pemesan')";

				require_once('../koneksi.php');

				if(mysqli_query($con,$sql)) {
				echo 'Minuman Berhasil dimasukkan!';
				} else {
				echo 'Minuman Gagal dimasukkan!';
				}

		mysqli_close($con);
	}
