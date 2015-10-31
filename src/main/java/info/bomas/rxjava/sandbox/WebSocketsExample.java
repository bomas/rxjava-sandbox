package info.bomas.rxjava.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.websocket.WebSocketClient;
import rx.Observable;
import rx.exceptions.OnErrorThrowable;

public class WebSocketsExample {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketsExample.class);
	
	
	public static void main(String[] args){
		ObjectMapper objectMapper = new ObjectMapper();
		
		WebSocketClient<WebSocketFrame, WebSocketFrame> client = RxNetty.newWebSocketClientBuilder("s-west.ripple.com", 443)
                .withWebSocketURI("/")
                .withWebSocketVersion(WebSocketVersion.V13)
                .withMessageAggregation(true)
                .withMaxFramePayloadLength(Integer.MAX_VALUE)
                .withName("Rippled")
                .build();
		
		client.connect().subscribe(connection -> {
				Observable<String> messagesObservable = connection.getInput()
						.filter(webSocketFrame -> webSocketFrame instanceof TextWebSocketFrame)
						.map(webSocketFrame -> ((TextWebSocketFrame) webSocketFrame).text());
				
				messagesObservable.forEach(text -> LOGGER.info(text));
			
				messagesObservable
					.map(text -> {
							try {
								return objectMapper.readTree(text);
							} catch (Exception e) {
								throw OnErrorThrowable.from(e);
							}
						})
					.filter(jsonNode -> jsonNode.get("ledger_index") != null)
					.map(jsonNode -> jsonNode.get("ledger_index").asLong())
					.forEach(ledgerIndex ->  {
						LOGGER.info("Retrieve ledger with index {}", ledgerIndex);
						String payload = String.format("{\"command\": \"ledger\",\"full\": false,\"accounts\": false,\"transactions\": true,\"expand\": true,\"ledger_index\": %s}",ledgerIndex);
						connection.writeAndFlush(new TextWebSocketFrame(payload));
					});
			
			
			TextWebSocketFrame frame = new TextWebSocketFrame("{\"id\": 1,\"command\": \"subscribe\",\"accounts\": [],\"streams\": [\"server\",\"ledger\"]}");
	        connection.writeAndFlush(frame);
		});
		
		
		
		while(true){
			
		}
	}

}
