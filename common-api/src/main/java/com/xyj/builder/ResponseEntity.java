package com.xyj.builder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * 查询结果返回
 * @param <T>
 */
public class ResponseEntity<T> implements Serializable {
    protected Long recordsTotal;
    protected String error;
    protected List<T> data;

    public ResponseEntity(){}
    public ResponseEntity(List<T> data){
        this.data = data;
    }

    public ResponseEntity(String error) {
        this.error = error;
    }

    public ResponseEntity total(Long recordsTotal ){
        this.recordsTotal = recordsTotal;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}