package xyz.diodon.spec;

import java.util.Hashtable;
import java.util.UUID;

public abstract class Service {
	public String Name;
	//private Hashtable<UUID, Store<?>> Stores;
	//private Hashtable<UUID, Read<?>> Reads;
	private Hashtable<UUID, Action> ActiveActions; //running actions
	public Hashtable<String, Class<?>> ActionRef;
	
	public void registerAction(String name, Class<?> ActionClass) {
		ActionRef.put(name, ActionClass);
	}
	
	public Action NewAction(String name) {
		try {
			Action a = (Action) ActionRef.get(name).newInstance();
			a.Register(this);
			ActiveActions.put(a.ID, a);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Service(String name) {
		Name = name;
	}
}
