package com.example.all_in_onecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.all_in_onecalculator.Interpreter.Evaluator;
import com.example.all_in_onecalculator.Parser.Parser;
import com.example.all_in_onecalculator.Tokenizer.Token;
import com.example.all_in_onecalculator.Tokenizer.Tokenizer;

import java.util.ArrayList;

public class Calculator extends AppCompatActivity {

    TextView mText;
    TextView mResultBar;
    Button m00,m0,m1,m2,m3,m4,m5,m6,m7,m8,m9,mAdd,mSub,mMul,mDiv,mPer,mClearAll,mDot,mEquals;
    ImageView mDel;
    TextView mSin,mCos,mTan,mLog,mLn,mlb,mrb,mPow,mSqrt,mFact,mPi,mE,mCubeRt,mDEG,mRAD;
    int count = 0;
    Evaluator evaluator = new Evaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        LinearLayout layout = findViewById(R.id.calculatorMainLayout);
        AnimationDrawable drawable = (AnimationDrawable) layout.getBackground();
        drawable.setEnterFadeDuration(2500);
        drawable.setExitFadeDuration(5000);
        drawable.start();

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

        mDot.setOnClickListener(v -> checkDuplicateThenAppend("."));
        mPer.setOnClickListener(v -> checkDuplicateThenAppend("%"));
        mDiv.setOnClickListener(v -> checkDuplicateThenAppend("/"));
        mMul.setOnClickListener(v -> checkDuplicateThenAppend("*"));
        mSub.setOnClickListener(v -> checkDuplicateThenAppend("-"));
        mAdd.setOnClickListener(v -> checkDuplicateThenAppend("+"));

