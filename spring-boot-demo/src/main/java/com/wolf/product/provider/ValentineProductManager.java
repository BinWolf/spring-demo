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
public class ValentineProductManager extends AbstractProductManager {

    public ValentineProductManager() {
        PRODUCTIDS = new int[]{311,312,313};
        DESCRIPTION = "节日产品";
    }

    @Autowired
    private ProductProvider productProvider;

    @Override
    public int getPriority() {
        return PriorityConstant.VALENTINE_PRODUCT_PRIORITY;
    }

    @Override
    public Product getFirstProduct(SwitchVo switchVo) {
        return productProvider.buildProduct(311);
    }

    @Override
    public Product getSecondProduct(SwitchVo switchVo) {
        return productProvider.buildProduct(312);
    }

    @Override
    public Product getThirdProduct(SwitchVo switchVo) {
        return productProvider.buildProduct(313);
    }

    @Override
    public boolean isFit(SwitchVo switchVo) {
        return switchVo.isDiscount();
    }
}
