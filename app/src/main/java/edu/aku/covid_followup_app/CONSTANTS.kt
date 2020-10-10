package edu.aku.covid_followup_app

class CONSTANTS {
    companion object {
        //For Login
        const val MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0
        const val MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 1
        const val MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 2
        const val MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 3
        const val MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 4
        const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 5
        const val MINIMUM_DISTANCE_CHANGE_FOR_UPDATES: Long = 1 // in Meters
        const val MINIMUM_TIME_BETWEEN_UPDATES: Long = 1000 // in Milliseconds
        const val TWO_MINUTES = 1000 * 60 * 2

        const val SYNC_LOGIN = "sync_login"

        //Main Activity
        const val REQUEST_APP_UPDATE = 1
        const val REQUEST_PERSONAL_EXIT = 2
        const val MAIN_DT_FLAG = "main_dt_flag"
        const val CLUSTER_INFO = "selected_cluster"
        const val HH_CLICKED = 3

        //Login Result Code
        const val LOGIN_RESULT_CODE = 10101
        const val LOGIN_SPLASH_FLAG = "splash_flag"

        //DateUtils
        const val MINYEAR = 1940
        const val MAXYEAR = 2020

        //PC
        const val NASAL_TAKEN = "nasal_taken"
        const val PERSONAL_END = 10102



    }
}