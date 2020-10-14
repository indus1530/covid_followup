package edu.aku.covid_followup_app.contracts;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;


public class PersonalContract {
    private String _ID = "";
    private String _UID = "";
    private String _UUID = "";
    private String sysdate = "";
    private String hh12 = ""; // Cluster
    private String hh13 = ""; // HHNo
    private String cstatus = ""; // Interview Status
    private String cstatus96x = ""; // Interview Status
    private String endingdatetime = "";
    private String formdate = "";
    private String deviceID = "";
    private String devicetagID = "";
    private String synced = "";
    private String synced_date = "";
    private String appversion = "";
    private String sA = "";
    private String sC = "";

    //Not in DB
    private String formFlag;


    public String getFormFlag() {
        return formFlag;
    }

    public void setFormFlag(String formFlag) {
        this.formFlag = formFlag;
    }


    public String getSysdate() {
        return sysdate;
    }

    public void setSysdate(String sysdate) {
        this.sysdate = sysdate;
    }


    public String getHh12() {
        return hh12;
    }

    public void setHh12(String hh12) {
        this.hh12 = hh12;
    }


    public String getHh13() {
        return hh13;
    }

    public void setHh13(String hh13) {
        this.hh13 = hh13;
    }


    public String getsA() {
        return sA;
    }

    public void setsA(String sA) {
        this.sA = sA;
    }



    public String getsC() {
        return sC;
    }

