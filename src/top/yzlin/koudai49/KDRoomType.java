package top.yzlin.koudai49;

import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;


public enum KDRoomType {
    /**
     * 取消代码规范警告
     */
    TEXT("TEXT", "消息"),
    REPLY("REPLY", "回复"),
    FLIP_CARD("FLIPCARD", "翻牌"),
    IMAGE("IMAGE", "图片"),
    AUDIO("AUDIO", "音频"),
    VIDEO("VIDEO", "视频"),
    LIVE_PUSH("LIVEPUSH", "直播"),
    VOTE("VOTE", "投票"),
    EXPRESS("EXPRESS", "表情");

    private String name;
    private String cnName;
    private static Map<String, KDRoomType> nameMap =
            EnumSet.allOf(KDRoomType.class).stream().collect(Collectors.toMap(k -> k.name, v -> v));

    KDRoomType(String name, String cnName) {
        this.name = name;
        this.cnName = cnName;
    }

    public static KDRoomType parse(String type) {
        return nameMap.get(type);
    }

    public String getName() {
        return name;
    }

    public String getCnName() {
        return cnName;
    }

    @Override
    public String toString() {
        return name;
    }

}
