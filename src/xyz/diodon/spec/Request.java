package xyz.diodon.spec;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Request {
	enum Goal {
		Store,
		Read,
		Action
	}
	UUID ID;
	String service;
	String argName;
	private Class argClass;
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

	public static Request Parse(String requestRaw, ServiceManager sm) {
		Request r = new Request();
		JsonParser jsonParser = new JsonParser();
		JsonElement requestBody = jsonParser.parse(requestRaw);
		Gson gson = new Gson();
		r = gson.fromJson(requestBody, Request.class);
		switch(goal) {
			case Store:
			case Read:
			case Action:
				r.argClass = sm.getAction(r.service, r.argName).argClass;
		}
		//handle parse problems
	}

	public Response ComputeResponse(ServiceManager sm) {
		switch(goal) {
			case Store:
				return null;
			case Read:
				return null;
			case Action:
				response = sm.getAction(service, argType).Respond(this);
				return response;
		}
	}
}

