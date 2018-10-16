package top.yzlin.monitoring;

import top.yzlin.raise.ModianMonitoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;

public class MonitoringMoreData<T extends Monitoring> implements Runnable{

    private List<T> monitoringList;

    public MonitoringMoreData(){
        monitoringList=new ArrayList<>();
    }

    public MonitoringMoreData(Collection<T> data){
        monitoringList=new ArrayList<>(data);
    }


    @Override
    public void run() {
        monitoringList.forEach(Monitoring::initOldData);
        while(true){
            for (T t : monitoringList) {
                t.doMonitoring();
            }
        }
    }

    public void setFrequency(long time){
        monitoringList.forEach(i-> i.setFrequency(time));
    }

    public int size() {
        return monitoringList.size();
    }

    public boolean add(T monitoring) {
        return monitoringList.add(monitoring);
    }

    public boolean remove(T o) {
        return monitoringList.remove(o);
    }

    public boolean addAll(Collection<? extends T> c) {
        return monitoringList.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return monitoringList.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return monitoringList.removeAll(c);
    }

    public void replaceAll(UnaryOperator<T> operator) {
        monitoringList.replaceAll(operator);
    }

    public void add(int index, T element) {
        monitoringList.add(index, element);
    }

    public Monitoring remove(int index) {
        return monitoringList.remove(index);
    }
}
