package top.yzlin.koudai48;

import top.yzlin.tools.Tools;

public class KDAutoSignPools implements Runnable {
    private KDAutoSign[] kdAutoSigns;

    public KDAutoSignPools(KDAutoSign[] kdAutoSigns) {
        this.kdAutoSigns = kdAutoSigns;
    }

    @Override
    public void run() {
        Tools.sleep(Tools.todayRemainTime() + (1000 * 60 * 5));
        while (true) {
            for (KDAutoSign kdAutoSign : kdAutoSigns) {
                kdAutoSign.signIn();
                Tools.sleep(1000 * 60);
            }
            Tools.sleep((1000 * 60 * 60 * 24) - (1000 * 66 * kdAutoSigns.length));
        }
    }
}
