package com.example.aplikasidatassiswa.data

data class Siswa(
    val id: Long = 0L,
    val nis: String,
    val nama: String,
    val kelas: String,
    val alamat: String,
    val jenisKelamin: String = "Laki-laki"
)
