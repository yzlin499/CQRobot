package top.yzlin.monitoring;

import top.yzlin.tools.Tools;

import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class Monitoring<T> implements Runnable {

    private BaseData<T> baseData;
    private Consumer<T[]> consumer;
    private long frequency=8000;


    private T oldData;
    private Predicate<T> predicate=p-> predicate(p,oldData);

    public Monitoring(BaseData<T> baseData){
        this.baseData=baseData;
    }

    public void setConsumer(Consumer<T[]> consumer) {
        this.consumer = consumer;
    }

    public BaseData<T> getBaseData() {
        return baseData;
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    protected abstract boolean predicate(T newData,T oldData);

    void initOldData(){
        oldData=baseData.getData()[0];
    }

    void doMonitoring(){
        T[] data=baseData.getData(predicate);
        if(data!=null && data.length>0){
            oldData=data[0];
            consumer.accept(data);
        }
        Tools.sleep(frequency);
    }

    @Override
    public void run() {
        initOldData();
        while(true){
            doMonitoring();
        }
    }



}
