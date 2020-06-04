package xyz.diodon.spec.subsolv;

import java.util.ArrayList;
import xyz.diodon.spec.Action;

public class SubsolvAction extends Action {
	@Override
	public ArrayList<Object> Respond(Object o) {
		SubsolvArg argument = (SubsolvArg)o;
		String ct = string_utils.purifyCT(argument.Ciphertext);
		
		long unixTime = System.currentTimeMillis();
		ArrayList<key> m = ((SubsolvService)Parent).subsolver.AdvancedCrack(ct, false);
		
		ArrayList<Object> results = new ArrayList<Object>();
		for(int i = 0; i < m.size(); i++){
			results.add(
				new SubsolvResult(
					m.get(i).decrypt(argument.Ciphertext),
					(int)(System.currentTimeMillis() - unixTime)
				));
		}
		return results;
	}
}