        mClearAll.setOnClickListener(v -> clearAll());
        mDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence s =  mText.getText();
                if(s.length()>0) {
                    mText.setText(s.subSequence(0, s.length() - 1));
                    count--;
                    enable_disableButtons(true);
                }
                  mResultBar.setText("");
            }
        });

        mDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clearAll();
                return true;
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
        mPi.setOnClickListener( v -> append("π"));
        mE.setOnClickListener( v -> append("e"));
        mCubeRt.setOnClickListener( v -> append("∛"));
        mRAD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (mRAD.getTextColors() == getResources().getColorStateList(R.color.deg_rad_color_enable)) {
                mRAD.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_disable));
                mDEG.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_enable));
                evaluator.setIsDegree(true);
            }
            else {
                mRAD.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_enable));
                mDEG.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_disable));
                evaluator.setIsDegree(false);
            }
            }
        });
        mDEG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDEG.getTextColors() == getResources().getColorStateList(R.color.deg_rad_color_enable)) {
                    mDEG.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_disable));
                    mRAD.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_enable));
                    evaluator.setIsDegree(false);
                }
                else {
                    mDEG.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_enable));
                    mRAD.setTextColor(getResources().getColorStateList(R.color.deg_rad_color_disable));
                    evaluator.setIsDegree(true);
                }
            }
        });

        mEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                clearAll();

                calculate();
            }
        });

    }

    private void calculate() {
        if (mText.getText().toString().isEmpty()) {
            mResultBar.setTextColor(getResources().getColorStateList(R.color.red));
            mResultBar.setText(new StringBuilder().append(" Input Something! "));
        } else {
            if (!isParanthesisBalanced(mText.getText().toString())) {
                mResultBar.setTextColor(getResources().getColorStateList(R.color.red));
                mResultBar.setText(new StringBuilder().append("Invalid Expression!"));
            } else {

                Tokenizer tokenizer = new Tokenizer(mText.getText().toString());
                ArrayList<Token> tokens = tokenizer.getTokens();
                Parser parser = new Parser(tokens);
                ArrayList<Token> postfix = parser.parse();
                float result = evaluator.eval(postfix);
                if (result == Float.MIN_VALUE) {
                    mResultBar.setTextColor(getResources().getColorStateList(R.color.red));
                    mResultBar.setText(new StringBuilder().append("Invalid Expression!"));
                } else {
                    mResultBar.setTextColor(getResources().getColorStateList(R.color.blue));
                    mResultBar.setText(new StringBuilder().append(result));
                }

            }
        }
    }


    private boolean isParanthesisBalanced(String exp) {
        int lp = 0;
        int rp = 0;
        for (int i = 0 ; i < exp.length(); i++){
            if (exp.charAt(i) == '(') lp++;
            else if (exp.charAt(i) == ')') rp++;
        }
        return lp == rp;
    }

    private void clearAll() {
        mText.setText("");
        mResultBar.setText("");
        count = 0;
        enable_disableButtons(true);
    }

    private void checkDuplicateThenAppend(String s) {
        if (count == 56) {
            Toast.makeText(this, "Text limit exceeded!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!mResultBar.getText().toString().isEmpty()){
            mResultBar.setText("");
        }
        CharSequence cs = mText.getText();
        if(cs.length()==0) {
            append(s);
            count++;
        }
        else if(cs.charAt(cs.length()-1)==s.charAt(0)) return;
        else{
            append(s);
            count++;
        }
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
        if (count == 56) {
            Toast.makeText(this, "Text limit exceeded!", Toast.LENGTH_SHORT).show();
            enable_disableButtons(false);
            return;
        }

        if(!mResultBar.getText().toString().isEmpty()){
            mResultBar.setText("");
        }

        mText.append(s);
        count++;
    }

    private void enable_disableButtons(boolean b) {

//        ColorStateList tv_color,btn_color;
//        if (b) {
//            tv_color = getResources().getColorStateList(R.color.white);
//            btn_color = getResources().getColorStateList(R.color.btn_color);
//        }
//        tv_color = getResources().getColorStateList(R.color.deg_rad_color_disable);
//        btn_color = getResources().getColorStateList(R.color.black);
        m0.setEnabled(b);
        m00.setEnabled(b);
        m1.setEnabled(b);
        m2.setEnabled(b);
        m3.setEnabled(b);
        m4.setEnabled(b);
        m5.setEnabled(b);
        m6.setEnabled(b);
        m7.setEnabled(b);
        m8.setEnabled(b);
        m9.setEnabled(b);
        mDot.setEnabled(b);
//        mDot.setTextColor(btn_color);
        mAdd.setEnabled(b);
//        mAdd.setTextColor(btn_color);
        mSub.setEnabled(b);
//        mSub.setTextColor(btn_color);
        mMul.setEnabled(b);
//        mMul.setTextColor(btn_color);
        mDiv.setEnabled(b);
//        mDiv.setTextColor(btn_color);
        mPer.setEnabled(b);
//        mPer.setTextColor(btn_color);

        mSin.setEnabled(b);
//        mSin.setTextColor(tv_color);
        mCos.setEnabled(b);
//        mCos.setTextColor(tv_color);
        mTan.setEnabled(b);
//        mTan.setTextColor(tv_color);
        mLog.setEnabled(b);
//        mLog.setTextColor(tv_color);
        mLn.setEnabled(b);
//        mLn.setTextColor(tv_color);
        mlb.setEnabled(b);
//        mlb.setTextColor(tv_color);
        mrb.setEnabled(b);
//        mrb.setTextColor(tv_color);
        mPow.setEnabled(b);
//        mPow.setTextColor(tv_color);
        mSqrt.setEnabled(b);
//        mSqrt.setTextColor(tv_color);
        mFact.setEnabled(b);
//        mFact.setTextColor(tv_color);
        mPi.setEnabled(b);
//        mPi.setTextColor(tv_color);
        mE.setEnabled(b);
//        mE.setTextColor(tv_color);
        mCubeRt.setEnabled(b);
//        mCubeRt.setTextColor(tv_color);

    }




}