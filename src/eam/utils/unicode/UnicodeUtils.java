package eam.utils.unicode;

public class UnicodeUtils {
	public String string2Unicode(String string) {
		StringBuffer unicode = new StringBuffer();
		
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			unicode.append("\\u" + Integer.toHexString(c));
		}
		return unicode.toString();
	}
}
