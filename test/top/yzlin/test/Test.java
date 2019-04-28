package top.yzlin.test;

import com.alibaba.fastjson.JSON;
import top.yzlin.koudai49.KDRoom;

public class Test {
    public static void main(String[] args) {
        KDRoom kdRoom = new KDRoom("13546860076", "");
        System.out.println(JSON.toJSON(kdRoom.getData(1556463042299L)));

    }
}
