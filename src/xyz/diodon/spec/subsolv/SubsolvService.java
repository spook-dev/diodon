package xyz.diodon.spec.subsolv;

import xyz.diodon.spec.Service;

public class SubsolvService extends Service {
	protected substitution_solver subsolver;
	public SubsolvService(String name) {
		super(name);
		
		registerAction("solve", SubsolvAction.class);
		
		subsolver = new substitution_solver();
		subsolver.populate_patternTable();
		System.out.println("subsolver service ready");
	}
	
}
