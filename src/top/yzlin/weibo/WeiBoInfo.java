package top.yzlin.weibo;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class WeiBoInfo {
    public static final String[] EMPTY_IMG = new String[0];
    private long id;
    private String text;
    private String repostText = "";
    private String url;
    private String[] img = EMPTY_IMG;
    private boolean isRepost;
    private Date date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRepostText() {
        return repostText;
    }

    public void setRepostText(String repostText) {
        this.repostText = repostText;
    }

    public String getUrl() {
        return url;
    }

    public String[] getImg() {
        return img;
    }

    public void setImg(String[] img) {
        this.img = img;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isRepost() {
        return isRepost;
    }

    public void setRepost(boolean repost) {
        isRepost = repost;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeiBoInfo)) {
            return false;
        }
        WeiBoInfo weiBoInfo = (WeiBoInfo) o;
        return id == weiBoInfo.id &&
                isRepost == weiBoInfo.isRepost &&
                Objects.equals(text, weiBoInfo.text) &&
                Objects.equals(repostText, weiBoInfo.repostText) &&
                Objects.equals(url, weiBoInfo.url) &&
                Arrays.equals(img, weiBoInfo.img) &&
                Objects.equals(date, weiBoInfo.date);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id, text, repostText, url, isRepost, date);
        result = 31 * result + Arrays.hashCode(img);
        return result;
    }

    @Override
    public String toString() {
        return "WeiBoInfo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", repostText='" + repostText + '\'' +
                ", url='" + url + '\'' +
                ", img=" + Arrays.toString(img) +
                ", isRepost=" + isRepost +
                ", date=" + date +
                '}';
    }
}
