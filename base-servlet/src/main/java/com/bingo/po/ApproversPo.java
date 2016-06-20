package com.bingo.po;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PO to link approvers in DataBase
 * Created at 2016-06-20
 */
public class ApproversPo {
	@JsonProperty("account")
	private Integer account;
	@JsonProperty("uid")
	private Long uid;
	@JsonProperty("on_work_time")
	private String onWorkTime;
	@JsonProperty("fields")
	private String fields;
	@JsonProperty("off_work_time")
	private String offWorkTime;
	@JsonProperty("approver_name")
	private String approverName;
	@JsonProperty("updated_time")
	private Long updatedTime;
	@JsonProperty("video")
	private Integer video;
	@JsonProperty("created_time")
	private Long createdTime;
	@JsonProperty("article")
	private Integer article;
	@JsonProperty("quality")
	private Integer quality;
	@JsonProperty("invite")
	private Integer invite;

	//Only for test, Do not use in normal function
	public void fillValueForTest(){
		this.account = 8;
		this.uid = 8L;
		this.onWorkTime = "String for Test 10";
		this.fields = "String for Test 6";
		this.offWorkTime = "String for Test 10";
		this.approverName = "String for Test 5";
		this.updatedTime = 11L;
		this.video = 7;
		this.createdTime = 1L;
		this.article = 23;
		this.quality = 47;
		this.invite = 9;
	}

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getOnWorkTime() {
		return onWorkTime;
	}

	public void setOnWorkTime(String onWorkTime) {
		this.onWorkTime = onWorkTime;
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getOffWorkTime() {
		return offWorkTime;
	}

	public void setOffWorkTime(String offWorkTime) {
		this.offWorkTime = offWorkTime;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Long getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Long updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Integer getVideo() {
		return video;
	}

	public void setVideo(Integer video) {
		this.video = video;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getArticle() {
		return article;
	}

	public void setArticle(Integer article) {
		this.article = article;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

	public Integer getInvite() {
		return invite;
	}

	public void setInvite(Integer invite) {
		this.invite = invite;
	}

	public void updatePO(ApproversPo inPo){
		if (inPo.account != null)
			this.account = inPo.account;
		if (inPo.uid != null)
			this.uid = inPo.uid;
		if (inPo.onWorkTime != null)
			this.onWorkTime = inPo.onWorkTime;
		if (inPo.fields != null)
			this.fields = inPo.fields;
		if (inPo.offWorkTime != null)
			this.offWorkTime = inPo.offWorkTime;
		if (inPo.approverName != null)
			this.approverName = inPo.approverName;
		if (inPo.updatedTime != null)
			this.updatedTime = inPo.updatedTime;
		if (inPo.video != null)
			this.video = inPo.video;
		if (inPo.createdTime != null)
			this.createdTime = inPo.createdTime;
		if (inPo.article != null)
			this.article = inPo.article;
		if (inPo.quality != null)
			this.quality = inPo.quality;
		if (inPo.invite != null)
			this.invite = inPo.invite;
	}

	 public static final String APPROVERS_ACCOUNT = "account";
	 public static final String APPROVERS_UID = "uid";
	 public static final String APPROVERS_ON_WORK_TIME = "on_work_time";
	 public static final String APPROVERS_FIELDS = "fields";
	 public static final String APPROVERS_OFF_WORK_TIME = "off_work_time";
	 public static final String APPROVERS_APPROVER_NAME = "approver_name";
	 public static final String APPROVERS_UPDATED_TIME = "updated_time";
	 public static final String APPROVERS_VIDEO = "video";
	 public static final String APPROVERS_CREATED_TIME = "created_time";
	 public static final String APPROVERS_ARTICLE = "article";
	 public static final String APPROVERS_QUALITY = "quality";
	 public static final String APPROVERS_INVITE = "invite";
}
