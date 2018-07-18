package com.nataliakt.analyzer.oasis;

import com.nataliakt.analyzer.semantic.SemanticAnalyzer;
import com.nataliakt.analyzer.semantic.model.Scope;
import com.nataliakt.analyzer.semantic.model.Scope.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class OasisSemanticTest {

    @Test
    void testClassNamesEquals() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();

        Executable executable = () -> {
            Scope program = semanticAnalyzer.analyze("Classe {} Classe{}");
        };

        assertThrows(AlreadyExistingScopeNameException.class, executable, "Erro ao dispara exception de nome de classe em uso");
    }

    @Test
    void testClassNames() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe1 {} Classe2 {}");

        assertEquals("main", program.getName(), "Escopo de programa inválido");

        Scope[] scopes = program.getChildren().toArray(Scope[]::new);

        assertEquals(2, scopes.length, "Número de filhos inválido");
        assertEquals("Classe1", scopes[0].getName(), "Escopo de classe inválido");
        assertEquals("Classe2", scopes[1].getName(), "Escopo de classe inválido");
    }

    @Test
    void testAttribute() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe {\n" +
                "integer teste, teste2 = 2\n" +
                "string texto = 'oii'\n" +
                "}" +
                "Classe2{\nbit teste=true\n}");

        assertEquals("main[][Classe[integer teste=null, integer teste2=2, string texto=oi][], Classe2[bit teste=true][]]",program.toString());
    }

    @Test
    void testAttributeExpressions() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe {\n" +
                "integer numero = 1 + 1, a\n" +
                "bit verdades = true and true\n" +
                "}");

        assertEquals("main[][Classe[integer numero=(1 + 1), integer a=null, bit verdades=(true and true)][]]", program.toString());
    }

    @Test
    void testAttributeExixts() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Executable executable = () -> {
            Scope program = semanticAnalyzer.analyze("Classe {\n" +
                    "integer teste, teste = 2\n" +
                    "}" +
                    "Classe2{\nbit teste=true\n}");
        };

        assertThrows(AlreadyExistingVariableNameException.class, executable);
    }

    @Test
    void testAttributeWrongDefinition() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Executable executable = () -> {
            Scope program = semanticAnalyzer.analyze("Classe {\n" +
                    "integer teste = true\n" +
                    "}" +
                    "Classe2{\nbit teste=true\n}");
        };

        assertThrows(OasisSemantic.WrongDefinitionException.class, executable);
    }

    @Test
    void testMethod() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe {\n" +
                "main inicio() {}\n" +
                "+soma (decimal n1 = 0 + 5, decimal n2 = 0) : decimal soma {}\n" +
                "retornoDefault (string param) : bit retorno = true or true {}\n" +
                "}");

        assertEquals("main[][Classe[][inicio[][], soma[decimal n1=(0.0 + 5.0), decimal n2=0.0, decimal soma=null][], retornoDefault[string param=null, bit retorno=(true or true)][]]]", program.toString());
    }

    @Test
    void testIf() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe {\n" +
                "main inicio() {\n" +
                "if ^true {} else {}\n" +
                "}\n" +
                "}");

        assertEquals("main[][Classe[][inicio[][<IF ^(true)>[][], <ELSE null>[][]]]]", program.toString());
    }

    @Test
    void testWhile() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe {\n" +
                "main inicio() {\n" +
                "while true {}\n" +
                "}\n" +
                "}");

        assertEquals("main[][Classe[][inicio[][<WHILE true:null>[][]]]]", program.toString());
    }

    @Test
    void testDefinition() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe {\n" +
                "integer var\n"+
                "main inicio() {\n" +
                "var = 1\n" +
                "}\n" +
                "}");

        assertEquals("main[][Classe[integer var=null][inicio[][var = 1]]]", program.toString());
    }

    @Test
    void testDefinitionVariable() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe {\n" +
                "integer var, var2 = 1\n"+
                "main inicio() {\n" +
                "var = var2\n" +
                "}\n" +
                "}");

        assertEquals("main[][Classe[integer var=null, integer var2=1][inicio[][var = integer var2=1]]]", program.toString());
    }

    @Test
    void testNewDefinition() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe {\n" +
                "main inicio() {\n" +
                "integer var = 0\n" +
                "}\n" +
                "}");

        assertEquals("main[][Classe[][inicio[integer var=null][var = 0]]]", program.toString());
    }

    @Test
    void testFor() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe {\n" +
                "main inicio() {\n" +
                "for integer i = 0: i < 10: i=i+1{}\n" +
                "}\n" +
                "}");

        assertEquals("main[][Classe[][inicio[integer i=null][i = 0, <FOR (integer i=null < 10):i = (integer i=null + 1)>[][]]]]", program.toString());
    }

}