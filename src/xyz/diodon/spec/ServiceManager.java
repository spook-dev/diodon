package xyz.diodon.spec;

import java.util.HashTable;

public class ServiceManager {
	String name;
	public HashTable<String, Action> Services;

	public Action getAction(String service, String name) {
		return Services.get(service).getAction(name);
	}
}
