package top.yzlin.sharedbanlist.cqrobotapi;

import top.yzlin.sharedbanlist.BanInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsInBanList implements OperationInterface {
    private static final Pattern PATTERN = Pattern.compile("黑名单查询[:|：](?<targetQQ>[\\d]{7,12})");
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy/MM/dd");
    private Matcher matcher;

    static String replyWord(List<BanInfo> banInfos) {
        StringBuilder stringBuilder = new StringBuilder();
        if (banInfos.size() == 0) {
            stringBuilder.append("这个人，没有任何不良记录");
        } else {
            if (banInfos.size() < 3) {
                stringBuilder.append("这个人，有点不良记录");
            } else {
                stringBuilder.append("这个人，是真的坏!!!");
            }
            banInfos.forEach(b -> stringBuilder
                    .append("\n\n于").append(simpleDateFormat.format(new Date(b.getAddTime() * 1000)))
                    .append("被").append(b.getFromGroup()).append("群封了\n原因:").append(b.getDescribe()));
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean check(String word) {
        return (matcher = PATTERN.matcher(word)).find();
    }

    @Override
    public String reply(BanListOperation banListOperation) {
        return replyWord(banListOperation.isInBanList(matcher.group("targetQQ")));
    }
}
