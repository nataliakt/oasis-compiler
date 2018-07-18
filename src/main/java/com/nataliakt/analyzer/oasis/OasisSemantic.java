package com.nataliakt.analyzer.oasis;

import com.nataliakt.analyzer.lexical.model.Token;
import com.nataliakt.analyzer.semantic.SemanticAnalyzer;
import com.nataliakt.analyzer.semantic.model.*;
import com.nataliakt.analyzer.semantic.model.expression.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OasisSemantic extends SemanticAnalyzer {

    public static final String CLASS = "MAID";
    public static final String INT = "INT";
    public static final String DOUBLE = "DOUBLE";
    public static final String BOOLEAN = "BOOLEAN";
    public static final String STRING = "STRING";
    public static final String MAIN = "MAIN";
    public static final String MIID = "MIID";
    public static final String ENCAP = "ENCAP";
    public static final String IF = "IF";
    public static final String ELSE = "ELSE";
    public static final String FC = "FC";

    private int endVariable = 0;
    private Scope currentScope;
    private Token nextToken;

    public OasisSemantic() {
        super(new OasisSintatic());
        currentScope = getProgram();
    }

    @Override
    public void addAction(Token token) {
        List<Token> tokens = getTokens().collect(Collectors.toList());
        int nextIndex = tokens.indexOf(token) + 1;
        nextToken = null;
        if (nextIndex < tokens.size()) {
            nextToken = tokens.get(nextIndex);
        }

        if (currentScope == null) {
            currentScope = getProgram();
        }

        if (token.getName().equals(FC)) {
            currentScope = currentScope.getFather();
        } else if (currentScope == getProgram()) {
            programScope(token);
        } else if (currentScope.getFather() == getProgram()) {
            classScope(token);
        } else {
            methodScope(token);
        }

        if (nextToken != null) {
            addAction(nextToken);
        }


    }

    private void programScope(Token token) {
        switch (token.getName()) {
            case CLASS:
                // Nova classe
                Scope newClass = new Scope(token.getValue(), getProgram());
                currentScope = newClass;

                break;
        }
    }

    private void classScope(Token token) {
        List<Token> tokens;
        int index;

        switch (token.getName()) {
            // Atributos
            case INT:
            case DOUBLE:
            case BOOLEAN:
            case STRING:
                tokens = getTokens().collect(Collectors.toList());
                index = tokens.indexOf(token) + 1;
                do {
                    Token tokenId = tokens.get(index);
                    Token tokenDef = tokens.get(index + 1);
                    Token tokenValue = tokens.get(index + 2);
                    Variable variable = null;

                    if (tokenDef.getName().equals("V")) {
                        index += 2;
                        variable = new Variable(token.getValue(), tokenId.getValue());

                    } else if (tokenDef.getName().equals("NL")) {
                        nextToken = tokenDef;
                        index = -1;
                        variable = new Variable(token.getValue(), tokenId.getValue());
                    } else {
                        endVariable = index + 2;
                        variable = new Variable(token.getValue(), tokenId.getValue(), getVariableExpression(token.getValue(), tokenValue));
                        index = endVariable;

                        Token tokenV = tokens.get(index);
                        if (tokenV.getName().equals("V")) {
                            index++;
                        } else {
                            nextToken = tokens.get(endVariable);
                            index = -1;
                        }
                    }

                    if (variable != null) {
                        currentScope.addVariable(variable);
                    }
                } while (index != -1);

                break;

            // Método
            case MAIN:
            case MIID:
            case ENCAP:
                tokens = getTokens().collect(Collectors.toList());
                index = tokens.indexOf(token);
                boolean main = false;
                boolean privated = true;
                String methodName = token.getValue();

                if (token.getName().equals("MAIN")) {
                    main = true;
                    index++;
                    token = tokens.get(index);
                    methodName = token.getValue();
                }

                if (token.getValue().substring(0,1).equals("+")) {
                    privated = false;
                    methodName = token.getValue().substring(1);
                }

                if (token.getValue().substring(0,1).equals("-")) {
                    methodName = token.getValue().substring(1);
                }

                //currentScope = currentScope.getProgramChild();

                Function method = new Function(methodName, currentScope);
                method.setMain(main);
                method.setPrivated(privated);
                currentScope = method;

                // Ignora o AP
                index += 2;

                // Busca os atributos
                Token type = tokens.get(index);
                nextToken = type;
                while (!type.getName().equals("FP") && tokens.size() > index + 1) {
                    Variable variable = null;
                    Token tokenId = tokens.get(++index);
                    Token tokenDef = tokens.get(++index);

                    if (tokenDef.getName().equals("DEF")) {
                        endVariable = index + 1;
                        Token tokenValue = tokens.get(++index);
                        variable = new Variable(type.getValue(), tokenId.getValue(), getVariableExpression(type.getValue(), tokenValue));
                        index = endVariable;
                        nextToken = tokens.get(endVariable);
                    } else {
                        variable = new Variable(type.getValue(), tokenId.getValue());
                        nextToken = tokenDef;
                    }

                    ((Function) currentScope).addParam(variable);

                    Token tokenV = tokens.get(index);
                    if (tokenV.getName().equals("V")) {
                        index++;
                    }
                    type = tokens.get(index);
                }

                if (tokens.size() <= index + 1) {
                    return;
                }

                // Pula os PF e DP
                index++;
                Token tokenDp = tokens.get(index);
                nextToken = tokenDp;
                if (tokenDp.getName().equals("DP")) {
                    Token tokenType = tokens.get(++index);
                    Token tokenId = tokens.get(++index);

                    Token tokenDef = tokens.get(++index);

                    Variable variable;
                    if (tokenDef.getName().equals("DEF")) {
                        Token tokenValue = tokens.get(++index);
                        endVariable = index;
                        variable = new Variable(tokenType.getValue(), tokenId.getValue(), getVariableExpression(tokenType.getValue(), tokenValue));
                        nextToken = tokens.get(endVariable);
                    } else {
                        variable = new Variable(tokenType.getValue(), tokenId.getValue());
                        nextToken = tokenDef;
                    }

                    method.setReturnVariable(variable);
                }

                break;
        }
    }

    private void methodScope(Token token) {
        List<Token> tokens;
        int index;

        switch (token.getName()) {
            case IF:
                tokens = getTokens().collect(Collectors.toList());
                index = tokens.indexOf(token);

                Token tokenExp = tokens.get(++index);
                endVariable = index;
                Expression exp = getVariableExpression("bit", tokenExp);
                nextToken = tokens.get(endVariable);

                Condition condition = new Condition(currentScope, exp);
                currentScope = condition;

                break;

            case ELSE:
                tokens = getTokens().collect(Collectors.toList());
                index = tokens.indexOf(token);

                Condition scopeIf = (Condition) currentScope.getLastBrother(Condition.class);

                Token tokenVar = tokens.get(++index);
                if (tokenVar.getName().equals("AC")) {
                    Condition elseW = new Condition("ELSE", scopeIf);
                    nextToken = tokens.get(index);
                    currentScope = elseW;
                } else {
                    endVariable = index;
                    Expression var = getVariableExpression("bit", tokenVar);
                    nextToken = tokens.get(endVariable);
                    Condition elseIf = new Condition("ELSE IF", scopeIf, var);
                    currentScope = elseIf;
                }

                break;

        }
    }

    private Expression getVariableExpression(String type, Token tokenValue) {
        List<Token> tokens = getTokens().collect(Collectors.toList());
        int index = tokens.indexOf(tokenValue);
        Expression expression;

        switch (tokenValue.getName()) {
            case "NEG":
                if (!type.equals("bit")) {
                    throw new WrongDefinitionException("bit", type);
                }
                endVariable++;
                expression = new Not(getValueOf("bit", tokens.get(++index)));
                return expression;

            case "AP":
                endVariable += 2;
                return getVariableExpression(type, tokens.get(++index));

            default:
                endVariable++;
                Token exp = tokens.get(++index);
                Token value2 = tokens.get(++index);

                switch (exp.getName()) {
                    case "E":
                        expression = new And(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;
                    case "OU":
                        expression = new Or(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;

                    case "IGUAL":
                        expression = new Equal(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;
                    case "DIF":
                        expression = new Different(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;
                    case "MAIOR":
                        expression = new Bigger(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;
                    case "MENOR":
                        expression = new Smaller(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;
                    case "MAIORIGUAL":
                        expression = new BiggerEqual(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;
                    case "MENORIGUAL":
                        expression = new SmallerEqual(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;

                    case "SOMA":
                        expression = new Sum(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;
                    case "SUB":
                        expression = new Sub(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;
                    case "MULT":
                        expression = new Mult(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;
                    case "DIV":
                        expression = new Div(getValueOf(type, tokenValue), getValueOf(type, value2));
                        break;

                    default:
                        return new Value(getValueOf(type, tokenValue));
                }

                endVariable += 2;
                return expression;
        }
    }

    private Object getValueOf(Token tokenValue) {
        return getValueOf(tokenValue.getName(), tokenValue);
    }

    private Object getValueOf(String type, Token tokenValue) {
        String expected;
        String value = tokenValue.getValue();
        switch (type) {
            case "integer":
            case "INTEIRO":
                if (tokenValue.getName().equals("INTEIRO")) {
                    return Integer.parseInt(value);
                }
                expected = "INTEIRO";
                break;
            case "decimal":
            case "REAL":
                if (tokenValue.getName().equals("REAL") || tokenValue.getName().equals("INTEIRO")) {
                    return Double.parseDouble(value);
                }
                expected = "REAL ou INTEIRO";
                break;
            case "string":
            case "TEXTO":
                if (tokenValue.getName().equals("TEXTO")) {
                    return value.substring(1, value.length() - 2);
                }
                expected = "TEXTO";
                break;
            case "bit":
            case "TRUE":
            case "FALSE":
                if (tokenValue.getName().equals("TRUE") || tokenValue.getName().equals("FALSE")) {
                    return Boolean.valueOf(value.equals("true"));
                }
                expected = "TRUE ou FALSE";
                break;
            default:
                // TODO: classes e variáveis
//                if (type.substring(0, 1).toUpperCase().equals(type.substring(0, 1))) {
//                    return new Variable(type, )
//                }
                return null;
        }

        throw new WrongDefinitionException(expected, tokenValue.getName());
    }

    class WrongDefinitionException extends RuntimeException {

        WrongDefinitionException(String expected, String current) {
            super("Declaração de variável incorreta. Era esperado " + expected + " e foi passado " + current);
        }

    }

}
