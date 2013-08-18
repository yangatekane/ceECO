package com.htm;

import android.content.Intent;
import android.os.Bundle;

/**
 * Created by yanga on 2013/08/15.
 */
public class JobCardsActivity extends BaseActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_cards);
        int number = getIntent().getIntExtra("myNumber",0);
        startActivity(new Intent(JobCardsActivity.this,SearchJobsResults.class).putExtra("number",number));
        finish();
    }
}