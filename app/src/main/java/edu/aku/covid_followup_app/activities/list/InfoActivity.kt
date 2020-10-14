package edu.aku.covid_followup_app.activities.list

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kennyc.view.MultiStateView
import edu.aku.covid_followup_app.CONSTANTS
import edu.aku.covid_followup_app.CONSTANTS.Companion.CLUSTER_INFO
import edu.aku.covid_followup_app.R
import edu.aku.covid_followup_app.adapters.HHListAdapter
import edu.aku.covid_followup_app.contracts.ClustersContract
import edu.aku.covid_followup_app.contracts.FormsContract
import edu.aku.covid_followup_app.contracts.MembersContract
import edu.aku.covid_followup_app.core.MainApp
import edu.aku.covid_followup_app.databinding.ActivityInfoBinding
import edu.aku.covid_followup_app.utils.WarningActivityInterface
import edu.aku.covid_followup_app.utils.openWarningActivity
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class InfoActivity : AppCompatActivity(), WarningActivityInterface {

    private lateinit var adapter: HHListAdapter
    private lateinit var bi: ActivityInfoBinding
    private lateinit var selectedCluster: ClustersContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bi = DataBindingUtil.setContentView(this, R.layout.activity_info)
        bi.callback = this

        settingUIContent()
    }


    private fun settingUIContent() {
        bi.multiStateView.viewState = MultiStateView.ViewState.LOADING

        selectedCluster = intent.getSerializableExtra(CLUSTER_INFO) as ClustersContract
        this.title = "Household List of Cluster:${selectedCluster.cluster_id}"

        mainVModel = this.run {
            ViewModelProvider(this).get(ListViewModel::class.java)
        }

        mainVModel.hhLst.observe(this, { item ->
            bi.multiStateView.postDelayed(Runnable {
                if (item.size == 0) {
                    bi.multiStateView.viewState = MultiStateView.ViewState.EMPTY
                    val worker: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
                    worker.schedule(Runnable {
                        finish()
                    }, 3500, TimeUnit.MILLISECONDS)
                } else {
                    bi.multiStateView.viewState = MultiStateView.ViewState.CONTENT
                }
            }, 2000L)
            adapter.setMList(item)
        })

        mainVModel.getHouseHoldLst(this, selectedCluster.cluster_id)
        setupRecyclerView(mutableListOf())
    }


    companion object {
        lateinit var mainVModel: ListViewModel
    }


    private fun setupRecyclerView(membersLst: MutableList<MembersContract>) {
        adapter = HHListAdapter(this, membersLst, true)
        bi.hhList.layoutManager = LinearLayoutManager(this)
        bi.hhList.adapter = adapter
        adapter.setItemClicked { item, position, flag ->
            openWarningActivity(activity = this,
                    id = CONSTANTS.HH_CLICKED,
                    item = item,
                    title = "Update Household information",
                    message = "Are you sure to update information of HHID: ${item.hhid} ?",
                    btnYesTxt = "Yes",
                    btnNoTxt = "Cancel")
        }
    }

    override fun callWarningActivity(id: Int, item: Any?) {
        if (id == CONSTANTS.HH_CLICKED) {

            val mem = item as MembersContract

            MainApp.fc = FormsContract()
            MainApp.fc.deviceID = MainApp.appInfo.deviceID
            MainApp.fc.devicetagID = MainApp.appInfo.tagName
            MainApp.fc.hhno = mem.hhid
            MainApp.fc.clusterCode = mem.cluster
            MainApp.fc.formDate = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(Date().time)
            MainApp.fc.sysDate = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(Date().time)
            MainApp.fc.user = MainApp.userEmail
            MainApp.fc.appversion = MainApp.appInfo.appVersion
            MainApp.fc.ha11 = mem.address
            setGPS(this)

            runBlocking {
                val result = lifecycleScope.async { updateDB(this@InfoActivity) }
                if (result.await()) startActivity(Intent(this@InfoActivity, MembersActivity::class.java).putExtra(CONSTANTS.MEMBER_INFO, mem))
            }
        }
    }

    private fun setGPS(activity: Activity) {
        val GPSPref = activity.getSharedPreferences("GPSCoordinates", MODE_PRIVATE)
        try {
            val lat = GPSPref.getString("Latitude", "0")
            val lang = GPSPref.getString("Longitude", "0")
            if (lat == "0" && lang == "0") {
                Toast.makeText(activity, "Could not obtained GPS points", Toast.LENGTH_SHORT).show()
            }
            val date = DateFormat.format("dd-MM-yyyy HH:mm", GPSPref.getString("Time", "0")!!.toLong()).toString()
            MainApp.fc.gpsLat = GPSPref.getString("Latitude", "0")
            MainApp.fc.gpsLng = GPSPref.getString("Longitude", "0")
            MainApp.fc.gpsAcc = GPSPref.getString("Accuracy", "0")
            MainApp.fc.gpsDT = date
        } catch (e: Exception) {
            Log.e("GPS", "setGPS: " + e.message)
        }
    }

    private suspend fun updateDB(context: Context): Boolean = withContext(Dispatchers.Main) {
        val db = MainApp.appInfo.dbHelper
        val updcount = db.addForm(MainApp.fc)
        MainApp.fc._ID = updcount.toString()
        if (updcount > 0) {
            MainApp.fc._UID = MainApp.appInfo.deviceID + MainApp.fc._ID
            db.updateFormsColumn(FormsContract.FormsTable.COLUMN_UID, MainApp.fc._UID, MainApp.fc._ID)
            return@withContext true
        } else {
            Toast.makeText(context, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show()
        }
        return@withContext false
    }

    override fun onResume() {
        super.onResume()
//        if (adapter.itemCount == 0) bi.multiStateView.viewState = MultiStateView.ViewState.LOADING
        adapter.notifyDataSetChanged()
    }
}