package top.yzlin.raise;

import top.yzlin.cqrobotsdk.CQRobot;

public class ModianMonitoring extends AbstractMonitoring {

    public ModianMonitoring(Modian modian, String groupID, CQRobot cqRobot) {
        super(modian, groupID, cqRobot);
    }

    public ModianMonitoring(String proID, String groupID, CQRobot cqRobot) {
        super(new Modian(proID),groupID,cqRobot);
    }
}