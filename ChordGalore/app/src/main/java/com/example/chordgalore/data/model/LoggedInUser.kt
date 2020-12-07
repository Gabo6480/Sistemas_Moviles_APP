package com.example.chordgalore.data.model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.example.chordgalore.R
import java.io.ByteArrayOutputStream


/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String,
    val profileBitmap: Bitmap,
    val portadaBitmap: Bitmap
)
