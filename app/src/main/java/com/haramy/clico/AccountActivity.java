package com.haramy.clico;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.haramy.clico.utils.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Project: Clico.
 * Created by Dell on 09/07/2016.
 * Copyright (C) 2016 : Rami Hamrouni
 */
public class AccountActivity extends BaseActivity {

    private CircleImageView mProfileImg;
    private boolean mIsTraderMode;
    private Switch mModeSwitcher;
    private RatingBar mRatingBar;
    private TextView mReputationTv;
    private TextView mModeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Moez BEN AISSA"); // Anis BELHIBA
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        mProfileImg = (CircleImageView) findViewById(R.id.profile_image);
        setUpAppBarListener();
        initModeListeners();
    }

    private void setUpAppBarListener(){
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset>-150){
                    mProfileImg.setAlpha(1f);
                }else if (verticalOffset>-200){
                    mProfileImg.setAlpha(0.75f);
                } else if (verticalOffset>-250){
                    mProfileImg.setAlpha(0.5f);
                } else {
                    mProfileImg.setAlpha(0f);
                }
            }
        });
    }

    private void initModeListeners(){
        mIsTraderMode = Utils.getUserMode(AccountActivity.this);
        mModeSwitcher = (Switch) findViewById(R.id.switch1);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
        mReputationTv = (TextView) findViewById(R.id.textView_reputation);
        mModeTv = (TextView) findViewById(R.id.textView_lbl_switchMode);
        updateModeListeners(false);

        mModeSwitcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsTraderMode = isChecked;
                updateModeListeners(true);
            }
        });

    }

    private void updateModeListeners(boolean callFromListener){
        if (mIsTraderMode){
            mReputationTv.setText("+54");
            mRatingBar.setRating(1.5f);
            if (!callFromListener) mModeSwitcher.setChecked(true);
            mModeTv.setText("Mode (Trade)");
        }else {
            mReputationTv.setText("+478");
            mRatingBar.setRating(3.5f);
            if (!callFromListener) mModeSwitcher.setChecked(false);
            mModeTv.setText("Mode (Client)");
        }
        Utils.setUserMode(AccountActivity.this, mIsTraderMode);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}
