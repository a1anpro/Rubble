package yanhui.exception;

import java.io.IOException;

import yanhui.utils.Token;

public class ParseException extends Exception{
	public ParseException(String msg, Token t){
		// �쳣��Ϣmsg�ͳ��ִ����token
		// �﷨���� + ����λ + ������Ϣmsg
		super("syntax error around " + location(t) + ". " + msg);
	}
	public ParseException(Token t){
		this("",t);// �����ع����ã����ֻ������token��û�д�����Ϣ������½�������Ϣ�ÿ�
	}
	
	public ParseException(IOException e){
		super(e);
	}
	public ParseException(String msg){
		super(msg);
	}
	
	// �ڲ������������ڲ�ʹ��
	public static String location(Token t){
		String ret = "";
		if (t == Token.EOF){
			ret = "the last line";// ���roken t���ļ�ĩ
		}
		else{
			ret = "\"" + t.getText() + "\" at line " + t.getLineNumber();
		}
		return ret;
	}
}
