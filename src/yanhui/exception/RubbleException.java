package yanhui.exception;

import yanhui.parser.ASTree;

public class RubbleException extends RuntimeException{
	public RubbleException(String m){
		super(m);
	}
	public RubbleException(String m, ASTree t){
		super(m + " " + t.location());
	}
}
