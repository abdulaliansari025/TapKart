package com.ois.onistech.gmb.DataClass;

import java.util.List;

public class Featured_Cat {

    public String im_item;
    public String pgm_market;
    public String featured;
    public String pgm_fresh;
    public String pgm_img;
 //   public String count;
    List<Featured_Subcat> sub_data;

    public Featured_Cat(String im_item, String pgm_market, String featured, String pgm_fresh, String pgm_img, List<Featured_Subcat> sub_data) {
        this.im_item = im_item;
        this.pgm_market = pgm_market;
        this.featured = featured;
        this.pgm_fresh = pgm_fresh;
        this.pgm_img = pgm_img;
      //  this.count = count;
        this.sub_data = sub_data;
    }


    @Override
    public String toString() {
        return "Featured_Cat{" +
                "im_item='" + im_item + '\'' +
                ", pgm_market='" + pgm_market + '\'' +
                ", featured='" + featured + '\'' +
                ", pgm_fresh='" + pgm_fresh + '\'' +
                ", pgm_img='" + pgm_img + '\'' +
                ", sub_data=" + sub_data +
                '}';
    }

    public String getPgm_img() {
        return pgm_img;
    }

    public void setPgm_img(String pgm_img) {
        this.pgm_img = pgm_img;
    }

    public String getPgm_market() {
        return pgm_market;
    }

    public void setPgm_market(String pgm_market) {
        this.pgm_market = pgm_market;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getPgm_fresh() {
        return pgm_fresh;
    }
// public String im_item;

    public void setPgm_fresh(String pgm_fresh) {
        this.pgm_fresh = pgm_fresh;
    }

    public String getIm_item() {
        return im_item;
    }

    public void setIm_item(String im_item) {
        this.im_item = im_item;
    }

    public List<Featured_Subcat> getSub_data() {
        return sub_data;
    }

    public void setSub_data(List<Featured_Subcat> sub_data) {
        this.sub_data = sub_data;
    }

}
