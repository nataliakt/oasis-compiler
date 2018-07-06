package com.nataliakt.analyzer.oasis;

import com.nataliakt.analyzer.sintatic.SintaticAnalyzer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OasisSintaticTest {

    SintaticAnalyzer sintaticAnalyzer;

    @BeforeEach
    void setUp() {
        sintaticAnalyzer = new OasisSintatic();
    }

    @Test
    void testNewClass() {
        boolean analize = sintaticAnalyzer.analyze("Classe {}");

        assertEquals(true, analize, "Falha ao analisar classe");
    }

    @Test
    void testAtributes() {
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
        boolean analize = sintaticAnalyzer.analyze("Classe {\n" +
                "main iniciar () {}\n" +
                "run thread () {}\n" +
                "+metodoPublico () : bit resposta{\n" +
                "resposta = true\n" +
                "}\n" +
                "}");

        assertEquals(true, analize, "Falha ao analisar métodos");
    }
}