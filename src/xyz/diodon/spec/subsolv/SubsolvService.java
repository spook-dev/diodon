package xyz.diodon.spec.subsolv;

import com.google.gson.reflect.TypeToken;

import xyz.diodon.spec.Request;
import xyz.diodon.spec.Service;

public class SubsolvService extends Service {
	protected substitution_solver subsolver;
	public SubsolvService(String name) {
		super(name);
		
		registerAction("solve", SubsolvAction.class,
				new TypeToken<Request<SubsolvArg>>(){}.getType(),
				new TypeToken<Request<SubsolvResult>>(){}.getType());
		
		subsolver = new substitution_solver();
		subsolver.populate_patternTable();
		System.out.println("subsolver service ready");
	}
	
}
