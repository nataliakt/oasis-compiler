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
//		System.out.println(PARSER_TABLE[0][41][1]);
//        LexicalAnalyzer la = new OasisLexical();
//        System.out.println(la.analyze("Class{\n}"));

		OasisSintatic os = new OasisSintatic();
		System.out.println(os.analyze("Class {" +
				"" +
				"}"));
	}

}
