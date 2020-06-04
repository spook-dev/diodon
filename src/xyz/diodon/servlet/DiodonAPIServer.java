package xyz.diodon.servlet;

import javax.servlet.annotation.WebServlet;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import xyz.diodon.spec.*;
import xyz.diodon.spec.subsolv.*;

@WebServlet("/api")
public class DiodonAPIServer extends WebSocketServlet {
	private static final long serialVersionUID = 1L;
	public ServiceManager SM;

	@Override
	public void configure(WebSocketServletFactory factory) {
		WebSocketCreator creator = new WebSocketCreator() {
			@Override
			public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
				return new DiodonAPIServerSocket(SM);
			}
		};

		factory.setCreator(creator);
	}

	public DiodonAPIServer() {
		super();
		SM = new ServiceManager();
		SubsolvService subsolver = new SubsolvService("subsolver");
		SM.AddService(subsolver);
	}

}
