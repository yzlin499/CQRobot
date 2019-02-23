package top.yzlin.cqrobotsdk;

import com.alibaba.fastjson.JSONObject;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import top.yzlin.tools.Tools;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

public class JSONWebSocketClient extends WebSocketClient {
    private static final Consumer<JSONObject> NOTHING = e -> {
    };
    private int port;
    private Consumer<JSONObject> onMessage = NOTHING;

    public JSONWebSocketClient(String uri) {
        this(createURI(uri));
    }

    public JSONWebSocketClient(URI serverUri) {
        super(serverUri);
        port = serverUri.getPort();
    }

    private static URI createURI(String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setOnMessage(Consumer<JSONObject> onMessage) {
        this.onMessage = onMessage;
    }

    @Override
    public void onOpen(ServerHandshake arg0) {
        Tools.print("成功链接webSocket,端口:" + port);
    }

    @Override
    public void onMessage(String arg) {
        onMessage.accept(JSONObject.parseObject(arg));
    }

    @Override
    public void onError(Exception arg0) {
        arg0.printStackTrace();
        Tools.log(arg0.getMessage());
        Tools.print("HTTPAPI出错了，炸了炸了");
    }

    @Override
    public void onClose(int arg0, String arg1, boolean arg2) {
        Tools.print("关闭连接，断开");
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        try {
            System.out.println(new String(bytes.array(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
