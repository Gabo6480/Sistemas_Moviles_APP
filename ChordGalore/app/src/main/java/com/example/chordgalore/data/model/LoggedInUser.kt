package com.example.chordgalore.data.model

import android.graphics.Bitmap


/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val displayName: String,
    val profileBitmap: Bitmap,

    val portadaBitmap: Bitmap
)
