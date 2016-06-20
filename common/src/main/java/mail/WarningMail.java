package mail;

/**
 * @author liubo
 * @email liubo@yidian-inc.com
 *
 */
public class WarningMail implements Comparable<WarningMail> {

    private String msg;

    private Throwable detail; // ?

    private String detailMsg;

    private int priority = 0;

    public WarningMail(String msg) {
        this.msg = msg;
    }

    public WarningMail(String msg, Throwable detail) {
        this.msg = msg;
        this.detail = detail;
    }

    public WarningMail(String msg, Throwable detail, int priority) {
        this.msg = msg;
        this.detail = detail;
        this.priority = priority;
    }

    public WarningMail(String msg, Throwable detail, String detailMsg, int priority) {
        this.msg = msg;
        this.detail = detail;
        this.priority = priority;
        this.detailMsg = detailMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int compareTo(WarningMail o) {
        return Integer.compare(this.priority, o.priority);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Throwable getDetail() {
        return detail;
    }

    public void setDetail(Throwable detail) {
        this.detail = detail;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }
}


