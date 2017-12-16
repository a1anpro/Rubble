package yanhui.utils;

import yanhui.exception.RubbleException;

public abstract class Token {
	private int lineNumber; // 该token的位置
	public static final Token EOF = new Token(-1){};//end of file
	public static final String EOL = "\\n"; // end of line
	
	protected Token(int line){
		lineNumber = line;
	}
	
	// 标识类型
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
	// 规定相应的子类要隐藏他们
	public int getLineNumber(){
		return lineNumber;
	}
	public String getText(){
		return "";
	}
}
