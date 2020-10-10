package edu.aku.covid_followup_app.utils

import android.content.Context
import android.widget.ArrayAdapter
import edu.aku.covid_followup_app.activities.other.SplashscreenActivity.Companion.districts
import edu.aku.covid_followup_app.activities.other.SplashscreenActivity.Companion.subDistrictsMap
import edu.aku.covid_followup_app.core.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private suspend fun getEnumGeoArea(context: Context) = withContext(Dispatchers.IO) {
    val db = DatabaseHelper(context)
    return@withContext db.districtSubDistrict
}

suspend fun getEnumData(context: Context): MutableMap<String, String> {
    val enumLst = getEnumGeoArea(context)
    val splitLst: MutableMap<String, String> = mutableMapOf()
    enumLst.forEach { item ->
        splitLst[item.subdistrict] = item.district
    }
    return splitLst
}

private suspend fun getEnumContract(context: Context, province: String, district: String) = withContext(Dispatchers.IO) {
    val db = DatabaseHelper(context)
    return@withContext db.districtSubDistrict.find { it.subdistrict == district }
}

suspend fun setProvinceDistricts(context: Context, def: MutableMap<String, String>, adapter: ArrayAdapter<String>) {
    def.entries.forEach { item ->
        if (!districts.contains(item.value)) {
            districts.add(item.value)
            adapter.notifyDataSetChanged()
        }
        getEnumContract(context, item.value, item.key)?.let { subDistrictsMap[item.key] = Pair(item.value, it) }
    }
}

suspend fun populatingSpinners(context: Context, adapter: ArrayAdapter<String>) {
    GlobalScope.launch {
        val def = withContext(Dispatchers.Main) { getEnumData(context) }
        if (def.isNotEmpty())
            withContext(Dispatchers.Main) { setProvinceDistricts(context, def, adapter) }
    }
}

private fun String.partialList(min: Int, max: Int): List<String> {
    val items = this.split("|")
    return when {
        items.size < max || items.size < min -> items.subList(0, 0)
        else -> items.subList(min, max)
    }
}