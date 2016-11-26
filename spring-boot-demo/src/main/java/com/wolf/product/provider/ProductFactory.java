package com.wolf.product.provider;

import com.wolf.product.manager.IProductManager;
import com.wolf.product.vo.Product;
import com.wolf.product.vo.SwitchVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by wolf on 16/11/26.
 */
@Component
public class ProductFactory implements ApplicationContextAware, InitializingBean {

    private final Logger loger = LoggerFactory.getLogger(this.getClass());
    private ApplicationContext applicationContext;
    private List<IProductManager> managers = new ArrayList<IProductManager>();

    public List<Product> getFillProducts(SwitchVo switchVo) {
        List<Product> list = null;
        for (IProductManager manager : managers) {
            list = manager.getFillProducts(switchVo);
            if (!CollectionUtils.isEmpty(list)) {
                loger.debug("Manager is " + manager.getClass() + " ; desc : " + manager.getDescription());
                break;
            }
        }

        if (CollectionUtils.isEmpty(list)) {
            loger.error("Product list is null.....");
        }
        return list;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,IProductManager> beans =  applicationContext.getBeansOfType(IProductManager.class);
        for (IProductManager manager : beans.values()) {
            managers.add(manager);
        }
        Collections.sort(managers, new Comparator<IProductManager>() {
            @Override
            public int compare(IProductManager o1, IProductManager o2) {
                return Integer.compare(o2.getPriority(), o1.getPriority());
            }
        });
        loger.debug("Product Manager initialized success :" + managers.size());
        for (int i = 0; i < managers.size(); i++) {
            loger.debug(managers.get(i).getDescription());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
