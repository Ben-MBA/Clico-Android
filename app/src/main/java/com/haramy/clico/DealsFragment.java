package com.haramy.clico;

import android.app.DatePickerDialog;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.haramy.clico.model.Deal;
import com.haramy.clico.utils.Constants;
import com.haramy.clico.utils.DealsAdapter;
import com.haramy.clico.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Project: Clico.
 * Created by Dell on 09/07/2016.
 * Copyright (C) 2016 : Rami Hamrouni
 */
public class DealsFragment extends Fragment {

    public static final int MODE_CLIENT_SEARCH = 1;
    public static final int MODE_CLIENT_SHARE = 2;
    public static final int MODE_TRADE_SEARCH = 3;
    public static final int MODE_TRADE_SHARE = 4;

    private int mFragmentMode = MODE_TRADE_SEARCH;

    private View mRootView;
    private AutoCompleteTextView mActivityEdt;
    private AutoCompleteTextView mPlaceEdt;
    private TextView mDateFromTv;
    private TextView mDateToTv;
    private Button mActionBtn;
    private Spinner mDealsSpinner;
    private RecyclerView mResultRecylcerView;
    private TextView mResultLabelTv;
    private ExpandableRelativeLayout mFakeExpandableLayout;
    private DealsAdapter mAdapter;
    private View mLayoutTopBackground;
    private boolean mIsDateFrom;
    private DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            updateDate(year, month+1, day);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_deals, container, false);

        return mRootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            mFragmentMode = bundle.getInt(Constants.EXTRA_FRAGMENT_MODE, MODE_TRADE_SEARCH);
        }
        buildView();
    }


    private void buildView(){
        findViews();
        updateFragment();
    }

    private void updateFragment(){
        updateBackgroundImage();
        setupLabels();
        setActions();
        setUpRecyclerView();
        animateFragment();
        clearEditTexts();
        if (!mFakeExpandableLayout.isExpanded()) mFakeExpandableLayout.expand();
    }

    private void findViews(){
        mFakeExpandableLayout = (ExpandableRelativeLayout) mRootView.findViewById(R.id.fakeLayout);
        mActivityEdt = (AutoCompleteTextView) mRootView.findViewById(R.id.editText_activity_search);
        mPlaceEdt = (AutoCompleteTextView) mRootView.findViewById(R.id.editText_activity_location);
        mDateFromTv = (TextView) mRootView.findViewById(R.id.textView_value_dateFrom);
        mDateToTv = (TextView) mRootView.findViewById(R.id.textView_value_dateTo);
        mActionBtn = (Button) mRootView.findViewById(R.id.button_search);
        mDealsSpinner = (Spinner) mRootView.findViewById(R.id.spinner);
        mResultLabelTv = (TextView) mRootView.findViewById(R.id.textView_resultLabel);
        mResultRecylcerView = (RecyclerView) mRootView.findViewById(R.id.recyclerView_result);
        mLayoutTopBackground = mRootView.findViewById(R.id.layoutTopBackground);
    }

    private void setupLabels(){
        switch (mFragmentMode){
            case MODE_CLIENT_SEARCH:
                mActivityEdt.setHint("Activity");
                mPlaceEdt.setHint("Where");
                mActionBtn.setText("Search");
                mResultLabelTv.setText("Search result");
                break;
            case MODE_CLIENT_SHARE:
                mActivityEdt.setHint("Activity");
                mPlaceEdt.setHint("Where");
                mActionBtn.setText("Share deal");
                mResultLabelTv.setText("Historic");
                break;
            case MODE_TRADE_SEARCH:
                mActivityEdt.setHint("Activity");
                mPlaceEdt.setHint("Where");
                mActionBtn.setText("Find client");
                mResultLabelTv.setText("Search result");
                break;
            case MODE_TRADE_SHARE:
                mActivityEdt.setHint("Activity");
                mPlaceEdt.setHint("Where");
                mActionBtn.setText("Share deal");
                mResultLabelTv.setText("Historic");
                break;
        }
    }

    private void clearEditTexts(){
        mActivityEdt.setText("");
        mPlaceEdt.setText("");
        mDateFromTv.setText("");
        mDateToTv.setText("");
        mDealsSpinner.setSelection(3);
    }

    private void setActions(){
        ArrayAdapter<CharSequence> activitiesAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.activities_array, android.R.layout.simple_dropdown_item_1line);
        mActivityEdt.setAdapter(activitiesAdapter);

        ArrayAdapter<CharSequence> countriesAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.countries_array, android.R.layout.simple_dropdown_item_1line);
        mPlaceEdt.setAdapter(countriesAdapter);

        mActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFakeExpandableLayout.toggle();
                setUpRecyclerView();
            }
        });

        mResultLabelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFakeExpandableLayout.toggle();
            }
        });

        mDateFromTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsDateFrom = true;
                showDateDialog();
            }
        });

        mDateToTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsDateFrom = false;
                showDateDialog();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.deals_array, R.layout.simple_spinner_item_white);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDealsSpinner.setAdapter(adapter);
        mDealsSpinner.setSelection(3);

        mFakeExpandableLayout.setListener(new ExpandableLayoutListener() {
            @Override
            public void onAnimationStart() {}

            @Override
            public void onAnimationEnd() {}

            @Override
            public void onPreOpen() {}

            @Override
            public void onPreClose() {}

            @Override
            public void onOpened() {
                mResultLabelTv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                        R.drawable.ic_keyboard_arrow_down_pink_500_24dp, 0);
            }

            @Override
            public void onClosed() {
                mResultLabelTv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                        R.drawable.ic_keyboard_arrow_up_pink_500_24dp, 0);
            }
        });
    }

    private void setUpRecyclerView(){
        mAdapter = new DealsAdapter(getActivity(), getDealsData());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mResultRecylcerView.setLayoutManager(mLayoutManager);
        mResultRecylcerView.setItemAnimator(new DefaultItemAnimator());
        mResultRecylcerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper
                .SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                    RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {
                DealsAdapter.ViewHolder vh = (DealsAdapter.ViewHolder) viewHolder;
                RelativeLayout rl = vh.container;

                Paint p = new Paint();
                if(isCurrentlyActive) {
                    p.setARGB(100,255,105,180);// deepPink
                }else{
                    p.setARGB(255, 255, 255, 255); // white
                }

                c.drawRect(rl.getLeft(), rl.getTop(), rl.getRight(), rl.getBottom(), p);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(mResultRecylcerView);
    }

    private ArrayList<Deal> getDealsData(){
        ArrayList<Deal> list;
        if (mFragmentMode == MODE_CLIENT_SEARCH || mFragmentMode == MODE_TRADE_SHARE){
            list =  Utils.getStaticDeals();
        } else {
            list =  Utils.getStaticClient();
        }
        return list;
    }

    public void setFragmentMode(int mode){
        if (mode!=mFragmentMode){
            mFragmentMode = mode;
            updateFragment();
        }
    }

    private void updateBackgroundImage(){
        if (mFragmentMode == MODE_CLIENT_SEARCH || mFragmentMode == MODE_TRADE_SHARE){
            mLayoutTopBackground
                    .setBackgroundDrawable(getResources().getDrawable(R.drawable.blurred_resto));
        } else {
            mLayoutTopBackground
                    .setBackgroundDrawable(getResources().getDrawable(R.drawable.blurred_clients));
        }
    }

    private void animateFragment(){
        Animation fadeInOutAnimation = AnimationUtils
                .loadAnimation(getActivity(), R.anim.fade_in_out);
        mRootView.startAnimation(fadeInOutAnimation);

    }

    private void showDateDialog(){
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(getActivity(), mDateListener, c.get(Calendar.YEAR),
                c.get(Calendar.MONTH) , c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDate(int year, int month, int day){
        StringBuilder date = new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year);
        if (mIsDateFrom){
            mDateFromTv.setText(date);
        }else {
            mDateToTv.setText(date);
        }
    }

}
