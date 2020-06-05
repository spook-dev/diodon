package xyz.diodon.spec;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import xyz.diodon.spec.subsolv.SubsolvArg;
import xyz.diodon.spec.subsolv.SubsolvService;

public class Request<T> {
	public enum Goal {
		Store, Read, Action
	}

	public UUID ID;
	public String service;
	public String method;
	public Goal goal;
	public T argument;
	
	private static ServiceManager SM;

	public Request(String s, String m, Goal g, T a) {
		ID = UUID.randomUUID();
		service = s;
		method = m;
		goal = g;
		argument = a;
	}

	public static String gsonify(Object o) {
		Gson gson = new Gson();
		return gson.toJson(o);
	}

	public static String gsonify(Object o, ServiceManager sm) {
		return sm.customSerialize(o);
	}

	public static ArrayList<Response> Respond(String requestRaw, ServiceManager sm) {
		SM = sm;
		Request<?> r = Parse(requestRaw);
		return r.ComputeResponse();
	}

	protected static Request<?> Parse(String requestRaw) {
		Request<?> r;
		JsonElement requestBody = JsonParser.parseString(requestRaw);
		JsonObject requestObject = requestBody.getAsJsonObject();
		Gson gson = new Gson();
		Service s = SM.GetService(requestObject.get("service").getAsString());
		Type RequestType = s.GetArgType(requestObject.get("method").getAsString());
		r = gson.fromJson(requestBody, RequestType);
		// handle parse problems
		return r;
	}

	protected ArrayList<Response> ComputeResponse() {
		ArrayList<Response> responses = new ArrayList<Response>();
		Service s = SM.GetService(service);
		switch (goal) {
		case Store:
			break;
		case Read:
			break;
		case Action:
			Action a = s.NewAction(method);
			ArrayList<Object> attachments = a.Respond(this.argument);
			int i = 0;
			for (Object o : attachments) {
				responses.add(new Response(ID, o, i++, attachments.size()));
			}
			break;
		}
		return responses;
	}

	public String toString() {
		return gsonify(this);
	}

	public static void main(String[] args) {
		ServiceManager sm = new ServiceManager();
		SubsolvService subsolver = new SubsolvService("subsolver");
		sm.AddService(subsolver);
		
		Request<SubsolvArg> r = new Request<SubsolvArg>("subsolver", "solve", Request.Goal.Action, new SubsolvArg("MC.VHMTGR CSZSWAU FJTBAM GRA GACZ 'DGAZ' QRTWA DRA QJCNAM SG GRA BSGTJBSW DFTABFA YJHBMSGTJB TB GRA ASCWU 2000D."));
		
		ArrayList<Response> responses = Request.Respond(r.toString(), sm);
		for (Response re : responses) {
			System.out.println(re.toString());
		}
	}
}
