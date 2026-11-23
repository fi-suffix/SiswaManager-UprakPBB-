package com.example.aplikasidatassiswa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.aplikasidatassiswa.data.Siswa
import com.example.aplikasidatassiswa.ui.theme.AppColors
import com.example.aplikasidatassiswa.ui.theme.AppTypography

private fun initialsOf(name: String): String =
    name.trim().split(Regex("\\s+"))
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }

@Composable
fun Avatar(
    initials: String,
    size: Dp,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    val palette = AppColors.current
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size / 2))
            .background(palette.infoBg),
        contentAlignment = Alignment.Center
    ) {
        Text(text = initials, style = textStyle, color = palette.primary)
    }
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    radius: Dp = 18.dp,
    padding: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val palette = AppColors.current
    val shape = RoundedCornerShape(radius)
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(palette.surface)
            .border(1.dp, palette.border, shape)
            .padding(padding),
        content = content
    )
}

@Composable
fun FieldLabel(text: String) {
    Text(
        text = text,
        style = AppTypography.labelUpper,
        color = AppColors.current.muted
    )
}

@Composable
fun FigmaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    label: String? = null,
    isError: Boolean = false,
    supportingText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    minLines: Int = 1
) {
    val palette = AppColors.current
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        if (label != null) FieldLabel(label)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = AppTypography.body,
                    color = palette.muted
                )
            },
            isError = isError,
            supportingText = supportingText?.let {
                { Text(text = it, style = AppTypography.bodySmall, color = Color(0xFFD93025)) }
            },
            singleLine = minLines == 1,
            minLines = minLines,
            shape = RoundedCornerShape(14.dp),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = palette.surface,
                unfocusedContainerColor = palette.surface,
                focusedBorderColor = palette.primary,
                unfocusedBorderColor = palette.border,
                focusedTextColor = palette.text,
                unfocusedTextColor = palette.text,
                cursorColor = palette.primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(if (minLines == 1) 54.dp else 104.dp)
        )
    }
}

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = AppColors.current.primary,
    contentColor: Color = AppColors.current.onPrimary,
    height: Dp = 52.dp,
    textStyle: TextStyle = AppTypography.button,
    radius: Dp = 15.dp,
    leading: @Composable (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(radius))
            .background(containerColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leading != null) {
            leading()
            androidx.compose.foundation.layout.Spacer(Modifier.size(9.dp))
        }
        Text(text = text, style = textStyle, color = contentColor)
        if (trailing != null) {
            androidx.compose.foundation.layout.Spacer(Modifier.size(9.dp))
            trailing()
        }
    }
}

@Composable
fun SectionHeading(
    title: String,
    action: String? = null,
    onAction: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = AppTypography.sectionTitle, color = AppColors.current.text)
        if (action != null) {
            Text(
                text = action,
                style = AppTypography.meta,
                color = AppColors.current.primary,
                modifier = if (onAction != null) Modifier.clickable(onClick = onAction) else Modifier
            )
        }
    }
}

@Composable
fun FilterPill(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val palette = AppColors.current
    val shape = RoundedCornerShape(16.dp)
    Box(
        modifier = modifier
            .clip(shape)
            .background(if (selected) palette.primary else palette.surface)
            .border(1.dp, if (selected) palette.primary else palette.border, shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = if (selected) AppTypography.navActive else AppTypography.meta,
            color = if (selected) palette.onPrimary else palette.muted
        )
    }
}

@Composable
fun AppDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(AppColors.current.border)
    )
}

@Composable
fun StudentCard(
    siswa: Siswa,
    modifier: Modifier = Modifier,
    showActions: Boolean = false,
    showChevron: Boolean = false,
    onClick: (() -> Unit)? = null,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null
) {
    val palette = AppColors.current
    val shape = RoundedCornerShape(18.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(palette.surface)
            .border(1.dp, palette.border, shape)
            .padding(if (showActions) 10.dp else 12.dp)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ) {
        Avatar(
            initials = initialsOf(siswa.nama),
            size = if (showActions) 40.dp else 42.dp,
            textStyle = if (showActions) AppTypography.avatarSmall else AppTypography.avatarMedium
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = siswa.nama,
                style = if (showActions) AppTypography.cardTitleSmall else AppTypography.cardTitle,
                color = palette.text
            )
            Text(
                text = "NIS ${siswa.nis} • ${siswa.kelas}",
                style = if (showActions) AppTypography.metaSmall else AppTypography.meta,
                color = palette.muted
            )
            Text(
                text = siswa.alamat,
                style = AppTypography.bodySmall,
                color = palette.muted
            )
        }
        when {
            showActions -> Column(
                verticalArrangement = Arrangement.spacedBy(7.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Edit",
                    style = AppTypography.metaSmall,
                    color = palette.primary,
                    modifier = Modifier.clickable { onEdit?.invoke() }
                )
                Text(
                    text = "Hapus",
                    style = AppTypography.metaSmall,
                    color = palette.warningBright,
                    modifier = Modifier.clickable { onDelete?.invoke() }
                )
            }
            showChevron -> Text(
                text = "›",
                style = AppTypography.chevron,
                color = palette.muted
            )
        }
    }
}
