package com.tkachev.javasandantertesttask;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import javax.websocket.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@ClientEndpoint
public class WebSocketEndPoint {

    private Session session = null;

    private static final Logger logger = LogManager.getLogger(WebSocketEndPoint.class);

    private StartController controller;

    public WebSocketEndPoint(URI uri) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, uri);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        if(controller == null){
            controller = StartApplication.getController();
        }
        this.session = session;
    }

    @OnMessage
    public void onMessage(Session session, ByteBuffer message) throws IOException {
        if (controller != null) {
            String stringMessage = new String(message.array(), StandardCharsets.UTF_8);
            controller.getPriceModel().loadPrices(stringMessage);
        }else{
            logger.warn("Message is empty, sID : " + session.getId());
        }

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        if (controller != null) {
            controller.getPriceModel().loadPrices(message);
        }else{
            logger.warn("Message is empty, sID : " + session.getId());
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        this.session = null;
    }

    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) throws IOException {
        try{
            logger.error("Error on session " + session.getId(), throwable);
            session.close();
        }catch (IOException e){
            logger.error("Error closing session id: " + session.getId() + " " , e);
        }
    }
}