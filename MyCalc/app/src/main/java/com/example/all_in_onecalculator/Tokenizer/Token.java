package com.example.all_in_onecalculator.Tokenizer;

public class Token {
    TokenType mTokenType;
    float mValue;
    boolean misOp;
    public Token(){misOp = false;}

    public TokenType getTokenType() {
        return mTokenType;
    }

    public void setTokenType(TokenType tokenType) {
        mTokenType = tokenType;
    }

    public float getValue() {
        return mValue;
    }

    public void setValue(float value) {
        mValue = value;
    }

    public boolean isOperator() {
        return misOp;
    }

    public void setIsOp(boolean misOp) {
        this.misOp = misOp;
    }
}
