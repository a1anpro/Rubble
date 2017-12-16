package yanhui.parser;

import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree {
	protected List<ASTree> children;

	public ASTList(List<ASTree> list){
		children = list;
	}
	
	public ASTree child(int i) {
		return children.get(i);
	}

	public int numChildren() {
		return children.size();
	}

	public Iterator<ASTree> children() {
		return children.iterator();
	}

	// 返回该结点的位置
	public String location() {
		for (ASTree t : children){
			String str = t.location();
			if (str != null){
				return str;
			}
		}
		return null;
	}

}
