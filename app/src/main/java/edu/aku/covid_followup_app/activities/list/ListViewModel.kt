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

    private suspend fun clusterHHLoadFromDB(context: Context, cluster: String) = withContext(Dispatchers.Main) {
        val db = DatabaseHelper(context)
        val data = db.getHHAccordingToCluster(cluster)
        val getHHLst = mutableListOf<MembersContract>()
        data.forEach { hh ->
            val flag = getHHLst.find { item -> item.hhid == hh.hhid }
            if (flag == null) {
                getHHLst.add(hh)
            }
        }
        hhLst.value = getHHLst
    }

    private suspend fun clusterMMLoadFromDB(context: Context, cluster: String, hh: String) = withContext(Dispatchers.Main) {
        val db = DatabaseHelper(context)
        val data = db.getMMAccordingToClusterHH(cluster, hh)
        val getHHLst = mutableListOf<MembersContract>()
        data.forEach { hh ->
            getHHLst.add(hh)
        }
        mmLst.value = getHHLst
    }

}