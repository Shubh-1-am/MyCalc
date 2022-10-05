package com.example.all_in_onecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {

    TextView mText;
    TextView mResultBar;
    Button m00,m0,m1,m2,m3,m4,m5,m6,m7,m8,m9,mAdd,mSub,mMul,mDiv,mPer,mClearAll,mDot,mEquals;
    ImageView mDel;
    TextView mSin,mCos,mTan,mLog,mLn,mlb,mrb,mPow,mSqrt,mFact,mPi,mE,mCubeRt,mDEG,mRAD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        findView();
        m00.setOnClickListener(v -> append("00"));
        m0.setOnClickListener(v -> append("0"));
        m1.setOnClickListener(v -> append("1"));
        m2.setOnClickListener(v -> append("2"));
        m3.setOnClickListener(v -> append("3"));
        m4.setOnClickListener(v -> append("4"));
        m5.setOnClickListener(v -> append("5"));
        m6.setOnClickListener(v -> append("6"));
        m7.setOnClickListener(v -> append("7"));
        m8.setOnClickListener(v -> append("8"));
        m9.setOnClickListener(v -> append("9"));

        mDot.setOnClickListener(v -> checkDuplicacyThenAppend("."));
        mPer.setOnClickListener(v -> checkDuplicacyThenAppend("%"));
        mDiv.setOnClickListener(v -> checkDuplicacyThenAppend("/"));
        mMul.setOnClickListener(v -> checkDuplicacyThenAppend("*"));
        mSub.setOnClickListener(v -> checkDuplicacyThenAppend("-"));
        mAdd.setOnClickListener(v -> checkDuplicacyThenAppend("+"));

        mClearAll.setOnClickListener(v -> clearAll());
        mDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence s =  mText.getText();
                if(s.length()>0)
                    mText.setText(s.subSequence(0,s.length()-1));
            }
        });

        mSin.setOnClickListener(v -> append("sin("));
        mCos.setOnClickListener(v -> append("cos("));
        mTan.setOnClickListener(v -> append("tan("));
        mLog.setOnClickListener(v -> append("log("));
        mLn.setOnClickListener( v -> append("ln("));
        mlb.setOnClickListener( v -> append("("));
        mrb.setOnClickListener( v -> append(")"));
        mPow.setOnClickListener( v -> append("^"));
        mSqrt.setOnClickListener( v -> append("√"));
        mFact.setOnClickListener( v -> append("!"));
//        mPi.setOnClickListener( v -> append(Double.toString(Math.PI)));
        mE.setOnClickListener( v -> append(Double.toString(Math.E)));
        mCubeRt.setOnClickListener( v -> append("∛"));
        mRAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (mRAD.getTextColors() == getResources().getColorStateList(R.color.deg_rad_color_enable)) {
                mRAD.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_disable));
                mDEG.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_enable));
            }
            else {
                mRAD.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_enable));
                mDEG.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_disable));
            }
            }
        });
        mDEG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDEG.getTextColors() == getResources().getColorStateList(R.color.deg_rad_color_enable)) {
                    mDEG.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_disable));
                    mRAD.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_enable));
                }
                else {
                    mDEG.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_enable));
                    mRAD.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_disable));
                }
            }
        });







    }

    private void clearAll() {mText.setText("");}

    private void checkDuplicacyThenAppend(String s) {
        CharSequence cs = mText.getText();
        if(cs.length()==0) append(s);
        else if(cs.charAt(cs.length()-1)==s.charAt(0)) return;
        else{append(s);}
    }

    private void findView() {
        mText = findViewById(R.id.tv_expression);
        mResultBar = findViewById(R.id.tv_result);
         m00 = findViewById(R.id.btn_00);
         m0 = findViewById(R.id.btn_0);
         m1 = findViewById(R.id.btn_1);
         m2 = findViewById(R.id.btn_2);
         m3 = findViewById(R.id.btn_3);
         m4 = findViewById(R.id.btn_4);
         m5 = findViewById(R.id.btn_5);
         m6 = findViewById(R.id.btn_6);
         m7 = findViewById(R.id.btn_7);
         m8 = findViewById(R.id.btn_8);
         m9 = findViewById(R.id.btn_9);
         mAdd = findViewById(R.id.btn_add);
         mSub = findViewById(R.id.btn_sub);
         mDiv = findViewById(R.id.btn_div);
         mMul = findViewById(R.id.btn_mul);
         mPer = findViewById(R.id.btn_percent);
         mClearAll = findViewById(R.id.btn_clear);
         mDel = findViewById(R.id.iv_del);
         mDot = findViewById(R.id.btn_dot);
         mEquals = findViewById(R.id.btn_equals);

         mSin = findViewById(R.id.tv_sin);
         mCos = findViewById(R.id.tv_cos);
         mTan = findViewById(R.id.tv_tan);
         mLog = findViewById(R.id.tv_log);
         mLn = findViewById(R.id.tv_ln);
         mlb = findViewById(R.id.tv_lb);
         mrb = findViewById(R.id.tv_rb);
         mPow = findViewById(R.id.tv_pow);
         mSqrt = findViewById(R.id.tv_sqrt);
         mFact = findViewById(R.id.tv_fact);
         mPi = findViewById(R.id.tv_pi);
         mE = findViewById(R.id.tv_e);
         mCubeRt = findViewById(R.id.tv_cubeRt);
         mDEG = findViewById(R.id.tv_DEG);
         mRAD = findViewById(R.id.tv_RAD);
    }
    private void append(String s){
        mText.append(s);
    }
}