package com.nataliakt.analyzer.semantic;

import com.nataliakt.analyzer.lexical.model.Token;
import com.nataliakt.analyzer.semantic.model.Scope;
import com.nataliakt.analyzer.sintatic.SintaticAnalyzer;

import java.util.List;
import java.util.stream.Stream;

public abstract class SemanticAnalyzer {

    private SintaticAnalyzer sintaticAnalyzer;
    private Scope program;

    public SemanticAnalyzer(SintaticAnalyzer sintaticAnalyzer) {
        this.sintaticAnalyzer = sintaticAnalyzer;
    }

    /**
     * Analiza um texto semanticamente e retorna uma árvore de escopos
     *
     * @param text
     *            Texto de entrada
     * @return Mapa de tokens no formato (token, valor do token)
     */
    public Scope analyze(String text)
    {
        program = new Scope("main");

        boolean sintatic = sintaticAnalyzer.analyze(text);
        assert sintatic;

        addAction(getTokens().findFirst().get());

        return program;
    }

    public abstract void addAction(Token token);

    public Scope getProgram() {
        return program;
    }

    public Stream<Token> getTokens() {
        return sintaticAnalyzer.getTokens();
    }

    public static class SemanticException extends RuntimeException
    {
        public SemanticException(String message)
        {
            super(message);
        }
    }
}
