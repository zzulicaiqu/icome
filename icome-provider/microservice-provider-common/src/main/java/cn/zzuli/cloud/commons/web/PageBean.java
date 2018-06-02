package cn.zzuli.cloud.commons.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hanyu
 *         分页结果返回工具类
 */
public class PageBean<T> implements Serializable {

    private static final long serialVersionUID = 5480142166857508306L;
    private int total;
    private List<T> rows;

    public PageBean() {
        this.total = 0;
        this.rows = new ArrayList<>();
    }

    public PageBean(int total, List<T> t) {
        this.total = total;
        this.rows = t;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }


}
