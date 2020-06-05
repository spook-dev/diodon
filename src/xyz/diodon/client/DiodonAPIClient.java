package xyz.diodon.client;

import java.net.URI;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import xyz.diodon.spec.*;
import xyz.diodon.spec.subsolv.SubsolvArg;

public class DiodonAPIClient
{
	public DiodonAPIClient() {
		
	}
    public static void main(String[] args)
    {
        URI uri = URI.create("ws://localhost:8080/diodon/api");
        
        WebSocketClient client = new WebSocketClient();
        try
        {
            try
            {
                client.start();
                // The socket that receives events
                DiodonAPIClientSocket socket = new DiodonAPIClientSocket();
                // Attempt Connect
                Future<Session> fut = client.connect(socket,uri);
                // Wait for Connect
                Session session = fut.get();
                // Send a message
                Request<SubsolvArg> r = new Request<SubsolvArg>("subsolver", "solve", Request.Goal.Action, new SubsolvArg("MC.VHMTGR CSZSWAU FJTBAM GRA GACZ 'DGAZ' QRTWA DRA QJCNAM SG GRA BSGTJBSW DFTABFA YJHBMSGTJB TB GRA ASCWU 2000D."));
                session.getRemote().sendString(r.toString());
            }
            finally
            {
                //client.stop();
            }
        }
        catch (Throwable t)
        {
            t.printStackTrace(System.err);
        }
    }
}