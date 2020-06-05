package xyz.diodon.spec;

import java.util.ArrayList;
import java.util.UUID;

//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonSerializationContext;
//import com.google.gson.JsonSerializer;

public abstract class Action {
	public String Name;
	
	public UUID ID;
	public String Status = "";
	protected Service Parent;
	
	public void Register(Service parent) {
		ID = UUID.randomUUID();
		Parent = parent;
	}

	public abstract ArrayList<Object> Respond(Object r);
	
//	public static JsonSerializer<Action> ActionSerializer = new JsonSerializer<Action>() {
//		@Override
//		public JsonElement serialize(Action src, Type typeOfSrc, JsonSerializationContext context) {
//			JsonObject jsonAction = new JsonObject();
//			jsonAction.addProperty("Name", src.Name);
//			jsonAction.addProperty("ID", src.ID.toString());
//			jsonAction.addProperty("Status", src.Status);
//			return jsonAction;
//		}
//	};
}
