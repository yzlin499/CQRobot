package top.yzlin.raise;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import top.yzlin.tools.Tools;

import java.io.IOException;
import java.util.Comparator;
import java.util.function.Predicate;

public class Modian extends RaiseProject{
    private String infoParam;
    private String ordersParam;

    public Modian(int projectID){
        this(String.valueOf(projectID));
    }

    public static void main(String[] args) {
        new Modian("39367");
    }


    public Modian(String projectID){
        init(projectID);
    }

    private void init(String projectID) {
        infoParam = "pro_id=" + projectID + "&sign=" + Tools.MD5("pro_id=" + projectID + "&p=das41aq6").substring(5, 21);
        ordersParam = "page=1&pro_id=" + projectID + "&sign=" + Tools.MD5("page=1&pro_id=" + projectID + "&p=das41aq6").substring(5, 21);
        try {
            JSONObject data = JSONObject.parseObject(Tools.sendPost("https://wds.modian.com/api/project/detail", infoParam));
            if (!"0".equals(data.getString("status"))) {
                Tools.print(data.getString("message"));
                throw new IOException();
            }
            data = data.getJSONArray("data").getJSONObject(0);
            setTitle(data.getString("pro_name"));
            setGoalMoney(data.getDoubleValue("goal"));
            setEndTime(data.getString("end_time"));
            setMoneyUrl("https://zhongchou.modian.com/item/" + projectID + ".html");
            Tools.print(getTitle() + "项目初始化");
        } catch (IOException e) {
            e.printStackTrace();
            Tools.print("集资初始化失败，炸了，重启吧");
        }
    }

    public void setProjectID(String projectID) {
        init(projectID);
    }

    public String getInfoParam() {
        return infoParam;
    }

    public String getOrdersParam() {
        return ordersParam;
    }

    @Override
    public double getNowMoney() {
        try {
            JSONObject data = JSONObject.parseObject(Tools.sendPost("https://wds.modian.com/api/project/detail", infoParam));
            if (!"0".equals(data.getString("status"))) {
                Tools.print("项目最新进度获取失败");
                Tools.sleep(10000);
                return getNowMoney();
            }
            return data.getJSONArray("data").getJSONObject(0).getDoubleValue("already_raised");
        } catch (JSONException e) {
            Tools.print("项目最新进度获取失败");
            Tools.print(e.getMessage());
            Tools.sleep(10000);
            return getNowMoney();
        }
    }

    /**
     * 获取新的集资信息
     *
     * @return RaiseData
     */
    public RaiseData[] getData(RaiseData raiseData) {
        return getData(h -> h.getPayTime() > raiseData.getPayTime());
    }

    /**
     * 获取新的集资信息
     *
     * @return RaiseData
     */
    @Override
    public RaiseData[] getData(Predicate<RaiseData> predicate) {
        try {
            String data = Tools.sendPost("https://wds.modian.com/api/project/orders", ordersParam);
            JSONObject t = JSONObject.parseObject(data);
            if (!"0".equals(t.getString("status"))) {
                Tools.print(t.getString("message") + ordersParam);
                throw new IOException("非200");
            }
            JSONArray tja = t.getJSONArray("data");
            if (tja.size() == 0) {
                Tools.print("数据获取为空，等待100秒之后递归");
                Tools.sleep(100000);
                return getData(predicate);
            }
            return tja.toJavaList(RaiseData.class).stream()
                    .filter(predicate)
                    .sorted(Comparator.comparing(RaiseData::getPayTime).reversed())
                    .toArray(RaiseData[]::new);
        } catch (JSONException e) {
            Tools.print("我大约着是摩点崩了，等个60秒吧");
            Tools.sleep(60000);
            return getData(predicate);
        } catch (IOException e) {
            Tools.print("读取集资列表时炸了老哥,30秒之后重新加载数据");
            Tools.sleep(30000);
            return getData(predicate);
        }
    }
}
