package edu.aku.covid_followup_app.activities.list

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.aku.covid_followup_app.contracts.MembersContract
import edu.aku.covid_followup_app.core.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListViewModel : ViewModel() {

    var hhLst = MutableLiveData<MutableList<MembersContract>>()
        private set

    var mmLst = MutableLiveData<MutableList<MembersContract>>()
        private set

    var checkedItems = MutableLiveData<MutableList<Int>>()
        private set


    fun getHouseHoldLst(context: Context, cluster: String) {

        viewModelScope.launch {
            try {
                hhLst.value = mutableListOf()
                clusterHHLoadFromDB(context, cluster)
            } catch (error: Error) {

            } finally {

            }
        }

    }

    fun getMembersLst(context: Context, cluster: String, hh: String) {

        viewModelScope.launch {
            try {
                mmLst.value = mutableListOf()
                clusterMMLoadFromDB(context, cluster, hh)
            } catch (error: Error) {

            } finally {

            }
        }

    }

    fun setCheckedItemValues(index: Int) {
        var lst = checkedItems.value
        if (lst.isNullOrEmpty()) {
            lst = mutableListOf()
            lst.add(index)
        } else lst.add(index)

        checkedItems.value = lst
    }

    fun getCheckedItemValues(fmItem: Int): Boolean {
        val flag = checkedItems.value?.find { it == fmItem }
        flag?.let { return true } ?: return false
    }


    fun updateSpecificMMList(cluster: String, hh: String, mm: String, pa03: String) {
        val lst = mmLst.value
        val member = mmLst.value?.find { item -> cluster == item.cluster && hh == item.hhid && mm == item.memberid }
                ?: return
        member.memFlag = pa03.toInt()
        lst?.map { if (member.id == it.id) member else it }
        mmLst.value = lst
    }

    fun updateSpecificHHList(cluster: String, hh: String, formflag: String) {
        val lst = hhLst.value
        val houshold = hhLst.value?.find { item -> cluster == item.cluster && hh == item.hhid }
                ?: return
        houshold.formFlag = formflag.toInt()
        lst?.map { if (houshold.id == it.id) houshold else it }
        hhLst.value = lst
    }

    private suspend fun clusterHHLoadFromDB(context: Context, cluster: String) = withContext(Dispatchers.Main) {
        val db = DatabaseHelper(context)
        val data = db.getHHAccordingToCluster(cluster)
        val getHHLst = mutableListOf<MembersContract>()
        data.forEach { hh ->
            val flag = getHHLst.find { item -> item.hhid == hh.hhid }
            if (flag == null) {
                val subForm = db.getExistingForm(cluster, hh.hhid)
                if (subForm != null) {
                    hh.formFlag = subForm.istatus.toInt()
                    hh.existForm = subForm
                }
                getHHLst.add(hh)
            }
        }
        hhLst.value = getHHLst
    }

    private suspend fun clusterMMLoadFromDB(context: Context, cluster: String, hh: String) = withContext(Dispatchers.Main) {
        val db = DatabaseHelper(context)
        val data = db.getMMAccordingToClusterHH(cluster, hh)
        val getMMLst = mutableListOf<MembersContract>()
        data.forEach { hh ->
            val flag = getMMLst.find { item -> item.memberid == hh.memberid }
            if (flag == null) {
                val subForm = db.getExistingPersonal(cluster, hh.hhid, hh.memberid)
                if (subForm != null) {
                    if (subForm.pa03 == "3") hh.memFlag = 0
                    else hh.memFlag = 1
                }
                getMMLst.add(hh)
            }
        }
        mmLst.value = getMMLst
    }

}