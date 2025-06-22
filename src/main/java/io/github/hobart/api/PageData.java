package io.github.hobart.api;

import java.util.Collections;
import java.util.List;

public class PageData<T> {

    private int pageNum; //当前页码

    private int pageSize; //每页数量

    private long total; //总记录数

    private int totalPage; //总页数

    private List<T> list = Collections.emptyList();

    public PageData() {
    }

    public PageData(List<T> list) {
        this.list = list;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
