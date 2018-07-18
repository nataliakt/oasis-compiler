package com.nataliakt.compiler.assembly;

import com.nataliakt.analyzer.semantic.model.Scope;
import com.nataliakt.compiler.oasis.OasisAssembly;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AssemblyCompilerTest {

    @Test
    void testVariable() {
        AssemblyCompiler assemblyCompiler = new OasisAssembly();
        Scope program = assemblyCompiler.getSemanticAnalyzer().analyze("Class {\n" +
                "integer a = 1\n" +
                "}");

        Map<String, List<String>> vAssembly = assemblyCompiler.compile(program);

        System.out.println("Código:");
        System.out.println();
        System.out.println(AssemblyCompiler.getCode(vAssembly.get("a")));
        System.out.println("FIM");

        assertEquals("{a=[mov eax, 1]}", vAssembly.toString());
    }

    @Test
    void testSum() {
        AssemblyCompiler assemblyCompiler = new OasisAssembly();
        Scope program = assemblyCompiler.getSemanticAnalyzer().analyze("Class {\n" +
                "integer a = 1 + 1\n" +
                "}");

        Map<String, List<String>> vAssembly = assemblyCompiler.compile(program);

        System.out.println("Código:");
        System.out.println();
        System.out.println(AssemblyCompiler.getCode(vAssembly.get("a")));
        System.out.println("FIM");

        assertEquals("{a=[mov eax, 1, mov ebx, 1, add eax, ebx]}", vAssembly.toString());
    }

    @Test
    void testSub() {
        AssemblyCompiler assemblyCompiler = new OasisAssembly();
        Scope program = assemblyCompiler.getSemanticAnalyzer().analyze("Class {\n" +
                "integer a = 5 - 1\n" +
                "}");

        Map<String, List<String>> vAssembly = assemblyCompiler.compile(program);

        System.out.println("Código:");
        System.out.println();
        System.out.println(AssemblyCompiler.getCode(vAssembly.get("a")));
        System.out.println("FIM");

        assertEquals("{a=[mov eax, 5, mov ebx, 1, sub eax, ebx]}", vAssembly.toString());
    }

    @Test
    void testMult() {
        AssemblyCompiler assemblyCompiler = new OasisAssembly();
        Scope program = assemblyCompiler.getSemanticAnalyzer().analyze("Class {\n" +
                "integer a = 5 * 1\n" +
                "}");

        Map<String, List<String>> vAssembly = assemblyCompiler.compile(program);

        System.out.println("Código:");
        System.out.println();
        System.out.println(AssemblyCompiler.getCode(vAssembly.get("a")));
        System.out.println("FIM");

        System.out.println(vAssembly);

        assertEquals("{a=[mov eax, 5, mov ebx, 1, mov edx, 0, mul ebx]}", vAssembly.toString());
    }

    @Test
    void testDiv() {
        AssemblyCompiler assemblyCompiler = new OasisAssembly();
        Scope program = assemblyCompiler.getSemanticAnalyzer().analyze("Class {\n" +
                "integer a = 5 / 1\n" +
                "}");

        Map<String, List<String>> vAssembly = assemblyCompiler.compile(program);

        System.out.println("Código:");
        System.out.println();
        System.out.println(AssemblyCompiler.getCode(vAssembly.get("a")));
        System.out.println("FIM");

        System.out.println(vAssembly);

        assertEquals("{a=[mov eax, 5, mov ebx, 1, mov edx, 0, div ebx]}", vAssembly.toString());
    }

    @Test
    void testVariableLink() {
        AssemblyCompiler assemblyCompiler = new OasisAssembly();
        Scope program = assemblyCompiler.getSemanticAnalyzer().analyze("Class {\n" +
                "integer a = 5, b = a\n" +
                "}");

        Map<String, List<String>> vAssembly = assemblyCompiler.compile(program);

        System.out.println("Código:");
        System.out.println();
        System.out.println(AssemblyCompiler.getCode(vAssembly.get("b")));
        System.out.println("FIM");

        System.out.println(vAssembly);

        assertEquals("{a=[mov eax, 5], b=[mov eax, 5]}", vAssembly.toString());
    }

    @Test
    void testVariableSum() {
        AssemblyCompiler assemblyCompiler = new OasisAssembly();
        Scope program = assemblyCompiler.getSemanticAnalyzer().analyze("Class {\n" +
                "integer a = 4 / 2, b = a + 1\n" +
                "}");

        Map<String, List<String>> vAssembly = assemblyCompiler.compile(program);

        System.out.println("Código:");
        System.out.println();
        System.out.println(AssemblyCompiler.getCode(vAssembly.get("b")));
        System.out.println("FIM");

        System.out.println(vAssembly);

        assertEquals("{a=[mov eax, 4, mov ebx, 2, mov edx, 0, div ebx], b=[mov eax, 4, mov ebx, 2, mov edx, 0, div ebx, mov ebx, 1, add eax, ebx]}", vAssembly.toString());
    }

}