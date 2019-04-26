package top.yzlin.koudai49;

import com.alibaba.fastjson.JSONObject;
import top.yzlin.tools.Tools;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class KDValidation {
    private static final String VALIDATION_API = "https://pocketapi.48.cn/user/api/v1/login/app/mobile";

    /*创建文档*/
    static {
        File tf = new File("doc\\KDRoomConfiguration");
        if (!tf.exists()) {
            tf.mkdirs();
        }
    }

    private String account;
    private String userID;
    private String token;

    public KDValidation(String account) {
        this.account = account;

        File cfg = new File("doc\\KDRoomConfiguration\\" + account + ".cfg");
        if (!cfg.exists()) {
            System.out.println("没有创建配置文件，接下来来创建文件");
            System.out.println("请输入密码，按回车确定：");
            while (!createDocument(account, new Scanner(System.in).next())) {
                Tools.print("创建失败,重新输入");
            }
        } else {
            try {
                Properties p = new Properties();
                p.load(new FileReader(cfg));
                Tools.print("token加载完成");
                token = p.getProperty("token");
                userID = p.getProperty("userID");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 创建文档,一般是被构造方法调用
     *
     * @param account  账号
     * @param password 密码
     * @return 是否创建成功
     */
    private boolean createDocument(String account, String password) {
        if ((token = getNewToken(account, password)) == null) {
            Tools.print("创建失败");
            return false;
        } else {
            Tools.print("创建成功");
            return true;
        }
    }

    public String getAccount() {
        return account;
    }

    public String getUserID() {
        return userID;
    }

    public String getToken() {
        return token;
    }

    /**
     * 刷新token,并且将token写进配置
     *
     * @return 返回最新的token, 获取失败则返回null
     */
    public String getNewToken() {
        try {
            Properties p = new Properties();
            p.load(new FileReader("doc\\KDRoomConfiguration\\" + account + ".cfg"));
            return getNewToken(p.getProperty("account"), p.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 刷新token,并且将token写进配置
     *
     * @return 返回最新的token, 获取失败则返回null
     */
    private String getNewToken(String account, String password) {
        Tools.print("获取新token");
        JSONObject result = JSONObject.parseObject(Tools.sendPost(
                KD49API.VALIDATION_API,
                new JSONObject().fluentPut("mobile", account).fluentPut("pwd", password).toString(),
                KD49API.API_HEADER
        ));
        if (result == null) {
            return null;
        } else {
            if (result.getIntValue("status") != 200) {
                Tools.print("token获取失败：" + result.getString("message"));
                return null;
            } else {
                Properties p = new Properties();
                p.setProperty("account", account);
                p.setProperty("password", password);
                String tok = result.getJSONObject("content").getString("token");
                p.setProperty("token", tok);
                try {
                    p.store(new FileWriter("doc\\KDRoomConfiguration\\" + account + ".cfg"), "账号配置文件");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return tok;
            }
        }
    }
}
