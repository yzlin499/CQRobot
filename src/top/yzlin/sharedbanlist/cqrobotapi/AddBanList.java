package top.yzlin.sharedbanlist.cqrobotapi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBanList implements OperationInterface {
    private Pattern pattern = Pattern.compile("添加黑名单[:|：](?<targetQQ>[\\d]{8,11})[:|：](?<describe>[\\d]{8,11})");
    private Matcher matcher;

    @Override
    public boolean check(String word) {
        return (matcher = pattern.matcher(word)).find();
    }

    @Override
    public String reply(BanListOperation banListOperation) {
        return banListOperation.addQQToBanList(matcher.group("targetQQ"), matcher.group("describe")) ?
                "添加成功" : "添加失败";
    }
}
