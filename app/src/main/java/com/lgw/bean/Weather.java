package com.lgw.bean;

import java.io.Serializable;
import java.util.List;

public class Weather implements Serializable {
    /**
     * status : 1
     * count : 1
     * info : OK
     * infocode : 10000
     * lives : [{"province":"北京","city":"东城区","adcode":"110101","weather":"多云","temperature":"31","winddirection":"西南","windpower":"6","humidity":"30","reporttime":"2018-06-07 16:00:00"}]
     */
    public String status;
    public String count;
    public String info;
    public String infocode;
    public List<LivesBean> lives;

    public static class LivesBean {
        /**
         * province : 北京
         * city : 东城区
         * adcode : 110101
         * weather : 多云
         * temperature : 31
         * winddirection : 西南
         * windpower : 6
         * humidity : 30
         * reporttime : 2018-06-07 16:00:00
         */

        public String province;
        public String city;
        public String adcode;
        public String weather;
        public String temperature;
        public String winddirection;
        public String windpower;
        public String humidity;
        public String reporttime;
    }
}
