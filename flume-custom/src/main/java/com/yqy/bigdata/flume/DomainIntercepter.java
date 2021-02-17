package com.yqy.bigdata.flume;


import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author bahsk
 * @createTime 2021-02-14 21:50
 * @description
 */
public class DomainIntercepter implements Interceptor {


    List<Event> events;

    @Override
    public void initialize() {
        events = new ArrayList<>();
    }

    //单个Event处理
    @Override
    public Event intercept(Event event) {
        //获取header
        Map<String,String> headers = event.getHeaders();
        String body = new String(event.getBody());
        if(body.contains("cat")) {
            headers.put("type","cat");
        }else{
            headers.put("type","others");
        }
        return  event;
    }

    //多个Event处理
    @Override
    public List<Event> intercept(List<Event> events) {
        //清除optional operation
        events.clear();
        for(Event event : events) {
            events.add(intercept(event));
        }
        return  events;
    }

    @Override
    public void close() {
        events = null;
    }

    public static class Builder implements Interceptor.Builder {

        @Override
        public Interceptor build() {
            return new DomainIntercepter();
        }

        @Override
        public void configure(Context context) {

        }
    }
}
