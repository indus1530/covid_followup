package edu.aku.covid_followup_app.activities.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import edu.aku.covid_followup_app.CONSTANTS
import edu.aku.covid_followup_app.CONSTANTS.Companion.CLUSTER_INFO
import edu.aku.covid_followup_app.R
import edu.aku.covid_followup_app.activities.ui.SectionPAActivity
import edu.aku.covid_followup_app.adapters.HHListAdapter
import edu.aku.covid_followup_app.contracts.ClustersContract
import edu.aku.covid_followup_app.contracts.MembersContract
import edu.aku.covid_followup_app.databinding.ActivityInfoBinding
import edu.aku.covid_followup_app.utils.WarningActivityInterface
import edu.aku.covid_followup_app.utils.openWarningActivity

class InfoActivity : AppCompatActivity(), WarningActivityInterface {

    private lateinit var adapter: HHListAdapter
    private lateinit var bi: ActivityInfoBinding
    private lateinit var selectedCluster: ClustersContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        bi = DataBindingUtil.setContentView(this, R.layout.activity_info)
        bi.callback = this

        settingUIContent()
    }


    private fun settingUIContent() {
        selectedCluster = intent.getSerializableExtra(CLUSTER_INFO) as ClustersContract

        mainVModel = this.run {
            ViewModelProvider(this).get(ListViewModel::class.java)
        }

        mainVModel.hhLst.observe(this, { item ->
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
            /*openDialog(this, item)
            MainApp.setItemClick {
                currentFM = item
                startActivityForResult(Intent(this, SectionDActivity::class.java)
                        .putExtra(SERIAL_EXTRA, item.serialno.toInt()), CONSTANTS.MEMBER_ITEM)
            }*/
            openWarningActivity(this,
                    CONSTANTS.HH_CLICKED,
                    "Update Household information",
                    "Are you sure to update information of HHID: ${item.hhid} ?",
                    "Yes",
                    "Cancel")
        }
    }

    override fun callWarningActivity(id: Int) {
        if (id == CONSTANTS.HH_CLICKED) {
            startActivity(Intent(this, SectionPAActivity::class.java))
        }
    }

}