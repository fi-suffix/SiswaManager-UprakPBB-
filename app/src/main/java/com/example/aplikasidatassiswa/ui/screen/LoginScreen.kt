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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.aplikasidatassiswa.data.AppPreferences
import com.example.aplikasidatassiswa.ui.components.AppButton
import com.example.aplikasidatassiswa.ui.components.FigmaTextField
import com.example.aplikasidatassiswa.ui.theme.AppColors
import com.example.aplikasidatassiswa.ui.theme.AppTypography

@Composable
fun LoginScreen(onLogin: (String) -> Unit) {
    val palette = AppColors.current
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var usernameError by rememberSaveable { mutableStateOf<String?>(null) }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(62.dp)
                    .clip(RoundedCornerShape(19.dp))
                    .background(palette.primary),
                contentAlignment = Alignment.Center
            ) {
                Text("S", style = AppTypography.logo, color = palette.onPrimary)
            }
            Text("SiswaKu", style = AppTypography.brand, color = palette.text)
            Text(
                "MANAJEMEN DATA SISWA",
                style = AppTypography.meta,
                color = palette.primary
            )
        }

        Spacer(Modifier.height(36.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                "Selamat datang kembali",
                style = AppTypography.welcome,
                color = palette.text
            )
            Text(
                "Masuk untuk mengelola informasi dan data siswa sekolah.",
                style = AppTypography.body,
                color = palette.muted
            )
        }

        Spacer(Modifier.height(28.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            FigmaTextField(
                value = username,
                onValueChange = {
                    username = it
                    usernameError = null
                    passwordError = null
                },
                label = "USERNAME",
                placeholder = "Masukkan username",
                isError = usernameError != null,
                supportingText = usernameError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )

            FigmaTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = null
                },
                label = "PASSWORD",
                placeholder = "Masukkan password",
                isError = passwordError != null,
                supportingText = passwordError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Gunakan akun yang terdaftar",
                    style = AppTypography.bodySmall,
                    color = palette.muted
                )
                Text("Lupa password?", style = AppTypography.meta, color = palette.primary)
            }

            AppButton(
                text = "Masuk",
                onClick = {
                    val user = username.trim()
                    val pass = password
                    usernameError = if (user.isEmpty()) "Username wajib diisi" else null
                    passwordError = if (pass.isEmpty()) "Password wajib diisi" else null

                    if (usernameError == null && passwordError == null) {
                        if (user == AppPreferences.DEMO_USERNAME && pass == AppPreferences.DEMO_PASSWORD) {
                            onLogin(user)
                        } else {
                            passwordError = "Username atau password salah"
                        }
                    }
                },
                height = 52.dp
            )

            Text(
                "Demo: ${AppPreferences.DEMO_USERNAME} / ${AppPreferences.DEMO_PASSWORD}",
                style = AppTypography.bodySmall,
                color = palette.muted,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                "Aplikasi Manajemen Data Siswa",
                style = AppTypography.meta,
                color = palette.muted
            )
            Text(
                "SMK Plus Pelita Nusantara Bogor",
                style = AppTypography.bodySmall,
                color = palette.muted
            )
        }
    }
}
