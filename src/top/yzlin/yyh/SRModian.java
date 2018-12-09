package top.yzlin.yyh;

import top.yzlin.cqrobotsdk.CQRobot;
import top.yzlin.cqrobotsdk.cqinfo.PersonMsgInfo;
import top.yzlin.cqrobotsdk.msginterface.reply.PersonMsgReply;
import top.yzlin.raise.Modian;
import top.yzlin.raise.ModianMonitoring;

public class SRModian extends ModianMonitoring {

    public SRModian(String proID, String groupID, CQRobot cqRobot) {
        super(proID, groupID, cqRobot);

        cqRobot.addMsgSolution(new PersonMsgReply() {
            @Override
            public boolean fromQQ(String from) {
                return "472910740".equals(from) || "499680328".equals(from);
            }

            @Override
            public boolean checkMsg(String from) {
                return from.matches("更改集资ID[:|：][\\d]+");
            }

            @Override
            public String replyMsg(PersonMsgInfo a) {
                Modian modian = getModian();
                modian.setProjectID(a.getMsg().substring(7));
                return modian.getTitle();
            }
        });

    }


}
