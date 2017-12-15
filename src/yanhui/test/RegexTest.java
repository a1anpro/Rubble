package yanhui.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {
	public static void main(String[] args) {
		String regexPat = regexPat
				= "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
				          + "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";
		Pattern pattern = Pattern.compile(regexPat);
		
//		String str = "\"hello\"";
		String str = "12 _he";
		Matcher matcher = pattern.matcher(str);
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		
		matcher.region(0, str.length());
		matcher.lookingAt();
		String m = matcher.group(1);
		System.out.println("1:" + matcher.group(1));
		System.out.println("2:" + matcher.group(2));
		System.out.println("3:" + matcher.group(3));
		System.out.println("4:" + matcher.group(4));
		System.out.println("5:" + matcher.group(5));
//		System.out.println("6:" + matcher.group(6));
		System.out.println(m.length());
		System.out.println(matcher.end());
	}
}
