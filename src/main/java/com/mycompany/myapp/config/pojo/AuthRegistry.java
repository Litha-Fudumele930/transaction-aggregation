package com.mycompany.myapp.config.pojo;

public class AuthRegistry {

    public String url;
    public String feignClientName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFeignClientName() {
        return feignClientName;
    }

    public void setFeignClientName(String feignClientName) {
        this.feignClientName = feignClientName;
    }

    @Override
    public String toString() {
        return "AuthRegistry{" + "url='" + url + '\'' + ", feignClientName='" + feignClientName + '\'' + '}';
    }
}
