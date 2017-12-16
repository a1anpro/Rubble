package yanhui.parser;

import yanhui.utils.Token;

// 该类是ASTLeaf的子类，包含整型变量的值
public class NumberLiteral extends ASTLeaf {
	public NumberLiteral(Token t){
		super(t);
	}
	
	public int value(){
		// 只有是整型类型的token才有getNumber()方法
		return super.token().getNumber();
	}
}
