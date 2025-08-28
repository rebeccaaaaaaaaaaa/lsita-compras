package com.example.supercompras.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.supercompras.R

val kronaFontFamily: FontFamily
    @Composable
    get() = FontFamily(Font(R.font.krona))

val numansFontFamily: FontFamily
    @Composable
    get() = FontFamily(Font(R.font.numans))

@Composable
fun AppTypography(): Typography {
    val krona = kronaFontFamily
    val numans = numansFontFamily

    return Typography(
        headlineLarge = TextStyle(
            fontFamily = krona,
            fontWeight = FontWeight.Normal,
            color = Coral,
            fontSize = 20.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.5.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = numans,
            fontWeight = FontWeight.Normal,
            color = Marinho,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = numans,
            fontWeight = FontWeight.Normal,
            color = Marinho,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = numans,
            fontWeight = FontWeight.Normal,
            color = Marinho,
            fontSize = 12.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.5.sp
        ),
    )
}