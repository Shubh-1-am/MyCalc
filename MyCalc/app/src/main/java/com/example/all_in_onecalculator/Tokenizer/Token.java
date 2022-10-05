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

    public boolean isMisOp() {
        return misOp;
    }

    public void setMisOp(boolean misOp) {
        this.misOp = misOp;
    }
}
