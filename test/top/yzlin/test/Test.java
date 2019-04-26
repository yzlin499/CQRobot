package top.yzlin.test;

import top.yzlin.koudai49.KD49Data;

public class Test {
    public static void main(String[] args) {


        System.out.println(KD49Data.getInstance().getMemberInfoById(6700));
        System.out.println(KD49Data.getInstance().getMemberInfoById(6720));
        System.out.println(KD49Data.getInstance().getMemberInfoById(6740));
        System.out.println(KD49Data.getInstance().getMemberInfoById(6769));

    }
}
