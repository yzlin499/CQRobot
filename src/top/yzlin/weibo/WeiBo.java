package top.yzlin.weibo;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import top.yzlin.monitoring.BaseData;
import top.yzlin.tools.Tools;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class WeiBo implements BaseData<WeiBoInfo> {
    private String param;
    private String name;
    private static List<JSONObject> memberTemp;
    private String uid;

    public WeiBo(String name) {
        Tools.print(name + "微博监控");
        JSONObject jo = getUIDAndVerifier(name);
        if (jo != null) {
            param = "containerid=107603" + (uid = jo.getString("weibo_uid"));
        }
        this.name = name;
    }

    public WeiBo(String name, String uid) {
        this(name, uid, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeiBo(String name, String uid, String verifier) {
        this.name = name;
        param = "containerid=107603" + uid;
        this.uid = uid;
    }

    public static JSONObject getUIDAndVerifier(String name) {
        if (memberTemp == null) {
            String data = Tools.sendGet("http://h5.snh48.com/resource/jsonp/members.php", "gid=00&callback=get_members_success");
            JSONObject memberData = JSONObject.parseObject(data.substring(data.indexOf('(') + 1, data.lastIndexOf(')')));
            memberTemp = memberData.getJSONArray("rows").toJavaList(JSONObject.class);
        }
        for (JSONObject j : memberTemp) {
            if (name.equals(j.getString("sname"))) {
                return j;
            }
        }
        Tools.print("查无此人:" + name);
        return null;
    }

    @Override
    public WeiBoInfo[] getData(Predicate<WeiBoInfo> predicate) {
        JSONObject jo = JSONObject.parseObject(Tools.sendGet("https://m.weibo.cn/api/container/getIndex", param));
        if (jo.getIntValue("ok") == 1) {
            return jo.getJSONObject("data").getJSONArray("cards").toJavaList(JSONObject.class).stream()
                    .filter(j -> j.getIntValue("card_type") == 9 && !j.getJSONObject("mblog").containsKey("title"))
                    .map(j -> {
                        WeiBoInfo weiBoInfo = new WeiBoInfo();
                        String url = j.getString("scheme");
                        int n = url.indexOf("/status/");
                        weiBoInfo.setUrl("https://weibo.com/" + uid + '/' + url.substring(n + 8, url.indexOf('?', n)));
                        JSONObject mblog = j.getJSONObject("mblog");
                        weiBoInfo.setId(mblog.getLongValue("id"));
                        if (mblog.containsKey("retweeted_status")) {
                            weiBoInfo.setText(mblog.getString("raw_text"));
                            weiBoInfo.setRepost(true);
                            weiBoInfo.setImg(WeiBoInfo.EMPTY_IMG);
                            weiBoInfo.setRepostText(Jsoup.parse(mblog.getJSONObject("retweeted_status").getString("text")).text());
                        } else {
                            weiBoInfo.setText(Jsoup.parse(mblog.getString("text")).text());
                            weiBoInfo.setRepost(false);
                            weiBoInfo.setImg(Optional.ofNullable(mblog.getJSONArray("pics"))
                                    .map(a -> a.toJavaList(JSONObject.class).stream()
                                            .map(i -> i.getString("url"))
                                            .toArray(String[]::new))
                                    .orElse(WeiBoInfo.EMPTY_IMG));
                        }
                        return weiBoInfo;
                    }).filter(predicate)
                    .toArray(WeiBoInfo[]::new);
        } else {
            Tools.print(jo.getString("msg"));
            return new WeiBoInfo[0];
        }
    }
}

