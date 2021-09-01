package edu.aku.covid_followup_app.activities.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.covid_followup_app.R;
import edu.aku.covid_followup_app.contracts.PersonalContract;
import edu.aku.covid_followup_app.core.DatabaseHelper;
import edu.aku.covid_followup_app.core.MainApp;
import edu.aku.covid_followup_app.databinding.ActivitySectionPDBinding;
import edu.aku.covid_followup_app.utils.JSONUtils;

import static edu.aku.covid_followup_app.CONSTANTS.PERSONAL_END;
import static edu.aku.covid_followup_app.core.MainApp.pc;
import static edu.aku.covid_followup_app.utils.OtherUtilsKt.openWarningActivity;

public class SectionPDActivity extends AppCompatActivity {

    ActivitySectionPDBinding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_p_d);
        bi.setCallback(this);
        setupSkips();
    }


    private void setupSkips() {
        bi.pd0897.setOnCheckedChangeListener((compoundButton, b) -> Clear.clearAllFields(bi.llpd08d2, !b));
        bi.pd01.setOnCheckedChangeListener((radioGroup, i) -> Clear.clearAllFields(bi.llpd01));
        bi.pd06.setOnCheckedChangeListener((radioGroup, i) -> {
            Clear.clearAllFields(bi.llpd06);
            Clear.clearAllFields(bi.fldGrpCVpd12);
        });
    }


    public void BtnContinue() {
        if (!formValidation()) return;
        try {
            SaveDraft();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (UpdateDB()) {
            finish();
            //new JSONObject(pc.getsA()).get("ba01h5").equals("")
            //startActivity(new Intent(this, SectionPCActivity.class).putExtra(NASAL_TAKEN, member.getNasal()));
        } else {
            Toast.makeText(this, "Sorry. You can't go further.\n Please contact IT Team (Failed to update DB)", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatePersonalColumn(PersonalContract.PersonalTable.COLUMN_SA, MainApp.pc.getsA(), pc.get_ID());
        if (updcount > 0) {
            return true;
        } else {
            Toast.makeText(this, "Sorry! Failed to update DB", Toast.LENGTH_SHORT).show();
            return false;
        }

    }


    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();

        json.put("pd01", bi.pd01a.isChecked() ? "1"
                : bi.pd01b.isChecked() ? "2"
                : "-1");

        json.put("pd02a", bi.pd02a.isChecked() ? "1" : "-1");
        json.put("pd02b", bi.pd02b.isChecked() ? "2" : "-1");
        json.put("pd02c", bi.pd02c.isChecked() ? "3" : "-1");
        json.put("pd02d", bi.pd02d.isChecked() ? "4" : "-1");

        json.put("pd03a", bi.pd03aa.isChecked() ? "1"
                : bi.pd03ab.isChecked() ? "2"
                : "-1");

        json.put("pd03b", bi.pd03ba.isChecked() ? "1"
                : bi.pd03bb.isChecked() ? "2"
                : "-1");

        json.put("pd03c", bi.pd03ca.isChecked() ? "1"
                : bi.pd03cb.isChecked() ? "2"
                : "-1");

        json.put("pd03d", bi.pd03da.isChecked() ? "1"
                : bi.pd03db.isChecked() ? "2"
                : "-1");

        json.put("pd03e", bi.pd03ea.isChecked() ? "1"
                : bi.pd03eb.isChecked() ? "2"
                : "-1");

        json.put("pd03f", bi.pd03fa.isChecked() ? "1"
                : bi.pd03fb.isChecked() ? "2"
                : "-1");

        json.put("pd03g", bi.pd03ga.isChecked() ? "1"
                : bi.pd03gb.isChecked() ? "2"
                : "-1");

        json.put("pd03h", bi.pd03ha.isChecked() ? "1"
                : bi.pd03hb.isChecked() ? "2"
                : "-1");

        json.put("pd03i", bi.pd03ia.isChecked() ? "1"
                : bi.pd03ib.isChecked() ? "2"
                : "-1");

        json.put("pd03j", bi.pd03ja.isChecked() ? "1"
                : bi.pd03jb.isChecked() ? "2"
                : "-1");

        json.put("pd03k", bi.pd03ka.isChecked() ? "1"
                : bi.pd03kb.isChecked() ? "2"
                : "-1");

        json.put("pd03l", bi.pd03la.isChecked() ? "1"
                : bi.pd03lb.isChecked() ? "2"
                : "-1");

        json.put("pd03m", bi.pd03ma.isChecked() ? "1"
                : bi.pd03mb.isChecked() ? "2"
                : "-1");

        json.put("pd03n", bi.pd03na.isChecked() ? "1"
                : bi.pd03nb.isChecked() ? "2"
                : "-1");

        json.put("pd03o", bi.pd03oa.isChecked() ? "1"
                : bi.pd03ob.isChecked() ? "2"
                : "-1");

        json.put("pd04a", bi.pd04a.isChecked() ? "1" : "-1");
        json.put("pd04b", bi.pd04b.isChecked() ? "2" : "-1");
        json.put("pd04c", bi.pd04c.isChecked() ? "3" : "-1");
        json.put("pd04d", bi.pd04d.isChecked() ? "4" : "-1");
        json.put("pd04e", bi.pd04e.isChecked() ? "5" : "-1");
        json.put("pd04f", bi.pd04f.isChecked() ? "6" : "-1");
        json.put("pd04g", bi.pd04g.isChecked() ? "7" : "-1");
        json.put("pd04h", bi.pd04h.isChecked() ? "8" : "-1");
        json.put("pd04i", bi.pd04i.isChecked() ? "9" : "-1");
        json.put("pd04j", bi.pd04j.isChecked() ? "10" : "-1");
        json.put("pd04k", bi.pd04k.isChecked() ? "11" : "-1");
        json.put("pd04l", bi.pd04l.isChecked() ? "12" : "-1");
        json.put("pd04m", bi.pd04m.isChecked() ? "13" : "-1");
        json.put("pd04o", bi.pd04o.isChecked() ? "14" : "-1");
        json.put("pd05a", bi.pd05a.isChecked() ? "1" : "-1");
        json.put("pd05b", bi.pd05b.isChecked() ? "2" : "-1");
        json.put("pd05c", bi.pd05c.isChecked() ? "3" : "-1");
        json.put("pd05d", bi.pd05d.isChecked() ? "4" : "-1");
        json.put("pd05e", bi.pd05e.isChecked() ? "5" : "-1");
        json.put("pd05f", bi.pd05f.isChecked() ? "6" : "-1");
        json.put("pd05g", bi.pd05g.isChecked() ? "7" : "-1");
        json.put("pd05h", bi.pd05h.isChecked() ? "8" : "-1");
        json.put("pd05i", bi.pd05i.isChecked() ? "9" : "-1");
        json.put("pd05j", bi.pd05j.isChecked() ? "10" : "-1");
        json.put("pd05k", bi.pd05k.isChecked() ? "11" : "-1");
        json.put("pd05l", bi.pd05l.isChecked() ? "12" : "-1");
        json.put("pd05m", bi.pd05m.isChecked() ? "13" : "-1");
        json.put("pd05n", bi.pd05n.isChecked() ? "14" : "-1");

        json.put("pd06", bi.pd06a.isChecked() ? "1"
                : bi.pd06b.isChecked() ? "2"
                : "-1");

        json.put("pd07", bi.pd07a.isChecked() ? "1"
                : bi.pd07b.isChecked() ? "2"
                : bi.pd07c.isChecked() ? "3"
                : bi.pd07d.isChecked() ? "4"
                : bi.pd07e.isChecked() ? "5"
                : bi.pd07f.isChecked() ? "6"
                : bi.pd07g.isChecked() ? "7"
                : bi.pd0796.isChecked() ? "96"
                : "-1");

        json.put("pd0796x", bi.pd0796x.getText().toString());

        json.put("pd08d1d", bi.pd08d1d.getText().toString());
        json.put("pd08d1m", bi.pd08d1m.getText().toString());
        json.put("pd08d1y", bi.pd08d1y.getText().toString());

        json.put("pd08d2d", bi.pd08d2d.getText().toString());
        json.put("pd08d2m", bi.pd08d2m.getText().toString());
        json.put("pd08d2y", bi.pd08d2y.getText().toString());
        json.put("pd0897", bi.pd0897.isChecked() ? "97" : "-1");

        json.put("pd10", bi.pd10a.isChecked() ? "1"
                : bi.pd10b.isChecked() ? "2"
                : "-1");

        json.put("pd11", bi.pd11a.isChecked() ? "1"
                : bi.pd11b.isChecked() ? "2"
                : "-1");

        json.put("pd12a", bi.pd12a.isChecked() ? "1" : "-1");
        json.put("pd12b", bi.pd12b.isChecked() ? "2" : "-1");
        json.put("pd12c", bi.pd12c.isChecked() ? "3" : "-1");
        json.put("pd12d", bi.pd12d.isChecked() ? "4" : "-1");
        json.put("pd12e", bi.pd12e.isChecked() ? "5" : "-1");
        json.put("pd12f", bi.pd12f.isChecked() ? "6" : "-1");
        json.put("pd1296", bi.pd1296.isChecked() ? "96" : "-1");
        json.put("pd1296x", bi.pd1296x.getText().toString());

        try {
            JSONObject json_merge = JSONUtils.mergeJSONObjects(new JSONObject(pc.getsA()), json);
            pc.setsA(String.valueOf(json_merge));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void BtnEnd() {
        openWarningActivity(this,
                PERSONAL_END,
                null,
                "Warning!",
                "Do you want to Exit",
                "Yes",
                "No"
        );
    }


    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.GrpName);
    }

   /* @Override
    public void callWarningActivity(int id, Object item) {

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

    }*/


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back press not allowed", Toast.LENGTH_SHORT).show();
    }

}