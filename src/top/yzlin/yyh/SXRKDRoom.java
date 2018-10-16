package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.koudai48.KDRoom;
import top.yzlin.koudai48.KDRoomInfo;
import top.yzlin.koudai48.KDRoomMonitoring;
import top.yzlin.koudai48.KDValidation;

public class SXRKDRoom extends KDRoomMonitoring {
    public SXRKDRoom(KDValidation kdValidation, String memberName, String groupID, CQRobot cqRobot) {
        super(kdValidation, memberName, groupID, cqRobot);
    }

    public SXRKDRoom(KDRoom kdRoom, String groupID, CQRobot cqRobot) {
        super(kdRoom, groupID, cqRobot);
    }

    @Override
    protected String sendText(KDRoomInfo kdRoomInfo) {
        return "来自口袋的消息:\n"+super.sendText(kdRoomInfo);
    }
}
