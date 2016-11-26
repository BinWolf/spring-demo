package com.wolf.product.vo;

/**
 * Created by wolf on 16/11/26.
 */
public class SwitchVo {

    private boolean isDiscount; //是否打折
    private boolean isNotAdult; //是否未成年
    /**********暂时先设置这两个条件,如果还有其他条件可以添加********/
    public boolean isDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(boolean isDiscount) {
        this.isDiscount = isDiscount;
    }

    public boolean isNotAdult() {
        return isNotAdult;
    }

    public void setIsNotAdult(boolean isNotAdult) {
        this.isNotAdult = isNotAdult;
    }

    public SwitchVo(boolean isDiscount, boolean isNotAdult) {
        this.isDiscount = isDiscount;
        this.isNotAdult = isNotAdult;
    }

    public SwitchVo() {
    }

}
