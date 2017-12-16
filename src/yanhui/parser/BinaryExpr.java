package yanhui.parser;

import java.util.List;

public class BinaryExpr extends ASTList {
	public BinaryExpr(List<ASTree> c){
		super(c);// ���ø���ASTList�Ĺ��캯��
	}
	public ASTree left(){
		return child(0);
	}
	public String operator(){
		// ��Ԫ�����������ҷֱ��ǲ��������м�Ĳ��ǲ���������ASTList��child()
		//���ص���ASTree���ͣ�ʵ����ASTLeaf������ֻ��ASTLeaf����token
		//Ҫ��ȷ���ǣ�ֻ��Ҷ�ӽ�����token
		return ((ASTLeaf)child(1)).token().getText();
	}
	public ASTree right(){
		return child(2);
	}
}
