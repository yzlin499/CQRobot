package top.yzlin.raise;

import top.yzlin.cqrobotsdk.CQRobot;

public class ModianMonitoring extends AbstractMonitoring {

    private Modian modian;

    public ModianMonitoring(Modian modian, String groupID, CQRobot cqRobot) {
        super(modian, groupID, cqRobot);
        this.modian = modian;
    }

    public ModianMonitoring(String proID, String groupID, CQRobot cqRobot) {
        this(new Modian(proID), groupID, cqRobot);
    }

    public Modian getModian() {
        return modian;
    }
}