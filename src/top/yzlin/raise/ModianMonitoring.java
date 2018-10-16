package top.yzlin.raise;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.tools.Tools;

public class ModianMonitoring extends AbstractMonitoring {

    public ModianMonitoring(String proID, String groupID, CQRobot cqRobot) {
        super(new Modian(proID),groupID,cqRobot);
    }
}