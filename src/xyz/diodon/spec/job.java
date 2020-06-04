package xyz.diodon.spec;

import java.util.UUID;

public abstract class job {
	public String job_name;
	public UUID ID;
	public String status;
	
	public job() {
		ID = UUID.randomUUID();
	}
	
	public abstract void Run();
}
