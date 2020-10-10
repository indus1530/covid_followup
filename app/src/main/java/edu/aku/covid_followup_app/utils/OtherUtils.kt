package edu.aku.covid_followup_app.utils

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import edu.aku.covid_followup_app.R
import edu.aku.covid_followup_app.databinding.ItemDialogBinding
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

fun dbBackup(context: Context) {
    val sharedPref = context.getSharedPreferences("listingHHSMK", Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    val dt: String = sharedPref.getString("dt", "").toString()
    if (dt != SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())) {
        editor.putString("dt", SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()))
        editor.apply()
    }

    var folder: File
    folder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        File(context.getExternalFilesDir("")?.absolutePath + File.separator + PROJECT_NAME)
    else
        File(Environment.getExternalStorageDirectory().toString() + File.separator + PROJECT_NAME)

    var success = true
    if (!folder.exists()) {
        success = folder.mkdirs()
    }
    if (success) {
        val directoryName = folder.path + File.separator + sharedPref.getString("dt", "")
        folder = File(directoryName)
        if (!folder.exists()) {
            success = folder.mkdirs()
        }
        if (success) {
            val any = try {
                val dbFile = File(context.getDatabasePath(DATABASE_NAME).path)
                val fis = FileInputStream(dbFile)
                val outFileName: String = directoryName + File.separator + DB_NAME
                // Open the empty db as the output stream
                val output: OutputStream = FileOutputStream(outFileName)

                // Transfer bytes from the inputfile to the outputfile
                val buffer = ByteArray(1024)
                var length: Int
                while (fis.read(buffer).also { length = it } > 0) {
                    output.write(buffer, 0, length)
                }
                // Close the streams
                output.flush()
                output.close()
                fis.close()
            } catch (e: IOException) {
                e.message?.let { Log.e("dbBackup:", it) }
            }
        }
    } else {
        Toast.makeText(context, "Not create folder", Toast.LENGTH_SHORT).show()
    }

}

private fun checkPermission(context: Context): IntArray {
    return intArrayOf(ContextCompat.checkSelfPermission(context,
            Manifest.permission.READ_CONTACTS), ContextCompat.checkSelfPermission(context,
            Manifest.permission.GET_ACCOUNTS), ContextCompat.checkSelfPermission(context,
            Manifest.permission.READ_PHONE_STATE), ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_FINE_LOCATION), ContextCompat.checkSelfPermission(context,
            Manifest.permission.ACCESS_COARSE_LOCATION), ContextCompat.checkSelfPermission(context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE), ContextCompat.checkSelfPermission(context,
            Manifest.permission.CAMERA))
}

fun getPermissionsList(context: Context): List<String> {
    val permissions = arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA)
    val listPermissionsNeeded: MutableList<String> = ArrayList()
    for (i in checkPermission(context).indices) {
        if (checkPermission(context)[i] != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(permissions[i])
        }
    }
    return listPermissionsNeeded
}


@JvmOverloads
fun openWarningActivity(
        activity: Activity,
        id: Int,
        title: String = "WARNING!",
        message: String = "Are you sure, you want to exit without saving?",
        btnYesTxt: String = "YES",
        btnNoTxt: String = "NO") {
    val dialog = Dialog(activity)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val bi: ItemDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.item_dialog, null, false)
    dialog.setContentView(bi.root)
    bi.alertTitle.text = title
    bi.alertTitle.setTextColor(ContextCompat.getColor(activity, R.color.red))
    bi.content.text = message
    bi.btnOk.text = btnYesTxt
    bi.btnOk.setBackgroundColor(ContextCompat.getColor(activity, R.color.green))
    bi.btnNo.text = btnNoTxt
    bi.btnNo.setBackgroundColor(ContextCompat.getColor(activity, R.color.gray))
    dialog.setCancelable(false)
    val params = WindowManager.LayoutParams()
    params.copyFrom(dialog.window!!.attributes)
    params.width = WindowManager.LayoutParams.WRAP_CONTENT
    params.height = WindowManager.LayoutParams.WRAP_CONTENT
    dialog.window!!.attributes = params
    dialog.show()
    bi.btnOk.setOnClickListener {
        dialog.dismiss()
        val warningActivity = activity as WarningActivityInterface
        warningActivity.callWarningActivity(id)
    }
    bi.btnNo.setOnClickListener {
        dialog.dismiss()
    }
}

interface WarningActivityInterface {
    fun callWarningActivity(id: Int)
}