package top.yzlin.sharedbanlist.cqrobotapi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteBanList implements OperationInterface {
    private final static Pattern PATTERN = Pattern.compile("删除黑名单[:|：](?<targetQQ>[\\d]{7,12})");
    private Matcher matcher;

    @Override
    public boolean check(String word) {
        return (matcher = PATTERN.matcher(word)).find();
    }

    @Override
    public String reply(BanListOperation banListOperation) {
        return banListOperation.deleteQqFromGroup(matcher.group("targetQQ")) ?
                "删除成功" : "删除失败";
    }
}
