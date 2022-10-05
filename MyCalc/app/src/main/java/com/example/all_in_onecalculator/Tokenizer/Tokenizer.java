package com.example.all_in_onecalculator.Tokenizer;

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
            if (Character.isLetter(c)) mTokens.add(identifyTrigonometryOp());
            else if (Character.isDigit(c)) mTokens.add(identifyNumbers());
            else if (isOperator(c)) mTokens.add(identifyOperators());
            else advance();
        }
    }

    private Token identifyTrigonometryOp() {
        Token token = new Token();
        token.setTokenType(TokenType.NUMBER);
        StringBuilder sb = new StringBuilder();
        switch (peek()){
            case 's': sb.append(evaluateSIN());break;
            case 'c': sb.append(evaluateCOS()); break;
            case 't': sb.append(evaluateTAN()); break;
            case 'l': sb.append(evaluateLOG()); break;
        }
        advance();
        token.setValue(Float.parseFloat(sb.toString()));
        return token;
    }

    private String evaluateSIN() {
        mIndex += 4;
        String arg = "";
        char ch;
        while(mIndex < mLen && (ch=peek())!=')') {
            arg += ch;
            advance();
        }
        double temp = Math.toRadians(Double.parseDouble(arg));
        float res = (float) Math.sin(temp);
        return Float.toString(res);
    }
    private String evaluateCOS() {
        mIndex += 4;
        String arg = "";
        char ch;
        while(mIndex < mLen && (ch=peek())!=')') {
            arg += ch;
            advance();
        }
        double temp = Math.toRadians(Double.parseDouble(arg));
        float res = (float) Math.cos(temp);
        return Float.toString(res);
    }
    private String evaluateTAN() {
        mIndex += 4;
        String arg = "";
        char ch;
        while(mIndex < mLen && (ch=peek())!=')') {
            arg += ch;
            advance();
        }
        double temp = Math.toRadians(Double.parseDouble(arg));
        float res = (float) Math.tan(temp);
        return Float.toString(res);
    }
    private String evaluateLOG() {
        advance();
        String arg = "";
        char ch;
        if (peek() == 'o') {
            mIndex = mIndex + 3;

            while (mIndex < mLen && ((ch = peek())!= ')')) {
                arg += ch;
                advance();
            }
            double temp = Double.parseDouble(arg);
            float res = (float) Math.log10(temp);
            return Float.toString(res);
        }
        else {
            while (mIndex < mLen && ((ch = peek())!= ')')) {
                arg += ch;
                advance();
            }
            double temp = Double.parseDouble(arg);
            float res = (float) Math.log(temp);
            return Float.toString(res);
        }
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
