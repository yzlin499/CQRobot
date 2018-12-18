package top.yzlin.cqrobotsdk.msginterface;

import top.yzlin.cqrobotsdk.cqinfo.AbstractInfo;

public interface EventSolution<T extends AbstractInfo> {
    void msgSolution(T msg);
}
