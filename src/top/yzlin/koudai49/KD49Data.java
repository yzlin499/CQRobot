package top.yzlin.koudai49;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class KD49Data {
    private static final KD49Data KD_DATA = new KD49Data();
    private JSONArray data;

    private KD49Data() {
        try {
            File file = ResourceUtils.getFile("classpath:top/yzlin/koudai49/idol_info.json");
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder(fis.available());
            int tmp;
            while ((tmp = isr.read()) != -1) {
                stringBuilder.append((char) tmp);
            }
            data = JSON.parseArray(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            data = new JSONArray();
        }
    }


    public static KD49Data getInstance() {
        return KD_DATA;
    }

    public MemberInfo getMemberInfoById(int id) {
        int low = 0;
        int high = data.size() - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int midVal = data.getJSONObject(mid).getIntValue("id");
            if (midVal < id) {
                low = mid + 1;
            } else if (midVal > id) {
                high = mid - 1;
            } else {
                return data.getJSONObject(mid).toJavaObject(MemberInfo.class);
            }
        }
        return null;
    }

    public MemberInfo getMemberInfoByName(String name) {
        JSONObject jsonObject;
        for (int i = 0, l = data.size(); i < l; i++) {
            jsonObject = data.getJSONObject(i);
            if (Objects.equals(jsonObject.getString("name"), name)) {
                return jsonObject.toJavaObject(MemberInfo.class);
            }
        }
        return null;
    }

    public MemberInfo getMemberInfoByNickName(String nickName) {
        JSONObject jsonObject;
        for (int i = 0, l = data.size(); i < l; i++) {
            jsonObject = data.getJSONObject(i);
            if (Objects.equals(jsonObject.getString("nickName"), nickName)) {
                return jsonObject.toJavaObject(MemberInfo.class);
            }
        }
        return null;
    }

    public MemberInfo getMemberInfoByRoomId(int roomId) {
        JSONObject jsonObject;
        for (int i = 0, l = data.size(); i < l; i++) {
            jsonObject = data.getJSONObject(i);
            if (jsonObject.getIntValue("roomId") == roomId) {
                return jsonObject.toJavaObject(MemberInfo.class);
            }
        }
        return null;
    }
}
