package top.yzlin.sharedbanlist.cqrobotapi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteBanList implements OperationInterface {
    private Pattern pattern = Pattern.compile("删除黑名单[:|：](?<targetQQ>[\\d]{8,11})");
    private Matcher matcher;

    @Override
    public boolean check(String word) {
        return (matcher = pattern.matcher(word)).find();
    }

    @Override
    public String reply(BanListOperation banListOperation) {
        return banListOperation.deleteQqFromGroup(matcher.group("targetQQ")) ?
                "删除成功" : "删除失败";
    }
}
