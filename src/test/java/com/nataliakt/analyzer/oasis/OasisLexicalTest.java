package com.nataliakt.analyzer.oasis;

import com.nataliakt.analyzer.lexical.LexicalAnalyzer;
import com.nataliakt.analyzer.lexical.model.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OasisLexicalTest {

    @Test
    void testNewClass() {
        LexicalAnalyzer lexicalAnalyzer = new OasisLexical();
        List<Token> tokens = lexicalAnalyzer.analyze("Classe {}");

        int count = 0;
        assertEquals(3, tokens.size(), "Quantidade de tokens inesperada");
        assertEquals("<MAID,Classe> ", tokens.get(count++).toString(), "Id de classe não idetificado");
        assertEquals("<AC,{> ", tokens.get(count++).toString(), "Id de abertura de chaves não idetificado");
        assertEquals("<FC,}> ", tokens.get(count++).toString(), "Id de fechamento de chaves não idetificado");
    }

    @Test
    void testTypes() {
        LexicalAnalyzer lexicalAnalyzer = new OasisLexical();
        List<Token> tokens = lexicalAnalyzer.analyze("integer decimal bit string " +
                "'olá' \"olá\" 1 2.2 true false");

        int count = 0;
        assertEquals(10, tokens.size(), "Quantidade de tokens inesperada");
        assertEquals("<INT,integer> ", tokens.get(count++).toString(), "Tipo inteiro não identificado");
        assertEquals("<DOUBLE,decimal> ", tokens.get(count++).toString(), "Tipo decimal não identificado");
        assertEquals("<BOOLEAN,bit> ", tokens.get(count++).toString(), "Tipo booleano não identificado");
        assertEquals("<STRING,string> ", tokens.get(count++).toString(), "Tipo string não identificado");

        assertEquals("<TEXTO,'olá'> ", tokens.get(count++).toString(), "Texto não identificado");
        assertEquals("<TEXTO,\"olá\"> ", tokens.get(count++).toString(), "Texto não identificado");
        assertEquals("<INTEIRO,1> ", tokens.get(count++).toString(), "Inteiro não identificado");
        assertEquals("<REAL,2.2> ", tokens.get(count++).toString(), "Decimal não identificado");
        assertEquals("<TRUE,true> ", tokens.get(count++).toString(), "Booleano verdadeiro não identificado");
        assertEquals("<FALSE,false> ", tokens.get(count++).toString(), "Booleano falso não identificado");
    }
}