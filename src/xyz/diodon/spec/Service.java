package xyz.diodon.spec;

import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.UUID;

public abstract class Service {
	public String Name;
	//private Hashtable<UUID, Store<?>> Stores;
	//private Hashtable<UUID, Read<?>> Reads;
	private Hashtable<UUID, Action> ActiveActions = new Hashtable<UUID, Action>(); //running actions
	private Hashtable<String, Class<?>> ActionRef = new Hashtable<String, Class<?>>();
	private Hashtable<String, Type> ArgTypeRef = new Hashtable<String, Type>();
	private Hashtable<String, Type> ReturnTypeRef = new Hashtable<String, Type>();
	
	public void registerAction(String method, Class<?> ActionClass, Type argType, Type returnType) {
		ActionRef.put(method, ActionClass);
		ArgTypeRef.put(method, argType);
		ReturnTypeRef.put(method, returnType);
	}
	
	public Type GetArgType(String method) {
		return ArgTypeRef.get(method);
	}
	
	public Type GetReturnType(String method) {
		return ReturnTypeRef.get(method);
	}
	
	public Action NewAction(String name) {
		try {
			Action a = (Action) ActionRef.get(name).newInstance();
			a.Register(this);
			ActiveActions.put(a.ID, a);
			return a;
		} catch (InstantiationException e) {
			System.err.println("Method " + name + " of " + Name + " failed to instantiate");
		} catch (IllegalAccessException e) {
			System.err.println("Method " + name + " of " + Name + " is forbidden");
		}
		return null;
	}
	
	public Service(String name) {
		Name = name;
	}
}
