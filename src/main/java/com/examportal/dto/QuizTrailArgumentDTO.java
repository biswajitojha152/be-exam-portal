package com.examportal.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class QuizTrailArgumentDTO {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date fromDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date toDate;
    private String searchByUsername;
    private Integer categoryId;
    private Integer quizId;
    private String status;
    private Integer pageNo = 0;
    private Integer pageSize = 10;

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getSearchByUsername() {
        return searchByUsername;
    }

    public void setSearchByUsername(String searchByUsername) {
        this.searchByUsername = searchByUsername;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getQuizId() {
        return quizId;
    }

    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
