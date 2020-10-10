package edu.aku.covid_followup_app.contracts;


import android.database.Cursor;
import android.provider.BaseColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class ClustersContract {

    private static final String TAG = ClustersContract.class.getName();
    String dist_id;
    String subdistrict;
    String district;
    String sub_dist_id;
    String cluster_id;

    public ClustersContract() {
        // Default Constructor
    }

    public ClustersContract Sync(JSONObject jsonObject) throws JSONException {
        this.dist_id = jsonObject.getString(ClusterTable.COLUMN_DIST_ID);
        this.subdistrict = jsonObject.getString(ClusterTable.COLUMN_SUB_DIST_NAME);
        this.district = jsonObject.getString(ClusterTable.COLUMN_DISTRICT_NAME);
        this.cluster_id = jsonObject.getString(ClusterTable.COLUMN_CLUSTER_ID);
        this.sub_dist_id = jsonObject.getString(ClusterTable.COLUMN_SUB_DIST_ID);
        return this;
    }

    public ClustersContract Hydrate(Cursor cursor) {
        this.dist_id = cursor.getString(cursor.getColumnIndex(ClusterTable.COLUMN_DIST_ID));
        this.subdistrict = cursor.getString(cursor.getColumnIndex(ClusterTable.COLUMN_SUB_DIST_NAME));
        this.district = cursor.getString(cursor.getColumnIndex(ClusterTable.COLUMN_DISTRICT_NAME));
        this.cluster_id = cursor.getString(cursor.getColumnIndex(ClusterTable.COLUMN_CLUSTER_ID));
        this.sub_dist_id = cursor.getString(cursor.getColumnIndex(ClusterTable.COLUMN_SUB_DIST_ID));
        return this;
    }

    public String getDist_id() {
        return dist_id;
    }

    public void setDist_id(String dist_id) {
        this.dist_id = dist_id;
    }

    public String getSubdistrict() {
        return subdistrict;
    }

    public void setSubdistrict(String subdistrict) {
        this.subdistrict = subdistrict;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSub_dist_id() {
        return sub_dist_id;
    }

    public void setSub_dist_id(String sub_dist_id) {
        this.sub_dist_id = sub_dist_id;
    }

    public String getCluster_id() {
        return cluster_id;
    }

    public void setCluster_id(String cluster_id) {
        this.cluster_id = cluster_id;
    }

    public JSONObject toJSONObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(ClusterTable.COLUMN_DIST_ID, this.dist_id == null ? JSONObject.NULL : this.dist_id);
        json.put(ClusterTable.COLUMN_SUB_DIST_NAME, this.subdistrict == null ? JSONObject.NULL : this.subdistrict);
        json.put(ClusterTable.COLUMN_DISTRICT_NAME, this.district == null ? JSONObject.NULL : this.district);
        json.put(ClusterTable.COLUMN_SUB_DIST_ID, this.sub_dist_id == null ? JSONObject.NULL : this.sub_dist_id);
        json.put(ClusterTable.COLUMN_CLUSTER_ID, this.cluster_id == null ? JSONObject.NULL : this.cluster_id);
        return json;
    }


    public static abstract class ClusterTable implements BaseColumns {

        public static final String TABLE_NAME = "clusters";

        public static final String COLUMN_DIST_ID = "dist_id";
        public static final String COLUMN_SUB_DIST_NAME = "sub_dist_name";
        public static final String COLUMN_DISTRICT_NAME = "dist_name";
        public static final String COLUMN_CLUSTER_ID = "cluster";
        public static final String COLUMN_SUB_DIST_ID = "sub_dist_id";
    }
}