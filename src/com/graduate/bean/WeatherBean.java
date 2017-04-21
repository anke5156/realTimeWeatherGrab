package com.graduate.bean;

/**
 * Created by anke on 2017/4/21.
 */
public class WeatherBean {
    private String ctime;
    private String success;
    private ResultBean result;

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "ctime='" + ctime + '\'' +
                ", success='" + success + '\'' +
                ", result=" + result +
                '}';
    }
}
