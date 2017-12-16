package yanhui.parser;

import java.util.ArrayList;
import java.util.Iterator;

import yanhui.utils.Token;

// 叶子结点，没有子节点
public class ASTLeaf extends ASTree {
	private static ArrayList<ASTree> empty = new ArrayList<ASTree>();
	protected Token token;
	public ASTLeaf(Token t){
		token = t;
	}
	// 没有子结点，如果调用该方法则会抛出出界异常
	public ASTree child(int i){
		throw new IndexOutOfBoundsException();
	}
	// 没有子结点，始终返回0
	public int numChildren(){
		return 0;
	}
	public Iterator<ASTree> children(){
		return empty.iterator();// 返回的是ArrayList<...>的iterator()
	}
	public String location(){
		return "at line " +  token.getLineNumber();
	}
	public String toString(){
		return token.getText();// 返回的是叶子结点的内容
	}
	public Token token(){
		return token;// 返回这个叶子结点所包含的token
	}
}
