<?php 
	if($_SERVER['REQUEST_METHOD']=='POST'){

		$no = $_POST['no'];
		$nama_minuman = $_POST['nama_minuman'];
		$perusahaan = $_POST['perusahaan'];
		$netto = $_POST['netto'];
		$sedotan = $_POST['sedotan'];
		$tempat = $_POST['tempat'];
		$nama_pemesan = $_POST['nama_pemesan'];
		
		require_once('../koneksi.php');

		$sql = "UPDATE minuman SET nama_minuman = '$nama_minuman', perusahaan = '$perusahaan', netto = '$netto', sedotan = '$sedotan', tempat = '$tempat', nama_pemesan = '$nama_pemesan' WHERE no = $no;";
		
		if(mysqli_query($con,$sql)) {
				echo "Minuman Berhasil diperbaharui";
				} else {
				echo 'Maaf Minuman Gagal diperbaharui!';
				}
		
		mysqli_close($con);
	}
