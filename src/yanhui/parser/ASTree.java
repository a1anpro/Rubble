package yanhui.parser;

import java.util.Iterator;

public abstract class ASTree implements Iterable<ASTree>{
	// 返回第i个子结点
	public abstract ASTree child(int i);
	// 返回子节点个数
	public abstract int numChildren();
	// 返回一个用于遍历子节点的Iterator
	public abstract Iterator<ASTree> children();
	// 返回抽象语法树结点在程序内所处的位置的字符串
	public abstract String location();
	
	// 与children()方法相同，它是一个适配器，在将ASTree转为Iterable类型时会用
	public Iterator<ASTree> iterator(){
		return children();
	}
}
