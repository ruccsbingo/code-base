package com.yidian.wemedia.bean;


import com.yidian.wemedia.util.BeanUtil;
import com.yidian.wemedia.util.MD5Util;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by zhangbing on 16/5/26.
 */
public class PayRequestBean {

    public String amount;
    public String check_name;
    public String desc;
    public String mch_appid;
    public String mchid;
    public String nonce_str;
    public String openid;
    public String partner_trade_no;
    public String re_user_name;
    public String spbill_create_ip;
    public String sign;

    private List<MyField> myFieldList;

    public PayRequestBean(){
        myFieldList = BeanUtil.getMyFieldList(PayRequestBean.class);
    }

    public PayRequestBean(String amount,
                          String check_name,
                          String desc,
                          String mch_appid,
                          String mchid,
                          String nonce_str,
                          String openid,
                          String partner_trade_no,
                          String re_user_name,
                          String spbill_create_ip,
                          String key) throws IllegalAccessException, NoSuchAlgorithmException {
        this.amount = amount;
        this.check_name = check_name;
        this.desc = desc;
        this.mch_appid = mch_appid;
        this.mchid = mchid;
        this.nonce_str = nonce_str;
        this.openid = openid;
        this.partner_trade_no = partner_trade_no;
        this.re_user_name = re_user_name;
        this.spbill_create_ip = spbill_create_ip;

        myFieldList = BeanUtil.getMyFieldList(PayRequestBean.class);

        this.sign = MD5Util.MD5Sign(key, myFieldList, this);
    }

    @Override
    public String toString(){
        checkNotNull(myFieldList, "myfieldList is not init");
        return BeanUtil.toString(myFieldList, this);
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCheck_name() {
        return check_name;
    }

    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMch_appid() {
        return mch_appid;
    }

    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getRe_user_name() {
        return re_user_name;
    }

    public void setRe_user_name(String re_user_name) {
        this.re_user_name = re_user_name;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
