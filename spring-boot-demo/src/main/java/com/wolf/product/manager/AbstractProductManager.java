package com.wolf.product.manager;

import com.wolf.product.vo.Product;
import com.wolf.product.vo.SwitchVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wolf on 16/11/26.
 */
public abstract class AbstractProductManager implements IProductManager {
    protected int[] PRODUCTIDS;
    protected String DESCRIPTION;

    @Override
    public int[] getProductIds() {
        return PRODUCTIDS;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public List<Product> getFillProducts(SwitchVo switchVo) {

        if (!isFit(switchVo)) {
            return Collections.EMPTY_LIST;
        }

        List<Product> list = new ArrayList<Product>();
        list.add(getFirstProduct(switchVo));
        list.add(getSecondProduct(switchVo));
        list.add(getThirdProduct(switchVo));
        return list;
    }
}
