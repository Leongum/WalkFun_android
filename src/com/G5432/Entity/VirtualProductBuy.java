package com.G5432.Entity;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-2-28
 * Time: 下午2:49
 * To change this template use File | Settings | File Templates.
 */
public class VirtualProductBuy {

    @Expose
    private Integer userId;
    @Expose
    private Integer productId;
    @Expose
    private Integer numbers;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getNumbers() {
        return numbers;
    }

    public void setNumbers(Integer numbers) {
        this.numbers = numbers;
    }
}
