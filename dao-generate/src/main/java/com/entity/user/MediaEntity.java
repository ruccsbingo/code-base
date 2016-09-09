package com.entity.user;
import java.sql.*;

   /**
    * media 实体类
    * Thu Sep 01 14:53:52 CST 2016 zhangbing
    */ 


public class MediaEntity{
	private long id;
	private String mediaName;
	private String mediaSummary;
	private String mediaPic;
	private String mediaDomain;
	private long uid;
	private String userIdcard;
	private String userName;
	private String userEmail;
	private String userMobile;
	private String userOther;
	private String province;
	private String city;
	private String webUrl;
	private String webPic;
	private String companyName;
	private String companyAddress;
	private String companyPosition;
	private String companyPic;
	private String companyAuthorization;
	private String companyType;
	private byte type;
	private byte low;
	private long approvedBy;
	private long approveTime;
	private String comment;
	private byte status;
	private int postcount;
	private long createdTime;
	private long updatedTime;
	private byte submitTimes;
	private String approveTag;
	private byte markStatus;
	private byte channelStatus;
	private byte rank;
	private byte scope;
	private long expand;
	public void setId(long id){
	this.id=id;
	}
	public long getId(){
		return id;
	}
	public void setMediaName(String mediaName){
	this.mediaName=mediaName;
	}
	public String getMediaName(){
		return mediaName;
	}
	public void setMediaSummary(String mediaSummary){
	this.mediaSummary=mediaSummary;
	}
	public String getMediaSummary(){
		return mediaSummary;
	}
	public void setMediaPic(String mediaPic){
	this.mediaPic=mediaPic;
	}
	public String getMediaPic(){
		return mediaPic;
	}
	public void setMediaDomain(String mediaDomain){
	this.mediaDomain=mediaDomain;
	}
	public String getMediaDomain(){
		return mediaDomain;
	}
	public void setUid(long uid){
	this.uid=uid;
	}
	public long getUid(){
		return uid;
	}
	public void setUserIdcard(String userIdcard){
	this.userIdcard=userIdcard;
	}
	public String getUserIdcard(){
		return userIdcard;
	}
	public void setUserName(String userName){
	this.userName=userName;
	}
	public String getUserName(){
		return userName;
	}
	public void setUserEmail(String userEmail){
	this.userEmail=userEmail;
	}
	public String getUserEmail(){
		return userEmail;
	}
	public void setUserMobile(String userMobile){
	this.userMobile=userMobile;
	}
	public String getUserMobile(){
		return userMobile;
	}
	public void setUserOther(String userOther){
	this.userOther=userOther;
	}
	public String getUserOther(){
		return userOther;
	}
	public void setProvince(String province){
	this.province=province;
	}
	public String getProvince(){
		return province;
	}
	public void setCity(String city){
	this.city=city;
	}
	public String getCity(){
		return city;
	}
	public void setWebUrl(String webUrl){
	this.webUrl=webUrl;
	}
	public String getWebUrl(){
		return webUrl;
	}
	public void setWebPic(String webPic){
	this.webPic=webPic;
	}
	public String getWebPic(){
		return webPic;
	}
	public void setCompanyName(String companyName){
	this.companyName=companyName;
	}
	public String getCompanyName(){
		return companyName;
	}
	public void setCompanyAddress(String companyAddress){
	this.companyAddress=companyAddress;
	}
	public String getCompanyAddress(){
		return companyAddress;
	}
	public void setCompanyPosition(String companyPosition){
	this.companyPosition=companyPosition;
	}
	public String getCompanyPosition(){
		return companyPosition;
	}
	public void setCompanyPic(String companyPic){
	this.companyPic=companyPic;
	}
	public String getCompanyPic(){
		return companyPic;
	}
	public void setCompanyAuthorization(String companyAuthorization){
	this.companyAuthorization=companyAuthorization;
	}
	public String getCompanyAuthorization(){
		return companyAuthorization;
	}
	public void setCompanyType(String companyType){
	this.companyType=companyType;
	}
	public String getCompanyType(){
		return companyType;
	}
	public void setType(byte type){
	this.type=type;
	}
	public byte getType(){
		return type;
	}
	public void setLow(byte low){
	this.low=low;
	}
	public byte getLow(){
		return low;
	}
	public void setApprovedBy(long approvedBy){
	this.approvedBy=approvedBy;
	}
	public long getApprovedBy(){
		return approvedBy;
	}
	public void setApproveTime(long approveTime){
	this.approveTime=approveTime;
	}
	public long getApproveTime(){
		return approveTime;
	}
	public void setComment(String comment){
	this.comment=comment;
	}
	public String getComment(){
		return comment;
	}
	public void setStatus(byte status){
	this.status=status;
	}
	public byte getStatus(){
		return status;
	}
	public void setPostcount(int postcount){
	this.postcount=postcount;
	}
	public int getPostcount(){
		return postcount;
	}
	public void setCreatedTime(long createdTime){
	this.createdTime=createdTime;
	}
	public long getCreatedTime(){
		return createdTime;
	}
	public void setUpdatedTime(long updatedTime){
	this.updatedTime=updatedTime;
	}
	public long getUpdatedTime(){
		return updatedTime;
	}
	public void setSubmitTimes(byte submitTimes){
	this.submitTimes=submitTimes;
	}
	public byte getSubmitTimes(){
		return submitTimes;
	}
	public void setApproveTag(String approveTag){
	this.approveTag=approveTag;
	}
	public String getApproveTag(){
		return approveTag;
	}
	public void setMarkStatus(byte markStatus){
	this.markStatus=markStatus;
	}
	public byte getMarkStatus(){
		return markStatus;
	}
	public void setChannelStatus(byte channelStatus){
	this.channelStatus=channelStatus;
	}
	public byte getChannelStatus(){
		return channelStatus;
	}
	public void setRank(byte rank){
	this.rank=rank;
	}
	public byte getRank(){
		return rank;
	}
	public void setScope(byte scope){
	this.scope=scope;
	}
	public byte getScope(){
		return scope;
	}
	public void setExpand(long expand){
	this.expand=expand;
	}
	public long getExpand(){
		return expand;
	}
}

