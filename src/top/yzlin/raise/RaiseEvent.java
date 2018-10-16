package top.yzlin.raise;

public interface RaiseEvent {
    //设置集资监控的触发
    String eventTrigger(RaiseData raiseData);
}
