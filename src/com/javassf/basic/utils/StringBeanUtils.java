package com.javassf.basic.utils;

import java.io.UnsupportedEncodingException;
import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;
/**
 * String工具类
 */
public class StringBeanUtils {
	/**
	 * url格式处理
	 * @param url
	 * @return
	 * String
	 */
	public static String handelUrl(String url) {
		if (url == null) {
			return null;
		}
		url = url.trim();
		if ((url.equals("")) || (url.startsWith("http://")) || (url.startsWith("https://"))) {
			return url;
		}
		return "http://" + url.trim();
	}

	public static String[] splitAndTrim(String str, String sep, String sep2) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		if (!StringUtils.isBlank(sep2)) {
			str = StringUtils.replace(str, sep2, sep);
		}
		String[] arr = StringUtils.split(str, sep);

		int i = 0;
		for (int len = arr.length; i < len; i++) {
			arr[i] = arr[i].trim();
		}
		return arr;
	}

	/**
	 * 判断是否含有非法字符
	 * @param txt
	 * @return
	 * boolean
	 */
	public static boolean hasHtml(String txt) {
		if (StringUtils.isBlank(txt)) {
			return false;
		}

		boolean doub = false;
		int i = 0;
		if (i < txt.length()) {
			char c = txt.charAt(i);
			if (c == ' ') {
				return doub;
			}

			switch (c) {
			case '&':
				return true;
			case '<':
				return true;
			case '>':
				return true;
			case '"':
				return true;
			case '\n':
				return true;
			}
			return false;
		}

		return false;
	}

	/**
	 * 普通文本转为html
	 * @param txt
	 * @return
	 * String
	 */
	public static String txt2htm(String txt) {
		if (StringUtils.isBlank(txt)) {
			return txt;
		}
		StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2D));

		boolean doub = false;
		for (int i = 0; i < txt.length(); i++) {
			char c = txt.charAt(i);
			if (c == ' ') {
				if (doub) {
					sb.append(' ');
					doub = false;
				} else {
					sb.append("&nbsp;");
					doub = true;
				}
			} else {
				doub = false;
				switch (c) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '\n':
					sb.append("<br/>");
					break;
				default:
					sb.append(c);
				}
			}
		}

		return sb.toString();
	}

	/**
	 * 文本截取处理类
	 * @param s   源字符串
	 * @param len 截取长度
	 * @param append 代替字符串
	 * @return
	 * String
	 */
	public static String textCut(String s, int len, String append) {
		if (s == null) {
			return null;
		}
		int slen = s.length();
		if (slen <= len) {
			return s;
		}

		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		for (; (count < maxCount) && (i < slen); i++) {
			if (s.codePointAt(i) < 256)
				count++;
			else {
				count += 2;
			}
		}
		if (i < slen) {
			if (count > maxCount) {
				i--;
			}
			if (!StringUtils.isBlank(append)) {
				if (s.codePointAt(i - 1) < 256)
					i -= 2;
				else {
					i--;
				}
				return s.substring(0, i) + append;
			}
			return s.substring(0, i);
		}

		return s;
	}

	public static String htmlCut(String s, int len, String append) {
		String text = html2Text(s, len * 2);
		return textCut(text, len, append);
	}

	
	public static String html2Text(String html, int len) {
		try {
			Lexer lexer = new Lexer(html);

			StringBuilder sb = new StringBuilder(html.length());
			Node node;
			while ((node = lexer.nextNode()) != null) {
				// Node node;
				if ((node instanceof TextNode)) {
					sb.append(node.toHtml());
				}
				if (sb.length() > len) {
					break;
				}
			}
			return sb.toString();
		} catch (ParserException e) {
			throw new RuntimeException(e);
		}
	}

	public static StringBuilder replace(StringBuilder sb, String what, String with) {
		int pos = sb.indexOf(what);
		while (pos > -1) {
			sb.replace(pos, pos + what.length(), with);
			pos = sb.indexOf(what);
		}
		return sb;
	}

	public static String replaceKeyString(String str) {
		if (containsKeyString(str)) {
			return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r", "\\r").replace("\n", "\\n").replace("\t", "\\t").replace("\b", "\\b").replace("\f", "\\f");
		}
		return str;
	}

	public static boolean containsKeyString(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}

		return (str.contains("'")) || (str.contains("\"")) || (str.contains("\r")) || (str.contains("\n")) || (str.contains("\t")) || (str.contains("\b")) || (str.contains("\f"));
	}

	public static String replace(String s, String what, String with) {
		return replace(new StringBuilder(s), what, with).toString();
	}

	public static boolean contains(String str, String[] strs) {
		if ((strs == null) || (strs.length == 0)) {
			return false;
		}
		for (String s : strs) {
			if (s.equals(str)) {
				return true;
			}
		}
		return false;
	}

	public static String getChangeChart(String name) {
		String newname = "";
		try {
			if (!StringUtils.isBlank(name))
				newname = new String(name.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
		return newname;
	}
}