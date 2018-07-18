package com.nataliakt.compiler.oasis;

import com.nataliakt.analyzer.oasis.OasisSemantic;
import com.nataliakt.analyzer.semantic.SemanticAnalyzer;
import com.nataliakt.compiler.assembly.AssemblyCompiler;

public class OasisAssembly extends AssemblyCompiler {

    public OasisAssembly() {
        super(new OasisSemantic());
    }
}
