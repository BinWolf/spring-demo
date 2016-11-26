package com.wolf.product.provider;

import com.wolf.product.constant.PriorityConstant;
import com.wolf.product.manager.AbstractProductManager;
import com.wolf.product.vo.Product;
import com.wolf.product.vo.SwitchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wolf on 16/11/26.
 */

@Component
public class YoungProductManager extends AbstractProductManager {

    @Autowired
    private ProductProvider productProvider;
    public YoungProductManager() {
        PRODUCTIDS = new int[]{211, 212, 213};
        DESCRIPTION = "未成年人的产品";
    }

    @Override
    public int getPriority() {
        return PriorityConstant.NOTADULT_PRODUCT_PRIORITY;
    }

    @Override
    public Product getFirstProduct(SwitchVo switchVo) {
        return productProvider.buildProduct(PRODUCTIDS[0]);
    }

    @Override
    public Product getSecondProduct(SwitchVo switchVo) {
        return productProvider.buildProduct(PRODUCTIDS[1]);
    }

    @Override
    public Product getThirdProduct(SwitchVo switchVo) {
        return productProvider.buildProduct(PRODUCTIDS[2]);
    }

    @Override
    public boolean isFit(SwitchVo switchVo) {
        return switchVo.isNotAdult();
    }
}
