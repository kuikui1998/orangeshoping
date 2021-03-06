package com.changgou.goods.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @auther kuikui
 * @create 2020-11-05 9:30
 * 商品信息组合对象
 */
public class Goods implements Serializable {
    //Spu信息
    private Spu spu;

    //Sku集合信息
    private List<Sku> skuList;

    public Spu getSpu() {
        return spu;
    }

    public void setSpu(Spu spu) {
        this.spu = spu;
    }

    public List<Sku> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<Sku> skuList) {
        this.skuList = skuList;
    }
}
