package yanhui.parser;

import yanhui.utils.Token;

public class Name extends ASTLeaf {
	public Name(Token t){
		super(t);
	}
	public String name(){
		return super.token().getText();
	}
}
