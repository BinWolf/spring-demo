package com.wolf.product;

import com.wolf.product.conf.ProductConfigure;
import com.wolf.product.provider.ProductFactory;
import com.wolf.product.vo.Product;
import com.wolf.product.vo.SwitchVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by wolf on 16/11/26.
 */
public class ProductMain {
    private static final Logger log = LoggerFactory.getLogger(ProductMain.class);

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProductConfigure.class);
        ProductFactory productFactory = applicationContext.getBean(ProductFactory.class);
        SwitchVo switchVo = new SwitchVo();
        switchVo.setIsNotAdult(true);
        List<Product> list = productFactory.getFillProducts(switchVo);
        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                log.debug("Product id : " + list.get(i).getProductId());
            }
        }
    }
}