    public void setsC(String sC) {
        this.sC = sC;
    }



    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }


    public String getProjectName() {
        return "Covid Sero Followup";
    }


    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }


    public String get_UID() {
        return _UID;
    }

    public void set_UID(String _UID) {
        this._UID = _UID;
    }


    public String getCstatus() {
        return cstatus;
    }

    public void setCstatus(String cstatus) {
        this.cstatus = cstatus;
    }


    public String getCstatus96x() {
        return cstatus96x;
    }

    public void setCstatus96x(String cstatus96x) {
        this.cstatus96x = cstatus96x;
    }


    public String getEndingdatetime() {
        return endingdatetime;
    }

    public void setEndingdatetime(String endingdatetime) {
        this.endingdatetime = endingdatetime;
    }


    public String getFormdate() {
        return formdate;
    }

    public void setFormdate(String formdate) {
        this.formdate = formdate;
    }


    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }


    public String getDevicetagID() {
        return devicetagID;
    }

    public void setDevicetagID(String devicetagID) {
        this.devicetagID = devicetagID;
    }


    public String getSynced() {
        return synced;
    }

    public void setSynced(String synced) {
        this.synced = synced;
    }


    public String getSynced_date() {
        return synced_date;
    }

    public void setSynced_date(String synced_date) {
        this.synced_date = synced_date;
    }


    public String get_UUID() {
        return _UUID;
    }

    public void set_UUID(String _UUID) {
        this._UUID = _UUID;
    }


    public PersonalContract sync(JSONObject jsonObject) throws JSONException {
        this._ID = jsonObject.getString(PersonalTable.COLUMN_ID);
        this._UID = jsonObject.getString(PersonalTable.COLUMN_UID);
        this.sysdate = jsonObject.getString(PersonalTable.COLUMN_SYSDATE);
        this.hh12 = jsonObject.getString(PersonalTable.COLUMN_HH12);
        this.hh13 = jsonObject.getString(PersonalTable.COLUMN_HH13);
        this._UUID = jsonObject.getString(PersonalTable.COLUMN_UUID);
        this.cstatus = jsonObject.getString(PersonalTable.COLUMN_CSTATUS);
        this.cstatus96x = jsonObject.getString(PersonalTable.COLUMN_CSTATUS96x);
        this.endingdatetime = jsonObject.getString(PersonalTable.COLUMN_ENDINGDATETIME);
        this.formdate = jsonObject.getString(PersonalTable.COLUMN_FORMDATE);
        this.deviceID = jsonObject.getString(PersonalTable.COLUMN_DEVICEID);
        this.devicetagID = jsonObject.getString(PersonalTable.COLUMN_DEVICETAGID);
        this.synced = jsonObject.getString(PersonalTable.COLUMN_SYNCED);
        this.synced_date = jsonObject.getString(PersonalTable.COLUMN_SYNCED_DATE);
        this.appversion = jsonObject.getString(PersonalTable.COLUMN_SYNCED_DATE);
        this.sA = jsonObject.getString(PersonalTable.COLUMN_SA);
        this.sC = jsonObject.getString(PersonalTable.COLUMN_SC);

        return this;
    }



    public PersonalContract hydrate(Cursor cursor) {
        this._ID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_ID));
        this._UID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_UID));
        this.sysdate = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SYSDATE));
        this.hh12 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_HH12));
        this.hh13 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_HH13));
        this._UUID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_UUID));
        this.cstatus = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_CSTATUS));
        this.cstatus96x = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_CSTATUS96x));
        this.endingdatetime = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_ENDINGDATETIME));
        this.formdate = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_FORMDATE));
        this.deviceID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_DEVICEID));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_DEVICETAGID));
        this.appversion = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_APPVERSION));
        this.sA = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SA));
        this.sC = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SC));


        return this;
    }

    //TODO: Try this instead of toJSONObject
    @NotNull
    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this, PersonalContract.class);
    }

    public JSONObject toJSONObject() {

        JSONObject json = new JSONObject();

        try {
            json.put(PersonalTable.COLUMN_ID, this._ID == null ? JSONObject.NULL : this._ID);
            json.put(PersonalTable.COLUMN_SYSDATE, this.sysdate == null ? JSONObject.NULL : this.sysdate);
            json.put(PersonalTable.COLUMN_UID, this._UID == null ? JSONObject.NULL : this._UID);
            json.put(PersonalTable.COLUMN_HH12, this.hh12 == null ? JSONObject.NULL : this.hh12);
            json.put(PersonalTable.COLUMN_HH13, this.hh13 == null ? JSONObject.NULL : this.hh13);
            json.put(PersonalTable.COLUMN_UUID, this._UUID == null ? JSONObject.NULL : this._UUID);
            json.put(PersonalTable.COLUMN_CSTATUS, this.cstatus == null ? JSONObject.NULL : this.cstatus);
            json.put(PersonalTable.COLUMN_CSTATUS96x, this.cstatus96x == null ? JSONObject.NULL : this.cstatus96x);
            json.put(PersonalTable.COLUMN_ENDINGDATETIME, this.endingdatetime == null ? JSONObject.NULL : this.endingdatetime);

            if (this.sA != null && !this.sA.equals("")) {
                json.put(PersonalTable.COLUMN_SA, new JSONObject(this.sA));
            }
            if (this.sC != null && !this.sC.equals("")) {
                json.put(PersonalTable.COLUMN_SC, new JSONObject(this.sC));
            }

            json.put(PersonalTable.COLUMN_FORMDATE, this.formdate == null ? JSONObject.NULL : this.formdate);
            json.put(PersonalTable.COLUMN_DEVICEID, this.deviceID == null ? JSONObject.NULL : this.deviceID);
            json.put(PersonalTable.COLUMN_DEVICETAGID, this.devicetagID == null ? JSONObject.NULL : this.devicetagID);
            json.put(PersonalTable.COLUMN_APPVERSION, this.appversion == null ? JSONObject.NULL : this.appversion);

            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static abstract class PersonalTable implements BaseColumns {
        public static final String TABLE_NAME = "personals_r2";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_HH12 = "hh12";
        public static final String COLUMN_HH13 = "hh13";
        public static final String COLUMN_CSTATUS = "cstatus";
        public static final String COLUMN_CSTATUS96x = "cstatus96x";
        public static final String COLUMN_ENDINGDATETIME = "endingdatetime";
        public static final String COLUMN_FORMDATE = "formdate";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "tagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_SA = "sA";    // personal member
        public static final String COLUMN_SC = "sC";    // personal member

    }
}
