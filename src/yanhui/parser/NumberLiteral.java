package yanhui.parser;

import yanhui.utils.Token;

// ������ASTLeaf�����࣬�������ͱ�����ֵ
public class NumberLiteral extends ASTLeaf {
	public NumberLiteral(Token t){
		super(t);
	}
	
	public int value(){
		// ֻ�����������͵�token����getNumber()����
		return super.token().getNumber();
	}
}
