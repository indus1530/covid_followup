package edu.aku.smk_hhlisting_app.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import edu.aku.smk_hhlisting_app.contracts.ClustersContract;
import edu.aku.smk_hhlisting_app.contracts.ClustersContract.ClusterTable;
import edu.aku.smk_hhlisting_app.contracts.EnumBlockContract;
import edu.aku.smk_hhlisting_app.contracts.EnumBlockContract.EnumBlockTable;
import edu.aku.smk_hhlisting_app.contracts.FormsContract;
import edu.aku.smk_hhlisting_app.contracts.FormsContract.FormsTable;
import edu.aku.smk_hhlisting_app.contracts.MembersContract;
import edu.aku.smk_hhlisting_app.contracts.PersonalContract;
import edu.aku.smk_hhlisting_app.contracts.PersonalContract.PersonalTable;
import edu.aku.smk_hhlisting_app.contracts.UsersContract;
import edu.aku.smk_hhlisting_app.contracts.VersionAppContract;
import edu.aku.smk_hhlisting_app.contracts.VersionAppContract.VersionAppTable;

import static edu.aku.smk_hhlisting_app.utils.DBUtilsKt.DATABASE_NAME;
import static edu.aku.smk_hhlisting_app.utils.DBUtilsKt.DATABASE_VERSION;
import static edu.aku.smk_hhlisting_app.utils.DBUtilsKt.SQL_COUNT_LISTINGS;
import static edu.aku.smk_hhlisting_app.utils.DBUtilsKt.SQL_CREATE_BL_RANDOM;
import static edu.aku.smk_hhlisting_app.utils.DBUtilsKt.SQL_CREATE_DISTRICTS;
import static edu.aku.smk_hhlisting_app.utils.DBUtilsKt.SQL_CREATE_ENUMBLOCK;
import static edu.aku.smk_hhlisting_app.utils.DBUtilsKt.SQL_CREATE_FORM_TABLE;
import static edu.aku.smk_hhlisting_app.utils.DBUtilsKt.SQL_CREATE_PERSONALS_TABLE;
import static edu.aku.smk_hhlisting_app.utils.DBUtilsKt.SQL_CREATE_USERS;
import static edu.aku.smk_hhlisting_app.utils.DBUtilsKt.SQL_CREATE_VERSIONAPP;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = DatabaseHelper.class.getName();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FORM_TABLE);
        db.execSQL(SQL_CREATE_PERSONALS_TABLE);
        db.execSQL(SQL_CREATE_ENUMBLOCK);
        db.execSQL(SQL_CREATE_USERS);
        db.execSQL(SQL_CREATE_BL_RANDOM);
        db.execSQL(SQL_CREATE_VERSIONAPP);
        db.execSQL(SQL_CREATE_DISTRICTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simply discard all old data and start over when upgrading.
        /*db.execSQL("DROP TABLE IF EXISTS " + ListingEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SingleTaluka.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EnumBlockTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UsersTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SingleVertices.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + VersionAppTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SignUpTable.TABLE_NAME);
        onCreate(db);*/
    }


    //Other Functions
    public boolean Login(String username, String password) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor mCursor = db.rawQuery("SELECT * FROM " + UsersContract.UsersTable.TABLE_NAME + " WHERE " + UsersContract.UsersTable.ROW_USERNAME + "=? AND " + UsersContract.UsersTable.ROW_PASSWORD + "=?", new String[]{username, password});
        if (mCursor != null) {

            if (mCursor.getCount() > 0) {

                if (mCursor.moveToFirst()) {
//                    MainApp.DIST_ID = mCursor.getString(mCursor.getColumnIndex(UsersContract.UsersTable.DIST_ID));
                    mCursor.close();
                }
                return true;
            }
        }
        return false;
    }


    //Add Forms
    public Long addForm(FormsContract fc) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_PROJECT_NAME, fc.getProjectName());
        values.put(FormsTable.COLUMN_UID, fc.get_UID());
        values.put(FormsTable.COLUMN_FORMDATE, fc.getFormDate());
        values.put(FormsTable.COLUMN_SYSDATE, fc.getSysDate());
        values.put(FormsTable.COLUMN_LUID, fc.getLuid());
        values.put(FormsTable.COLUMN_USER, fc.getUser());
        values.put(FormsTable.COLUMN_ISTATUS, fc.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS88x, fc.getIstatus88x());
        values.put(FormsTable.COLUMN_FSTATUS, fc.getfStatus());
        values.put(FormsTable.COLUMN_FSTATUS88x, fc.getFstatus88x());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, fc.getEndingdatetime());
        values.put(FormsTable.COLUMN_SINFO, fc.getsInfo());
        values.put(FormsTable.COLUMN_SE, fc.getsE());
        values.put(FormsTable.COLUMN_SM, fc.getsM());
        values.put(FormsTable.COLUMN_SN, fc.getsN());
        values.put(FormsTable.COLUMN_SO, fc.getsO());
        values.put(FormsTable.COLUMN_GPSLAT, fc.getGpsLat());
        values.put(FormsTable.COLUMN_GPSLNG, fc.getGpsLng());
        values.put(FormsTable.COLUMN_GPSDATE, fc.getGpsDT());
        values.put(FormsTable.COLUMN_GPSACC, fc.getGpsAcc());
        values.put(FormsTable.COLUMN_DEVICETAGID, fc.getDevicetagID());
        values.put(FormsTable.COLUMN_DEVICEID, fc.getDeviceID());
        values.put(FormsTable.COLUMN_APPVERSION, fc.getAppversion());
        values.put(FormsTable.COLUMN_CLUSTERCODE, fc.getClusterCode());
        values.put(FormsTable.COLUMN_HHNO, fc.getHhno());
        values.put(FormsTable.COLUMN_FORMTYPE, fc.getFormType());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                FormsTable.TABLE_NAME,
                FormsTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addPersonal(PersonalContract personal) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PersonalTable.COLUMN_PROJECT_NAME, personal.getProjectName());
        values.put(PersonalTable.COLUMN_UID, personal.get_UID());
        values.put(PersonalTable.COLUMN_SYSDATE, personal.getSysdate());
        values.put(PersonalTable.COLUMN_A01, personal.getA01());
        values.put(PersonalTable.COLUMN_A02, personal.getA02());
        values.put(PersonalTable.COLUMN_A03, personal.getA03());
        values.put(PersonalTable.COLUMN_HH12, personal.getHh12());
        values.put(PersonalTable.COLUMN_HH13, personal.getHh13());
        values.put(PersonalTable.COLUMN_UUID, personal.get_UUID());
        values.put(PersonalTable.COLUMN_CSTATUS, personal.getCstatus());
        values.put(PersonalTable.COLUMN_CSTATUS96x, personal.getCstatus96x());
        values.put(PersonalTable.COLUMN_ENDINGDATETIME, personal.getEndingdatetime());
        values.put(PersonalTable.COLUMN_SA, personal.getsA());
        values.put(PersonalTable.COLUMN_SB, personal.getsB());
        values.put(PersonalTable.COLUMN_SC, personal.getsC());
        values.put(PersonalTable.COLUMN_SI, personal.getsI());
        values.put(PersonalTable.COLUMN_GPSLAT, personal.getGpsLat());
        values.put(PersonalTable.COLUMN_GPSLNG, personal.getGpsLng());
        values.put(PersonalTable.COLUMN_GPSDATE, personal.getGpsDT());
        values.put(PersonalTable.COLUMN_GPSACC, personal.getGpsAcc());
        values.put(PersonalTable.COLUMN_DEVICETAGID, personal.getDevicetagID());
        values.put(PersonalTable.COLUMN_DEVICEID, personal.getDeviceID());
        values.put(PersonalTable.COLUMN_APPVERSION, personal.getAppversion());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                PersonalTable.TABLE_NAME,
                PersonalTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }

    public Long addMember(MembersContract members) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MembersContract.MembersTable.COLUMN_ID, members.getId());
        values.put(MembersContract.MembersTable.COLUMN_BLOOD, members.getBlood());
        values.put(MembersContract.MembersTable.COLUMN_HHID, members.getHhid());
        values.put(MembersContract.MembersTable.COLUMN_HEAD, members.getHead());
        values.put(MembersContract.MembersTable.COLUMN_MEMBERID, members.getMemberid());
        values.put(MembersContract.MembersTable.COLUMN_MEMBERNAME, members.getMembername());
        values.put(MembersContract.MembersTable.COLUMN_ADDRESS, members.getAddress());
        values.put(MembersContract.MembersTable.COLUMN_NASAL, members.getNasal());
        values.put(MembersContract.MembersTable.COLUMN_HH_PERSONAL_COLID, members.getPersonal_colid());
        values.put(MembersContract.MembersTable.COLUMN_CLUSTER, members.getCluster());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                MembersContract.MembersTable.TABLE_NAME,
                MembersContract.MembersTable.COLUMN_ID,
                values);
        return newRowId;
    }


    //Update forms in DB


    //Sync functions
    public int syncUser(JSONArray userList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(UsersContract.UsersTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < userList.length(); i++) {

                JSONObject jsonObjectUser = userList.getJSONObject(i);

                UsersContract user = new UsersContract();
                user.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(UsersContract.UsersTable.ROW_USERNAME, user.getUserName());
                values.put(UsersContract.UsersTable.ROW_PASSWORD, user.getPassword());
                values.put(UsersContract.UsersTable.DIST_ID, user.getDIST_ID());
                long rowID = db.insert(UsersContract.UsersTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncUser(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public int syncDistrict(JSONArray distList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ClusterTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < distList.length(); i++) {

                JSONObject jsonObjectUser = distList.getJSONObject(i);

                ClustersContract dist = new ClustersContract();
                dist.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(ClusterTable.COLUMN_DIST_ID, dist.getDist_id());
                values.put(ClusterTable.COLUMN_SUB_DIST_NAME, dist.getSubdistrict());
                values.put(ClusterTable.COLUMN_DISTRICT_NAME, dist.getDistrict());
                values.put(ClusterTable.COLUMN_CLUSTER_ID, dist.getCluster_id());
                values.put(ClusterTable.COLUMN_SUB_DIST_ID, dist.getSub_dist_id());
                long rowID = db.insert(ClusterTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncDist(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public int syncEnumBlocks(JSONArray enumList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EnumBlockContract.EnumBlockTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < enumList.length(); i++) {
                JSONObject jsonObjectCC;
                try {
                    jsonObjectCC = enumList.getJSONObject(i);
                    EnumBlockContract Vc = new EnumBlockContract();
                    Vc.Sync(jsonObjectCC);

                    ContentValues values = new ContentValues();

                    values.put(EnumBlockContract.EnumBlockTable.COLUMN_DIST_ID, Vc.getDist_id());
                    values.put(EnumBlockContract.EnumBlockTable.COLUMN_GEO_AREA, Vc.getGeoarea());
                    values.put(EnumBlockContract.EnumBlockTable.COLUMN_CLUSTER_AREA, Vc.getCluster());

                    long rowID = db.insert(EnumBlockContract.EnumBlockTable.TABLE_NAME, null, values);
                    if (rowID != -1) insertCount++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "syncEnumBlocks(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    public int syncVersionApp(JSONObject versionList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(VersionAppContract.VersionAppTable.TABLE_NAME, null, null);
        long count = 0;
        try {
            JSONObject jsonObjectCC = ((JSONArray) versionList.get(VersionAppContract.VersionAppTable.COLUMN_VERSION_PATH)).getJSONObject(0);
            VersionAppContract Vc = new VersionAppContract();
            Vc.Sync(jsonObjectCC);

            ContentValues values = new ContentValues();

            values.put(VersionAppContract.VersionAppTable.COLUMN_PATH_NAME, Vc.getPathname());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_CODE, Vc.getVersioncode());
            values.put(VersionAppContract.VersionAppTable.COLUMN_VERSION_NAME, Vc.getVersionname());

            count = db.insert(VersionAppContract.VersionAppTable.TABLE_NAME, null, values);
            if (count > 0) count = 1;

        } catch (Exception ignored) {
        } finally {
            db.close();
        }

        return (int) count;
    }

    public int syncMembers(JSONArray membersList) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MembersContract.MembersTable.TABLE_NAME, null, null);
        int insertCount = 0;
        try {
            for (int i = 0; i < membersList.length(); i++) {

                JSONObject jsonObjectUser = membersList.getJSONObject(i);

                UsersContract user = new UsersContract();
                user.Sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(MembersContract.MembersTable.COLUMN_ID, user.getUserName());
                values.put(MembersContract.MembersTable.COLUMN_BLOOD, user.getPassword());
                values.put(MembersContract.MembersTable.COLUMN_HHID, user.getDIST_ID());
                values.put(MembersContract.MembersTable.COLUMN_HEAD, user.getDIST_ID());
                values.put(MembersContract.MembersTable.COLUMN_MEMBERID, user.getDIST_ID());
                values.put(MembersContract.MembersTable.COLUMN_MEMBERNAME, user.getDIST_ID());
                values.put(MembersContract.MembersTable.COLUMN_ADDRESS, user.getDIST_ID());
                values.put(MembersContract.MembersTable.COLUMN_NASAL, user.getDIST_ID());
                values.put(MembersContract.MembersTable.COLUMN_HH_PERSONAL_COLID, user.getDIST_ID());
                values.put(MembersContract.MembersTable.COLUMN_CLUSTER, user.getDIST_ID());
                long rowID = db.insert(MembersContract.MembersTable.TABLE_NAME, null, values);
                if (rowID != -1) insertCount++;
            }

        } catch (Exception e) {
            Log.d(TAG, "syncMembers(e): " + e);
            db.close();
        } finally {
            db.close();
        }
        return insertCount;
    }

    //Update from server response
    public void updateSyncedForms(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_SYNCED, true);
        values.put(FormsTable.COLUMN_SYNCED_DATE, new Date().toString());

        // Which row to update, based on the title
        String where = FormsTable._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                FormsTable.TABLE_NAME,
                values,
                where,
                whereArgs);
    }

    //Get Functions
    public int getListingCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SQL_COUNT_LISTINGS, null);
        int count = 0;

        while (cursor.moveToNext()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    public Collection<FormsContract> getTodayForms(String sysdate) {

        // String sysdate =  spDateT.substring(0, 8).trim()
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_FSTATUS,
                FormsTable.COLUMN_SYNCED,

        };
        String whereClause = FormsTable.COLUMN_SYSDATE + " Like ? ";
        String[] whereArgs = new String[]{"%" + sysdate + " %"};
//        String[] whereArgs = new String[]{"%" + spDateT.substring(0, 8).trim() + "%"};
        String groupBy = null;
        String having = null;

        String orderBy = FormsTable.COLUMN_ID + " DESC";

        Collection<FormsContract> allFC = new ArrayList<>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                fc.set_ID(c.getString(c.getColumnIndex(FormsTable.COLUMN_ID)));
                fc.set_UID(c.getString(c.getColumnIndex(FormsTable.COLUMN_UID)));
                fc.setFormDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_FORMDATE)));
                fc.setSysDate(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYSDATE)));
                fc.setClusterCode(c.getString(c.getColumnIndex(FormsTable.COLUMN_CLUSTERCODE)));
                fc.setHhno(c.getString(c.getColumnIndex(FormsTable.COLUMN_HHNO)));
                fc.setIstatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_ISTATUS)));
                fc.setfStatus(c.getString(c.getColumnIndex(FormsTable.COLUMN_FSTATUS)));
                fc.setSynced(c.getString(c.getColumnIndex(FormsTable.COLUMN_SYNCED)));
                allFC.add(fc);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<FormsContract> getUnsyncedForms() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                FormsTable._ID,
                FormsTable.COLUMN_UID,
                FormsTable.COLUMN_FORMDATE,
                FormsTable.COLUMN_SYSDATE,
                FormsTable.COLUMN_USER,
                FormsTable.COLUMN_ISTATUS,
                FormsTable.COLUMN_ISTATUS88x,
                FormsTable.COLUMN_FSTATUS,
                FormsTable.COLUMN_FSTATUS88x,
                FormsTable.COLUMN_LUID,
                FormsTable.COLUMN_ENDINGDATETIME,
                FormsTable.COLUMN_SINFO,
                FormsTable.COLUMN_SE,
                FormsTable.COLUMN_SM,
                FormsTable.COLUMN_SN,
                FormsTable.COLUMN_SO,
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_FORMTYPE
        };


        String whereClause = FormsTable.COLUMN_SYNCED + " is null AND " + FormsTable.COLUMN_ISTATUS + " != '' ";
        //String whereClause = FormsTable.COLUMN_ISTATUS +" != '' ";

        String[] whereArgs = null;

        String groupBy = null;
        String having = null;

        String orderBy =
                FormsTable.COLUMN_ID + " ASC";

        Collection<FormsContract> allFC = new ArrayList<FormsContract>();
        try {
            c = db.query(
                    FormsTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                FormsContract fc = new FormsContract();
                allFC.add(fc.Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allFC;
    }

    public Collection<PersonalContract> getUnsyncedPersonal() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                PersonalTable._ID,
                PersonalTable.COLUMN_UID,
                PersonalTable.COLUMN_SYSDATE,
                PersonalTable.COLUMN_A01,
                PersonalTable.COLUMN_A02,
                PersonalTable.COLUMN_A03,
                PersonalTable.COLUMN_HH12,
                PersonalTable.COLUMN_HH13,
                PersonalTable.COLUMN_UUID,
                PersonalTable.COLUMN_CSTATUS,
                PersonalTable.COLUMN_CSTATUS96x,
                PersonalTable.COLUMN_ENDINGDATETIME,
                PersonalTable.COLUMN_SA,
                PersonalTable.COLUMN_SB,
                PersonalTable.COLUMN_SC,
                PersonalTable.COLUMN_SI,
                PersonalTable.COLUMN_GPSLAT,
                PersonalTable.COLUMN_GPSLNG,
                PersonalTable.COLUMN_GPSDATE,
                PersonalTable.COLUMN_GPSACC,
                PersonalTable.COLUMN_DEVICETAGID,
                PersonalTable.COLUMN_DEVICEID,
                PersonalTable.COLUMN_APPVERSION,
        };


        String whereClause = PersonalTable.COLUMN_SYNCED + " is null OR " + PersonalTable.COLUMN_SYNCED + " == '' ";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = PersonalTable.COLUMN_ID + " ASC";

        Collection<PersonalContract> allPersonal = new ArrayList<>();
        try {
            c = db.query(
                    PersonalTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                PersonalContract personal = new PersonalContract();
                allPersonal.add(personal.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allPersonal;
    }

    public Collection<MembersContract> getUnsyncedMembers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                MembersContract.MembersTable.COLUMN_ID,
                MembersContract.MembersTable.COLUMN_BLOOD,
                MembersContract.MembersTable.COLUMN_HHID,
                MembersContract.MembersTable.COLUMN_HEAD,
                MembersContract.MembersTable.COLUMN_MEMBERID,
                MembersContract.MembersTable.COLUMN_MEMBERNAME,
                MembersContract.MembersTable.COLUMN_ADDRESS,
                MembersContract.MembersTable.COLUMN_NASAL,
                MembersContract.MembersTable.COLUMN_HH_PERSONAL_COLID,
                MembersContract.MembersTable.COLUMN_CLUSTER,
        };


        String whereClause = MembersContract.MembersTable.COLUMN_ID + " is null OR " + PersonalTable.COLUMN_SYNCED + " == '' ";
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = MembersContract.MembersTable.COLUMN_ID + " ASC";

        Collection<MembersContract> allMember = new ArrayList<>();
        try {
            c = db.query(
                    MembersContract.MembersTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                MembersContract members = new MembersContract();
                allMember.add(members.hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allMember;
    }

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[]{"mesage"};
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2 = new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try {
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(Query, null);

            //add value to cursor2
            Cursor2.addRow(new Object[]{"Success"});

            alc.set(1, Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0, c);
                c.moveToFirst();

                return alc;
            }
            return alc;
        } catch (Exception sqlEx) {
            Log.d("printing exception", Objects.requireNonNull(sqlEx.getMessage()));
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
            alc.set(1, Cursor2);
            return alc;
        }


    }

    public EnumBlockContract getEnumBlock(String cluster) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                EnumBlockTable._ID,
                EnumBlockTable.COLUMN_DIST_ID,
                EnumBlockTable.COLUMN_GEO_AREA,
                EnumBlockTable.COLUMN_CLUSTER_AREA
        };

        String whereClause = EnumBlockTable.COLUMN_CLUSTER_AREA + " =?";
        String[] whereArgs = new String[]{cluster};
        String groupBy = null;
        String having = null;

        String orderBy =
                EnumBlockTable._ID + " ASC";

        EnumBlockContract allEB = null;
        try {
            c = db.query(
                    EnumBlockTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allEB = new EnumBlockContract().HydrateEnum(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEB;
    }

    public VersionAppContract getVersionApp() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                VersionAppTable._ID,
                VersionAppTable.COLUMN_VERSION_CODE,
                VersionAppTable.COLUMN_VERSION_NAME,
                VersionAppTable.COLUMN_PATH_NAME
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = VersionAppTable._ID + " ASC";

        VersionAppContract allVC = new VersionAppContract();
        try {
            c = db.query(
                    VersionAppTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allVC.hydrate(c);
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allVC;
    }

    public List<EnumBlockContract> getEnumBlock() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                EnumBlockTable._ID,
                EnumBlockTable.COLUMN_DIST_ID,
                EnumBlockTable.COLUMN_GEO_AREA,
                EnumBlockTable.COLUMN_CLUSTER_AREA
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = EnumBlockTable._ID + " ASC";
        List<EnumBlockContract> allEB = new ArrayList<>();
        try {
            c = db.query(
                    EnumBlockTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allEB.add(new EnumBlockContract().HydrateEnum(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEB;
    }

    public List<ClustersContract> getDistrictSubDistrict() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                ClusterTable._ID,
                ClusterTable.COLUMN_DIST_ID,
                ClusterTable.COLUMN_SUB_DIST_NAME,
                ClusterTable.COLUMN_DISTRICT_NAME,
                ClusterTable.COLUMN_SUB_DIST_ID,
                ClusterTable.COLUMN_CLUSTER_ID,
        };

        String whereClause = null;
        String[] whereArgs = null;
        String groupBy = null;
        String having = null;

        String orderBy = ClusterTable._ID + " ASC";
        List<ClustersContract> allEB = new ArrayList<>();
        try {
            c = db.query(
                    ClusterTable.TABLE_NAME,  // The table to query
                    columns,                   // The columns to return
                    whereClause,               // The columns for the WHERE clause
                    whereArgs,                 // The values for the WHERE clause
                    groupBy,                   // don't group the rows
                    having,                    // don't filter by row groups
                    orderBy                    // The sort order
            );
            while (c.moveToNext()) {
                allEB.add(new ClustersContract().Hydrate(c));
            }
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return allEB;
    }
}