package com.wolf.product.manager;

import com.wolf.product.vo.Product;
import com.wolf.product.vo.SwitchVo;

import java.util.List;

/**
 * Created by wolf on 16/11/25.
 *
 */
public interface IProductManager {

    /**
     * 获取对应的产品id(比如:低于18岁的群体能买的东西)
     * @return
     */
    int[] getProductIds();

    /**
     * 优先权(产品显示级别)
     * @return
     */
    int getPriority();

    /**
     * 产品描述
     * @return
     */
    String getDescription();

    /**
     * 获取要返回前端的所有产品
     * @param switchVo
     * @return
     */
    List<Product> getFillProducts(SwitchVo switchVo);

    /**
     * 获取现在第一个位置的产品
     * @param switchVo
     * @return
     */
    Product getFirstProduct(SwitchVo switchVo);
    /**
     * 获取现在第二个位置的产品
     * @param switchVo
     *
     * @return
     */
    Product getSecondProduct(SwitchVo switchVo);
    /**
     * 获取现在第三个位置的产品
     * @param switchVo
     * @return
     */
    Product getThirdProduct(SwitchVo switchVo);

    boolean isFit(SwitchVo switchVo);
}
