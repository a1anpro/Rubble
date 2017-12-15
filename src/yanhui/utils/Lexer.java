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
	
	// ���� �ʷ�������
	public Lexer(Reader r){
		hasMore = true;
		// Reader �� LineNumberReader�ĸ���
		reader = new LineNumberReader(r);
	}
	
	public Token read() throws ParseException{
		// ���queueΪ���ҿ��Լ������Ļ����͵���readLine�������queue
		if (fillQueue(0)){
			// ������溯������true����˵����������ֵ,������һ��
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
		// ���queueΪ���Ҳ��ܼ������¶��ˣ��򷵻�false.�������queue
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
			throw new ParseException(e);// �����׽��IO�쳣�����׳�Parse�쳣
		}
		// û�ж�����ǰ�У�˵��û�и��������
		if (line == null){
			hasMore = false;
			return ;// ��������
		}
		// reader�����ڶ�һ�е�����ͬʱ�����˵�ǰ��
		int lineNumber = reader.getLineNumber();
		// ��ģʽƥ��line�з��ϵ�����
		Matcher matcher = pattern.matcher(line);
		//���ñ߽�
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		
		int startPos = 0;
		int endPos = line.length();
		
		while (startPos < endPos){
			matcher.region(startPos, endPos);
			if (matcher.lookingAt()){
				addToken(lineNumber, matcher);
				// end()����������һ��ƥ����������һ���ַ���ƫ����
				//��ζ����һ�δ����￪ʼ���ƥ��
				startPos = matcher.end();
			}
			else{
				// ���û��ƥ��������׳��쳣
				throw new ParseException("bad token at line " + lineNumber);
			}
		}
		// һ��ƥ������������еĽ������Ҳ���뵽������
		queue.add(new IdToken(lineNumber, Token.EOL));
	}
	
	protected void addToken(int lineNumber, Matcher matcher){
		String m = matcher.group(1);
		// m�ǵ�һ������ƥ�䵽�ģ����ûƥ�䵽�򷵻�null
		if (m != null){
			// ����2ƥ��ע��, �����ƥ�䵽��������
			if (matcher.group(2) == null){
				Token token;
				// ����3ƥ������
				if (matcher.group(3) != null){
					token = new NumToken(lineNumber, Integer.parseInt(m));
				}
				// ����3ƥ���ַ���
				else if (matcher.group(4) != null){
					token = new StrToken(lineNumber, toStringLiteral(m));
				}
				else{
					token = new IdToken(lineNumber, m);
				}
				queue.add(token);//��ƥ�䵽��token���뵽������
			}
		}
	}
	protected String toStringLiteral(String s){
		StringBuilder sb = new StringBuilder();
		int len = s.length() - 1;
		for (int i = 1; i < len; ++i){
			char c = s.charAt(i);// ȡs�еĵ�i���ַ�
			// ȥ���ַ����е�ת�������еķ�б��
			//ֻ������" \ n �����������������t,a�����û�п���
			if (c == '\\' && i + 1 < len){
				char c2 = s.charAt(i + 1);
				if (c2 == '"' || c2 == '\\'){
					c = s.charAt(++i);
				}
				else if (c2 == 'n'){
					++i;
					c = '\n';//�ӳɻ���
				}
			}
			sb.append(c);
		}
		// ��StringBuilder��Ϊ����������
		return sb.toString();
	}
	
	// ���ھ�̬��
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

