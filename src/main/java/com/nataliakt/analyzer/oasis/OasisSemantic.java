package com.nataliakt.analyzer.oasis;

import com.nataliakt.analyzer.lexical.model.Token;
import com.nataliakt.analyzer.semantic.SemanticAnalyzer;
import com.nataliakt.analyzer.semantic.model.Function;
import com.nataliakt.analyzer.semantic.model.Scope;
import com.nataliakt.analyzer.semantic.model.Variable;

import java.util.List;
import java.util.stream.Collectors;

public class OasisSemantic extends SemanticAnalyzer {

    public static final int CLASS = 0;
    public static final int ATTRIBUTE = 1;
    public static final int METHOD = 2;

    public Scope currentScope;

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
                        variable = getVariableTipe(token.getValue(), tokenId.getValue(), tokenValue);

                        if (tokens.get(index + 3).getName().equals("V")) {
                            index += 4;
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
                        Token tokenValue = tokens.get(++index);
                        variable = getVariableTipe(type.getValue(), tokenId.getValue(), tokenValue);
                        index++;
                    } else {
                        variable = new Variable(type.getValue(), tokenId.getValue());
                    }

                    currentScope.addVariable(variable);

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
                        variable = getVariableTipe(tokenType.getValue(), tokenId.getValue(), tokenValue);
                    } else {
                        variable = new Variable(tokenType.getValue(), tokenId.getValue());
                    }

                    method.setReturnVariable(variable);
                }

                break;
        }
    }

    private Variable getVariableTipe(String type, String name, Token tokenValue) {
        String value = tokenValue.getValue();
        String expected;
        switch (type) {
            case "integer":
                if (tokenValue.getName().equals("INTEIRO")) {
                    return new Variable(type, name, Integer.parseInt(value));
                }
                expected = "INTEIRO";
                break;
            case "decimal":
                if (tokenValue.getName().equals("REAL") || tokenValue.getName().equals("INTEIRO")) {
                    return new Variable(type, name, Double.parseDouble(value));
                }
                expected = "REAL ou INTEIRO";
                break;
            case "string":
                if (tokenValue.getName().equals("TEXTO")) {
                    return new Variable(type, name, value.substring(1, value.length() - 2));
                }
                expected = "TEXTO";
                break;
            case "bit":
                if (tokenValue.getName().equals("TRUE") || tokenValue.getName().equals("FALSE")) {
                    return new Variable(type, name, value.equals("true"));
                }
                expected = "TRUE ou FALSE";
                break;
            default:
                // TODO: classes
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
