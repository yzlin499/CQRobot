package top.yzlin.cqrobotsdk;

import top.yzlin.cqrobotsdk.cqinfo.AbstractInfo;
import top.yzlin.cqrobotsdk.msginterface.EventSolution;
import top.yzlin.cqrobotsdk.msginterface.reply.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCQRobot implements CQRobot {

    private Map<Integer, List<EventSolution>> eventListMap = new HashMap<>();

    void triggerEvent(AbstractInfo info) {
        getEventList(info.getAct()).forEach(item -> item.msgSolution(info));
    }

    /**
     * 添加群事件处理
     *
     * @param msm 注册事件
     * @return 是否注册成功
     */
    @Override
    public boolean addMsgSolution(EventSolution msm) {
        if (msm instanceof ReplySolution) {
            int type = 0;
            if (msm instanceof GroupMsgReply) {
                type = GET_GROUP_MSG;
            } else if (msm instanceof PersonMsgReply) {
                type = GET_PERSON_MSG;
            } else if (msm instanceof DiscussMsgReply) {
                type = GET_DISCUSS_MSG;
            }
            return getEventList(type).add(
                    new ProxyReplySolution(this, (ReplySolution) msm, type));
        } else {
            for (Class clazz : msm.getClass().getInterfaces()) {
                if (EventSolution.class.isAssignableFrom(clazz)) {
                    return getEventList(getEventClass(clazz)).add(msm);
                }
            }
        }
        return false;
    }

    /**
     * 删除事件
     *
     * @param msm 删除事件
     * @return 是否删除成功
     */
    @Override
    public boolean removeMsgSolution(EventSolution msm) {
        for (Class clazz : msm.getClass().getInterfaces()) {
            if (EventSolution.class.isAssignableFrom(clazz)) {
                return getEventList(getEventClass(clazz)).remove(msm);
            }
        }
        return false;
    }

    private List<EventSolution> getEventList(Integer integer) {
        if (eventListMap.containsKey(integer)) {
            return eventListMap.get(integer);
        } else {
            List<EventSolution> eventSolutionList = new ArrayList<>();
            eventListMap.put(integer, eventSolutionList);
            return eventSolutionList;
        }
    }

    @Override
    public void close() {
        eventListMap.forEach((k, v) -> v.clear());
        eventListMap.clear();
        eventListMap = null;
    }

    protected abstract Integer getEventClass(Class<? extends EventSolution> act);
}
