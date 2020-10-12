package edu.aku.covid_followup_app.activities.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.validatorcrawler.aliazaz.Clear;
import com.validatorcrawler.aliazaz.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import edu.aku.covid_followup_app.R;
import edu.aku.covid_followup_app.contracts.PersonalContract;
import edu.aku.covid_followup_app.core.DatabaseHelper;
import edu.aku.covid_followup_app.core.MainApp;
import edu.aku.covid_followup_app.databinding.ActivitySectionPCBinding;

import static edu.aku.covid_followup_app.core.MainApp.pc;

public class SectionPCActivity extends AppCompatActivity {

    ActivitySectionPCBinding bi;
    boolean stickerType = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_p_c);
        bi.setCallback(this);
        setupSkips();
    }

    private void setupSkips() {

        bi.pc01.setOnCheckedChangeListener(((radioGroup, i) -> {
            if (i == bi.pc012.getId()) {
                Clear.clearAllFields(bi.fldGrpSectionC01);
            }
        }));

    }

    public void BtnContinue() {
        if (!formValidation()) return;
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

    private boolean UpdateDB() {
        DatabaseHelper db = MainApp.appInfo.getDbHelper();
        int updcount = db.updatePersonalColumn(PersonalContract.PersonalTable.COLUMN_SC, pc.getsC(), pc.get_ID());
        return updcount == 1;
    }

    private void SaveDraft() throws JSONException {

        JSONObject json = new JSONObject();
        json.put("pc01", bi.pc011.isChecked() ? "1"
                : bi.pc012.isChecked() ? "2"
                : "-1");
        json.put("pc02", bi.pc02.getText().toString());
        json.put("pc02a", bi.pc02a.getText().toString());
        json.put("pc03", bi.pc03.getText().toString());
        json.put("pc03a", bi.pc03a1.isChecked() ? "1"
                : bi.pc03a2.isChecked() ? "2"
                : "-1");
        json.put("pc03b", bi.pc03b.getText().toString());


        pc.setsC(json.toString());

    }

    public void BtnEnd() {
        finish();
    }

    private boolean formValidation() {
        return Validator.emptyCheckingContainer(this, bi.fldGrpSectionC);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back press not allowed", Toast.LENGTH_SHORT).show();
    }

    public void btnScan(int type) {
        stickerType = type == 1;
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                .setPrompt("Scan sticker")
                .setCameraId(0)  // Use a specific camera of the device
                .setBeepEnabled(false)
                .setBarcodeImageEnabled(true)
                .setOrientationLocked(false)
                .initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                if (stickerType) {
                    bi.pc03.setText(null);
                    bi.pc03.setEnabled(true);
                } else {
                    bi.pc03b.setText(null);
                    bi.pc03b.setEnabled(true);
                }
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                if (stickerType) {
                    bi.pc03.setText(result.getContents().trim());
                    bi.pc03.setEnabled(false);
                } else {
                    bi.pc03b.setText(result.getContents().trim());
                    bi.pc03b.setEnabled(false);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}