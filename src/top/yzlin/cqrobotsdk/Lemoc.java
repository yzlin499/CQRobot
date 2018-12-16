package top.yzlin.cqrobotsdk;

import com.alibaba.fastjson.JSONObject;
import org.java_websocket.client.WebSocketClient;
import top.yzlin.cqrobotsdk.cqinfo.AbstractInfo;
import top.yzlin.cqrobotsdk.msginterface.EventSolution;

/**
 * MsgTools是通过酷Q机器人的LEMOC插件，与Q群机器人进行通信的类
 * <br><br>
 * 有关接收信息方法的重写与开发,请访问:<a href="https://cqp.cc/t/29722">https://cqp.cc/t/29722</a><br><br>
 * 有关CQ码,请访问:<a href="https://d.cqp.me/Pro/CQ%E7%A0%81">https://d.cqp.me/Pro/CQ%E7%A0%81</a>
 *
 * @author yzlin
 */
public class Lemoc extends AbstractCQRobot {

    private WebSocketClient client;
    private LemocTypeFactory lemocTypeFactory = LemocTypeFactory.getInstance();

    /**
     * 实例化一个链接的客户端，本地
     * @param port webSocket的端口
     */
    public Lemoc(int port) {
        this(Integer.toString(port));
    }

    /**
     * 实例化一个链接的客户端，本地
     *
     * @param port webSocket的端口
     */
    public Lemoc(String port) {
        this("ws://localhost", port);
    }

    /**
     * 实例化一个链接的客户端
     * @param wsPath 按照某个地址来创建
     * @param port   webSocket的端口
     */
    public Lemoc(String wsPath, String port) {
        //配置服务器
        client = new JSONWebSocketClient(wsPath + ':' + port);
        ((JSONWebSocketClient) client).setOnMessage(this::onMessage);
        client.connect();
    }

    /**
     * 发送个人信息
     *
     * @param qqID QQ号
     * @param msg  发送信息
     */
    @Override
    public void sendPersonMsg(String qqID, String msg) {
        if (qqID != null && msg != null) {
            client.send(new JSONObject()
                    .fluentPut("act", SEND_PERSON_MSG)
                    .fluentPut("QQID", qqID)
                    .fluentPut("msg", msg)
                    .toString());
        }

    }

    /**
     * 发送Q群信息
     *
     * @param groupID Q群号码
     * @param msg 发送信息
     */
    @Override
    public void sendGroupMsg(String groupID, String msg) {
        if (groupID != null && msg != null) {
            client.send(new JSONObject()
                    .fluentPut("act", SEND_GROUP_MSG)
                    .fluentPut("groupid", groupID)
                    .fluentPut("msg", msg)
                    .toString());
        }

    }

    /**
     * 发送讨论组信息
     *
     * @param discussID 讨论组号码
     * @param msg 发送信息
     */
    @Override
    public void sendDiscussMsg(String discussID, String msg) {
        if (msg != null && discussID != null) {
            client.send(new JSONObject()
                    .fluentPut("act", SEND_DISCUSS_MSG)
                    .fluentPut("discussid", discussID)
                    .fluentPut("msg", msg)
                    .toString());
        }
    }

    /**
     * 接收信息的处理方法
     *
     * @param msg
     */
    private void onMessage(JSONObject msg) {
        int act = msg.getIntValue("act");
        AbstractInfo info = msg.toJavaObject(lemocTypeFactory.getInfoClass(act));
        triggerEvent(info);
    }

    /**
     * 重写这个方法，在close方法调用之前运行
     */
    protected void destruct() {

    }

    /**
     * 关闭链接与线程
     */
    @Override
    public void close() {
        destruct();
        client.close();
        super.close();
        lemocTypeFactory = null;
        client = null;
    }

    @Override
    protected Integer getEventClass(Class<? extends EventSolution> act) {
        return lemocTypeFactory.getEventClass(act);
    }
}