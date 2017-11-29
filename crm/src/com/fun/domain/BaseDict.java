package com.fun.domain;

import javax.persistence.*;

/**
 * @Atuthor: luowenxin
 * @Date: 2017/11/15
 * 
 */


@Entity
@Table(name = "base_dict")
public class BaseDict {
    private String dictId;
    private String dictTypeCode;
    private String dictTypeName;
    private String dictItemName;
    private String dictItemCode;
    private Integer dictSort;
    private String dictEnable;
    private String dictMemo;

    @Id
    @Column(name = "dict_id", nullable = false, length = 32)
    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId;
    }

    @Basic
    @Column(name = "dict_type_code", nullable = false, length = 10)
    public String getDictTypeCode() {
        return dictTypeCode;
    }

    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
    }

    @Basic
    @Column(name = "dict_type_name", nullable = false, length = 64)
    public String getDictTypeName() {
        return dictTypeName;
    }

    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
    }

    @Basic
    @Column(name = "dict_item_name", nullable = false, length = 64)
    public String getDictItemName() {
        return dictItemName;
    }

    public void setDictItemName(String dictItemName) {
        this.dictItemName = dictItemName;
    }

    @Basic
    @Column(name = "dict_item_code", nullable = true, length = 10)
    public String getDictItemCode() {
        return dictItemCode;
    }

    public void setDictItemCode(String dictItemCode) {
        this.dictItemCode = dictItemCode;
    }

    @Basic
    @Column(name = "dict_sort", nullable = true)
    public Integer getDictSort() {
        return dictSort;
    }

    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }

    @Basic
    @Column(name = "dict_enable", nullable = false, length = 1)
    public String getDictEnable() {
        return dictEnable;
    }

    public void setDictEnable(String dictEnable) {
        this.dictEnable = dictEnable;
    }

    @Basic
    @Column(name = "dict_memo", nullable = true, length = 64)
    public String getDictMemo() {
        return dictMemo;
    }

    public void setDictMemo(String dictMemo) {
        this.dictMemo = dictMemo;
    }

	@Override
	public String toString() {
		return "BaseDict [dictId=" + dictId + ", dictTypeCode=" + dictTypeCode + ", dictTypeName=" + dictTypeName
				+ ", dictItemName=" + dictItemName + ", dictItemCode=" + dictItemCode + ", dictSort=" + dictSort
				+ ", dictEnable=" + dictEnable + ", dictMemo=" + dictMemo + "]";
	}

   
}
