package iingyeo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Kang on 2015. 6. 15..
 */
@Slf4j
public class SessionMapper {

    private static final ConcurrentHashMap<String, WebSocketSession> websocketSessionMap = new ConcurrentHashMap<String, WebSocketSession>();

    public static synchronized void add(WebSocketSession session) {
        log.debug("add Session : {} {}", session.getId(), session.toString());
        websocketSessionMap.put(session.getId(), session);
    }

    public static synchronized void remove(WebSocketSession session) {
        log.debug("remove Session : {} {}", session.getId(), session.toString());
        websocketSessionMap.remove(session.getId());
    }

    public static void sendAllForWebsocket(String message) {
        log.debug("sessionMap.size() : {}", websocketSessionMap.size());
        for (Map.Entry<String, WebSocketSession> em : websocketSessionMap.entrySet()) {
            try {
                WebSocketSession session = em.getValue();
                if (session.isOpen()) {
                    synchronized (session) {
                        session.sendMessage(new TextMessage(message));
                    }
                }
            } catch (IOException e) {
                log.error("{}", e);
            }
        }
    }
}


