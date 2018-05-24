package com.example.wyhjc.musicplayer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wyhjc.musicplayer.R;

import java.util.ArrayList;

/**
 * Set time to close fragment
 */
public class TimeFragment extends DialogFragment implements View.OnClickListener{

    private TextView mTimeNone, mTime10, mTime20, mTime30, mTime45, mTime60, mTime90;
    private ImageView mTimeNoneImage, mTime10Image, mTime20Image, mTime30Image, mTime45Image, mTime60Image, mTime90Image;
    private View view;
    private Context mContext;
    private ArrayList<ImageView> images;
    private ImageView imageViewTemp;

    public TimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_time, container);

        initView();
        return view;
    }

    //初始化视图并设置响应事件
    private void initView(){
        mTimeNone = (TextView)view.findViewById(R.id.timeNone_text);
        mTime10 = (TextView)view.findViewById(R.id.time10_text);
        mTime20 = (TextView)view.findViewById(R.id.time20_text);
        mTime30 = (TextView)view.findViewById(R.id.time30_text);
        mTime45 = (TextView)view.findViewById(R.id.time45_text);
        mTime60 = (TextView)view.findViewById(R.id.time60_text);
        mTime90 = (TextView)view.findViewById(R.id.time90_text);
        mTime10.setOnClickListener(this);
        mTime20.setOnClickListener(this);
        mTime30.setOnClickListener(this);
        mTime45.setOnClickListener(this);
        mTime60.setOnClickListener(this);
        mTime90.setOnClickListener(this);
        mTimeNone.setOnClickListener(this);

        mTimeNoneImage = (ImageView)view.findViewById(R.id.timeNone_image);
        imageViewTemp = mTimeNoneImage;
        select(mTimeNoneImage);
        mTime10Image = (ImageView)view.findViewById(R.id.time10_image);
        mTime20Image = (ImageView)view.findViewById(R.id.time20_image);
        mTime30Image = (ImageView)view.findViewById(R.id.time30_image);
        mTime45Image = (ImageView)view.findViewById(R.id.time45_image);
        mTime60Image = (ImageView)view.findViewById(R.id.time60_image);
        mTime90Image = (ImageView)view.findViewById(R.id.time90_image);
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置fragment高度 、宽度
        int dialogHeight = (int) (mContext.getResources().getDisplayMetrics().heightPixels * 0.71);
        int dialogWidth = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.79);
        getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        getDialog().setCanceledOnTouchOutside(true);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //if(savedInstanceState.getInt("last") >= 0 && mTimeManager != null)
            //mTimeManager.select(savedInstanceState.getInt("last"));
        //if(savedInstanceState!=null)
            //Toast.makeText(mContext,savedInstanceState.getInt("last"), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause(){
        super.onPause();
        //Toast.makeText(mContext, "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop(){
        super.onStop();
        //Toast.makeText(mContext, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Toast.makeText(mContext, "onDetach", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        //Toast.makeText(mContext, "onDestroyView", Toast.LENGTH_SHORT).show();
        super.onDestroyView();
    }

    private void select(ImageView imageView){
        imageViewTemp.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        imageViewTemp = imageView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.timeNone_text:
                select(mTimeNoneImage);
                getDialog().hide();
                break;
            case R.id.time10_text:
                select(mTime10Image);
                getDialog().hide();
                break;
            case R.id.time20_text:
                select(mTime20Image);
                getDialog().hide();
                break;
            case R.id.time30_text:
                select(mTime30Image);
                getDialog().hide();
                break;
            case R.id.time45_text:
                select(mTime45Image);
                getDialog().hide();
                break;
            case R.id.time60_text:
                select(mTime60Image);
                getDialog().hide();
                break;
            case R.id.time90_text:
                select(mTime90Image);
                getDialog().hide();
                break;
        }
    }
}
