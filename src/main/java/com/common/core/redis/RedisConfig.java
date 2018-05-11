package com.common.core.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.modules.sys.mq.RedisMessageReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

/**
 * Redis 缓存配置类，通过 Lettuce Client 实现
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;


    @Bean
    public RedisTemplate redisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate(lettuceConnectionFactory);

        // 设置 value 序列化使用的类
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 忽略json字符串中不识别的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略无法转换的对象 “No serializer found for class com.xxx.xxx”
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // key 序列化使用的类
        RedisSerializer stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);//key序列化

//        template.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
        template.setValueSerializer(new RedisObjectSerializer());//value序列化
//        template.setHashKeySerializer(stringSerializer);//Hash key序列化
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化

        // 更新一下 value 的序列化实现方式
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 订阅 Redis 中的消息通道
     * @param listenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactory);

        // 通道列表
        List<PatternTopic> topicList = Lists.newLinkedList();
        topicList.add(new PatternTopic("testTopic"));
        topicList.add(new PatternTopic("testTopic1"));
        // 订阅通道，第二个参数是通道，可以有多个用集合传参
        container.addMessageListener(listenerAdapter, topicList);
        return container;
    }

    /**
     * 接收到消息的处理类
     * @param redisMessageReceiver
     * @return
     */
    @Bean
    MessageListenerAdapter messageListenerAdapter(RedisMessageReceiver redisMessageReceiver) {
        // 这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
        // 也有好几个重载方法，这边默认调用处理器的方法叫 handleMessage

//        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();

        return new MessageListenerAdapter(redisMessageReceiver, "receiverMsg");
    }

}
