package com.nataliakt.analyzer.oasis;

import java.util.HashMap;
import java.util.Map;

import com.nataliakt.analyzer.sintatic.SintaticAnalyzer;
import com.nataliakt.analyzer.sintatic.model.Action;
import com.nataliakt.analyzer.sintatic.model.Action.ActionEnum;
import com.nataliakt.analyzer.sintatic.model.Actions;
import com.nataliakt.analyzer.sintatic.model.States;

import static com.nataliakt.analyzer.oasis.OasisLexicalConstants.TOKEN;
import static com.nataliakt.analyzer.oasis.OasisSintaticConstants.*;

/**
 * Implementação de {@link SintaticAnalyzer} para a linguagem Oasis
 */
public class OasisSintatic extends SintaticAnalyzer {

    private static Map<Integer, Actions> s = new HashMap<>();
    private static States states = new States(s, PRODUCTIONS);

    public OasisSintatic() {
        super(states, new OasisLexical());

        autoImport();
    }

    private void autoImport() {
        Map<String, Action> acs = new HashMap<>();

        for (int[][] stateLine : PARSER_TABLE) {
            int i = s.size();
            s.put(i, new Actions(acs));

            for (int t = 0; t < stateLine.length; t++) {
                if (stateLine[t].length == 2) {
                    switch (stateLine[t][0]) {
                        case SHIFT:
                            acs.put(TOKEN[t + 1], new Action(ActionEnum.SHIFT, stateLine[t][1]));
                            break;
                        case ACCEPT:
                            acs.put(TOKEN[t + 1], new Action(ActionEnum.ACC, stateLine[t][1]));
                            break;
                        case REDUCE:
                            acs.put(TOKEN[t + 1], new Action(ActionEnum.REDUCE, stateLine[t][1]));
                            break;
                        case GO_TO:
                            acs.put("TOKEN_" + t, new Action(ActionEnum.GO_TO, stateLine[t][1]));
                            break;
                    }
                }
            }

            acs = new HashMap<>(); // Limpando as actions
        }
    }

//    private void manual() {
//        Map<String, Action> acs = new HashMap<>();
//        acs.put("IF", new Action(ActionEnum.SHIFT, 2));
//        s.put(0, new Actions(acs));
//
//        acs = new HashMap<>(); // Limpando as actions
//
//        acs.put("$", new Action(ActionEnum.ACC, 0));
//        s.put(1, new Actions(acs));
//
//        acs = new HashMap<>(); // Limpando as actions
//
//        acs.put("ABRE_PARENTESES", new Action(ActionEnum.SHIFT, 3));
//        s.put(2, new Actions(acs));
//
//        acs = new HashMap<>(); // Limpando as actions
//
//        acs.put("ID", new Action(ActionEnum.SHIFT, 5));
//        acs.put("CONSTANTE", new Action(ActionEnum.SHIFT, 6));
//        s.put(3, new Actions(acs));
//
//        acs = new HashMap<>(); // Limpando as actions
//
//        acs.put("FECHA_PARENTESES", new Action(ActionEnum.SHIFT, 7));
//        s.put(4, new Actions(acs));
//
//        acs = new HashMap<>(); // Limpando as actions
//
//        acs.put("FECHA_PARENTESES", new Action(ActionEnum.REDUCE, 1));
//        s.put(5, new Actions(acs));
//
//        acs = new HashMap<>(); // Limpando as actions
//
//        acs.put("FECHA_PARENTESES", new Action(ActionEnum.REDUCE, 1));
//        s.put(6, new Actions(acs));
//
//        acs = new HashMap<>(); // Limpando as actions
//
//        acs.put("BEGIN", new Action(ActionEnum.SHIFT, 8));
//        s.put(7, new Actions(acs));
//
//        acs = new HashMap<>(); // Limpando as actions
//
//        acs.put("END", new Action(ActionEnum.REDUCE, 5));
//        s.put(8, new Actions(acs));
//
//        g.put(0, 1);
//        g.put(3, 4);
//    }

}
