package com.nataliakt.analyzer.oasis;

import com.nataliakt.analyzer.lexical.model.Token;
import com.nataliakt.analyzer.semantic.SemanticAnalyzer;
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
                        variable = getVariableTipe(token.getValue(), tokenId.getValue(), tokenValue.getValue());

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

                if (token.getName().equals("MAIN")) {
                    main = true;
                    token = tokens.get(index + 1);
                }

                if (token.getValue().substring(0,1).equals("-"))

                break;
        }
    }

    private Variable getVariableTipe(String type, String name, String value) {
        switch (type) {
            case "integer":
                return new Variable(type, name, Integer.parseInt(value));
            case "decimal":
                return new Variable(type, name, Double.parseDouble(value));
            case "string":
                return new Variable(type, name, value.substring(1, value.length() - 2));
            case "bit":
                return new Variable(type, name, value.equals("true"));
            default:
//                if (type.substring(0, 1).toUpperCase().equals(type.substring(0, 1))) {
//                    return new Variable(type, )
//                }
                return null;
        }
    }

}
