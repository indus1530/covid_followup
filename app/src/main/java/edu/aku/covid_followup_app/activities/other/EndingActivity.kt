package edu.aku.covid_followup_app.activities.other

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.validatorcrawler.aliazaz.Validator
import edu.aku.covid_followup_app.CONSTANTS.Companion.NOT_IN_HOME_END
import edu.aku.covid_followup_app.R
import edu.aku.covid_followup_app.activities.list.InfoActivity
import edu.aku.covid_followup_app.core.MainApp
import edu.aku.covid_followup_app.databinding.ActivityEndingBinding
import java.text.SimpleDateFormat
import java.util.*

class EndingActivity : AppCompatActivity() {
    lateinit var bi: ActivityEndingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = DataBindingUtil.setContentView(this, R.layout.activity_ending)
        bi.callback = this

        val check = intent.getBooleanExtra("complete", false)
        if (check) {
            bi.istatus01.isEnabled = true
            bi.istatus02.isEnabled = false
            bi.istatus03.isEnabled = false
            bi.istatus04.isEnabled = false
            bi.istatus05.isEnabled = false
            bi.istatus06.isEnabled = false
            bi.istatus07.isEnabled = false
            bi.istatus08.isEnabled = false
            bi.istatus96.isEnabled = false
        } else {
            val flag = intent.getBooleanExtra(NOT_IN_HOME_END, false)
            bi.istatus01.isEnabled = false
            bi.istatus02.isEnabled = !flag
            bi.istatus03.isEnabled = !flag
            bi.istatus04.isEnabled = !flag
            bi.istatus05.isEnabled = !flag
            bi.istatus06.isEnabled = !flag
            bi.istatus07.isEnabled = !flag
            bi.istatus08.isEnabled = flag
            bi.istatus96.isEnabled = !flag
        }
    }

    fun BtnEnd() {
        if (!formValidation()) return
        saveDraft()
        if (updateDB()) {
            finish()
        } else {
            Toast.makeText(this, "Error in updating db!!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveDraft() {
        val statusValue = if (bi.istatus01.isChecked) "1" else if (bi.istatus02.isChecked) "2" else if (bi.istatus03.isChecked) "3" else if (bi.istatus04.isChecked) "4" else if (bi.istatus05.isChecked) "5" else if (bi.istatus06.isChecked) "6" else if (bi.istatus07.isChecked) "7" else if (bi.istatus08.isChecked) "8" else if (bi.istatus96.isChecked) "96" else "-1"
        MainApp.fc.istatus = statusValue
        MainApp.fc.istatus88x = bi.istatus96x.text.toString()
        MainApp.fc.endingdatetime = SimpleDateFormat("dd-MM-yy HH:mm", Locale.getDefault()).format(Date().time)

        if (statusValue == "1") {
            InfoActivity.mainVModel.updateSpecificHHList(MainApp.fc.clusterCode, MainApp.fc.hhno, statusValue)
        }
    }


    private fun updateDB(): Boolean {
        val db = MainApp.appInfo.dbHelper
        val updcount = db.updateEnding()
        return if (updcount > 0) {
            true
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun formValidation(): Boolean {
        return Validator.emptyCheckingContainer(this, bi.fldGrpEnd)
    }

    override fun onBackPressed() {
        Toast.makeText(applicationContext, "Back press not allowed", Toast.LENGTH_LONG).show()
    }
}