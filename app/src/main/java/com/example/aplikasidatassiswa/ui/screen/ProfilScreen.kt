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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.aplikasidatassiswa.data.AppPreferences
import com.example.aplikasidatassiswa.ui.components.AppButton
import com.example.aplikasidatassiswa.ui.components.AppCard
import com.example.aplikasidatassiswa.ui.components.AppDivider
import com.example.aplikasidatassiswa.ui.components.Avatar
import com.example.aplikasidatassiswa.ui.components.MainScaffold
import com.example.aplikasidatassiswa.ui.theme.AppColors
import com.example.aplikasidatassiswa.ui.theme.AppTypography

@Composable
private fun DetailRow(label: String, value: String) {
    val palette = AppColors.current
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(label, style = AppTypography.meta, color = palette.muted)
        Text(value, style = AppTypography.cardTitle, color = palette.text)
    }
}

@Composable
fun ProfilScreen(
    prefs: AppPreferences,
    navController: NavController,
    onLogout: () -> Unit
) {
    val palette = AppColors.current
    val darkTheme by prefs.darkTheme.collectAsStateWithLifecycle()

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
                Text("Profil", style = AppTypography.screenTitle, color = palette.text)
                Text(
                    "Informasi akun pengguna",
                    style = AppTypography.body,
                    color = palette.muted
                )
            }

            AppCard(radius = 21.dp, padding = 20.dp) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(9.dp)
                ) {
                    Avatar(
                        initials = "AD",
                        size = 74.dp,
                        textStyle = AppTypography.avatarLarge
                    )
                    Text("Administrator", style = AppTypography.profileName, color = palette.text)
                    Text(
                        "Pengelola Data Siswa",
                        style = AppTypography.meta,
                        color = palette.muted
                    )
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(palette.successBg)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            "Akun aktif",
                            style = AppTypography.navActive,
                            color = palette.success
                        )
                    }
                }
            }

            AppCard(radius = 20.dp, padding = 16.dp) {
                Text("INFORMASI AKUN", style = AppTypography.sectionHead, color = palette.muted)
                Spacer(Modifier.height(10.dp))
                DetailRow("Username", prefs.username.ifBlank { "admin" })
                Spacer(Modifier.height(10.dp))
                AppDivider()
                Spacer(Modifier.height(10.dp))
                DetailRow("Email", "admin@sekolah.sch.id")
            }

            AppCard(radius = 20.dp, padding = 16.dp) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                        Text("Tema Gelap", style = AppTypography.cardTitle, color = palette.text)
                        Text(
                            "Aktifkan tampilan gelap",
                            style = AppTypography.bodySmall,
                            color = palette.muted
                        )
                    }
                    Switch(
                        checked = darkTheme,
                        onCheckedChange = { prefs.setDarkTheme(it) }
                    )
                }
            }

            AppCard(radius = 20.dp, padding = 16.dp) {
                Text("TENTANG APLIKASI", style = AppTypography.sectionHead, color = palette.muted)
                Spacer(Modifier.height(10.dp))
                Text("SiswaKu", style = AppTypography.cardTitle, color = palette.text)
                Spacer(Modifier.height(3.dp))
                Text(
                    "Aplikasi Manajemen Data Siswa • Versi 1.0.0",
                    style = AppTypography.bodySmall,
                    color = palette.muted
                )
            }

            AppButton(
                text = "Keluar dari akun",
                onClick = onLogout,
                containerColor = palette.warningBg,
                contentColor = palette.warning,
                height = 48.dp,
                textStyle = AppTypography.buttonSmall,
                leading = {
                    Text("↪", style = AppTypography.logoutIcon, color = palette.warning)
                }
            )

            Spacer(Modifier.height(8.dp))
        }
    }
}
