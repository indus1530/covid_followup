package edu.aku.covid_followup_app.activities.other;

import android.app.ActivityManager;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import edu.aku.covid_followup_app.CONSTANTS;
import edu.aku.covid_followup_app.R;
import edu.aku.covid_followup_app.activities.list.InfoActivity;
import edu.aku.covid_followup_app.activities.menu.MenuActivity;
import edu.aku.covid_followup_app.contracts.ClustersContract;
import edu.aku.covid_followup_app.contracts.FormsContract;
import edu.aku.covid_followup_app.contracts.VersionAppContract;
import edu.aku.covid_followup_app.core.AndroidDatabaseManager;
import edu.aku.covid_followup_app.core.MainApp;
import edu.aku.covid_followup_app.databinding.ActivityMainBinding;
import edu.aku.covid_followup_app.utils.AndroidUtilityKt;
import edu.aku.covid_followup_app.utils.OtherUtilsKt;
import edu.aku.covid_followup_app.utils.WarningActivityInterface;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;

import static edu.aku.covid_followup_app.CONSTANTS.REQUEST_APP_UPDATE;
import static edu.aku.covid_followup_app.core.MainApp.appInfo;
import static edu.aku.covid_followup_app.utils.DBUtilsKt.DATABASE_NAME;
import static edu.aku.covid_followup_app.utils.SplashUtilsKt.populatingSpinners;

public class MainActivity extends MenuActivity implements WarningActivityInterface {

