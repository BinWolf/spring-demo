package com.wolf.product;

import com.wolf.product.conf.ProductConfigure;
import com.wolf.product.provider.ProductFactory;
import com.wolf.product.vo.Product;
import com.wolf.product.vo.SwitchVo;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wolf on 16/11/26.
 */
public class Main {
    @Test
    public void test_qryProductList() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ProductConfigure.class);
        ProductFactory productFactory = applicationContext.getBean(ProductFactory.class);
        SwitchVo switchVo = new SwitchVo();
        switchVo.setIsNotAdult(true);
        List<Product> list = productFactory.getFillProducts(switchVo);
        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i).getProductId());
            }
        }

    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(1);
        list.add(4);
        list.add(3);
        Collections.sort(list);

    }
}
