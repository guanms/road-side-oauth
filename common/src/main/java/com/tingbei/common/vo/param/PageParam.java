package com.tingbei.common.vo.param;

/**
 * 分页查询通用参数(带上userVO的redisId)
 * Created by JJH on 16/1/19.
 *
 * @author JJH
 */
public class PageParam {
    private Integer limit;

    private Integer offset;

    private Integer pageNumber;

    private Integer pageSize;

    private String redisId;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getRedisId() {
        return redisId;
    }

    public void setRedisId(String redisId) {
        this.redisId = redisId;
    }
}
