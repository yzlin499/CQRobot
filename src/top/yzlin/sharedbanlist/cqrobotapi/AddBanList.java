package top.yzlin.sharedbanlist.cqrobotapi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddBanList implements OperationInterface {
    private final static Pattern PATTERN = Pattern.compile("添加黑名单[:|：](?<targetQQ>[\\d]{7,12})[:|：](?<describe>.+)");
    private Matcher matcher;

    @Override
    public boolean check(String word) {
        return (matcher = PATTERN.matcher(word)).find();
    }

    @Override
    public String reply(BanListOperation banListOperation) {

        return banListOperation.addQQToBanList(matcher.group("targetQQ"), matcher.group("describe")) ?
                "添加成功" : "添加失败";
    }
}
