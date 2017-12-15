package yanhui.test;

import yanhui.exception.ParseException;
import yanhui.utils.CodeDialog;
import yanhui.utils.Lexer;
import yanhui.utils.Token;

public class LexerTest {
	public static void main(String[] args) throws ParseException{
		Lexer l = new Lexer(new CodeDialog());
		for (Token t; (t = l.read()) != Token.EOF; ){
			System.out.println("->" + t.getText());
		}
	}
}
