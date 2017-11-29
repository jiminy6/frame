package com.fun.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author:luowenxin
 * @date: 2017年11月17日
 **/
@Entity
@Table(name = "cst_linkman", schema = "crm")
public class Linkman {
	 private Long lkmId;
	    private String lkmName;
	    private String lkmGender;
	    private String lkmPhone;
	    private String lkmMobile;
	    private String lkmEmail;
	    private String lkmPosition;
	    private String lkmMemo;
	    private Customer lkmCustomer;

	    @Id
	    @Column(name = "lkm_id", nullable = false)
	    public Long getLkmId() {
	        return lkmId;
	    }

	    public void setLkmId(Long lkmId) {
	        this.lkmId = lkmId;
	    }

	    @Basic
	    @Column(name = "lkm_name", nullable = true, length = 16)
	    public String getLkmName() {
	        return lkmName;
	    }

	    public void setLkmName(String lkmName) {
	        this.lkmName = lkmName;
	    }

	    @Basic
	    @Column(name = "lkm_gender", nullable = true, length = 1)
	    public String getLkmGender() {
	        return lkmGender;
	    }

	    public void setLkmGender(String lkmGender) {
	        this.lkmGender = lkmGender;
	    }

	    @Basic
	    @Column(name = "lkm_phone", nullable = true, length = 16)
	    public String getLkmPhone() {
	        return lkmPhone;
	    }

	    public void setLkmPhone(String lkmPhone) {
	        this.lkmPhone = lkmPhone;
	    }

	    @Basic
	    @Column(name = "lkm_mobile", nullable = true, length = 16)
	    public String getLkmMobile() {
	        return lkmMobile;
	    }

	    public void setLkmMobile(String lkmMobile) {
	        this.lkmMobile = lkmMobile;
	    }

	    @Basic
	    @Column(name = "lkm_email", nullable = true, length = 64)
	    public String getLkmEmail() {
	        return lkmEmail;
	    }

	    public void setLkmEmail(String lkmEmail) {
	        this.lkmEmail = lkmEmail;
	    }

	    @Basic
	    @Column(name = "lkm_position", nullable = true, length = 16)
	    public String getLkmPosition() {
	        return lkmPosition;
	    }

	    public void setLkmPosition(String lkmPosition) {
	        this.lkmPosition = lkmPosition;
	    }

	    @Basic
	    @Column(name = "lkm_memo", nullable = true, length = 512)
	    public String getLkmMemo() {
	        return lkmMemo;
	    }

	    public void setLkmMemo(String lkmMemo) {
	        this.lkmMemo = lkmMemo;
	    }

	    @ManyToOne
	    @JoinColumn(name = "lkm_cust_id", referencedColumnName = "cust_id", nullable = false)
		public Customer getLkmCustomer() {
			return lkmCustomer;
		}
		public void setLkmCustomer(Customer lkmCustomer) {
			this.lkmCustomer = lkmCustomer;
		}

		@Override
		public String toString() {
			return "Linkman [lkmId=" + lkmId + ", lkmName=" + lkmName + ", lkmGender=" + lkmGender + ", lkmPhone="
					+ lkmPhone + ", lkmMobile=" + lkmMobile + ", lkmEmail=" + lkmEmail + ", lkmPosition=" + lkmPosition
					+ ", lkmMemo=" + lkmMemo + "]";
		}

	    
	  

	  
}
