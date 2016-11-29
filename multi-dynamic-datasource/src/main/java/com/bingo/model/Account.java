package com.bingo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PO to link approvers in DataBase
 * Created at 2016-05-26
 */

public class Account {
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

	@JsonProperty("grading")
	private Integer grading;

	@JsonProperty("b_profile")
	private Integer bProfile;
	@JsonProperty("reward")
	private Integer reward;

	public Integer getReward() {
		return reward;
	}

	public void setReward(Integer reward) {
		this.reward = reward;
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


	public Integer getGrading() {return grading;}

	public void setGrading(Integer grading) { this.grading = grading;}

	public Integer getbProfile(){ return bProfile;}

	public void setbProfile(Integer bProfile){this.bProfile = bProfile;}
}
