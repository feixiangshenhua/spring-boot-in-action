package com.nari.guangzhou.dicp.dicpwsserver;


import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.enums.ReadyState;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

/**
 * Java实现的websocket客户端
 */
@Slf4j
public class WebSocketClient4j extends WebSocketClient {

    public WebSocketClient4j(String url) throws URISyntaxException {
        super(new URI(url));
    }

    @Override
    public void onOpen(ServerHandshake shake) {
        System.out.println("握手...");
        for (Iterator<String> it = shake.iterateHttpFields(); it.hasNext(); ) {
            String key = it.next();
            System.out.println(key + ":" + shake.getFieldValue(key));
        }
    }

    @Override
    public void onMessage(String paramString) {
        System.out.println("接收到消息：" + paramString);
    }

    @Override
    public void onClose(int paramInt, String paramString, boolean paramBoolean) {
        System.out.println("关闭...");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("异常" + e);

    }

}
