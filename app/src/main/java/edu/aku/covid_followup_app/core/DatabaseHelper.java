package edu.aku.covid_followup_app.core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import edu.aku.covid_followup_app.contracts.ClustersContract;
import edu.aku.covid_followup_app.contracts.ClustersContract.ClusterTable;
import edu.aku.covid_followup_app.contracts.FormsContract;
import edu.aku.covid_followup_app.contracts.FormsContract.FormsTable;
import edu.aku.covid_followup_app.contracts.MembersContract;
import edu.aku.covid_followup_app.contracts.MembersContract.MembersTable;
import edu.aku.covid_followup_app.contracts.PersonalContract;
import edu.aku.covid_followup_app.contracts.PersonalContract.PersonalTable;
import edu.aku.covid_followup_app.contracts.UsersContract;
import edu.aku.covid_followup_app.contracts.VersionAppContract;
import edu.aku.covid_followup_app.contracts.VersionAppContract.VersionAppTable;

import static edu.aku.covid_followup_app.core.MainApp.fc;
import static edu.aku.covid_followup_app.utils.DBUtilsKt.DATABASE_NAME;
import static edu.aku.covid_followup_app.utils.DBUtilsKt.DATABASE_VERSION;
import static edu.aku.covid_followup_app.utils.DBUtilsKt.SQL_COUNT_LISTINGS;
import static edu.aku.covid_followup_app.utils.DBUtilsKt.SQL_CREATE_BL_RANDOM;
import static edu.aku.covid_followup_app.utils.DBUtilsKt.SQL_CREATE_DISTRICTS;
import static edu.aku.covid_followup_app.utils.DBUtilsKt.SQL_CREATE_FORM_TABLE;
import static edu.aku.covid_followup_app.utils.DBUtilsKt.SQL_CREATE_PERSONALS_TABLE;
import static edu.aku.covid_followup_app.utils.DBUtilsKt.SQL_CREATE_USERS;
import static edu.aku.covid_followup_app.utils.DBUtilsKt.SQL_CREATE_VERSIONAPP;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = DatabaseHelper.class.getName();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FORM_TABLE);
        db.execSQL(SQL_CREATE_PERSONALS_TABLE);
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
        values.put(FormsTable.COLUMN_GPSLAT, fc.getGpsLat());
        values.put(FormsTable.COLUMN_GPSLNG, fc.getGpsLng());
        values.put(FormsTable.COLUMN_GPSDATE, fc.getGpsDT());
        values.put(FormsTable.COLUMN_GPSACC, fc.getGpsAcc());
        values.put(FormsTable.COLUMN_DEVICETAGID, fc.getDevicetagID());
        values.put(FormsTable.COLUMN_DEVICEID, fc.getDeviceID());
        values.put(FormsTable.COLUMN_APPVERSION, fc.getAppversion());
        values.put(FormsTable.COLUMN_CLUSTERCODE, fc.getClusterCode());
        values.put(FormsTable.COLUMN_HHNO, fc.getHhno());
        values.put(FormsTable.COLUMN_ADDRESS, fc.getHa11());

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
        values.put(PersonalTable.COLUMN_CLUSTERCODE, personal.getClusterCode());
        values.put(PersonalTable.COLUMN_HHNO, personal.getHhno());
        values.put(PersonalTable.COLUMN_UUID, personal.get_UUID());
        values.put(PersonalTable.COLUMN_CSTATUS, personal.getCstatus());
        values.put(PersonalTable.COLUMN_CSTATUS96x, personal.getCstatus96x());
        values.put(PersonalTable.COLUMN_ENDINGDATETIME, personal.getEndingdatetime());
        values.put(PersonalTable.COLUMN_SA, personal.getsA());
        values.put(PersonalTable.COLUMN_SC, personal.getsC());
        values.put(PersonalTable.COLUMN_FORMDATE, personal.getFormdate());
        values.put(PersonalTable.COLUMN_DEVICETAGID, personal.getDevicetagID());
        values.put(PersonalTable.COLUMN_DEVICEID, personal.getDeviceID());
        values.put(PersonalTable.COLUMN_APPVERSION, personal.getAppversion());
        values.put(PersonalTable.COLUMN_PA03, personal.getPa03());

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                PersonalTable.TABLE_NAME,
                PersonalTable.COLUMN_NAME_NULLABLE,
                values);
        return newRowId;
    }


    //Update forms in DB
    public int updatePersonalColumn(String column, String value, String valueID) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = PersonalTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(valueID)};

        return db.update(PersonalTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updateFormsColumn(String column, String value, String valueID) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(column, value);

        String selection = FormsTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(valueID)};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }

    public int updateEnding() {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(FormsTable.COLUMN_ISTATUS, fc.getIstatus());
        values.put(FormsTable.COLUMN_ISTATUS, fc.getIstatus88x());
        values.put(FormsTable.COLUMN_ENDINGDATETIME, fc.getEndingdatetime());

        // Which row to update, based on the ID
        String selection = FormsTable.COLUMN_ID + " =? ";
        String[] selectionArgs = {String.valueOf(fc.get_ID())};

        return db.update(FormsTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
    }


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

    public int syncCluster(JSONArray distList) {
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

                MembersContract mem = new MembersContract();
                mem.sync(jsonObjectUser);
                ContentValues values = new ContentValues();

                values.put(MembersContract.MembersTable.COLUMN_ID, mem.getId());
                values.put(MembersContract.MembersTable.COLUMN_BLOOD, mem.getBlood());
                values.put(MembersContract.MembersTable.COLUMN_HHID, mem.getHhid());
                values.put(MembersContract.MembersTable.COLUMN_HEAD, mem.getHead());
                values.put(MembersContract.MembersTable.COLUMN_MEMBERID, mem.getMemberid());
                values.put(MembersContract.MembersTable.COLUMN_MEMBERNAME, mem.getMembername());
                values.put(MembersContract.MembersTable.COLUMN_ADDRESS, mem.getAddress());
                values.put(MembersContract.MembersTable.COLUMN_NASAL, mem.getNasal());
                values.put(MembersContract.MembersTable.COLUMN_HH_PERSONAL_COLID, mem.getPersonal_colid());
                values.put(MembersContract.MembersTable.COLUMN_CLUSTER, mem.getCluster());
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

    public void updateSyncedPersonal(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(PersonalTable.COLUMN_SYNCED, true);
        values.put(PersonalTable.COLUMN_SYNCED_DATE, new Date().toString());

        // Which row to update, based on the title
        String where = PersonalTable._ID + " = ?";
        String[] whereArgs = {id};

        int count = db.update(
                PersonalTable.TABLE_NAME,
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
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_ADDRESS
        };


        String whereClause = FormsTable.COLUMN_SYNCED + " is null OR " + FormsTable.COLUMN_SYNCED + " == '' ";

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
                PersonalTable.COLUMN_CLUSTERCODE,
                PersonalTable.COLUMN_HHNO,
                PersonalTable.COLUMN_UUID,
                PersonalTable.COLUMN_CSTATUS,
                PersonalTable.COLUMN_CSTATUS96x,
                PersonalTable.COLUMN_ENDINGDATETIME,
                PersonalTable.COLUMN_SA,
                PersonalTable.COLUMN_SC,
                PersonalTable.COLUMN_FORMDATE,
                PersonalTable.COLUMN_DEVICETAGID,
                PersonalTable.COLUMN_DEVICEID,
                PersonalTable.COLUMN_APPVERSION,
                PersonalTable.COLUMN_PA03,
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

    public ArrayList<Cursor> getData(String query) {
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
            Cursor c = sqlDB.rawQuery(query, null);

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

    public FormsContract getExistingForm(String cluster, String hhno) {
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
                FormsTable.COLUMN_GPSLAT,
                FormsTable.COLUMN_GPSLNG,
                FormsTable.COLUMN_GPSDATE,
                FormsTable.COLUMN_GPSACC,
                FormsTable.COLUMN_DEVICETAGID,
                FormsTable.COLUMN_DEVICEID,
                FormsTable.COLUMN_APPVERSION,
                FormsTable.COLUMN_CLUSTERCODE,
                FormsTable.COLUMN_HHNO,
                FormsTable.COLUMN_ADDRESS
        };

        String whereClause = FormsTable.COLUMN_CLUSTERCODE + " =? AND " + FormsTable.COLUMN_HHNO + " =? AND " + FormsTable.COLUMN_ISTATUS + "=?";
        String[] whereArgs = {cluster, hhno, "8"};
        String groupBy = null;
        String having = null;
        String orderBy = FormsTable.COLUMN_ID + " ASC";

        FormsContract allFC = null;
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
                allFC = new FormsContract().Hydrate(c);
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

    public List<MembersContract> getHHAccordingToCluster(String cluster) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                MembersTable.COLUMN_ID,
                MembersTable.COLUMN_BLOOD,
                MembersTable.COLUMN_HHID,
                MembersTable.COLUMN_HEAD,
                MembersTable.COLUMN_MEMBERID,
                MembersTable.COLUMN_MEMBERNAME,
                MembersTable.COLUMN_ADDRESS,
                MembersTable.COLUMN_NASAL,
                MembersTable.COLUMN_HH_PERSONAL_COLID,
                MembersTable.COLUMN_CLUSTER,
        };


        String whereClause = MembersTable.COLUMN_CLUSTER + " =? ";
        String[] whereArgs = {cluster};
        String groupBy = null;
        String having = null;
        String orderBy = MembersTable.COLUMN_HEAD + " ASC";

        List<MembersContract> allMember = new ArrayList<>();
        try {
            c = db.query(
                    MembersTable.TABLE_NAME,  // The table to query
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

    public List<MembersContract> getMMAccordingToClusterHH(String cluster, String hh) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String[] columns = {
                MembersTable.COLUMN_ID,
                MembersTable.COLUMN_BLOOD,
                MembersTable.COLUMN_HHID,
                MembersTable.COLUMN_HEAD,
                MembersTable.COLUMN_MEMBERID,
                MembersTable.COLUMN_MEMBERNAME,
                MembersTable.COLUMN_ADDRESS,
                MembersTable.COLUMN_NASAL,
                MembersTable.COLUMN_HH_PERSONAL_COLID,
                MembersTable.COLUMN_CLUSTER,
        };


        String whereClause = MembersTable.COLUMN_CLUSTER + " =? AND " + MembersTable.COLUMN_HHID + "=?";
        String[] whereArgs = {cluster, hh};
        String groupBy = null;
        String having = null;
        String orderBy = MembersTable.COLUMN_MEMBERID + " ASC";

        List<MembersContract> allMember = new ArrayList<>();
        try {
            c = db.query(
                    MembersTable.TABLE_NAME,  // The table to query
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
}