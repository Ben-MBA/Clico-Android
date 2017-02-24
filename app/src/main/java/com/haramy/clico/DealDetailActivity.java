package com.haramy.clico;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Project: Clico.
 * Created by Dell on 10/07/2016.
 * Copyright (C) 2016 : Rami Hamrouni
 */
public class DealDetailActivity extends BaseActivity {

    private CircleImageView mLogoImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_detail);

        setupToolbar();
        setUpAppBarListener();
    }

    private void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Maison Rostang");

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DealDetailActivity.this.finish();
            }
        });
    }

    private void setUpAppBarListener(){
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mLogoImg = (CircleImageView)findViewById(R.id.imageView_logo);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset>-150){
                    mLogoImg.setAlpha(1f);
                }else if (verticalOffset>-200){
                    mLogoImg.setAlpha(0.75f);
                } else if (verticalOffset>-250){
                    mLogoImg.setAlpha(0.5f);
                } else {
                    mLogoImg.setAlpha(0f);
                }
            }
        });
    }
}
