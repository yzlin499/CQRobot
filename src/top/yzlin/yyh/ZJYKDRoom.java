package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.koudai48.KDRoom;
import top.yzlin.koudai48.KDRoomInfo;
import top.yzlin.koudai48.KDRoomMonitoring;
import top.yzlin.koudai48.KDValidation;

public class ZJYKDRoom extends KDRoomMonitoring {
    public ZJYKDRoom(KDValidation kdValidation, String memberName, String groupID, CQRobot cqRobot) {
        super(kdValidation, memberName, groupID, cqRobot);
    }

    public ZJYKDRoom(KDRoom kdRoom, String groupID, CQRobot cqRobot) {
        super(kdRoom, groupID, cqRobot);
    }

    @Override
    protected String sendText(KDRoomInfo kdRoomInfo) {
        String msg = kdRoomInfo.getMsg();
        if (msg.charAt(0) == '%') {
            return null;
        } else if (kdRoomInfo.getMsg().charAt(0) == '$') {
            return kdRoomInfo.getMsg().substring(1);
        } else {
            return super.sendText(kdRoomInfo);
        }
    }
}
