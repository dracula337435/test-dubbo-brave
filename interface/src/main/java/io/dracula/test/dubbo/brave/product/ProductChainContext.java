package io.dracula.test.dubbo.brave.product;

/**
 * @author dk
 */
public class ProductChainContext {

    private String frontId;

    private String sendId;

    private String previousProduct;

    private static ThreadLocal<ProductChainContext> local = ThreadLocal.withInitial(ProductChainContext::new);

    /**
     * 暂时留白，仅为了禁止从外部实例化
     */
    private ProductChainContext(){

    }

    public static ProductChainContext getCurrent(){
        return local.get();
    }

    public String getFrontId() {
        return frontId;
    }

    public void setFrontId(String frontId) {
        this.frontId = frontId;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getPreviousProduct() {
        return previousProduct;
    }

    public void setPreviousProduct(String previousProduct) {
        this.previousProduct = previousProduct;
    }
}
