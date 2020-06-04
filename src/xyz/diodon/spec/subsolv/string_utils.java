import java.util.ArrayList;
import java.lang.Math;
public class string_utils {
	private static boolean isUppercase(String s){
		if(s.length() == 0) return false;
		int ord = (int) s.charAt(0);
		return ord <= 90 && ord >= 65;
	}
	private static boolean isLowercase(String s){
		if(s.length() == 0) return false;
		int ord = (int) s.charAt(0);
		return ord >= 97 && ord <= 122;
	}
	private static boolean isNumber(String s){
		if(s.length() == 0) return false;
		int ord = (int) s.charAt(0);
		return ord >= 48 && ord <= 57;
	}
	public static String stripPunctuation(String word){
		if(word.length() > 2){
			if(word.charAt(word.length() - 2) == ("'").charAt(0)){
				word = word.substring(0, word.length() - 2);
			}
		}
		String lastChar = Character.toString(word.charAt(word.length() - 1));
		while(!isUppercase(lastChar) && !isLowercase(lastChar) && !isNumber(lastChar)){
			word = word.substring(0, word.length() - 1);
			if(word.length() == 0){
				return "";
			}
			lastChar = Character.toString(word.charAt(word.length() - 1));
		}
		String firstChar = Character.toString(word.charAt(0));
		while(!isUppercase(firstChar) && !isLowercase(firstChar) && !isNumber(firstChar)){
			word = word.substring(1);
			if(word.length() == 0){
				return "";
			}
			firstChar = Character.toString(word.charAt(0));
		}
		return word.replace("'", "").replace(".", " ").replace("-", " ");
	}
	public static String purifyCT(String CT){ //tk write stripPunctuation
		ArrayList<String> ct = new ArrayList<String>();
		for(String word: CT.replace("'", "").replace(".", " ").replace("-", " ").split(" ")){
			if(word.length() == 0){
				continue;
			}
			boolean good = true;
			word = stripPunctuation(word);
			for(int i = 0; i < word.length(); i++){
				String c = Character.toString(word.charAt(i));
				if(!isUppercase(c) && !isLowercase(c)){
					good = false;
					break;
				}
			}
			if(good && !isNumber(word)){
				ct.add(word.toLowerCase());
			}
		}
		String r = ct.get(0);
		String temp;
		for(int i = 1; i < ct.size(); i++){
			temp = ct.get(i);
			if(temp.length() != 0) r = r + " " + temp;
		}
		return r;
	}
	private static float map(int small, int big, int current){
		float s = small;
		float b = big;
		float c = current;
		return (c - s) / (b - s);
	}
	public static String percentagebar(int small, int big, int current, int bars){
		float percent = map(small, big, current);
		int bar = Math.round(bars * percent);
		String pbar = "[";
		for(int i = 0; i < bars; i++){
			if(bar > i){
				pbar = pbar + "=";
			}
			else{
				pbar = pbar + " ";
			}
		}
		return pbar + "]";
	}
	public static String percentagebar(int small, int big, int current){
		return percentagebar(small, big, current, 20);
	}
	public static void main (String[] args){
		System.out.println(percentagebar(0, 100, 50));
		System.out.println(percentagebar(0, 100, 20));
		System.out.println(percentagebar(0, 100, 10));
	}
}
