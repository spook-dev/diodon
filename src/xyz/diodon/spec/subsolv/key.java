/*
 * key.java
 * 
 * Copyright 2017 James <James@DESKTOP-S2GQL4S>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Collection;

public class key {
	public Hashtable<String, String> k;
	
	
	public String toString(){
		String ret = "";
		String element;
		for(Enumeration<String> keyEnum = this.k.keys(); keyEnum.hasMoreElements();){
			element = keyEnum.nextElement();
			ret = ret + element + ": " + get(element) + "\n";
		}
		return ret;
	}
	
	public key(String a, String b){ //ciphertext, plaintext
		this.k = new Hashtable<String, String>();
		//so we can assume that a and b are matching patterns
		for(int i = 0; i < a.length(); i ++){
			set(Character.toString(a.charAt(i)), Character.toString(b.charAt(i)));
		}
	}
	
	public key(key k1){//make a copy of a key
		this.k = new Hashtable<String, String>(k1.k);
	}
	
	public key(){
		this.k = new Hashtable<String, String>();
	}
	
	public void set(String k_, String v){
		this.k.put(k_, v);
	}
	
	public String get(String k_){
		return this.k.get(k_);
	}
	
	public key inverted(){
		key k_inv = new key();
		
		for(Enumeration<String> keyEnum = this.k.keys(); keyEnum.hasMoreElements();){
			String c_k = keyEnum.nextElement();
			k_inv.set(get(c_k), c_k);
		}
		return k_inv;
	}
	
	private static boolean isUppercase(String s){
		int ord = (int) s.charAt(0);
		return ord <= 90 && ord >= 65;
	}
	private static boolean isLowercase(String s){
		int ord = (int) s.charAt(0);
		return ord >= 97 && ord <= 122;
	}
	private boolean keyExist(String s){
		return k.containsKey(s);
	}
	
	public String decrypt(String ciphertext){
		String plaintext = "";
		for(int i = 0; i < ciphertext.length(); i++){
			String c = ciphertext.substring(i, i+1);
			if(keyExist(c.toLowerCase())){
				if(isLowercase(c)){
					plaintext = plaintext + get(c);
				}
				else if(isUppercase(c)){
					plaintext = plaintext + get(c.toLowerCase()).toUpperCase();
				}
			}
			else if(isUppercase(c) || isLowercase(c)){
				plaintext = plaintext + c + "^"; //tk need a warning mechanism
			}
			else if(c.equals(" ")){
				plaintext = plaintext + c;
			}
			else {
				plaintext = plaintext + c;
			}
		}
		return plaintext;
	}
	
	public static key combine(key k1, key k2){
		key shorter;
		key longer;
		if(k1.k.size() < k2.k.size()){ //cycle through shorter key
			shorter = new key(k1);
			longer = new key(k2);
		}
		else{
			shorter = new key(k2);
			longer = new key(k1);
		}
		String element;
		String val;
		for(Enumeration<String> kEnum = shorter.k.keys(); kEnum.hasMoreElements();){
			element = kEnum.nextElement();
			val = shorter.get(element);
			if(longer.keyExist(element) && !longer.get(element).equals(val)){ //check value clash
				return null;
			}
			else{ //having verified it, no need to check later
				longer.k.remove(element);
			}
		}
		//longer now contains completely distinct key values, now check that values are all distinct
		Collection<String> values = shorter.k.values();
		for(Enumeration<String> kEnum = longer.k.keys(); kEnum.hasMoreElements();){
			element = kEnum.nextElement();
			val = longer.get(element);
			if(values.contains(val)){
				return null;
			}
			else{
				shorter.set(element, val);
			}
		}
		return shorter;
	}
	
	public static boolean matches(key k1, key k2){
		return k1.k.equals(k2.k);
	}
	
	public static void main (String[] args) {
		key k1 = new key();
		k1.set("a", "b");
		k1.set("c", "d");
		key k2 = new key();
		k2.set("a", "b");
		k2.set("c", "d");
		k2.set("t", "e");
		k2.set("x", "y");
		System.out.println(key.combine(k1, k2));
		//~ key k1 = new key();
		//~ System.out.println(k1);
	}
}

