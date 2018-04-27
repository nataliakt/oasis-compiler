package com.nataliakt.analyzer;

import com.nataliakt.analyzer.lexical.LexicalAnalyzer;
import com.nataliakt.analyzer.oasis.OasisLexical;
import com.nataliakt.analyzer.oasis.OasisSintatic;
import com.nataliakt.analyzer.oasis.OasisSintaticConstants;

import static com.nataliakt.analyzer.oasis.OasisSintaticConstants.PARSER_TABLE;
import static com.nataliakt.analyzer.oasis.OasisSintaticConstants.PRODUCTIONS;


public class Main
{
	
	public static void main(String[] args)
	{
//        LexicalAnalyzer la = new OasisLexical();
//        System.out.println(la.analyze("integer"));

		OasisSintatic os = new OasisSintatic();
		System.out.println(os.analyze("Class {\n" +
				"integer asd\n" +
				"main teste():bit opi{}\n" +
				"}"));
	}

}
