package com.haramy.clico;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Project: Clico.
 * Created by Dell on 09/07/2016.
 * Copyright (C) 2016 : Rami Hamrouni
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    public void onBackPressed(){
        this.finish();
    }
}
