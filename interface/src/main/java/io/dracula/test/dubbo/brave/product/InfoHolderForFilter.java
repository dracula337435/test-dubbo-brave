package io.dracula.test.dubbo.brave.product;

import com.alibaba.dubbo.config.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author dk
 */
public class InfoHolderForFilter {

    @Autowired
    private ApplicationConfig applicationConfig;

    private String product;

    @PostConstruct
    public void setProduct(){
        if(product == null || "".equals(product)){
            product = applicationConfig.getName();
        }
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}
