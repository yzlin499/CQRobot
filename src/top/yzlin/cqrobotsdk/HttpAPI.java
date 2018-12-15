package top.yzlin.cqrobotsdk;

import com.alibaba.fastjson.JSONObject;
import top.yzlin.cqrobotsdk.msginterface.EventSolution;

public class HttpAPI implements CQRobot {

    private JSONWebSocketClient apiClient;
    private JSONWebSocketClient eventClient;

    /**
     * 实例化一个链接的客户端，本地
     *
     * @param port webSocket的端口
     */
    public HttpAPI(int port) {
        this(Integer.toString(port));
    }

    /**
     * 实例化一个链接的客户端，本地
     *
     * @param port webSocket的端口
     */
    public HttpAPI(String port) {
        this("ws://0.0.0.0", port);
    }

    /**
     * 实例化一个链接的客户端
     *
     * @param wsPath 按照某个地址来创建
     * @param port   webSocket的端口
     */
    public HttpAPI(String wsPath, String port) {
        //配置服务器
        apiClient = new JSONWebSocketClient(wsPath + ':' + port + "/api/");
        eventClient = new JSONWebSocketClient(wsPath + ':' + port + "/event/");
        eventClient.setOnMessage(this::onMessage);
        apiClient.connect();
        eventClient.connect();
    }

    public static void main(String[] args) {
        CQRobot cqRobot = new HttpAPI(6700);
    }

    private void onMessage(JSONObject jo) {
        System.out.println(jo);
    }

    private JSONObject makeJSON(String action, JSONObject params) {
        return new JSONObject()
                .fluentPut("action", action)
                .fluentPut("params", params);
    }

    @Override
    public void sendPersonMsg(String qqID, String msg) {
        sendMsg(qqID, msg, HttpAPICode.SEND_PERSON_MSG, "user_id");
    }

    @Override
    public void sendGroupMsg(String groupID, String msg) {
        sendMsg(groupID, msg, HttpAPICode.SEND_GROUP_MSG, "group_id");
    }

    @Override
    public void sendDiscussMsg(String discussID, String msg) {
        sendMsg(discussID, msg, HttpAPICode.SEND_DISCUSS_MSG, "discuss_id");
    }

    private void sendMsg(String id, String msg, String msgType, String key) {
        if (id != null && msg != null) {
            apiClient.send(makeJSON(msgType,
                    new JSONObject()
                            .fluentPut(key, id)
                            .fluentPut("message", msg)).toString());
        }
    }

    @Override
    public boolean addMsgSolution(EventSolution msm) {
        return false;
    }

    @Override
    public boolean removeMsgSolution(EventSolution msm) {
        return false;
    }

    @Override
    public void close() {

    }
}
