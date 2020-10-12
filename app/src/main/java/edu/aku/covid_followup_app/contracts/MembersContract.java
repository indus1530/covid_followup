package edu.aku.covid_followup_app.contracts;


import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class MembersContract implements Serializable {

    private static final String TAG = MembersContract.class.getName();
    private String id;
    private String hhid;
    private String head;
    private String memberid;
    private String membername;
    private String address;
    private String blood;
    private String nasal;
    private String personal_colid;
    private String cluster;

    public MembersContract() {
    }

    public MembersContract sync(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString(MembersTable.COLUMN_ID);
        this.hhid = jsonObject.getString(MembersTable.COLUMN_HHID);
        this.head = jsonObject.getString(MembersTable.COLUMN_HEAD);
        this.memberid = jsonObject.getString(MembersTable.COLUMN_MEMBERID);
        this.membername = jsonObject.getString(MembersTable.COLUMN_MEMBERNAME);
        this.blood = jsonObject.getString(MembersTable.COLUMN_BLOOD);
        this.address = jsonObject.getString(MembersTable.COLUMN_ADDRESS);
        this.nasal = jsonObject.getString(MembersTable.COLUMN_NASAL);
        this.personal_colid = jsonObject.getString(MembersTable.COLUMN_HH_PERSONAL_COLID);
        this.cluster = jsonObject.getString(MembersTable.COLUMN_CLUSTER);

        return this;
    }

    public MembersContract hydrate(Cursor cursor) {
        this.id = cursor.getString(cursor.getColumnIndex(MembersTable.COLUMN_ID));
        this.hhid = cursor.getString(cursor.getColumnIndex(MembersTable.COLUMN_HHID));
        this.head = cursor.getString(cursor.getColumnIndex(MembersTable.COLUMN_HEAD));
        this.memberid = cursor.getString(cursor.getColumnIndex(MembersTable.COLUMN_MEMBERID));
        this.membername = cursor.getString(cursor.getColumnIndex(MembersTable.COLUMN_MEMBERNAME));
        this.blood = cursor.getString(cursor.getColumnIndex(MembersTable.COLUMN_BLOOD));
        this.address = cursor.getString(cursor.getColumnIndex(MembersTable.COLUMN_ADDRESS));
        this.nasal = cursor.getString(cursor.getColumnIndex(MembersTable.COLUMN_NASAL));
        this.personal_colid = cursor.getString(cursor.getColumnIndex(MembersTable.COLUMN_HH_PERSONAL_COLID));
        this.cluster = cursor.getString(cursor.getColumnIndex(MembersTable.COLUMN_CLUSTER));
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHhid() {
        return hhid;
    }

    public void setHhid(String hhid) {
        this.hhid = hhid;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNasal() {
        return nasal;
    }

    public void setNasal(String nasal) {
        this.nasal = nasal;
    }

    public String getPersonal_colid() {
        return personal_colid;
    }

    public void setPersonal_colid(String personal_colid) {
        this.personal_colid = personal_colid;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public static abstract class MembersTable implements BaseColumns {

        public static final String TABLE_NAME = "members";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_BLOOD = "blood";
        public static final String COLUMN_HHID = "hhid";
        public static final String COLUMN_HEAD = "head";
        public static final String COLUMN_MEMBERID = "memberid";
        public static final String COLUMN_MEMBERNAME = "membername";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_NASAL = "nasal";
        public static final String COLUMN_HH_PERSONAL_COLID = "personal_colid";
        public static final String COLUMN_CLUSTER = "cluster";
    }
}