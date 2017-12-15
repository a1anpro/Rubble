package yanhui.utils;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import yanhui.exception.ParseException;

public class Lexer {
	public static String regexPat
    = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
            + "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
	private Pattern pattern = Pattern.compile(regexPat);
	private ArrayList<Token> queue = new ArrayList<Token>();
	private boolean hasMore;
	//A buffered character-input stream that keeps track of line numbers.
	private LineNumberReader reader;	
	
	// 构造 词法分析器
	public Lexer(Reader r){
		hasMore = true;
		// Reader 是 LineNumberReader的父类
		reader = new LineNumberReader(r);
	}
	
	public Token read() throws ParseException{
		// 如果queue为空且可以继续读的话，就调用readLine函数填充queue
		if (fillQueue(0)){
			// 如果上面函数返回true，则说明队列里有值,读出第一个
			return  queue.remove(0);
		}
		else{
			return Token.EOF;
		}
	}
	
	public Token peek(int i) throws ParseException{
		if (fillQueue(i)){
			return queue.get(i);
		}
		else {
			return Token.EOF;
		}
	}
	
	private boolean fillQueue(int i) throws ParseException{
		boolean ret = true;
		// 如果queue为空且不能继续往下读了，则返回false.否则填充queue
		while (i >= queue.size()){
			if (hasMore){
				readLine();
			}
			else{
				return false;
			}
		}
		return ret;
	}
	
	protected void readLine() throws ParseException{
		String line;
		try {
			line = reader.readLine();
		} catch (IOException e) {
			throw new ParseException(e);// 如果捕捉到IO异常，则抛出Parse异常
		}
		// 没有读到当前行，说明没有更多的行了
		if (line == null){
			hasMore = false;
			return ;// 当即结束
		}
		// reader对象在读一行的内容同时保存了当前行
		int lineNumber = reader.getLineNumber();
		// 用模式匹配line中符合的内容
		Matcher matcher = pattern.matcher(line);
		//设置边界
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		
		int startPos = 0;
		int endPos = line.length();
		
		while (startPos < endPos){
			matcher.region(startPos, endPos);
			if (matcher.lookingAt()){
				addToken(lineNumber, matcher);
				// end()函数返回上一个匹配操作的最后一个字符的偏移量
				//意味着下一次从这里开始向后匹配
				startPos = matcher.end();
			}
			else{
				// 如果没有匹配出错，则抛出异常
				throw new ParseException("bad token at line " + lineNumber);
			}
		}
		// 一行匹配结束，将该行的结束标记也加入到队列中
		queue.add(new IdToken(lineNumber, Token.EOL));
	}
	
	protected void addToken(int lineNumber, Matcher matcher){
		String m = matcher.group(1);
		// m是第一个括号匹配到的，如果没匹配到则返回null
		if (m != null){
			// 括号2匹配注释, 如果被匹配到了则舍弃
			if (matcher.group(2) == null){
				Token token;
				// 括号3匹配整数
				if (matcher.group(3) != null){
					token = new NumToken(lineNumber, Integer.parseInt(m));
				}
				// 括号3匹配字符串
				else if (matcher.group(4) != null){
					token = new StrToken(lineNumber, toStringLiteral(m));
				}
				else{
					token = new IdToken(lineNumber, m);
				}
				queue.add(token);//将匹配到的token加入到队列中
			}
		}
	}
	protected String toStringLiteral(String s){
		StringBuilder sb = new StringBuilder();
		int len = s.length() - 1;
		for (int i = 1; i < len; ++i){
			char c = s.charAt(i);// 取s中的第i个字符
			// 去除字符串中的转移序列中的反斜杠
			//只处理了" \ n 的情况，还有其他如t,a的情况没有考虑
			if (c == '\\' && i + 1 < len){
				char c2 = s.charAt(i + 1);
				if (c2 == '"' || c2 == '\\'){
					c = s.charAt(++i);
				}
				else if (c2 == 'n'){
					++i;
					c = '\n';//加成换行
				}
			}
			sb.append(c);
		}
		// 用StringBuilder是为了提升性能
		return sb.toString();
	}
	
	// 类内静态类
	///////////////////////////////////////
	protected static class NumToken extends Token{
		private int value;
		
		protected NumToken(int line, int v){
			super(line);
			value = v;
		}
		public boolean isNumber(){
			return true;
		}
		public String getText(){
			return Integer.toString(value);
		}
		public int getNumber(){
			return value;
		}
	}
	protected static class IdToken extends Token{
		private String value;
		protected IdToken(int line, String id){
			super(line);
			value = id;
		}
		
		public boolean isIdentifier(){
			return true;
		}
		public String getText(){
			return value;
		}
	}
	protected static class StrToken extends Token{
		protected String literal;
		StrToken(int line, String str){
			super(line);
			literal = str;
		}
		
		public boolean isString(){
			return true;
		}
		public String getText(){
			return literal;
		}
	}
	///////////////////////////////////////
}

