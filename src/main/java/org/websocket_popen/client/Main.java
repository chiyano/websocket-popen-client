package org.websocket_popen.client;

import gnu.getopt.Getopt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.util.concurrent.Future;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocket.Connection;
import org.eclipse.jetty.websocket.WebSocketClient;
import org.eclipse.jetty.websocket.WebSocketClientFactory;

public class Main {
	private static final String programName = System.getProperty("program.name", "websocket-popen-client");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String uri = null;

		Getopt opt = new Getopt(programName, args, "h");
		int c;
		while ((c = opt.getopt()) > 0) {
			switch (c) {
			case 'h':
				usage();
				System.exit(0);
				break;
			default:
				break;
			}
		}
		if (args.length > 0) {
			uri = args[opt.getOptind()];
		} else {
			usage();
			System.exit(1);
		}
		if (uri == null) {
			throw new IllegalArgumentException("No URI specified");
		}

		try {
			new Main(uri);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Associate to WebSocket URL and then enter the REPL.
	 * 
	 * @param url a websocket url websocket-open served
	 * @throws Exception
	 */
	public Main(String url) throws Exception {
		WebSocketClientFactory webSocketClientFactory = new WebSocketClientFactory();
		webSocketClientFactory.start();
		WebSocketClient client = webSocketClientFactory.newWebSocketClient();
		Future<Connection> futureConnection = 
				client.open(new URI(url), new WebSocket.OnTextMessage() {

					public void onMessage(String data) {
						System.out.print(data);
					}

					public void onOpen(Connection connection) {
					}

					public void onClose(int closeCode, String message) {
					}
				});
		final Connection connection = futureConnection.get();
		
		// Send first message. Since WebSocket is a stream oriented but not  
		// message oriented. Before a receive handler is not registered, 
		// the delivered data is discarded. Because of that,  
		// websocket-popen-server blocks outbound data until a first message 
		// received.
		connection.sendMessage("");

		// Standard input loop
		final Thread thread = new Thread(new Runnable() {
			public void run() {
				try {
					CharBuffer buf = CharBuffer.allocate(4096);
					BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
					while (reader.read(buf) > -1) {
						connection.sendMessage(buf.flip().toString());
						buf.clear();
					}
					connection.close();
				} catch (ClosedByInterruptException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		});
		thread.start();

		// Block while the websocket connection is active.
		while (connection.isOpen());
		
		// TODO I think better to explicitly close the thread reading STDIN, 
		// but I don't know how to do it.
		
		connection.close();

		System.exit(0);
	}

	/**
	 * Usage
	 */
	private static void usage() {
		System.out.println(""
				+ "Usage: " + programName + " [-h] <URI>");
	}

}
