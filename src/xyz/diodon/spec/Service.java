package xyz.diodon.spec;

import java.util.HashTable;

public class Service {
	String name;
	//public HashTable<String, Store> Stores;
	//public HashTable<String, Read> Reads;
	public HashTable<String, Action> Actions;

	public Action getAction(String name) {
		return Actions.get(name);
	}
}
