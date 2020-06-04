package xyz.diodon.spec;

import java.util.UUID;

public abstract class Action {
	public String name;
	public String status;
	public Class argClass;
	public Class resultClass;
	
	public Action(String name, String status, Class argClass, Class resultClass) {
		this.name = name;
		this.status = status;	
		this.argClass = argClass;
		this.resultClass = resultClass;
	}

	public abstract Response Respond(Request r);
}
