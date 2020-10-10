package edu.aku.covid_followup_app.activities.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.aku.covid_followup_app.R
import edu.aku.covid_followup_app.adapters.HHListAdapter

class InfoActivity : AppCompatActivity() {

    var adapter: HHListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)
    }
}