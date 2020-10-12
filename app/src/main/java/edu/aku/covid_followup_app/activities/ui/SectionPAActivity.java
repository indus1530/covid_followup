package edu.aku.covid_followup_app.activities.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aku.covid_followup_app.CONSTANTS;
import edu.aku.covid_followup_app.R;
import edu.aku.covid_followup_app.contracts.MembersContract;
import edu.aku.covid_followup_app.contracts.PersonalContract;
import edu.aku.covid_followup_app.core.AppInfo;
import edu.aku.covid_followup_app.core.DatabaseHelper;
import edu.aku.covid_followup_app.core.MainApp;
import edu.aku.covid_followup_app.databinding.ActivitySectionPABinding;
import edu.aku.covid_followup_app.utils.WarningActivityInterface;

import static edu.aku.covid_followup_app.CONSTANTS.NASAL_TAKEN;
import static edu.aku.covid_followup_app.CONSTANTS.PERSONAL_END;
import static edu.aku.covid_followup_app.core.MainApp.pc;
import static edu.aku.covid_followup_app.utils.OtherUtilsKt.openWarningActivity;

public class SectionPAActivity extends AppCompatActivity implements WarningActivityInterface {

    ActivitySectionPABinding bi;
    private MembersContract member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_p_a);
        setupContentUI();
    }

    private void setupContentUI() {

        bi.pa07.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pa07b.getId()) {
                Clear.clearAllFields(bi.fldGrpCVpa08);
            }
        }));

        bi.pa08n.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(bi.pa08n.getText())) {
                    int count = Integer.parseInt(bi.pa08n.getText().toString());
                    bi.fldGrpCVpa0801.setVisibility(count < 1 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpa0802.setVisibility(count < 2 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpa0803.setVisibility(count < 3 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpa0804.setVisibility(count < 4 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpa0805.setVisibility(count < 5 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpa0806.setVisibility(count < 6 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpa0807.setVisibility(count < 7 ? View.GONE : View.VISIBLE);
                    bi.fldGrpCVpa0808.setVisibility(count < 8 ? View.GONE : View.VISIBLE);
                } else {
                    bi.fldGrpCVpa0801.setVisibility(View.GONE);
                    bi.fldGrpCVpa0802.setVisibility(View.GONE);
                    bi.fldGrpCVpa0803.setVisibility(View.GONE);
                    bi.fldGrpCVpa0804.setVisibility(View.GONE);
                    bi.fldGrpCVpa0805.setVisibility(View.GONE);
                    bi.fldGrpCVpa0806.setVisibility(View.GONE);
                    bi.fldGrpCVpa0807.setVisibility(View.GONE);
                    bi.fldGrpCVpa0808.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        /*bi.pa01.setText(member.getMemberid());
        bi.pa02.setText(member.getMembername());*/
    }

    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            SaveDraft();
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(this, SectionPCActivity.class).putExtra(NASAL_TAKEN, member.getNasal()));
            } else {
                Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BtnEnd() {
        if (!formValidation()) return;
        openWarningActivity(this,
                PERSONAL_END,
                "Warning!",
                "Do you want to Exit",
                "Yes",
                "No"
        );
    }

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        long updcount = db.addPersonal(pc);
        pc.set_ID(String.valueOf(updcount));
        if (updcount > 0) {
            pc.set_UUID(MainApp.appInfo.getDeviceID() + pc.get_ID());
            db.updatePersonalColumn(PersonalContract.PersonalTable.COLUMN_UID, pc.get_UUID(), pc.get_ID());
            return true;
        } else {
            Toast.makeText(this, "Updating Database... ERROR!", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void SaveDraft() throws JSONException {
        pc = new PersonalContract();
        pc.set_UUID(MainApp.fc.get_UID());
        pc.setSysdate(new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date().getTime()));
        pc.setDeviceID(MainApp.appInfo.getDeviceID());
        pc.setDevicetagID(MainApp.appInfo.getTagName());

        JSONObject json = new JSONObject();

        json.put("ha12", MainApp.fc.getClusterCode());
        json.put("ha13", MainApp.fc.getHhno());
        json.put("username", MainApp.userEmail);
        json.put("appversion", MainApp.appInfo.getAppVersion());

        json.put("hhid", member.getHhid());
        json.put("head", member.getHead());
        json.put("address", member.getAddress());
        json.put("blood", member.getBlood());
        json.put("nasal", member.getNasal());

        json.put("pa02", bi.pa02.getText().toString());

        json.put("pa02a", bi.pa0201.isChecked() ? "1"
                : bi.pa0202.isChecked() ? "2"
                : "-1");

        json.put("pa02b", bi.pa02b.getText().toString());

        json.put("pa03", bi.pa031.isChecked() ? "1"
                : bi.pa032.isChecked() ? "2"
                : bi.pa033.isChecked() ? "3"
                : "-1");

        json.put("pa041", bi.pa041a.isChecked() ? "1"
                : bi.pa041b.isChecked() ? "2"
                : "-1");

        json.put("pa042", bi.pa042a.isChecked() ? "1"
                : bi.pa042b.isChecked() ? "2"
                : "-1");

        json.put("pa043", bi.pa043a.isChecked() ? "1"
                : bi.pa043b.isChecked() ? "2"
                : "-1");

        json.put("pb13c", bi.pb13ca.isChecked() ? "1"
                : bi.pb13cb.isChecked() ? "2"
                : "-1");

        json.put("pa044", bi.pa044a.isChecked() ? "1"
                : bi.pa044b.isChecked() ? "2"
                : "-1");

        json.put("pa045", bi.pa045a.isChecked() ? "1"
                : bi.pa045b.isChecked() ? "2"
                : "-1");

        json.put("pa046", bi.pa046a.isChecked() ? "1"
                : bi.pa046b.isChecked() ? "2"
                : "-1");

        json.put("pa047", bi.pa047a.isChecked() ? "1"
                : bi.pa047b.isChecked() ? "2"
                : "-1");

        json.put("pa048", bi.pa048a.isChecked() ? "1"
                : bi.pa048b.isChecked() ? "2"
                : "-1");

        json.put("pa049", bi.pa049a.isChecked() ? "1"
                : bi.pa049b.isChecked() ? "2"
                : "-1");

        json.put("pa0410", bi.pa0410a.isChecked() ? "1"
                : bi.pa0410b.isChecked() ? "2"
                : "-1");

        json.put("pa0411", bi.pa0411a.isChecked() ? "1"
                : bi.pa0411b.isChecked() ? "2"
                : "-1");

        json.put("pa0412", bi.pa0412a.isChecked() ? "1"
                : bi.pa0412b.isChecked() ? "2"
                : "-1");

        json.put("pa0413", bi.pa0413a.isChecked() ? "1"
                : bi.pa0413b.isChecked() ? "2"
                : "-1");

        json.put("pa0414", bi.pa0414a.isChecked() ? "1"
                : bi.pa0414b.isChecked() ? "2"
                : "-1");

        json.put("pa0415", bi.pa0415a.isChecked() ? "1"
                : bi.pa0415b.isChecked() ? "2"
                : "-1");

        json.put("pa05", bi.pa0501.isChecked() ? "1"
                : bi.pa0502.isChecked() ? "2"
                : "-1");

        json.put("pa06", bi.pa06.getText().toString());

        json.put("pa06a", bi.pa06a.getText().toString());
        json.put("pa06b", bi.pa06b.getText().toString());
        json.put("pa06c", bi.pa06c.getText().toString());
        json.put("pa06d", bi.pa06d.getText().toString());
        json.put("pa06e", bi.pa06e.getText().toString());
        json.put("pa06f", bi.pa06f.getText().toString());
        json.put("pa06g", bi.pa06g.getText().toString());
        json.put("pa06h", bi.pa06h.getText().toString());
        json.put("pa06a98", "-1");

        json.put("pa06b98", "-1");

        json.put("pa06c98", "-1");

        json.put("pa06d98", "-1");

        json.put("pa06e98", "-1");

        json.put("pa06f98", "-1");

        json.put("pa06g98", "-1");

        json.put("pa06h98", "-1");

        json.put("pa07", bi.pa07a.isChecked() ? "1"
                : bi.pa07b.isChecked() ? "2"
                : "-1");

        json.put("pa08n", bi.pa08n.getText().toString());

        json.put("pa0801n", bi.pa0801n.getText().toString());

        json.put("pa0801ad", bi.pa0801ad.getText().toString());

        json.put("pa0802n", bi.pa0802n.getText().toString());

        json.put("pa0802ad", bi.pa0802ad.getText().toString());

        json.put("pa0803n", bi.pa0803n.getText().toString());

        json.put("pa0803ad", bi.pa0803ad.getText().toString());

        json.put("pa0804n", bi.pa0804n.getText().toString());

        json.put("pa0804ad", bi.pa0804ad.getText().toString());

        json.put("pa0805n", bi.pa0805n.getText().toString());

        json.put("pa0805ad", bi.pa0805ad.getText().toString());

        json.put("pa0806n", bi.pa0806n.getText().toString());

        json.put("pa0806ad", bi.pa0806ad.getText().toString());

        json.put("pa0807n", bi.pa0807n.getText().toString());

        json.put("pa0807ad", bi.pa0807ad.getText().toString());

        json.put("pa0808n", bi.pa0808n.getText().toString());

        json.put("pa0808ad", bi.pa0808ad.getText().toString());

        pc.setsA(String.valueOf(json));

        setGPS(this); // Set GPS

    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionPA);
    }

    public static void setGPS(Activity activity) {
        SharedPreferences GPSPref = activity.getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);

        try {
            String lat = GPSPref.getString("Latitude", "0");
            String lang = GPSPref.getString("Longitude", "0");
            String acc = GPSPref.getString("Accuracy", "0");
            String dt = GPSPref.getString("Time", "0");

            if (lat.equals("0") && lang.equals("0")) {
                Toast.makeText(activity, "Could not obtained GPS points", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "GPS set", Toast.LENGTH_SHORT).show();
            }

            String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

            MainApp.fc.setGpsLat(GPSPref.getString("Latitude", "0"));
            MainApp.fc.setGpsLng(GPSPref.getString("Longitude", "0"));
            MainApp.fc.setGpsAcc(GPSPref.getString("Accuracy", "0"));
//            MainApp.fc.setGpsTime(GPSPref.getString(date, "0")); // Timestamp is converted to date above
            MainApp.fc.setGpsDT(date); // Timestamp is converted to date above

        } catch (Exception e) {
            Log.e("GPS", "setGPS: " + e.getMessage());
        }
    }

    @Override
    public void callWarningActivity(int id) {

        if (id == CONSTANTS.REQUEST_PERSONAL_EXIT) {
            try {
                SaveDraft();
                if (UpdateDB()) {
                    finish();
                } else {
                    Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}