    public static String TAG = MainActivity.class.getName();
    private static File file;
    ArrayAdapter<String> provinceAdapter;
    private Boolean exit = false;
    private String dtToday = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
    private String sysdateToday = new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(new Date());
    private DownloadManager downloadManager;
    private SharedPreferences sharedPrefDownload;
    private SharedPreferences.Editor editorDownload;
    private ActivityMainBinding bi;
    private String preVer = "", newVer = "";
    private ClustersContract selectedCluster;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(sharedPrefDownload.getLong("refID", 0));

                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                assert downloadManager != null;
                Cursor cursor = downloadManager.query(query);
                if (cursor.moveToFirst()) {
                    int colIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                    if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(colIndex)) {

                        editorDownload.putBoolean("flag", true);
                        editorDownload.commit();

                        Toast.makeText(context, "New App downloaded", Toast.LENGTH_SHORT).show();
                        bi.lblAppVersion.setText(new StringBuilder(getString(R.string.app_name) + " App New Version ").append(newVer).append("  Downloaded"));

                        ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
                        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

                        if (Objects.requireNonNull(taskInfo.get(0).topActivity).getClassName().equals(MainActivity.class.getName())) {
                            showDialog(newVer, preVer);
                        }
                    }
                }
            }
        }
    };

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem dbManager = menu.findItem(R.id.menu_openDB);
        dbManager.setVisible(MainApp.admin);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bi.setCallback(this);
        bi.adminBlock.setVisibility(MainApp.admin ? View.VISIBLE : View.GONE);

        //Database handler
        Collection<FormsContract> todaysForms = appInfo.getDbHelper().getTodayForms(sysdateToday);
        Collection<FormsContract> unsyncedForms = appInfo.getDbHelper().getUnsyncedForms();

        StringBuilder rSumText = new StringBuilder()
                .append("TODAY'S RECORDS SUMMARY\r\n")
                .append("=======================\r\n")
                .append("\r\n")
                .append("Total Forms Today" + "(").append(dtToday).append("): ").append(todaysForms.size()).append("\r\n");
        if (todaysForms.size() > 0) {
            String iStatus;
            rSumText.append("---------------------------------------------------------\r\n")
                    .append("[Cluster][Household][Form Status][Sync Status]\r\n")
                    .append("---------------------------------------------------------\r\n");

            for (FormsContract fc : todaysForms) {
                Log.d(TAG, "onCreate: '" + fc.getIstatus() + "'");
                switch (fc.getIstatus()) {
                    case "1":
                        iStatus = "Complete";
                        break;
                    case "2":
                        iStatus = "No Resp";
                        break;
                    case "3":
                        iStatus = "Empty";
                        break;
                    case "4":
                        iStatus = "Refused";
                        break;
                    case "5":
                        iStatus = "Non Res.";
                        break;
                    case "6":
                        iStatus = "Not Found";
                        break;
                    case "96":
                        iStatus = "Other";
                        break;
                    case "":
                        iStatus = "Open";
                        break;
                    default:
                        iStatus = "\t\tN/A" + fc.getIstatus();
                }

                rSumText.append(fc.getClusterCode())
                        .append(fc.getHhno())
                        .append("\t\t\t\t")
                        .append(iStatus)
                        .append("\t\t\t\t")
                        .append(fc.getSynced() == null ? "Not Synced" : "Synced")
                        .append("\r\n")
                        .append("---------------------------------------------------------\r\n");
            }
        }
        SharedPreferences syncPref = getSharedPreferences("src", Context.MODE_PRIVATE);
        rSumText.append("\r\nDEVICE INFORMATION\r\n")
                .append("  ========================================================\r\n")
                .append("\t|| Unsynced Forms: \t\t\t\t").append(String.format(Locale.getDefault(), "%02d", unsyncedForms.size()))
                .append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t||\r\n")
                .append("\t|| Last Data Download: \t\t").append(syncPref.getString("LastDataDownload", "Never Downloaded   "))
                .append("\t\t\t\t\t\t||\r\n")
                .append("\t|| Last Data Upload: \t\t\t").append(syncPref.getString("LastDataUpload", "Never Uploaded     "))
                .append("\t\t\t\t\t\t||\r\n")
                .append("\t========================================================\r\n");
        bi.recordSummary.setText(rSumText);

        // Auto download app
        sharedPrefDownload = getSharedPreferences("appDownload", MODE_PRIVATE);
        editorDownload = sharedPrefDownload.edit();
        VersionAppContract versionAppContract = appInfo.getDbHelper().getVersionApp();
        if (versionAppContract.getVersioncode() != null) {
            preVer = appInfo.getVersionName() + "." + appInfo.getVersionCode();
            newVer = versionAppContract.getVersionname() + "." + versionAppContract.getVersioncode();
            if (appInfo.getVersionCode() < Integer.parseInt(versionAppContract.getVersioncode())) {
                bi.lblAppVersion.setVisibility(View.VISIBLE);

                String fileName = DATABASE_NAME.replace(".db", "_New_Apps");
                file = new File(Environment.getDataDirectory() + File.separator + fileName, versionAppContract.getPathname());

                if (file.exists()) {
                    bi.lblAppVersion.setText(new StringBuilder(getString(R.string.app_name)).append("\nNew Ver.").append(newVer).append("  is downloaded. Kindly accept the installation prompt."));
                    showDialog(newVer, preVer);
                } else {
                    if (!AndroidUtilityKt.isNetworkConnected(this)) {
                        bi.lblAppVersion.setText(new StringBuilder(getString(R.string.app_name)).append(" App New Ver.").append(newVer).append("  is available..\n(Couldn't able to download due to Internet connectivity issue!!)"));
                        return;
                    }
                    bi.lblAppVersion.setText(new StringBuilder(getString(R.string.app_name)).append(" App \nNew Ver.").append(newVer).append("  is downloading.."));
                    downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    Uri uri = Uri.parse(MainApp._UPDATE_URL + versionAppContract.getPathname());
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.setDestinationInExternalPublicDir(fileName, versionAppContract.getPathname())
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setTitle("Downloading updated version of " + getString(R.string.app_name) + " Ver." + newVer);
                    long refID = downloadManager.enqueue(request);
                    editorDownload.putLong("refID", refID);
                    editorDownload.putBoolean("flag", false);
                    editorDownload.apply();
                }

            } else {
                bi.lblAppVersion.setVisibility(View.GONE);
                bi.lblAppVersion.setText(null);
            }
        }
        registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

