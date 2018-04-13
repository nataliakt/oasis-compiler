package com.nataliakt.lexicalanalyzer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.nataliakt.lexicalanalyzer.model.State;
import com.nataliakt.lexicalanalyzer.model.Token;
import com.nataliakt.sintaticanalyzer.OasisSintatic;

public class Main
{
	
	public static void main(String[] args) throws IOException
	{
//		String path = "/home/natalia/Documentos/lexico.html";
//		byte[] encoded = Files.readAllBytes(Paths.get(path));
//		String html = new String(encoded, Charset.defaultCharset());
//		
//		String code = "Class {\n}";
//		LexicalAnalyzer l = ParseGals.parse(html);
//		List<Token> tokens = l.analyze(code);
//		System.out.println(tokens);
		
		OasisSintatic os = new OasisSintatic();
		System.out.println(os.analyze("if (6) begin end"));
		
	}

	public void main2()
	{

		State q0 = new State("q0"); // Inicial
		State q1 = new State("q1", "ID");
		State q2 = new State("q2", "IF");
		State q3 = new State("q3", "ID");
		State q4 = new State("q4", "CONSTANTE");
		State q5 = new State("q5", "ABRE_PARENTESES");
		State q6 = new State("q6", "FECHA_PARENTESES");
		State q7 = new State("q7", "OPERADOR_MAIOR");
		State q8 = new State("q8", "OPERADOR_MENOR");
		State q9 = new State("q9", "OPERADOR_DIFERENTE");
		State q10 = new State("q10", "ID");
		State q11 = new State("q11", "ID");
		State q12 = new State("q12", "ID");
		State q13 = new State("q13", "ID");
		State q14 = new State("q14", "BEGIN");
		State q15 = new State("q15", "ID");
		State q16 = new State("q16", "ID");
		State q17 = new State("q17", "END");
		State q18 = new State("q18", "ESPACO");
		State q19 = new State("q19", "ID");
		State q20 = new State("q20", "ID");
		State q21 = new State("q21", "ELSE");

		// IF
		q0.put("[i]", q1);
		q1.put("[f]", q2);
		q1.put("[^\\Wf]", q3);
		q2.put("[\\w]", q3);
		
		// ID
		q0.put("[^\\Wbei0-9_]", q3);
		q3.put("[\\w]", q3);
		
		// CONSTANTE
		q0.put("[\\d]", q4);
		q4.put("[\\d]", q4);
		
		// ABRE_PARENTESES
		q0.put("[(]", q5);
		
		// ABRE_PARENTESES
		q0.put("[)]", q6);
		
		// OPERADOR_MAIOR
		q0.put("[>]", q7);
		
		// OPERADOR_MENOR
		q0.put("[<]", q8);
		
		// OPERADOR_DIFERENTE
		q0.put("[#]", q9);
		
		// BEGIN
		q0.put("[b]", q10);
		q10.put("[e]", q11);
		q11.put("[g]", q12);
		q12.put("[i]", q13);
		q13.put("[n]", q14);
		q10.put("[^\\We]", q3);
		q11.put("[^\\Wg]", q3);
		q12.put("[^\\Wi]", q3);
		q13.put("[^\\Wn]", q3);
		q14.put("[\\w]", q3);
		
		// END
		q0.put("[e]", q15);
		q15.put("[n]", q16);
		q16.put("[d]", q17);
		q15.put("[^\\Wnl]", q3);
		q16.put("[^\\Wd]", q3);
		q17.put("[\\w]", q3);
		
		// ELSE
		q15.put("[l]", q19);
		q19.put("[s]", q20);
		q20.put("[e]", q21);
		q19.put("[^\\Ws]", q3);
		q20.put("[^\\We]", q3);
		q21.put("[\\w]", q3);
		
		// ESPACO
		q0.put("[\\s]", q18);

		LexicalAnalyzer lexicalAnalyzer = new OasisLexical();
		String code = "if (10 # soma)\n"
						+ "begin\n"
						+ "end\n"
					+ "else";
		List<Token> tokens = lexicalAnalyzer.analyze(code);
		System.out.println(tokens.toString());

	}

}
