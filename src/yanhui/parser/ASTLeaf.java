package yanhui.parser;

import java.util.ArrayList;
import java.util.Iterator;

import yanhui.utils.Token;

// Ҷ�ӽ�㣬û���ӽڵ�
public class ASTLeaf extends ASTree {
	private static ArrayList<ASTree> empty = new ArrayList<ASTree>();
	protected Token token;
	public ASTLeaf(Token t){
		token = t;
	}
	// û���ӽ�㣬������ø÷�������׳������쳣
	public ASTree child(int i){
		throw new IndexOutOfBoundsException();
	}
	// û���ӽ�㣬ʼ�շ���0
	public int numChildren(){
		return 0;
	}
	public Iterator<ASTree> children(){
		return empty.iterator();// ���ص���ArrayList<...>��iterator()
	}
	public String location(){
		return "at line " +  token.getLineNumber();
	}
	public String toString(){
		return token.getText();// ���ص���Ҷ�ӽ�������
	}
	public Token token(){
		return token;// �������Ҷ�ӽ����������token
	}
}
