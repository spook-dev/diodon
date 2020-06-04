package xyz.diodon.servlet;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;

import xyz.diodon.spec.*;

public class DiodonAPIServerSocket extends WebSocketAdapter {
	private ServiceManager SM;
	
	public DiodonAPIServerSocket(ServiceManager sm) {
		SM = sm;
	}
	
	@Override
    public void onWebSocketConnect(Session sess)
    {
        super.onWebSocketConnect(sess);
        System.out.println("Socket Connected: " + sess);
    }
    
    @Override
    public void onWebSocketText(String message)
    {
        super.onWebSocketText(message);
        System.out.println("Received: " + message);
        ArrayList<Response> responses = Request.Respond(message, SM);

		for(Response r : responses) {
	        try {
				getRemote().sendString(r.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
    
    @Override
    public void onWebSocketClose(int statusCode, String reason)
    {
        super.onWebSocketClose(statusCode,reason);
        System.out.println("Socket Closed: [" + statusCode + "] " + reason);
    }
    
    @Override
    public void onWebSocketError(Throwable cause)
    {
        super.onWebSocketError(cause);
        cause.printStackTrace(System.err);
    }
}
