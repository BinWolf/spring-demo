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
public class NormalProductManager extends AbstractProductManager {

    @Autowired
    private ProductProvider productProvider;

    public NormalProductManager() {
        PRODUCTIDS = new int[]{111,112,113};
        DESCRIPTION = "所有人都能买的产品";
    }

    @Override
    public int getPriority() {
        return PriorityConstant.NORMAL_PRODUCT_PRIORITY;
    }

    @Override
    public Product getFirstProduct(SwitchVo switchVo) {
        return productProvider.buildProduct(111);
    }

    @Override
    public Product getSecondProduct(SwitchVo switchVo) {
        return productProvider.buildProduct(112);
    }

    @Override
    public Product getThirdProduct(SwitchVo switchVo) {
        return productProvider.buildProduct(113);
    }

    @Override
    public boolean isFit(SwitchVo switchVo) {
        return true;
    }
}
