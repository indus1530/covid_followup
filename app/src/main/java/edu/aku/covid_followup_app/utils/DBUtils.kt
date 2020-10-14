package edu.aku.covid_followup_app.utils

import edu.aku.covid_followup_app.contracts.ClustersContract.ClusterTable
import edu.aku.covid_followup_app.contracts.FormsContract.FormsTable
import edu.aku.covid_followup_app.contracts.MembersContract.MembersTable
import edu.aku.covid_followup_app.contracts.PersonalContract.PersonalTable
import edu.aku.covid_followup_app.contracts.UsersContract.UsersTable
import edu.aku.covid_followup_app.contracts.VersionAppContract.VersionAppTable

const val DATABASE_NAME = "covidsero-fup.db"
const val PROJECT_NAME = "DMU-COVIDSERO/FUP"
val DB_NAME = DATABASE_NAME.replace(".db", "-copy.db")
const val DATABASE_VERSION = 1
const val SQL_CREATE_BL_RANDOM = ("CREATE TABLE " + MembersTable.TABLE_NAME + "("
        + MembersTable.COLUMN_ID + "  INTEGER PRIMARY KEY AUTOINCREMENT,"
        + MembersTable.COLUMN_HEAD + " TEXT,"
        + MembersTable.COLUMN_HHID + " TEXT,"
        + MembersTable.COLUMN_MEMBERID + " TEXT,"
        + MembersTable.COLUMN_MEMBERNAME + " TEXT,"
        + MembersTable.COLUMN_ADDRESS + " TEXT,"
        + MembersTable.COLUMN_NASAL + " TEXT,"
        + MembersTable.COLUMN_HH_PERSONAL_COLID + " TEXT,"
        + MembersTable.COLUMN_CLUSTER + " TEXT,"
        + MembersTable.COLUMN_BLOOD + " TEXT );")
const val SQL_CREATE_FORM_TABLE = (("CREATE TABLE "
        + FormsTable.TABLE_NAME) + "("
        + FormsTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + FormsTable.COLUMN_PROJECT_NAME + " TEXT,"
        + FormsTable.COLUMN_DEVICEID + " TEXT,"
        + FormsTable.COLUMN_DEVICETAGID + " TEXT,"
        + FormsTable.COLUMN_USER + " TEXT,"
        + FormsTable.COLUMN_UID + " TEXT,"
        + FormsTable.COLUMN_LUID + " TEXT,"
        + FormsTable.COLUMN_GPSLAT + " TEXT,"
        + FormsTable.COLUMN_GPSLNG + " TEXT,"
        + FormsTable.COLUMN_GPSDATE + " TEXT,"
        + FormsTable.COLUMN_GPSACC + " TEXT,"
        + FormsTable.COLUMN_FORMDATE + " TEXT,"
        + FormsTable.COLUMN_SYSDATE + " TEXT,"
        + FormsTable.COLUMN_APPVERSION + " TEXT,"
        + FormsTable.COLUMN_CLUSTERCODE + " TEXT,"
        + FormsTable.COLUMN_HHNO + " TEXT,"
        + FormsTable.COLUMN_ADDRESS + " TEXT,"
        + FormsTable.COLUMN_SINFO + " TEXT,"
        + FormsTable.COLUMN_FSTATUS + " TEXT,"
        + FormsTable.COLUMN_FSTATUS88x + " TEXT,"
        + FormsTable.COLUMN_ENDINGDATETIME + " TEXT,"
        + FormsTable.COLUMN_ISTATUS + " TEXT,"
        + FormsTable.COLUMN_ISTATUS88x + " TEXT,"
        + FormsTable.COLUMN_SYNCED + " TEXT,"
        + FormsTable.COLUMN_SYNCED_DATE + " TEXT" + " );")
const val SQL_CREATE_PERSONALS_TABLE = (("CREATE TABLE "
        + PersonalTable.TABLE_NAME) + "("
        + PersonalTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + PersonalTable.COLUMN_PROJECT_NAME + " TEXT,"
        + PersonalTable.COLUMN_DEVICEID + " TEXT,"
        + PersonalTable.COLUMN_DEVICETAGID + " TEXT,"
        + PersonalTable.COLUMN_UID + " TEXT,"
        + PersonalTable.COLUMN_SYSDATE + " TEXT,"
        + PersonalTable.COLUMN_HH12 + " TEXT,"
        + PersonalTable.COLUMN_HH13 + " TEXT,"
        + PersonalTable.COLUMN_UUID + " TEXT,"
        + PersonalTable.COLUMN_GPSLAT + " TEXT,"
        + PersonalTable.COLUMN_GPSLNG + " TEXT,"
        + PersonalTable.COLUMN_GPSDATE + " TEXT,"
        + PersonalTable.COLUMN_GPSACC + " TEXT,"
        + PersonalTable.COLUMN_APPVERSION + " TEXT,"
        + PersonalTable.COLUMN_SA + " TEXT,"
        + PersonalTable.COLUMN_SC + " TEXT,"
        + PersonalTable.COLUMN_ENDINGDATETIME + " TEXT,"
        + PersonalTable.COLUMN_CSTATUS + " TEXT,"
        + PersonalTable.COLUMN_CSTATUS96x + " TEXT,"
        + PersonalTable.COLUMN_SYNCED + " TEXT,"
        + PersonalTable.COLUMN_SYNCED_DATE + " TEXT" + " );")
const val SQL_COUNT_LISTINGS = "SELECT count(*) as ttlisting from " + FormsTable.TABLE_NAME
const val SQL_CREATE_VERSIONAPP = "CREATE TABLE " + VersionAppTable.TABLE_NAME + " (" +
        VersionAppTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
        VersionAppTable.COLUMN_VERSION_CODE + " TEXT, " +
        VersionAppTable.COLUMN_VERSION_NAME + " TEXT, " +
        VersionAppTable.COLUMN_PATH_NAME + " TEXT );"
const val SQL_CREATE_USERS = ("CREATE TABLE " + UsersTable.TABLE_NAME + "("
        + UsersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + UsersTable.ROW_USERNAME + " TEXT,"
        + UsersTable.ROW_PASSWORD + " TEXT,"
        + UsersTable.DIST_ID + " TEXT );")
const val SQL_CREATE_DISTRICTS = ("CREATE TABLE " + ClusterTable.TABLE_NAME + "("
        + ClusterTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
        + ClusterTable.COLUMN_DIST_ID + " TEXT,"
        + ClusterTable.COLUMN_DISTRICT_NAME + " TEXT,"
        + ClusterTable.COLUMN_SUB_DIST_NAME + " TEXT,"
        + ClusterTable.COLUMN_SUB_DIST_ID + " TEXT,"
        + ClusterTable.COLUMN_CLUSTER_ID + " TEXT );")