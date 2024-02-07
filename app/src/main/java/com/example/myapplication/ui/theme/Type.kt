package com.example.myapplication.ui.theme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

val fonts = FontFamily(
    Font(R.font.robotothin, FontWeight.Light),
    Font(R.font.robotothin, FontWeight.SemiBold),
    Font(R.font.robotothin, FontWeight.Normal)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        color = Color.White,
        fontSize = 96.sp,
        letterSpacing = 0.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        color = Color.White,
        fontSize = 36.sp,
        letterSpacing = 0.sp
    ),
    displaySmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),

    bodySmall = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.SemiBold,
        color = Color.White,
        fontSize = 18.sp,
        letterSpacing = 0.sp,
    ),
)