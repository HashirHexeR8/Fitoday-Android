package com.active.fitody.util

import android.graphics.Bitmap
import android.view.View
import com.active.fitody.FitodyApplication
import com.active.fitody.UserPrefs
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class Utilities {

    private val profilePictureFolderName = "profile_picture"
    private val profilePictureFileName = "_user_profile_image.png"

    companion object {
        val instance = Utilities()
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("ddMMyyyy")
        return sdf.format(Date())
    }

    fun getCurrentDay(): String {
        val sdf = SimpleDateFormat("E")
        return sdf.format(Date())
    }

    fun getTimeInHMS(totalSeconds: Int): String {
        var timeString = ""
        if (totalSeconds < 10) {
            timeString = "00:0${totalSeconds}"
        }
        else if (totalSeconds > 59) {
            val minutes = totalSeconds / 60
            val remainingSeconds = totalSeconds % 60
            timeString = "${if (minutes < 10) "0$minutes" else minutes}:${if (remainingSeconds < 10) "0$remainingSeconds" else remainingSeconds}"
//            if (minutes > 59) {
//                val hours = minutes % 60
//                val remainingMinutes = hours / 60
//                timeString = "${if (hours < 10) "0$hours" else hours}:${if (remainingMinutes < 10) "0$remainingMinutes" else remainingMinutes}:${if (remainingSeconds < 10) "0$remainingSeconds" else remainingSeconds}"
//            }
        }
        else {
            timeString = "00:${totalSeconds}"
        }
        return timeString
    }

    public fun showSnackBar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }

    private fun getFilePath(): String {
        val filePath = FitodyApplication.context().filesDir.absolutePath + File.separator + profilePictureFolderName
        val file = File(filePath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return filePath
    }

    public fun storeUserProfilePicture(bitmap: Bitmap) {
        val fileName = UserPrefs.getInstance().getUserId().toString() + profilePictureFileName
        val filePath = getFilePath() + File.separator + fileName
        try {
            FileOutputStream(filePath).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) // bmp is your Bitmap instance
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    public fun getProfilePicturePath(): String {
        return getFilePath() + File.separator + UserPrefs.getInstance().getUserId().toString() + profilePictureFileName
    }

    public fun roundOffToTwoDigits(value: Float): Float {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        return df.format(value).toFloat()
    }


}