package com.example.aplikasidatassiswa.ui.screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.aplikasidatassiswa.data.Siswa
import com.example.aplikasidatassiswa.ui.SiswaViewModel
import com.example.aplikasidatassiswa.ui.components.AppButton
import com.example.aplikasidatassiswa.ui.components.FilterPill
import com.example.aplikasidatassiswa.ui.components.FigmaTextField
import com.example.aplikasidatassiswa.ui.components.MainScaffold
import com.example.aplikasidatassiswa.ui.components.StudentCard
import com.example.aplikasidatassiswa.ui.theme.AppColors
import com.example.aplikasidatassiswa.ui.theme.AppTypography

@Composable
fun DaftarSiswaScreen(
    vm: SiswaViewModel,
    navController: NavController
) {
    val palette = AppColors.current
    val daftar by vm.siswa.collectAsStateWithLifecycle()
    val query by vm.query.collectAsStateWithLifecycle()
    val selectedKelas by vm.selectedKelas.collectAsStateWithLifecycle()
    val kelasOptions by vm.kelasOptions.collectAsStateWithLifecycle()
    var pendingDelete by remember { mutableStateOf<Siswa?>(null) }

    MainScaffold(navController = navController) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    "Data Siswa",
                    style = AppTypography.screenTitle,
                    color = palette.text
                )
                Text(
                    "Kelola seluruh informasi siswa sekolah",
                    style = AppTypography.body,
                    color = palette.muted
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${daftar.size} siswa ditemukan",
                    style = AppTypography.labelUpper,
                    color = palette.text
                )
                Text("Urutkan  ↓", style = AppTypography.meta, color = palette.muted)
            }

            FigmaTextField(
                value = query,
                onValueChange = { vm.setQuery(it) },
                label = "PENCARIAN",
                placeholder = "Cari nama atau NIS...",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ) {
                kelasOptions.forEach { kelas ->
                    FilterPill(
                        label = kelas,
                        selected = kelas == selectedKelas,
                        onClick = { vm.setKelas(kelas) }
                    )
                }
            }

            if (daftar.isEmpty()) {
                Text(
                    "Tidak ada data yang cocok.",
                    style = AppTypography.body,
                    color = palette.muted,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    daftar.forEach { siswa ->
                        StudentCard(
                            siswa = siswa,
                            showActions = true,
                            onEdit = { navController.navigate("form/${siswa.id}") },
                            onDelete = { pendingDelete = siswa }
                        )
                    }
                }
            }

            AppButton(
                text = "Tambah Siswa",
                onClick = { navController.navigate("form/0") },
                containerColor = palette.infoBg,
                contentColor = palette.primary,
                height = 48.dp,
                textStyle = AppTypography.buttonSmall,
                leading = {
                    Text("+", style = AppTypography.plus, color = palette.primary)
                }
            )

            Spacer(Modifier.height(16.dp))
        }
    }

    pendingDelete?.let { siswa ->
        AlertDialog(
            onDismissRequest = { pendingDelete = null },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Yakin ingin menghapus data ${siswa.nama}?") },
            confirmButton = {
                TextButton(onClick = {
                    vm.delete(siswa.id)
                    pendingDelete = null
                }) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { pendingDelete = null }) {
                    Text("Batal")
                }
            }
        )
    }
}
