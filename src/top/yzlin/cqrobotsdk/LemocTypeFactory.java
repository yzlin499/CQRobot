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
        beanMap.put(Lemoc.GET_GROUP_MSG, GroupMsgInfo.class);
        beanMap.put(Lemoc.GET_PERSON_MSG, PersonMsgInfo.class);
        beanMap.put(Lemoc.GET_DISCUSS_MSG, DiscussMsgInfo.class);
        beanMap.put(Lemoc.GET_GROUP_MEMBER_INCREASE, GroupMemberIncreaseEventInfo.class);
        beanMap.put(Lemoc.GET_GROUP_MEMBER_DECREASE, GroupMemberDecreaseEventInfo.class);
        beanMap.put(Lemoc.GET_GROUP_ADMIN_CHANGE, GroupAdminChangeEventInfo.class);
        beanMap.put(Lemoc.GET_GROUP_REQUEST, GroupMemberRequestEventInfo.class);
        beanMap.put(Lemoc.GET_FRIEND_INCREASE, FriendIncreaseEventInfo.class);
        beanMap.put(Lemoc.GET_FRIEND_REQUEST, FriendRequestEventInfo.class);

        eventMap.put(GroupMsgSolution.class, Lemoc.GET_GROUP_MSG);
        eventMap.put(PersonMsgSolution.class, Lemoc.GET_PERSON_MSG);
        eventMap.put(DiscussMsgSolution.class, Lemoc.GET_DISCUSS_MSG);
        eventMap.put(GroupMemberIncreaseSolution.class, Lemoc.GET_GROUP_MEMBER_INCREASE);
        eventMap.put(GroupMemberDecreaseSolution.class, Lemoc.GET_GROUP_MEMBER_DECREASE);
        eventMap.put(GroupAdminChangeSolution.class, Lemoc.GET_GROUP_ADMIN_CHANGE);
        eventMap.put(GroupMemberRequestSolution.class, Lemoc.GET_GROUP_REQUEST);
        eventMap.put(FriendIncreaseSolution.class, Lemoc.GET_FRIEND_INCREASE);
        eventMap.put(FriendRequestSolution.class, Lemoc.GET_FRIEND_REQUEST);

    }

    public Class<? extends AbstractInfo> getInfoClass(int act) {
        return beanMap.getOrDefault(act, AbstractInfo.class);
    }

    public Integer getEventClass(Class<? extends EventSolution> act) {
        return eventMap.get(act);
    }
}