//        Testing visibility
        if (Integer.parseInt(appInfo.getVersionName().split("\\.")[0]) > 0)
            bi.testing.setVisibility(View.GONE);
        else bi.testing.setVisibility(View.VISIBLE);

        setUIContent();
        setListeners();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void callWarningActivity(int id) {
        if (id == REQUEST_APP_UPDATE) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        if (exit) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(() -> exit = false, 3 * 1000);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        callingCoroutine();
    }


    //Screen Buttons
    public void OpenFormClicked(View view) {
        startActivity(new Intent(this, InfoActivity.class).putExtra(CONSTANTS.CLUSTER_INFO, selectedCluster));
    }

    public void OpenDBManagerBtn(View view) {
        startActivity(new Intent(this, AndroidDatabaseManager.class));
    }

    public void CopyDataToFileBtn(View view) {
        new CopyTask(this).execute();
    }


    //TextWatchers


    //Other Dependent Functions
    private void setUIContent() {

    }

    private void showDialog(String newVer, String preVer) {
        OtherUtilsKt.openWarningActivity(
                this,
                REQUEST_APP_UPDATE,
                getString(R.string.app_name) + " APP is available!",
                getString(R.string.app_name) + " App Ver." + newVer + " is now available. Your are currently using older Ver." + preVer + ".\nInstall new version to use this app.",
                "Install",
                "Cancel"
        );
    }

    private void setListeners() {
        provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, SplashscreenActivity.districts);
        bi.spinnerProvince.setAdapter(provinceAdapter);
        bi.spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> districts = new ArrayList<>(Collections.singletonList("...."));
                bi.openFormBtn.setClickable(false);
                if (position == 0) return;
                for (Map.Entry<String, Pair<String, ClustersContract>> entry : SplashscreenActivity.subDistrictsMap.entrySet()) {
                    if (entry.getValue().getFirst().equals(bi.spinnerProvince.getSelectedItem().toString()))
                        districts.add(entry.getKey());
                }
                bi.spinnerDistrict.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1
                        , districts));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bi.spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return;
                bi.openFormBtn.setClickable(true);
                /*MainApp.DIST_ID = Objects.requireNonNull(SplashscreenActivity.districtsMap.get(spinnerDistrict.getSelectedItem().toString())).getSecond().getDist_id();*/
                selectedCluster = Objects.requireNonNull(SplashscreenActivity.subDistrictsMap.get(bi.spinnerDistrict.getSelectedItem().toString())).getSecond();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void callingCoroutine() {
        //To call coroutine here
        populatingSpinners(this, provinceAdapter, new SplashscreenActivity.Continuation<Unit>() {
            @Override
            public void resume(Unit value) {

            }

            @Override
            public void resumeWithException(@NotNull Throwable exception) {

            }

            @NotNull
            @Override
            public CoroutineContext getContext() {
                return null;
            }
        });
    }


    //Reactive Streams
    /*private Observable<EnumBlockContract> getEnumBlockForCluster(String clusterNo) {
        return Observable.create(emitter -> {
            emitter.onNext(appInfo.getDbHelper().getEnumBlock(clusterNo));
            emitter.onComplete();
        });
    }*/


    //Getting data from db
    public void gettingEnumClusterData(String clusterNo) {
        /*getEnumBlockForCluster(clusterNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<EnumBlockContract>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(EnumBlockContract enumContract) {
                        String selected = enumContract.getGeoarea();

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "Sorry not found any block", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        disposable.dispose();
                    }
                });*/
    }


    //Async tasks
    public static class CopyTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog asycdialog;
        private Context mContext;

        public CopyTask(Context mContext) {
            this.mContext = mContext;
            asycdialog = new ProgressDialog(mContext);
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            asycdialog.setTitle("COPYING DATA");
            asycdialog.setMessage("Loading...");
            asycdialog.setCancelable(false);
            asycdialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            // do the task you want to do. This will be executed in background.
            try {

                File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                if (!root.exists()) {
                    root.mkdirs();
                }
                File gpxfile = new File(root, Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID)
                        + "_" + new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date())
                        + "_" + FormsContract.FormsTable.TABLE_NAME);
                FileWriter writer = new FileWriter(gpxfile);

                Collection<FormsContract> listing = appInfo.getDbHelper().getUnsyncedForms();
                if (listing.size() > 0) {
                    JSONArray jsonSync = new JSONArray();
                    for (FormsContract fc : listing) {
                        jsonSync.put(fc.toJSONObject());
                    }

                    writer.append(String.valueOf(jsonSync));
                    writer.flush();
                    writer.close();

                    if (listing.size() < 100) {
                        Thread.sleep(3000);
                    }
                }
            } catch (JSONException | IOException | InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            asycdialog.dismiss();
            Toast.makeText(mContext, "Copying done", Toast.LENGTH_SHORT).show();
        }
    }

}
