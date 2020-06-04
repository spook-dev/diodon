package xyz.diodon.spec;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public abstract class Response {
	enum Goal {
		Store,
		Read,
		Action
	}
	UUID RequestID;
	String service;
	Goal goal;
	Object result;

	private Response response;

	public static String gsonify(Object o) {
		Gson gson = new Gson();
		return gson.toJson(o);
	}

	public String toString() {
		return gsonify(this);
	}
}

