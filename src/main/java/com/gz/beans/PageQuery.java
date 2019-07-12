package com.gz.beans;

import javax.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class PageQuery {

    @Getter
    @Setter
    @Min(value = 1, message = "当前页码不合法")
    private int pageNo = 1;//页码

    @Getter
    @Setter
    @Min(value = 1, message = "每页展示数量不合法")
    private int pageSize = 10;//每页有多少条

    @Setter
    private int offset;//偏移量

    public int getOffset() {
        return (pageNo - 1) * pageSize;
    }
}
