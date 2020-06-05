package xyz.diodon.spec.subsolv;

/*
 * nsa2ez.java
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
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Math;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileNotFoundException;

public class substitution_solver {
	private Hashtable<String, ArrayList<String>> patternTable;

	public substitution_solver() {
		this.patternTable = new Hashtable<String, ArrayList<String>>();
	}

	private List<String> getResourceFiles(String path) throws IOException {
		List<String> filenames = new ArrayList<>();

		try (InputStream in = getClass().getResourceAsStream(path);
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
			String resource;

			while ((resource = br.readLine()) != null) {
				filenames.add(resource);
			}
		}

		return filenames;
	}

	public void populate_patternTable(){
		List<String> pattern_files;
		try {
			pattern_files = getResourceFiles("pattern_data/patterns/");
		} catch (IOException e1) {
			System.err.println("IOException loading pattern_data/patterns/");
			e1.printStackTrace();
			return;
		}
		
		for(String filename : pattern_files){
			String  pattern_template;
			if(filename.indexOf(".") > 0)
				pattern_template = filename.substring(0, filename.lastIndexOf("."));
			else
				continue;
			this.patternTable.put(pattern_template, new ArrayList<String>());
			ArrayList<String> pattern_matches = this.patternTable.get(pattern_template);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("pattern_data/patterns/" + filename)))) {
				String line;
				while ((line = br.readLine()) != null) {
				   pattern_matches.add(line);
				}
			}
			catch (FileNotFoundException e) {}
			catch (IOException e) {}
		}
	}

	private static String generate_pattern(String word) {
		word = word.toLowerCase();
		String pattern = "";
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		Hashtable<Character, Character> charmap = new Hashtable<Character, Character>();
		char c;
		for (int i = 0; i < word.length(); i++) {
			c = word.charAt(i);
			if (!charmap.containsKey(c)) {
				charmap.put(c, alphabet.charAt(0));
				alphabet = alphabet.substring(1);
			}
			pattern = pattern + charmap.get(c).toString();
		}
		return pattern;
	}

	public ArrayList<key> crack(String ct) {
		String[] words = ct.split(" ");
		ArrayList<ArrayList<key>> words_tree = new ArrayList<ArrayList<key>>();
		for (int i = 0; i < words.length; i++) {
			ArrayList<key> pattern_keys = new ArrayList<key>();
			ArrayList<String> pattern_matches = new ArrayList<String>();
			pattern_matches = patternTable.get(generate_pattern(words[i])); // this is full of words, it needs to be
																			// full of keys
			for (int j = 0; j < pattern_matches.size(); j++) {
				pattern_keys.add(new key(words[i], pattern_matches.get(j)));
			}
			words_tree.add(pattern_keys);
		}
		// tk optimize sorting algorithm
		Collections.sort(words_tree, (l1, l2) -> (l1.size() - l2.size())
				/ (Math.abs(l1.size() - l2.size()) == 0 ? 1 : Math.abs(l1.size() - l2.size()))); // sort shortest keys
																									// first
		// total initialization time ~36 ms, up from sub1.0 speed of ~80ms
		// sorting time is largest factor at around 20+ ms

		ArrayList<key> master_keys = new ArrayList<key>();
		ArrayList<key> new_master_keys = new ArrayList<key>();
		;
		master_keys.addAll(words_tree.get(0));
		words_tree.remove(0);
		for (ArrayList<key> new_keys : words_tree) {
			new_master_keys.clear(); // combination of current master keys and new keys
			for (key new_key : new_keys) { // for new_key in new_keys
				for (key m_key : master_keys) { // for m_key in master_keys
					key c_key = key.combine(m_key, new_key);
					if (c_key != null) {
						new_master_keys.add(c_key);
					}
				}
			}
			master_keys.clear();
			for (key element : new_master_keys) {
				if (!master_keys.contains(element)) {
					master_keys.add(element);
				}
			}
		}
		return master_keys;
	}

	public ArrayList<key> AdvancedCrack(String ct, boolean doPrint) {
		ArrayList<String> words = new ArrayList<String>();
		for (String word : ct.split(" ")) {
			if (!words.contains(word) && patternTable.containsKey(generate_pattern(word))) {
				words.add(word);
			}
		}
		int c;
		for (int l = words.size(); l > 0; l--) {
			if (doPrint) {
				System.out.format("trying with %d/%d words\n", l, words.size());
			}
			c = 1;
			// generate list of words that weren't omitted
			ArrayList<ArrayList<String>> combos = combination_utils.combinations(words, l);
			int possibilities = combos.size();
			for (ArrayList<String> i : combos) {
				if (doPrint) {
					System.out.format("\r%s - %s/%s", string_utils.percentagebar(0, possibilities, c), c,
							possibilities); // tk make this function
					// ~ Thread.sleep(100);
					// ~ sys.stdout.flush()
				}
				String current_ct = "";
				for (String w : i) {
					current_ct = current_ct + w + " ";
				}
				current_ct = current_ct.substring(0, current_ct.length() - 1);
				ArrayList<key> test = crack(current_ct);
				if (test.size() > 0) {
					if (doPrint)
						System.out.println();
					return test;
				}
				c++;
			}
			if (doPrint)
				System.out.println();
		}
		return null;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		substitution_solver n = new substitution_solver();
		n.populate_patternTable();
		System.out.println("ready");
		String CT;
		String ct;
		if (args.length > 0) {
			CT = String.join(" ", args);
		} else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			CT = reader.readLine();
		}
		ct = string_utils.purifyCT(CT);

		long unixTime = System.currentTimeMillis();
		ArrayList<key> m = n.AdvancedCrack(ct, false);
		System.out.println(System.currentTimeMillis() - unixTime);
		for (int i = 0; i < m.size(); i++) {
			System.out.println(m.get(i).decrypt(CT));
		}
	}
}
