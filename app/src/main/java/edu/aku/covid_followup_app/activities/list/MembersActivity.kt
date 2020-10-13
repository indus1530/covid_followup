package edu.aku.covid_followup_app.activities.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import edu.aku.covid_followup_app.CONSTANTS
import edu.aku.covid_followup_app.CONSTANTS.Companion.MEMBER_INFO
import edu.aku.covid_followup_app.R
import edu.aku.covid_followup_app.activities.other.EndingActivity
import edu.aku.covid_followup_app.activities.ui.SectionPAActivity
import edu.aku.covid_followup_app.adapters.MMListAdapter
import edu.aku.covid_followup_app.contracts.MembersContract
import edu.aku.covid_followup_app.databinding.ActivityMembersBinding
import edu.aku.covid_followup_app.utils.WarningActivityInterface
import edu.aku.covid_followup_app.utils.openWarningActivity

class MembersActivity : AppCompatActivity(), WarningActivityInterface {

    private lateinit var adapter: MMListAdapter
    private lateinit var bi: ActivityMembersBinding
    private lateinit var selectedMember: MembersContract
    private var memberCounter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bi = DataBindingUtil.setContentView(this, R.layout.activity_members)
        bi.callback = this

        settingUIContent()
    }

    private fun settingUIContent() {
        selectedMember = intent.getSerializableExtra(MEMBER_INFO) as MembersContract
        bi.toolbarLayout.title = "Cluster:${selectedMember.cluster} --> Household:${selectedMember.hhid}"
        bi.toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.black))
        InfoActivity.mainVModel.mmLst.observe(this, { item ->
            adapter.setMList(item)
        })
        InfoActivity.mainVModel.getMembersLst(this, selectedMember.cluster, selectedMember.hhid)
        setupRecyclerView(mutableListOf())
        bi.toolbarLayout.setExpandedTitleTextAppearance(R.style.expandCollapse)
    }

    private fun setupRecyclerView(membersLst: MutableList<MembersContract>) {
        adapter = MMListAdapter(this, membersLst, InfoActivity.mainVModel)
        bi.mmList.layoutManager = LinearLayoutManager(this)
        bi.mmList.adapter = adapter
        adapter.setItemClicked { item, position ->
            openWarningActivity(activity = this,
                    id = CONSTANTS.MM_CLICKED,
                    item = item,
                    title = "Update Member information",
                    message = "Are you sure, you want to update information of ${item.membername}?",
                    btnYesTxt = "Yes! Update it",
                    btnNoTxt = "Cancel")
        }
    }

    override fun callWarningActivity(id: Int, item: Any?) {
        if (id == CONSTANTS.MM_CLICKED) {
            memberCounter++
            val member = item as MembersContract
            InfoActivity.mainVModel.setCheckedItemValues(member.memberid.toInt())
            startActivity(Intent(this, SectionPAActivity::class.java).putExtra(MEMBER_INFO, member))
        }
    }

    fun BtnEndHH(v: View) {
        if (memberCounter != adapter.itemCount) {
            Snackbar.make(findViewById(android.R.id.content), "Please lock all members to proceed next section", Snackbar.LENGTH_LONG)
                    .show()
            return
        }
        finish()
        startActivity(Intent(this, EndingActivity::class.java).putExtra("complete", true))
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Back press not allowed", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}