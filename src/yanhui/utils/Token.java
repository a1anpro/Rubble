package yanhui.utils;

import yanhui.exception.RubbleException;

public abstract class Token {
	private int lineNumber; // ��token��λ��
	public static final Token EOF = new Token(-1){};//end of file
	public static final String EOL = "\\n"; // end of line
	
	protected Token(int line){
		lineNumber = line;
	}
	
	// ��ʶ����
	public boolean isIdentifier(){
		return false;
	}
	public boolean isNumber(){
		return false;
	}
	public boolean isString(){
		return false;
	}
	
	public int getNumber(){
		throw new RubbleException("not a number token");
	}
	// �涨��Ӧ������Ҫ��������
	public int getLineNumber(){
		return lineNumber;
	}
	public String getText(){
		return "";
	}
}
