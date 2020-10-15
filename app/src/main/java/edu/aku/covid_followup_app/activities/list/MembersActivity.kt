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
import com.kennyc.view.MultiStateView
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
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
import kotlinx.coroutines.Runnable
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

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

        bi.memMultiStateView.viewState = MultiStateView.ViewState.LOADING

        selectedMember = intent.getSerializableExtra(MEMBER_INFO) as MembersContract
        bi.toolbarLayout.title = "Cluster:${selectedMember.cluster} --> Household:${selectedMember.hhid}"
        bi.toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.black))
        InfoActivity.mainVModel.mmLst.observe(this, { item ->
            bi.memMultiStateView.postDelayed(Runnable {
                if (item.size == 0) {
                    bi.memMultiStateView.viewState = MultiStateView.ViewState.EMPTY
                    val worker: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
                    worker.schedule(Runnable {
                        finish()
                    }, 3500, TimeUnit.MILLISECONDS)
                } else {
                    bi.memMultiStateView.viewState = MultiStateView.ViewState.CONTENT
                }
            }, 2000L)

            adapter.mList = item
            memberCounter = item.filter { sub -> sub.memFlag != 3 }.size
        })
        InfoActivity.mainVModel.getMembersLst(this, selectedMember.cluster, selectedMember.hhid)
        setupRecyclerView(mutableListOf())
        bi.toolbarLayout.setExpandedTitleTextAppearance(R.style.expandCollapse)

        val actionItems = mutableListOf<SpeedDialActionItem>()
        actionItems.add(SpeedDialActionItem.Builder(R.id.sd_main_fab, R.drawable.ic_finish).setLabel("Finish").create())
        actionItems.add(SpeedDialActionItem.Builder(R.id.sd_fab, R.drawable.ic_exit).setLabel("Force exit").create())

        bi.speedDial.addAllActionItems(actionItems)

        bi.speedDial.setOnActionSelectedListener(SpeedDialView.OnActionSelectedListener { actionItem ->
            when (actionItem.getLabel(this)) {
                "Force exit" -> {
                    openWarningActivity(activity = this,
                            id = CONSTANTS.FORCE_EXIT_CLICKED,
                            item = false,
                            title = "Forcefully Exit Form",
                            message = "Are you sure, you want to exist this form ",
                            btnYesTxt = "Yes! Do it",
                            btnNoTxt = "Cancel")
                    return@OnActionSelectedListener true // false will close it without animation
                }
                "Finish" -> {
                    if (memberCounter != adapter.itemCount) {
                        Snackbar.make(findViewById(android.R.id.content), "Please lock all members for proceeding to the next section", Snackbar.LENGTH_LONG)
                                .show()
                        return@OnActionSelectedListener false
                    }
                    val flag = adapter.mList.find { item -> item.memFlag == 3 }
                    finish()
                    startActivity(Intent(this, EndingActivity::class.java).putExtra("complete", flag == null).putExtra(CONSTANTS.NOT_IN_HOME_END, flag != null))
                    return@OnActionSelectedListener true // false will close it without animation
                }
            }
            false
        })

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
        } else if (id == CONSTANTS.FORCE_EXIT_CLICKED) {
            finish()
            startActivity(Intent(this, EndingActivity::class.java))
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