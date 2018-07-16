package com.nataliakt.analyzer.oasis;

import com.nataliakt.analyzer.lexical.model.Token;
import com.nataliakt.analyzer.semantic.SemanticAnalyzer;
import com.nataliakt.analyzer.semantic.model.*;
import com.nataliakt.analyzer.semantic.model.expression.*;

import java.util.List;
import java.util.stream.Collectors;

public class OasisSemantic extends SemanticAnalyzer {

    public static final int CLASS = 0;
    public static final int ATTRIBUTE = 1;
    public static final int METHOD = 2;
    public static final int IF = 3;

    private int endVariable = 0;
    private Scope currentScope;

    public OasisSemantic() {
        super(new OasisSintatic());
    }

    @Override
    public void addAction(int action, Token token) {
        List<Token> tokens;
        int index;
        switch (action) {
            case CLASS:
                // Nova classe
                Scope newClass = new Scope(token.getValue(), getProgram());
                currentScope = newClass;

                break;

            case ATTRIBUTE:
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
                        index = -1;
                        variable = new Variable(token.getValue(), tokenId.getValue());
                    } else {
                        endVariable = index + 2;
                        variable = new Variable(token.getValue(), tokenId.getValue(), getVariableExpression(token.getValue(), tokenValue));

                        if (tokens.get(endVariable).getName().equals("V")) {
                            index = endVariable + 1;
                        } else {
                            index = -1;
                        }
                    }

                    if (variable != null) {
                        currentScope.addVariable(variable);
                    }
                } while(index != -1);

                break;

            case METHOD:
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

                currentScope = currentScope.getProgramChild();

                Function method = new Function(methodName, currentScope);
                method.setMain(main);
                method.setPrivated(privated);
                currentScope = method;

                // Ignora o AP
                index += 2;

                // Busca os atributos
                Token type = tokens.get(index);
                while (!type.getName().equals("FP")) {
                    Variable variable = null;
                    Token tokenId = tokens.get(++index);
                    Token tokenDef = tokens.get(++index);

                    if (tokenDef.getName().equals("DEF")) {
                        endVariable = index + 1;
                        Token tokenValue = tokens.get(++index);
                        variable = new Variable(type.getValue(), tokenId.getValue(), getVariableExpression(type.getValue(), tokenValue));
                        index = endVariable;
                    } else {
                        variable = new Variable(type.getValue(), tokenId.getValue());
                    }

                    ((Function) currentScope).addParam(variable);

                    Token tokenV = tokens.get(index);
                    if (tokenV.getName().equals("V")) {
                        index++;
                    }
                    type = tokens.get(index);
                }

                // Pula os PF e DP
                index++;
                Token tokenDp = tokens.get(index);
                if (tokenDp.getName().equals("DP")) {
                    Token tokenType = tokens.get(++index);
                    Token tokenId = tokens.get(++index);

                    Token tokenDef = tokens.get(++index);

                    Variable variable;
                    if (tokenDef.getName().equals("DEF")) {
                        Token tokenValue = tokens.get(++index);
                        variable = new Variable(tokenType.getValue(), tokenId.getValue(), getVariableExpression(tokenType.getValue(), tokenValue));
                    } else {
                        variable = new Variable(tokenType.getValue(), tokenId.getValue());
                    }

                    method.setReturnVariable(variable);
                }

                break;

            case IF:
                tokens = getTokens().collect(Collectors.toList());
                index = tokens.indexOf(token);

                Token tokenExp = tokens.get(++index);
                Expression exp = getVariableExpression("bit", tokenExp);

                Condition condition = new Condition(currentScope, exp);
                currentScope = condition;

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
                expression = new And(Boolean.valueOf(false));
                expression.setExpression(getVariableExpression("bit", tokens.get(++index)));
                return expression;

            case "AP":
                endVariable += 2;
                return getVariableExpression(type, tokens.get(++index));

            default:
                endVariable++;
                Token exp = tokens.get(++index);

                switch (exp.getName()) {
                    case "E":
                        if (!type.equals("bit")) {
                            throw new WrongDefinitionException("bit", type);
                        }
                        expression = new And((Boolean) getValueOf("bit", tokenValue));
                        break;
                    case "OU":
                        if (!type.equals("bit")) {
                            throw new WrongDefinitionException("bit", type);
                        }
                        expression = new Or((Boolean) getValueOf("bit", tokenValue));
                        break;

                    case "IGUAL":
                        if (!type.equals("bit")) {
                            throw new WrongDefinitionException("bit", type);
                        }
                        expression = new Equal(getValueOf(type, tokenValue));
                        break;
                    case "DIF":
                        if (!type.equals("bit")) {
                            throw new WrongDefinitionException("bit", type);
                        }
                        expression = new Different(getValueOf(type, tokenValue));
                        break;
                    case "MAIOR":
                        if (!type.equals("bit")) {
                            throw new WrongDefinitionException("bit", type);
                        }
                        expression = new Bigger(getValueOf(type, tokenValue));
                        break;
                    case "MENOR":
                        if (!type.equals("bit")) {
                            throw new WrongDefinitionException("bit", type);
                        }
                        expression = new Smaller(getValueOf(type, tokenValue));
                        break;
                    case "MAIORIGUAL":
                        if (!type.equals("bit")) {
                            throw new WrongDefinitionException("bit", type);
                        }
                        expression = new BiggerEqual(getValueOf(type, tokenValue));
                        break;
                    case "MENORIGUAL":
                        if (!type.equals("bit")) {
                            throw new WrongDefinitionException("bit", type);
                        }
                        expression = new SmallerEqual(getValueOf(type, tokenValue));
                        break;

                    case "SOMA":
                        if (!type.equals("decimal") && !type.equals("integer") && !type.equals("string")) {
                            throw new WrongDefinitionException("decimal, integer ou string", type);
                        }
                        expression = new Sum(getValueOf(type, tokenValue));
                        break;
                    case "SUB":
                        if (!type.equals("decimal") && !type.equals("integer")) {
                            throw new WrongDefinitionException("decimal ou integer", type);
                        }
                        expression = new Sub(getValueOf(type, tokenValue));
                        break;
                    case "MULT":
                        if (!type.equals("decimal") && !type.equals("integer")) {
                            throw new WrongDefinitionException("decimal ou integer", type);
                        }
                        expression = new Mult(getValueOf(type, tokenValue));
                        break;
                    case "DIV":
                        if (!type.equals("decimal") && !type.equals("integer")) {
                            throw new WrongDefinitionException("decimal ou integer", type);
                        }
                        expression = new Div(getValueOf(type, tokenValue));
                        break;

                    default:
                        expression = new Expression(getValueOf(type, tokenValue));
                        return expression;
                }

                endVariable++;
                expression.setExpression(getVariableExpression(type, tokens.get(++index)));
                return expression;
        }
    }

    private Object getValueOf(String type, Token tokenValue) {
        String expected;
        String value = tokenValue.getValue();
        switch (type) {
            case "integer":
                if (tokenValue.getName().equals("INTEIRO")) {
                    return Integer.parseInt(value);
                }
                expected = "INTEIRO";
                break;
            case "decimal":
                if (tokenValue.getName().equals("REAL") || tokenValue.getName().equals("INTEIRO")) {
                    return Double.parseDouble(value);
                }
                expected = "REAL ou INTEIRO";
                break;
            case "string":
                if (tokenValue.getName().equals("TEXTO")) {
                    return value.substring(1, value.length() - 2);
                }
                expected = "TEXTO";
                break;
            case "bit":
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
