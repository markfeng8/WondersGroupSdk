package com.wondersgroup.android.sdk.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by x-sir on 2018/8/20 :)
 * Function:医院列表返回数据
 */
public class HospitalEntity extends BaseEntity {

    private List<DetailsBeanX> details;

    public List<DetailsBeanX> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBeanX> details) {
        this.details = details;
    }

    public static class DetailsBeanX implements Serializable {
        /**
         * details : [{"org_code":"47117170333050211A1001","org_name":"湖州市中心医院"},{"org_code":"47117166633050211A1001","org_name":"湖州市第一人民医院"},{"org_code":"47117169033050211A5201","org_name":"湖州市第三人民医院"},{"org_code":"47117172X33050211G1001","org_name":"湖州市妇幼保健院"},{"org_code":"47117171133050211A2101","org_name":"湖州市中医院"}]
         * area : 330500
         * area_name : 湖州市
         */

        private String area;
        private String area_name;
        private List<DetailsBeanX.DetailsBean> details;

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public List<DetailsBeanX.DetailsBean> getDetails() {
            return details;
        }

        public void setDetails(List<DetailsBeanX.DetailsBean> details) {
            this.details = details;
        }

        public static class DetailsBean {
            /**
             * org_code : 47117170333050211A1001
             * org_name : 湖州市中心医院
             */

            private String org_code;
            private String org_name;

            public String getOrg_code() {
                return org_code;
            }

            public void setOrg_code(String org_code) {
                this.org_code = org_code;
            }

            public String getOrg_name() {
                return org_name;
            }

            public void setOrg_name(String org_name) {
                this.org_name = org_name;
            }
        }
    }
}
