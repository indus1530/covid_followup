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
    private String a01 = ""; // Date
    private String a02 = ""; // Time
    private String a03 = ""; // Interviewer
    private String hh12 = ""; // Cluster
    private String hh13 = ""; // HHNo
    private String cstatus = ""; // Interview Status
    private String cstatus96x = ""; // Interview Status
    private String endingdatetime = "";
    private String gpsLat = "";
    private String gpsLng = "";
    private String gpsDT = "";
    private String gpsAcc = "";
    private String deviceID = "";
    private String devicetagID = "";
    private String synced = "";
    private String synced_date = "";
    private String appversion = "";
    private String sA = "";
    private String sB = "";
    private String sC = "";
    private String sI = "";

    //Not in DB
    private String memberName;
    private String memberSerial;
    private String gender;
    private String agey;
    private String agem;
    private String cluster;
    private String hhno;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }


    public String getMemberSerial() {
        return memberSerial;
    }

    public void setMemberSerial(String memberSerial) {
        this.memberSerial = memberSerial;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getAgey() {
        return agey;
    }

    public void setAgey(String agey) {
        this.agey = agey;
    }


    public String getAgem() {
        return agem;
    }

    public void setAgem(String agem) {
        this.agem = agem;
    }


    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }


    public String getHhno() {
        return hhno;
    }

    public void setHhno(String hhno) {
        this.hhno = hhno;
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


    public String getsB() {
        return sB;
    }

    public void setsB(String sB) {
        this.sB = sB;
    }


    public String getsC() {
        return sC;
    }

    public void setsC(String sC) {
        this.sC = sC;
    }


    public String getsI() {
        return sI;
    }

    public void setsI(String sI) {
        this.sI = sI;
    }


    public String getAppversion() {
        return appversion;
    }

    public void setAppversion(String appversion) {
        this.appversion = appversion;
    }


    public String getProjectName() {
        return "covid_sero";
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


    public String getA01() {
        return a01;
    }

    public void setA01(String a01) {
        this.a01 = a01;
    }


    public String getA02() {
        return a02;
    }

    public void setA02(String a02) {
        this.a02 = a02;
    }


    public String getA03() {
        return a03;
    }

    public void setA03(String a03) {
        this.a03 = a03;
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


    public String getGpsLat() {
        return gpsLat;
    }

    public void setGpsLat(String gpsLat) {
        this.gpsLat = gpsLat;
    }


    public String getGpsLng() {
        return gpsLng;
    }

    public void setGpsLng(String gpsLng) {
        this.gpsLng = gpsLng;
    }


    public String getGpsDT() {
        return gpsDT;
    }

    public void setGpsDT(String gpsDT) {
        this.gpsDT = gpsDT;
    }


    public String getGpsAcc() {
        return gpsAcc;
    }

    public void setGpsAcc(String gpsAcc) {
        this.gpsAcc = gpsAcc;
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
        this.a01 = jsonObject.getString(PersonalTable.COLUMN_A01);
        this.a02 = jsonObject.getString(PersonalTable.COLUMN_A02);
        this.a03 = jsonObject.getString(PersonalTable.COLUMN_A03);
        this.hh12 = jsonObject.getString(PersonalTable.COLUMN_HH12);
        this.hh13 = jsonObject.getString(PersonalTable.COLUMN_HH13);
        this._UUID = jsonObject.getString(PersonalTable.COLUMN_UUID);
        this.cstatus = jsonObject.getString(PersonalTable.COLUMN_CSTATUS);
        this.cstatus96x = jsonObject.getString(PersonalTable.COLUMN_CSTATUS96x);
        this.endingdatetime = jsonObject.getString(PersonalTable.COLUMN_ENDINGDATETIME);
        this.gpsLat = jsonObject.getString(PersonalTable.COLUMN_GPSLAT);
        this.gpsLng = jsonObject.getString(PersonalTable.COLUMN_GPSLNG);
        this.gpsDT = jsonObject.getString(PersonalTable.COLUMN_GPSDATE);
        this.gpsAcc = jsonObject.getString(PersonalTable.COLUMN_GPSACC);
        this.deviceID = jsonObject.getString(PersonalTable.COLUMN_DEVICEID);
        this.devicetagID = jsonObject.getString(PersonalTable.COLUMN_DEVICETAGID);
        this.synced = jsonObject.getString(PersonalTable.COLUMN_SYNCED);
        this.synced_date = jsonObject.getString(PersonalTable.COLUMN_SYNCED_DATE);
        this.appversion = jsonObject.getString(PersonalTable.COLUMN_SYNCED_DATE);
        this.sA = jsonObject.getString(PersonalTable.COLUMN_SA);
        this.sB = jsonObject.getString(PersonalTable.COLUMN_SB);
        this.sC = jsonObject.getString(PersonalTable.COLUMN_SC);
        this.sI = jsonObject.getString(PersonalTable.COLUMN_SI);

        return this;
    }

    public PersonalContract hydrate(Cursor cursor) {
        this._ID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_ID));
        this._UID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_UID));
        this.sysdate = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SYSDATE));
        this.a01 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_A01));
        this.a02 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_A02));
        this.a03 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_A03));
        this.hh12 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_HH12));
        this.hh13 = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_HH13));
        this._UUID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_UUID));
        this.cstatus = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_CSTATUS));
        this.cstatus96x = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_CSTATUS96x));
        this.endingdatetime = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_ENDINGDATETIME));
        this.gpsLat = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_GPSLAT));
        this.gpsLng = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_GPSLNG));
        this.gpsDT = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_GPSDATE));
        this.gpsAcc = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_GPSACC));
        this.deviceID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_DEVICEID));
        this.devicetagID = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_DEVICETAGID));
        this.appversion = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_APPVERSION));
        this.sA = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SA));
        this.sB = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SB));
        this.sC = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SC));
        this.sI = cursor.getString(cursor.getColumnIndex(PersonalTable.COLUMN_SI));


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
            json.put(PersonalTable.COLUMN_A01, this.a01 == null ? JSONObject.NULL : this.a01);
            json.put(PersonalTable.COLUMN_A02, this.a02 == null ? JSONObject.NULL : this.a02);
            json.put(PersonalTable.COLUMN_A03, this.a03 == null ? JSONObject.NULL : this.a03);
            json.put(PersonalTable.COLUMN_HH12, this.hh12 == null ? JSONObject.NULL : this.hh12);
            json.put(PersonalTable.COLUMN_HH13, this.hh13 == null ? JSONObject.NULL : this.hh13);
            json.put(PersonalTable.COLUMN_UUID, this._UUID == null ? JSONObject.NULL : this._UUID);
            json.put(PersonalTable.COLUMN_CSTATUS, this.cstatus == null ? JSONObject.NULL : this.cstatus);
            json.put(PersonalTable.COLUMN_CSTATUS96x, this.cstatus96x == null ? JSONObject.NULL : this.cstatus96x);
            json.put(PersonalTable.COLUMN_ENDINGDATETIME, this.endingdatetime == null ? JSONObject.NULL : this.endingdatetime);

            if (this.sA != null && !this.sA.equals("")) {
                json.put(PersonalTable.COLUMN_SA, new JSONObject(this.sA));
            }
            if (this.sB != null && !this.sB.equals("")) {
                json.put(PersonalTable.COLUMN_SB, new JSONObject(this.sB));
            }
            if (this.sC != null && !this.sC.equals("")) {
                json.put(PersonalTable.COLUMN_SC, new JSONObject(this.sC));
            }
            if (this.sI != null && !this.sI.equals("")) {
                json.put(PersonalTable.COLUMN_SI, new JSONObject(this.sI));
            }

            json.put(PersonalTable.COLUMN_GPSLAT, this.gpsLat == null ? JSONObject.NULL : this.gpsLat);
            json.put(PersonalTable.COLUMN_GPSLNG, this.gpsLng == null ? JSONObject.NULL : this.gpsLng);
            json.put(PersonalTable.COLUMN_GPSDATE, this.gpsDT == null ? JSONObject.NULL : this.gpsDT);
            json.put(PersonalTable.COLUMN_GPSACC, this.gpsAcc == null ? JSONObject.NULL : this.gpsAcc);
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
        public static final String TABLE_NAME = "personals";
        public static final String COLUMN_NAME_NULLABLE = "NULLHACK";
        public static final String COLUMN_PROJECT_NAME = "projectName";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_UID = "_uid";
        public static final String COLUMN_UUID = "_uuid";
        public static final String COLUMN_SYSDATE = "sysdate";
        public static final String COLUMN_A01 = "a01";
        public static final String COLUMN_A02 = "a02";
        public static final String COLUMN_A03 = "a03";
        public static final String COLUMN_HH12 = "hh12";
        public static final String COLUMN_HH13 = "hh13";
        public static final String COLUMN_CSTATUS = "cstatus";
        public static final String COLUMN_CSTATUS96x = "cstatus96x";
        public static final String COLUMN_ENDINGDATETIME = "endingdatetime";
        public static final String COLUMN_GPSLAT = "gpslat";
        public static final String COLUMN_GPSLNG = "gpslng";
        public static final String COLUMN_GPSDATE = "gpsdate";
        public static final String COLUMN_GPSACC = "gpsacc";
        public static final String COLUMN_DEVICEID = "deviceid";
        public static final String COLUMN_DEVICETAGID = "tagid";
        public static final String COLUMN_SYNCED = "synced";
        public static final String COLUMN_SYNCED_DATE = "synced_date";
        public static final String COLUMN_APPVERSION = "appversion";
        public static final String COLUMN_SA = "sA";    // personal member
        public static final String COLUMN_SB = "sB";    // personal member
        public static final String COLUMN_SC = "sC";    // personal member
        public static final String COLUMN_SI = "sI";    // personal member

    }
}
