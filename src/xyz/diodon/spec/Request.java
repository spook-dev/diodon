package xyz.diodon.spec;

import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class Request {
	enum Goal {
		Store,
		Read,
		Action
	}
	
	public UUID ID;
	public String service;
	public String method;
	public Goal goal;
	public Object argument;

	public static String gsonify(Object o) {
		Gson gson = new Gson();
		return gson.toJson(o);
	}
	public static String gsonify(Object o, ServiceManager sm) {
		return sm.customSerialize(o);
	}
	public static ArrayList<Response> Respond(String requestRaw, ServiceManager sm) {
		Request r = Parse(requestRaw);
		return r.ComputeResponse(sm);
	}

	private static Request Parse(String requestRaw) {
		Request r;
		JsonElement requestBody = JsonParser.parseString(requestRaw);
		Gson gson = new Gson();
		r = gson.fromJson(requestBody, Request.class);
		//handle parse problems
		return r;
	}

	public ArrayList<Response> ComputeResponse(ServiceManager sm) {
		ArrayList<Response> responses = new ArrayList<Response>();
		Service s = sm.GetService(service);
		//TODO null check on s
		switch(goal) {
			case Store:
				break;
			case Read:
				break;
			case Action:
				Action a = s.NewAction(method);
				ArrayList<Object> attachments = a.Respond(this.argument);
				int i = 0;
				for(Object o : attachments) {
					responses.add(new Response(ID, o, i++, attachments.size()));
				}
				break;
			default:
				//TODO add handling
		}
		return responses;
	}

	public String toString() {
		return gsonify(this);
	}
}

