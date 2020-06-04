package xyz.diodon.spec;

import java.util.Hashtable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class ServiceManager {
	public Hashtable<String, Service> Services;
	@Expose(serialize=false)
	public GsonBuilder customGson;
	
	public ServiceManager() {
		Services = new Hashtable<String, Service>();
		customGson = new GsonBuilder();
		customGson.excludeFieldsWithoutExposeAnnotation();
		//customGson.registerTypeAdapter(Action.class, Action.ActionSerializer);
	}
	
	public void AddService(Service s) {
		Services.put(s.Name, s);
	}
	public Service GetService(String name) {
		return Services.get(name);
	}
	
	public String customSerialize(Object o) {
		Gson gson = customGson.create();
		return gson.toJson(o);
	}
}
