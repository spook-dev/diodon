package xyz.diodon.spec;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public abstract class Request {
	enum Goal {
		Store,
		Read,
		Action
	}
	String service;
	Goal goal;
	Object argument;

	private Response response;

	public static String gsonify(Object o) {
		Gson gson = new Gson();
		return gson.toJson(o);
	}

	public String toString() {
		return gsonify(this);
	}

	public Request(String requestRaw) {

	}

	public abstract String ComputeResponse();
}

