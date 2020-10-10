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


    fun getHouseHoldLst(context: Context, cluster: String) {

        viewModelScope.launch {
            try {
                hhLst.value = mutableListOf()
                hhLoadFromDB(context, cluster)
            } catch (error: Error) {

            } finally {

            }
        }

    }

    suspend fun hhLoadFromDB(context: Context, cluster: String) = withContext(Dispatchers.Main) {
        val db = DatabaseHelper(context)
        val data = db.getHHAccordingToCluster(cluster)
        val getHHLst = mutableListOf<MembersContract>()
        data.forEach { hh ->
            val flag = hhLst.value?.filter { item -> item.hhid != hh.hhid }
            if (flag?.isEmpty() == true) {
                getHHLst.add(hh)
            }
        }

        hhLst.value = getHHLst
    }

}