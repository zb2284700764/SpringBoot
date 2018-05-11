package com.modules.sys.mq;

import org.springframework.stereotype.Component;

/**
 * 用 Redis 实现的消息队列接受者
 */
@Component
public class RedisMessageReceiver {

    /**
     * 接收消息
     */
    public void receiverMsg(String msg) {

        // 接收到消息之后可以进行其他处理

        System.out.println("RedisMessageReceiver Receiver Msg --> " + msg);
    }

}
