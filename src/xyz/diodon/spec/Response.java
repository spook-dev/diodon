package xyz.diodon.spec;

import java.util.UUID;

public class Response {
	public UUID RequestID;
	//private Request Parent;
	public int group_idx;
	public int group_size;
	Object Result;
	
	public Response(UUID RequestID, Object result) { //Request parent, 
		//Parent = parent;
		Result = result;
		group_idx = 0;
		group_size = 1;
	}
	
	public Response(UUID RequestID, Object result, int groupidx, int groupsize) {
		Result = result;
		group_idx = groupidx;
		group_size = groupsize;
	}

	public String toString() {
		return Request.gsonify(this);
	}
}

