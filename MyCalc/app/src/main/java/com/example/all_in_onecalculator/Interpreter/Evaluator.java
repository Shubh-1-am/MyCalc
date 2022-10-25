package com.example.all_in_onecalculator.Interpreter;

import com.example.all_in_onecalculator.Tokenizer.Token;
import com.example.all_in_onecalculator.Tokenizer.TokenType;

import java.util.ArrayList;
import java.util.Stack;

public class Evaluator {


    private boolean isDeg;

    public boolean isDegree() {
        return isDeg;
    }

    public void setIsDegree(boolean deg) {
        isDeg = deg;
    }

    public  float eval(ArrayList<Token> tokens){
        Stack<Float> result = new Stack<>();
        for (int i = 0 ; i < tokens.size();i++){
           if (tokens.get(i).isOperator()){
               if (isBinaryOperator(tokens.get(i))){
                   float a = result.pop();
                   float b = result.pop();
                   float ans = operateBinaryOperator(a,b,tokens.get(i).getTokenType());
                   result.add(ans);
               }
               else {
                   float a = result.pop();
                   float ans = operateUnaryOperator(a,tokens.get(i).getTokenType());
                   result.add(ans);
               }

           }  else {
               result.add(tokens.get(i).getValue());
           }
           }
        return result.peek();
        }

    private float operateUnaryOperator(float a, TokenType tokenType) {
        switch (tokenType) {
            case Sqrt: return (float)Math.sqrt(a);
            case CUBErt: return (float) Math.cbrt(a);
            case FACT: return calculateFactorial((int)a);
            case SIN:
            case COS:
            case TAN: return calculateTrigFxn(a,tokenType);
            case LOG:
            case ln: return calculateLogfxn(a,tokenType);

        }
        return -1;
    }

    private float calculateLogfxn(float a, TokenType tokenType) {
        if (isDegree()) {
            a = (float) (a * Math.PI / 180.0);
        }
        switch (tokenType) {
            case LOG: return (float)Math.log10(a);
            case ln:  return (float)Math.log(a);
        }
        return -1;
    }

    private float calculateTrigFxn(float a, TokenType tokenType) {
        if (isDegree()) {
            a = (float) (a * Math.PI / 180.0);
        }

        switch (tokenType){
            case SIN: return (float)Math.sin(a);
            case COS: return (float)Math.cos(a);
            case TAN: return (float)Math.tan(a);
        }
        return -1;

    }

    private float calculatePercent(float b,float a) {
        return (float) (b*a/100.0);

    }

    private int calculateFactorial(int a) {
        if (a == 0) return 1;
        return a * calculateFactorial(a-1);
    }



    private float operateBinaryOperator(float a, float b, TokenType tokenType) {
        switch (tokenType) {
            case MUL: return a * b;
            case DIV: return b / a;
            case PLUS: return a + b;
            case MINUS: return b - a;
            case POW: return (float)Math.pow(b,a);
            case PERC: return calculatePercent(b,a);
        }
        return -1;
    }

    private boolean isBinaryOperator(Token token) {
        switch (token.getTokenType()) {
            case MUL:
            case DIV:
            case PLUS:
            case MINUS:
            case PERC:
            case POW: return true;
        }
      return false;
    }


}
