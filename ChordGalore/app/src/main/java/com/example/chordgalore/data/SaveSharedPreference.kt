package com.example.chordgalore.data

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.preference.PreferenceManager
import com.example.chordgalore.R
import com.example.chordgalore.data.model.LoggedInUser
import java.io.ByteArrayOutputStream


class SaveSharedPreference {
    companion object {
        private val PREF_USER_NAME = "username"
        private val PREF_USER_ID = "userid"
        private val PREF_PROFILE = "profile"
        private val PREF_PORTADA = "portada"

        private fun getSharedPreferences(ctx: Context?): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(ctx)
        }

        fun setUserData(ctx: Context?, user: LoggedInUser?) {
            val editor: SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.putString(PREF_USER_NAME, user?.displayName)
            editor.putString(PREF_USER_ID, user?.userId.toString())
            editor.putString(PREF_PROFILE, user?.profileBitmap?.let { bitmapToBase64(it) })
            editor.putString(PREF_PORTADA, user?.portadaBitmap?.let { bitmapToBase64(it) })
            editor.apply()
        }

        fun getUserData(ctx: Context?): LoggedInUser? {
            return getSharedPreferences(ctx).getString(PREF_USER_NAME, "")?.let {
                getSharedPreferences(ctx).getString(PREF_USER_ID, "")?.let { it1 ->
                    getSharedPreferences(ctx).getString(PREF_PROFILE, "")?.let { it2 ->
                        getSharedPreferences(ctx).getString(PREF_PORTADA, "")?.let { it3 ->
                            LoggedInUser(
                                //if(it.isNotEmpty()) 0 else it.toInt(10),
                                it,
                                it1,
                                base64ToBitmap(it2, ctx),
                                base64ToBitmap(it3, ctx),
                            )
                        }
                    }
                }
            }
        }

        fun clearUserName(ctx: Context?) {
            val editor: SharedPreferences.Editor = getSharedPreferences(ctx).edit()
            editor.clear() //clear all stored data
            editor.apply()
        }

        private fun bitmapToBase64(bitmap: Bitmap) : String{
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos) //bm is the bitmap object
            val b: ByteArray = baos.toByteArray()

            return Base64.encodeToString(b, Base64.DEFAULT)
        }

        private fun base64ToBitmap(base64: String?, ctx: Context?) : Bitmap{
            val decodedString = Base64.decode(base64, Base64.DEFAULT)
            return if (decodedString != null && decodedString.isNotEmpty())
                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            else
                BitmapFactory.decodeResource(ctx?.resources, R.drawable.default_image)
        }
    }
}