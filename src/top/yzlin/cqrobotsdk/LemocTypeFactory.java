package top.yzlin.cqrobotsdk;

import top.yzlin.cqrobotsdk.cqinfo.*;
import top.yzlin.cqrobotsdk.msginterface.*;

import java.util.HashMap;

public class LemocTypeFactory {
    private final static LemocTypeFactory INSTANCE = new LemocTypeFactory();

    private LemocTypeFactory() {
        init();
    }

    public static LemocTypeFactory getInstance() {
        return INSTANCE;
    }

    private HashMap<Integer, Class<? extends AbstractInfo>> beanMap = new HashMap<>();
    private HashMap<Class<? extends EventSolution>, Integer> eventMap = new HashMap<>();

    private void init() {
        beanMap.put(CQRobot.GET_GROUP_MSG, GroupMsgInfo.class);
        beanMap.put(CQRobot.GET_PERSON_MSG, PersonMsgInfo.class);
        beanMap.put(CQRobot.GET_DISCUSS_MSG, DiscussMsgInfo.class);
        beanMap.put(CQRobot.GET_GROUP_MEMBER_INCREASE, GroupMemberIncreaseEventInfo.class);
        beanMap.put(CQRobot.GET_GROUP_MEMBER_DECREASE, GroupMemberDecreaseEventInfo.class);
        beanMap.put(CQRobot.GET_GROUP_ADMIN_CHANGE, GroupAdminChangeEventInfo.class);
        beanMap.put(CQRobot.GET_GROUP_REQUEST, GroupMemberRequestEventInfo.class);
        beanMap.put(CQRobot.GET_FRIEND_INCREASE, FriendIncreaseEventInfo.class);
        beanMap.put(CQRobot.GET_FRIEND_REQUEST, FriendRequestEventInfo.class);

        eventMap.put(GroupMsgSolution.class, CQRobot.GET_GROUP_MSG);
        eventMap.put(PersonMsgSolution.class, CQRobot.GET_PERSON_MSG);
        eventMap.put(DiscussMsgSolution.class, CQRobot.GET_DISCUSS_MSG);
        eventMap.put(GroupMemberIncreaseSolution.class, CQRobot.GET_GROUP_MEMBER_INCREASE);
        eventMap.put(GroupMemberDecreaseSolution.class, CQRobot.GET_GROUP_MEMBER_DECREASE);
        eventMap.put(GroupAdminChangeSolution.class, CQRobot.GET_GROUP_ADMIN_CHANGE);
        eventMap.put(GroupMemberRequestSolution.class, CQRobot.GET_GROUP_REQUEST);
        eventMap.put(FriendIncreaseSolution.class, CQRobot.GET_FRIEND_INCREASE);
        eventMap.put(FriendRequestSolution.class, CQRobot.GET_FRIEND_REQUEST);
        eventMap.put(GroupFileUploadSolution.class, CQRobot.GET_GROUP_UPLOAD_FILE);
    }

    public Class<? extends AbstractInfo> getInfoClass(int act) {
        return beanMap.getOrDefault(act, AbstractInfo.class);
    }

    public Integer getEventClass(Class<? extends EventSolution> act) {
        return eventMap.get(act);
    }
}
