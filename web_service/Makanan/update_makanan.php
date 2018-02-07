<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){

		$no = $_POST['no'];
		$nama_makanan = $_POST['nama_makanan'];
		$asal_makanan = $_POST['asal_makanan'];
		$harga_makanan = $_POST['harga_makanan'];
		
		require_once('../koneksi.php');

		$sql = "UPDATE makanan SET nama_makanan = '$nama_makanan', asal_makanan = '$asal_makanan', harga_makanan = '$harga_makanan' WHERE no = $no;";
		
		if(mysqli_query($con,$sql)) {
				echo "Makanan Berhasil diperbaharui";
				} else {
				echo 'Maaf Makanan Gagal diperbaharui!';
				}
		
		mysqli_close($con);
	}
