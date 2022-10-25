package com.example.all_in_onecalculator.Tokenizer;

import android.util.Log;

import java.util.ArrayList;

public class Tokenizer {

    ArrayList<Token> mTokens;
    String mText;
    int mLen;
    int mIndex;

    public Tokenizer(String expression){
        mText = expression;
        mLen = expression.length();
        mIndex = 0;
        mTokens = new ArrayList<>();

        while (mIndex < mLen) {
            char c = mText.charAt(mIndex);
            if (Character.isLetter(c)) mTokens.add(identifyMiscOp());
            else if (Character.isDigit(c)) mTokens.add(identifyNumbers());
//            else if (isPI(c)) mTokens.add(identifyPI());
            else if (isOperator(c)) mTokens.add(identifyOperators());
            else advance();
        }
    }

//    private Token identifyPI() {
//        Token token = new Token();
//        token.setTokenType(TokenType.NUMBER);
//        token.setValue((float) Math.PI);
//
//        advance();
//
//        return token;
//    }
//
//    private boolean isPI(char c) {
//        return c == 'π';
//    }

    private Token identifyMiscOp() {
        Token token = new Token();
        if (peek() == 'e') {
            token.setTokenType(TokenType.NUMBER);
            token.setValue((float) Math.E);
            advance();
            return token;
        }
        token.misOp = true;
        switch (peek()) {
            case 's': token.setTokenType(TokenType.SIN);mIndex += 3;break;
            case 'c': token.setTokenType(TokenType.COS);mIndex += 3;break;
            case 't': token.setTokenType(TokenType.TAN);mIndex += 3;break;
            case 'l':
                advance();
                if (peek() == 'o') {
                    token.setTokenType(TokenType.LOG);
                    mIndex += 2;
                }
                else {
                    token.setTokenType(TokenType.ln);
                    mIndex ++;
                }
                break;
        }
       return token;
    }


    private Token identifyNumbers() {
        Token token = new Token();
        token.setTokenType(TokenType.NUMBER);
        StringBuilder sb = new StringBuilder();
        char ch;
        while (mIndex < mLen && (Character.isDigit(ch=peek()) || ch == '.')) {
            sb.append(ch);
            advance();
        }
        token.setValue(Float.parseFloat(sb.toString()));
        return token;
    }

    private Token identifyOperators() {
    Token token = new Token();
    token.misOp = true;
    switch (peek()){
        case '+': token.setTokenType(TokenType.PLUS);break;
        case '-': token.setTokenType(TokenType.MINUS);break;
        case '*': token.setTokenType(TokenType.MUL);break;
        case '/': token.setTokenType(TokenType.DIV);break;
        case '(': token.setTokenType(TokenType.LEFT_PAREN);break;
        case ')': token.setTokenType(TokenType.RIGHT_PAREN);break;
        case '√': token.setTokenType(TokenType.Sqrt);break;
        case '∛': token.setTokenType(TokenType.CUBErt);break;
        case '^': token.setTokenType(TokenType.POW);break;
        case '!': token.setTokenType(TokenType.FACT);break;
        case '%': token.setTokenType(TokenType.PERC);break;
        
    }
    advance();
    return token;
    }

    private boolean isOperator(char c) {

        switch (c){
            case '+':
            case '-':
            case '*':
            case '/':
            case ')':
            case '(':
            case '√':
            case '∛':
            case '^':
            case '!':
            case '%':
                return true;
        }
        return  false;
    }

    private void advance() { mIndex++;}
    private char peek() {return mText.charAt(mIndex);}

    public ArrayList<Token> getTokens() {
        return mTokens;
    }


}



