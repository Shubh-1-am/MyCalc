package com.example.all_in_onecalculator.Parser;

import android.util.Log;

import com.example.all_in_onecalculator.Tokenizer.Token;
import com.example.all_in_onecalculator.Tokenizer.TokenType;

import java.util.ArrayList;
import java.util.Stack;

public class Parser {

    private ArrayList<Token> mTokens;
    private Stack<Token> mOperators;

    public Parser(ArrayList<Token> tokens){
        mTokens = tokens;
        mOperators = new Stack<>();
    }

    public ArrayList<Token> parse(){
        ArrayList<Token> postfix = new ArrayList<>();
        for(Token token : mTokens){
            if(token.isOperator()){
                if(!mOperators.isEmpty()){

                    if(token.getTokenType() == TokenType.RIGHT_PAREN){
                        while (mOperators.peek().getTokenType() != TokenType.LEFT_PAREN){
                            postfix.add(mOperators.pop());
                        }
                        mOperators.pop();
                    }
                    while (precedence(token) <= precedence(mOperators.peek())) {
                        postfix.add(mOperators.pop());
                    }
                }
                mOperators.add(token);
            } else {
                postfix.add(token);
            }
        }

        while (!mOperators.empty()){
            postfix.add(mOperators.pop());
        }
        return  postfix;
    }

    private int precedence(Token token) {

        switch (token.getTokenType()) {
            case PLUS:
            case MINUS: return 0;
            case DIV:
            case MUL: return 1;
            case POW: return 2;
            case ln:
            case LOG:
            case COS:
            case SIN:
            case TAN:
            case FACT:
            case PERC:
            case Sqrt:
            case CUBErt: return 3;

        }
        return -1;
    }

}
