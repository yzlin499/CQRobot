package top.yzlin.monitoring;

import java.util.function.Predicate;

public interface BaseData<T> {

    default T[] getData(){
        return getData(i->true);
    }

    T[] getData(Predicate<T> predicate);
}
