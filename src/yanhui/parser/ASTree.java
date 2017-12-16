package yanhui.parser;

import java.util.Iterator;

public abstract class ASTree implements Iterable<ASTree>{
	// ���ص�i���ӽ��
	public abstract ASTree child(int i);
	// �����ӽڵ����
	public abstract int numChildren();
	// ����һ�����ڱ����ӽڵ��Iterator
	public abstract Iterator<ASTree> children();
	// ���س����﷨������ڳ�����������λ�õ��ַ���
	public abstract String location();
	
	// ��children()������ͬ������һ�����������ڽ�ASTreeתΪIterable����ʱ����
	public Iterator<ASTree> iterator(){
		return children();
	}
}
