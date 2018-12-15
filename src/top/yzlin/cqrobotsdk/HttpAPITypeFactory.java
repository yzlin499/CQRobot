package top.yzlin.cqrobotsdk;

import com.alibaba.fastjson.JSONObject;
import top.yzlin.cqrobotsdk.cqinfo.*;

import java.util.Objects;
import java.util.Optional;

class HttpAPITypeFactory {
    private static final HttpAPITypeFactory INSTANCE = new HttpAPITypeFactory();

    private HttpAPITypeFactory() {
        init();
    }

    static HttpAPITypeFactory getInstance() {
        return INSTANCE;
    }

    private void init() {

    }

    AbstractInfo getInfo(JSONObject data) {
        switch (data.getString("post_type")) {
            case "message":
                switch (data.getString("message_type")) {
                    case "private":
                        return toPersonMsgInfo(data);
                    case "group":
                        return toGroupMsgInfo(data);
                    case "discuss":
                        return toDiscussMsgInfo(data);
                    default:
                        return null;
                }
            case "notice":
                switch (data.getString("notice_type")) {
                    case "group_upload":
                        return toGroupFileUploadEventInfo(data);
                    case "group_admin":
                        return toGroupAdminChangeEventInfo(data);
                    case "group_decrease":
                        return toGroupMemberDecreaseEventInfo(data);
                    case "group_increase":
                        return toGroupMemberIncreaseEventInfo(data);
                    case "friend_add":
                        return toFriendIncreaseEventInfo(data);
                    default:
                        return null;
                }
            case "request":
                switch (data.getString("request_type")) {
                    case "friend":
                        return toFriendRequestEventInfo(data);
                    case "group":
                        return toGroupMemberRequestEventInfo(data);
                    default:
                        return null;
                }
            default:
                return null;
        }

    }

    private void initAbstractInfo(int act, AbstractInfo info, JSONObject data) {
        info.setAct(act);
        info.setFromQQ(data.getString("user_id"));
        info.setSendTime(data.getLongValue("time"));
        info.setSubType(Objects.toString(data.getString("sub_type"), ""));
    }

    private void initMsgInfo(MsgInfo info, JSONObject data) {
        info.setFont(data.getString("font"));
        info.setRawMsg(data.getString("raw_message"));
        info.setMsg(data.getString("message"));

        JSONObject sender = data.getJSONObject("sender");
        info.setAge(sender.getString("age"));
        info.setNick(sender.getString("nickname"));
        info.setSex(sender.getString("sex"));
    }

    private PersonMsgInfo toPersonMsgInfo(JSONObject data) {
        PersonMsgInfo personMsgInfo = new PersonMsgInfo();
        initAbstractInfo(Lemoc.GET_PERSON_MSG, personMsgInfo, data);
        initMsgInfo(personMsgInfo, data);
        return personMsgInfo;
    }

    private GroupMsgInfo toGroupMsgInfo(JSONObject data) {
        GroupMsgInfo groupMsgInfo = new GroupMsgInfo();
        initAbstractInfo(Lemoc.GET_GROUP_MSG, groupMsgInfo, data);
        initMsgInfo(groupMsgInfo, data);
        groupMsgInfo.setFromAnonymous(Optional.ofNullable(data.getJSONObject("anonymous"))
                .map(j -> j.getString("name")).orElse(""));
        groupMsgInfo.setFromGroup(data.getString("group_id"));
        groupMsgInfo.setFromGroupName("");
        groupMsgInfo.setUsername(data.getJSONObject("sender").getString("card"));
        return groupMsgInfo;
    }

    private DiscussMsgInfo toDiscussMsgInfo(JSONObject data) {
        DiscussMsgInfo discussMsgInfo = new DiscussMsgInfo();
        initAbstractInfo(Lemoc.GET_DISCUSS_MSG, discussMsgInfo, data);
        initMsgInfo(discussMsgInfo, data);
        discussMsgInfo.setFromDiscuss(data.getString("discuss_id"));
        return discussMsgInfo;
    }

    private GroupFileUploadEventInfo toGroupFileUploadEventInfo(JSONObject data) {
        GroupFileUploadEventInfo info = new GroupFileUploadEventInfo();
        initAbstractInfo(Lemoc.GET_GROUP_UPLOAD_FILE, info, data);
        info.setFromGroup(data.getString("group_id"));
        info.setFileName(data.getJSONObject("file").getString("name"));
        return info;
    }

    private GroupAdminChangeEventInfo toGroupAdminChangeEventInfo(JSONObject data) {
        GroupAdminChangeEventInfo info = new GroupAdminChangeEventInfo();
        initAbstractInfo(Lemoc.GET_GROUP_ADMIN_CHANGE, info, data);
        info.setFromGroup(data.getString("group_id"));
        info.setBeingOperateQQ(data.getString("operator_id"));
        return info;
    }

    private GroupMemberDecreaseEventInfo toGroupMemberDecreaseEventInfo(JSONObject data) {
        GroupMemberDecreaseEventInfo info = new GroupMemberDecreaseEventInfo();
        initAbstractInfo(Lemoc.GET_GROUP_MEMBER_DECREASE, info, data);
        info.setFromGroup(data.getString("group_id"));
        info.setBeingOperateQQ(data.getString("operator_id"));
        return info;
    }

    private GroupMemberIncreaseEventInfo toGroupMemberIncreaseEventInfo(JSONObject data) {
        GroupMemberIncreaseEventInfo info = new GroupMemberIncreaseEventInfo();
        initAbstractInfo(Lemoc.GET_GROUP_MEMBER_INCREASE, info, data);
        info.setFromGroup(data.getString("group_id"));
        info.setBeingOperateQQ(data.getString("operator_id"));
        return info;
    }

    private FriendIncreaseEventInfo toFriendIncreaseEventInfo(JSONObject data) {
        FriendIncreaseEventInfo info = new FriendIncreaseEventInfo();
        initAbstractInfo(Lemoc.GET_FRIEND_INCREASE, info, data);
        return info;
    }

    private FriendRequestEventInfo toFriendRequestEventInfo(JSONObject data) {
        FriendRequestEventInfo info = new FriendRequestEventInfo();
        initAbstractInfo(Lemoc.GET_FRIEND_REQUEST, info, data);
        info.setMsg(data.getString("comment"));
        info.setResponseFlag(data.getString("flag"));
        return info;
    }

    private GroupMemberRequestEventInfo toGroupMemberRequestEventInfo(JSONObject data) {
        GroupMemberRequestEventInfo info = new GroupMemberRequestEventInfo();
        initAbstractInfo(Lemoc.GET_GROUP_REQUEST, info, data);
        info.setMsg(data.getString("comment"));
        info.setFormGroup(data.getString("group_id"));
        info.setResponseFlag(data.getString("flag"));
        return info;
    }
}
