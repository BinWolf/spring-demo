package com.wolf.product.provider;

import com.wolf.product.vo.Product;
import org.springframework.stereotype.Component;

/**
 * Created by wolf on 16/11/26.
 * 产品的详细,该类在真实系统中应该要查询数据库
 */
@Component
public class ProductProvider {

    public Product buildProduct(int productId) {
        //根据id查询数据库,在这里就直接new来代替数据库查询
        Product product = new Product.ProductBuilder().setProductId(productId).setName("测试产品").setPrice(88.88).build();
        return product;
    }
}
