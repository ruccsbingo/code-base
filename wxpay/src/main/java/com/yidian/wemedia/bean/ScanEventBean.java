package com.yidian.wemedia.bean;

import com.yidian.wemedia.util.BeanUtil;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zhangbing on 16/5/27.
 */
public class ScanEventBean {

    public String ToUserName;
    public String FromUserName;
    public String CreateTime;
    public String MsgType;
    public String Event;
    public String EventKey;
    public String Ticket;

    private List<MyField> myFieldList;

    public ScanEventBean(){
        myFieldList = BeanUtil.getMyFieldList(ScanEventBean.class);
    }

    public ScanEventBean(String toUserName,
                         String fromUserName,
                         String createTime,
                         String msgType,
                         String event,
                         String eventKey,
                         String ticket){
        myFieldList = BeanUtil.getMyFieldList(ScanEventBean.class);

        this.ToUserName = toUserName;
        this.FromUserName = fromUserName;
        this.CreateTime = createTime;
        this.MsgType = msgType;
        this.Event = event;
        this.EventKey = eventKey;
        this.Ticket = ticket;
    }

    @Override
    public String toString(){
        checkNotNull(myFieldList, "myfieldList is not init");
        return BeanUtil.toString(myFieldList, this);
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

}
