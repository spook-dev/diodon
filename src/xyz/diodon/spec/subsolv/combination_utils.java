package xyz.diodon.spec.subsolv;
/*
 * combination_utils.java
 * 
 * Copyright 2017 Quack <qazxvy@gmail.com>
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
import java.util.ArrayList;
import java.util.Collections;

public class combination_utils {
	public static ArrayList<ArrayList<Boolean>> generate_truth(int k, int l){
		return generate_truth(new ArrayList<Boolean>(), k, l);
	}
	
	public static ArrayList<ArrayList<Boolean>> generate_truth(ArrayList<Boolean> C, int k, int l) {
		int c_length = C.size();
		int c_T = Collections.frequency(C, true);
		ArrayList<Boolean> newC = new ArrayList<Boolean>();
		newC.addAll(C);
		if((k - c_T) == (l - c_length)){
			for(int i = 0; i < (l - c_length); i++)
				newC.add(Boolean.TRUE);
			ArrayList<ArrayList<Boolean>> ret = new ArrayList<ArrayList<Boolean>>();
			ret.add(newC);
			return ret;
		}
		else if(k == c_T){
			for(int i = 0; i < (l - c_length); i++)
				newC.add(Boolean.FALSE);
			ArrayList<ArrayList<Boolean>> ret = new ArrayList<ArrayList<Boolean>>();
			ret.add(newC);
			return ret;
		}
		else{ //tk this is sloppy, optimize?
			ArrayList<Boolean> a = new ArrayList<Boolean>();
			ArrayList<Boolean> b = new ArrayList<Boolean>();
			a.addAll(C);
			b.addAll(C);
			a.add(Boolean.TRUE);
			b.add(Boolean.FALSE);
			ArrayList<ArrayList<Boolean>> a_totaled = generate_truth(a, k, l);
			ArrayList<ArrayList<Boolean>> b_totaled = generate_truth(b, k, l);
			a_totaled.addAll(b_totaled);
			return a_totaled;
		}
	}
	
	public static ArrayList<ArrayList<String>> combinations(ArrayList<String> ct, int new_length){
		ArrayList<ArrayList<Boolean>> truth_table = generate_truth(new_length, ct.size());
		ArrayList<ArrayList<String>> new_cts = new ArrayList<ArrayList<String>>();
		for(int line = 0; line < truth_table.size(); line++){
			ArrayList<String> new_ct = new ArrayList<String>();
			for(int element = 0; element < truth_table.get(line).size(); element++){
				if(truth_table.get(line).get(element)){
					new_ct.add(ct.get(element));
				}
			}
			new_cts.add(new_ct);
		}
		return new_cts;
	}
	
	public static void main (String args[]) {
		
	}
}

