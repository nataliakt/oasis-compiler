package com.nataliakt.analyzer.oasis;

import com.nataliakt.analyzer.sintatic.SintaticAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OasisSintaticTest {

    @Test
    void testNewClass() {
        SintaticAnalyzer sintaticAnalyzer = new OasisSintatic();
        boolean analize = sintaticAnalyzer.analyze("Classe {}");

        assertEquals(true, analize, "Falha ao analisar classe");
    }

    @Test
    void testNewsClass() {
        SintaticAnalyzer sintaticAnalyzer = new OasisSintatic();
        boolean analize = sintaticAnalyzer.analyze("Classe {} Classe {}");

        assertEquals(true, analize, "Falha ao analisar classes");
    }

    @Test
    void testAtributes() {
        SintaticAnalyzer sintaticAnalyzer = new OasisSintatic();
        boolean analize = sintaticAnalyzer.analyze("Classe {\n" +
                "integer a, a = 999\n" +
                "bit a = true\n" +
                "string a, test = 'olá'\n" +
                "decimal o = 2.9\n" +
                "}");

        assertEquals(true, analize, "Falha ao analisar atributos");
    }

    @Test
    void testMethods() {
        SintaticAnalyzer sintaticAnalyzer = new OasisSintatic();
        boolean analize = sintaticAnalyzer.analyze("Classe {\n" +
                "+metodoPublico () {}\n" +
                "}");

        assertEquals(true, analize, "Falha ao analisar métodos");
    }
}