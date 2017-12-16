package yanhui.parser;

import java.util.List;

public class BinaryExpr extends ASTList {
	public BinaryExpr(List<ASTree> c){
		super(c);// 调用父类ASTList的构造函数
	}
	public ASTree left(){
		return child(0);
	}
	public String operator(){
		// 二元操作符的左右分别是操作数，中间的才是操作符，而ASTList的child()
		//返回的是ASTree类型（实际是ASTLeaf），而只有ASTLeaf才有token
		//要明确的是：只有叶子结点才有token
		return ((ASTLeaf)child(1)).token().getText();
	}
	public ASTree right(){
		return child(2);
	}
}
