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
    void testClassAttribute() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe {\n" +
                "integer teste, teste2 = 2\n" +
                "string texto = 'oii'\n" +
                "}" +
                "Classe2{\nbit teste=true\n}");

        assertEquals("main[][Classe[integer teste=null, integer teste2=2, string texto=oi][], Classe2[bit teste=true][]]",program.toString());
    }

    @Test
    void testClassAttributeExixts() {
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
    void testClassMethod() {
        SemanticAnalyzer semanticAnalyzer = new OasisSemantic();
        Scope program = semanticAnalyzer.analyze("Classe {\n" +
                "main inicio() {}\n" +
                "+soma (decimal n1, decimal n2) : decimal soma {}\n" +
                "}");

        System.out.println(program.toString());
    }

}