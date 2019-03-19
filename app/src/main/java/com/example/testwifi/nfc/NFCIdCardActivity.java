package com.example.testwifi.nfc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.testwifi.R;

public class NFCIdCardActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcid_card);
        findViewById(R.id.test_nfc_card_1);
        findViewById(R.id.test_nfc_card_2);
        findViewById(R.id.test_nfc_card_3);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_nfc_card_1:
                break;
            case R.id.test_nfc_card_2:
                break;
            case R.id.test_nfc_card_3:
                break;
        }
    }
}
