package yanhui.exception;

import java.io.IOException;

import yanhui.utils.Token;

public class ParseException extends Exception{
	public ParseException(String msg, Token t){
		// 异常信息msg和出现错误的token
		// 语法报错 + 错误定位 + 错误信息msg
		super("syntax error around " + location(t) + ". " + msg);
	}
	public ParseException(Token t){
		this("",t);// 代码重构利用，如果只传入了token而没有错误信息的情况下将错误信息置空
	}
	
	public ParseException(IOException e){
		super(e);
	}
	public ParseException(String msg){
		super(msg);
	}
	
	// 内部函数，仅供内部使用
	public static String location(Token t){
		String ret = "";
		if (t == Token.EOF){
			ret = "the last line";// 如果roken t是文件末
		}
		else{
			ret = "\"" + t.getText() + "\" at line " + t.getLineNumber();
		}
		return ret;
	}
}
