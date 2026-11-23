package com.example.aplikasidatassiswa.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.aplikasidatassiswa.data.Siswa
import com.example.aplikasidatassiswa.ui.SiswaViewModel
import com.example.aplikasidatassiswa.ui.components.AppButton
import com.example.aplikasidatassiswa.ui.components.AppCard
import com.example.aplikasidatassiswa.ui.components.FieldLabel
import com.example.aplikasidatassiswa.ui.components.FilterPill
import com.example.aplikasidatassiswa.ui.components.FigmaTextField
import com.example.aplikasidatassiswa.ui.theme.AppColors
import com.example.aplikasidatassiswa.ui.theme.AppTypography

@Composable
fun FormSiswaScreen(
    vm: SiswaViewModel,
    siswaId: Long,
    onSaved: () -> Unit,
    onBack: () -> Unit
) {
    val palette = AppColors.current
    var nis by remember { mutableStateOf("") }
    var nama by remember { mutableStateOf("") }
    var kelas by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var jenisKelamin by remember { mutableStateOf("Laki-laki") }

    var nisError by remember { mutableStateOf<String?>(null) }
    var namaError by remember { mutableStateOf<String?>(null) }
    var kelasError by remember { mutableStateOf<String?>(null) }
    var alamatError by remember { mutableStateOf<String?>(null) }

    val isEdit = siswaId != 0L

    LaunchedEffect(siswaId) {
        if (isEdit) {
            vm.getById(siswaId)?.let { siswa ->
                nis = siswa.nis
                nama = siswa.nama
                kelas = siswa.kelas
                alamat = siswa.alamat
                jenisKelamin = siswa.jenisKelamin
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Kembali",
                    tint = palette.text
                )
            }
            Text(
                text = if (isEdit) "Edit Data Siswa" else "Tambah Data Siswa",
                style = AppTypography.sectionTitle,
                color = palette.text
            )
        }

        Spacer(Modifier.height(20.dp))

        AppCard(padding = 16.dp) {
            FigmaTextField(
                value = nis,
                onValueChange = { nis = it; nisError = null },
                label = "NIS",
                placeholder = "Masukkan NIS",
                isError = nisError != null,
                supportingText = nisError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(Modifier.height(14.dp))
            FigmaTextField(
                value = nama,
                onValueChange = { nama = it; namaError = null },
                label = "NAMA SISWA",
                placeholder = "Masukkan nama siswa",
                isError = namaError != null,
                supportingText = namaError
            )
            Spacer(Modifier.height(14.dp))
            FigmaTextField(
                value = kelas,
                onValueChange = { kelas = it; kelasError = null },
                label = "KELAS",
                placeholder = "Contoh: XI RPL 1",
                isError = kelasError != null,
                supportingText = kelasError
            )
            Spacer(Modifier.height(14.dp))
            FigmaTextField(
                value = alamat,
                onValueChange = { alamat = it; alamatError = null },
                label = "ALAMAT",
                placeholder = "Masukkan alamat",
                isError = alamatError != null,
                supportingText = alamatError,
                minLines = 3
            )
            Spacer(Modifier.height(14.dp))
            FieldLabel("JENIS KELAMIN")
            Spacer(Modifier.height(7.dp))
            Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(7.dp)) {
                listOf("Laki-laki", "Perempuan").forEach { gender ->
                    FilterPill(
                        label = gender,
                        selected = jenisKelamin == gender,
                        onClick = { jenisKelamin = gender }
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        AppButton(
            text = if (isEdit) "Perbarui Data" else "Simpan",
            onClick = {
                val nisValue = nis.trim()
                val namaValue = nama.trim()
                val kelasValue = kelas.trim()
                val alamatValue = alamat.trim()

                nisError = when {
                    nisValue.isEmpty() -> "NIS wajib diisi"
                    !nisValue.all { it.isDigit() } -> "NIS hanya boleh angka"
                    vm.isNisExists(nisValue, siswaId) -> "NIS sudah terdaftar"
                    else -> null
                }
                namaError = if (namaValue.isEmpty()) "Nama wajib diisi" else null
                kelasError = if (kelasValue.isEmpty()) "Kelas wajib diisi" else null
                alamatError = if (alamatValue.isEmpty()) "Alamat wajib diisi" else null

                if (listOf(nisError, namaError, kelasError, alamatError).all { it == null }) {
                    vm.save(
                        Siswa(
                            id = siswaId,
                            nis = nisValue,
                            nama = namaValue,
                            kelas = kelasValue,
                            alamat = alamatValue,
                            jenisKelamin = jenisKelamin
                        )
                    )
                    onSaved()
                }
            },
            height = 52.dp
        )

        Spacer(Modifier.height(24.dp))
    }
}
