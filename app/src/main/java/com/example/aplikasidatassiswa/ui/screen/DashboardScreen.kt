package com.example.aplikasidatassiswa.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.aplikasidatassiswa.ui.SiswaViewModel
import com.example.aplikasidatassiswa.ui.components.AppCard
import com.example.aplikasidatassiswa.ui.components.Avatar
import com.example.aplikasidatassiswa.ui.components.MainScaffold
import com.example.aplikasidatassiswa.ui.components.SectionHeading
import com.example.aplikasidatassiswa.ui.components.StudentCard
import com.example.aplikasidatassiswa.ui.theme.AppColors
import com.example.aplikasidatassiswa.ui.theme.AppTypography

@Composable
fun DashboardScreen(
    vm: SiswaViewModel,
    navController: NavController
) {
    val palette = AppColors.current
    val total by vm.totalCount.collectAsStateWithLifecycle()
    val maleCount by vm.maleCount.collectAsStateWithLifecycle()
    val femaleCount by vm.femaleCount.collectAsStateWithLifecycle()
    val recent by vm.recentSiswa.collectAsStateWithLifecycle()

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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        "Selamat pagi 👋",
                        style = AppTypography.greeting,
                        color = palette.muted
                    )
                    Text(
                        "Dashboard",
                        style = AppTypography.screenTitle,
                        color = palette.text
                    )
                }
                Avatar(initials = "AD", size = 44.dp, textStyle = AppTypography.avatarMedium)
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(21.dp))
                    .background(palette.primary)
                    .padding(19.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "TOTAL SISWA TERDAFTAR",
                        style = AppTypography.meta,
                        color = palette.onPrimary
                    )
                    Text(
                        "● Aktif",
                        style = AppTypography.meta,
                        color = palette.onPrimary
                    )
                }
                Text(
                    "$total",
                    style = AppTypography.heroValue,
                    color = palette.onPrimary
                )
                Text(
                    "Data siswa tersimpan dalam aplikasi",
                    style = AppTypography.bodySmall,
                    color = palette.onPrimary
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Lihat seluruh data",
                        style = AppTypography.labelUpper,
                        color = palette.onPrimary
                    )
                    Text("↗", style = AppTypography.arrow, color = palette.onPrimary)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                AppCard(
                    modifier = Modifier.weight(1f),
                    radius = 18.dp,
                    padding = 15.dp
                ) {
                    Text("Laki-laki", style = AppTypography.meta, color = palette.muted)
                    Text("$maleCount", style = AppTypography.statValue, color = palette.text)
                    Text(
                        "${if (total == 0) 0 else maleCount * 100 / total}% dari total",
                        style = AppTypography.meta,
                        color = palette.success
                    )
                }
                AppCard(
                    modifier = Modifier.weight(1f),
                    radius = 18.dp,
                    padding = 15.dp
                ) {
                    Text("Perempuan", style = AppTypography.meta, color = palette.muted)
                    Text("$femaleCount", style = AppTypography.statValue, color = palette.text)
                    Text(
                        "${if (total == 0) 0 else femaleCount * 100 / total}% dari total",
                        style = AppTypography.meta,
                        color = palette.success
                    )
                }
            }

            SectionHeading(
                title = "Siswa terbaru",
                action = "Lihat semua",
                onAction = { navController.navigate("daftar") }
            )

            if (recent.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Belum ada data siswa.",
                        style = AppTypography.bodySmall,
                        color = palette.muted
                    )
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    recent.forEach { siswa ->
                        StudentCard(
                            siswa = siswa,
                            showChevron = true,
                            onClick = { navController.navigate("form/${siswa.id}") }
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}
