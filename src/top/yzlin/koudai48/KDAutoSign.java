package top.yzlin.koudai48;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.yzlin.tools.SetConnection;
import top.yzlin.tools.Tools;

public class KDAutoSign implements Runnable{

    private KDValidation kdValidation;
    private String token;

    private SetConnection conn = connection -> {
        connection.setRequestProperty("token", token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("version", "5.0.1");
        connection.setRequestProperty("build", "52127");
        connection.setRequestProperty("os", "android");
    };

    public KDAutoSign(KDValidation kdValidation){
        this.kdValidation=kdValidation;
        token = kdValidation.getToken();
    }

    void signIn() {
        JSONObject jsonObject = JSONObject.parseObject(Tools.sendPost("https://puser.48.cn/usersystem/api/user/v1/check/in", "{}", conn));
        if (jsonObject.getIntValue("status") == 401) {
            Tools.print(jsonObject.getString("message"));
            token = kdValidation.getNewToken();
            signIn();
            return;
        } else if (jsonObject.getIntValue("status") != 200) {
            Tools.print(jsonObject.getString("message"));
            return;
        }
        Tools.sleep(500);
        int[] id = JSONObject.parseObject(Tools.sendPost("https://pdynamic.48.cn/dynamicsystem/api/dynamic/v1/list/friends",
                "{\"lastTime\":0,\"userId\":" + kdValidation.getUserID() + ",\"memberId\":[1,2,3,5,8,9,10,11,12,14,17,18,19,20,21,22,25,27,28,32,33,35,36,37,38,39,40,43,45,46,47,63,67,1544,2470,2508,5562,5564,5566,5567,5574,5973,6429,6431,6432,6734,6735,6737,6738,6739,6740,6741,6742,6743,6744,6745,6746,6747,9073,49005,49006,49007,63549,63554,63555,63558,63559,63561,63566,63567,63572,63576,63577,63579,63580,286973,286977,286978,286979,286980,286982,327558,327560,327561,327562,327566,327571,327573,327580,327584,327587,327591,327592,327597,327682,327683,399631,399652,399654,399669,399672,407101,407103,407104,407106,407108,407110,407112,407114,407118,407119,407121,407124,407126,407127,407130,407168,410175,410177,411040,417311,417315,417316,417317,417318,417320,417321,417324,417325,417326,417328,417329,417330,417331,417332,417333,417335,417336,419966,444081,459991,459994,459996,459997,460000,460004,460005,460933,480668,480670,480678,480679,490333,526172,528336,530440,530443,530450,530584,593820,594002,594003,594005],\"limit\":10}",
                conn)).getJSONObject("content").getJSONObject("praise")
                .keySet().stream().mapToInt(Integer::parseInt).sorted().toArray();
        int c = 0;
        for (int i = id.length - 1; i >= 0 && c < 5; i--) {
            Tools.sleep(500);
            JSONObject jo = JSON.parseObject(Tools.sendPost("https://pdynamic.48.cn/dynamicsystem/api/dynamic/v1/praise",
                    "{\"resId\":\"" + id[i] + "\"}", conn));
            c += jo.getJSONObject("content").getIntValue("exp");
            Tools.print(id[i] + jo.toString());
        }

        c = 0;
        Tools.sleep(1000);
        String[] liveID = JSONObject.parseObject(Tools.sendPost(
                "https://plive.48.cn/livesystem/api/live/v1/memberLivePage",
                "{\"lastTime\":\"0\",\"groupId\":\"0\",\"type\":\"0\",\"memberId\":\"0\",\"giftUpdTime\":\"1498211389003\",\"limit\":\"10\"}",
                conn)).getJSONObject("content").getJSONArray("reviewList").toJavaList(JSONObject.class)
                .stream().map(s -> s.getString("liveId")).toArray(String[]::new);

        for (int i = 0; i < liveID.length && c < 2; i++) {
            Tools.sleep(500);
            JSONObject jo = JSONObject.parseObject(Tools.sendPost("https://plive.48.cn/livesystem/api/live/v1/memberLiveShare",
                    "{\"userId\":" + kdValidation.getUserID() + ",\"resId\":\"" + liveID[i] + "\"}",
                    conn));
            c += jo.getJSONObject("content").getIntValue("exp");
            Tools.print(liveID[i] + jo.toString());
        }

    }

    @Override
    public void run() {
        Tools.sleep(Tools.todayRemainTime() + (1000 * 60 * 5));
        while (true) {
            signIn();
            Tools.sleep((1000 * 60 * 60 * 24) - 6000);
        }
    }
}
