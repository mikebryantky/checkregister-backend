package com.mikebryant.checkregister;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@Profile("test")
public class RedisConfigurer {

    @Configuration
    static class Config
    {
        @Bean
        @SuppressWarnings("unchecked")
        public RedisSerializer<Object> defaultRedisSerializer()
        {
            return Mockito.mock(RedisSerializer.class);
        }


        @Bean
        public RedisConnectionFactory connectionFactory()
        {
            RedisConnectionFactory factory = Mockito.mock(RedisConnectionFactory.class);
            RedisConnection connection = Mockito.mock(RedisConnection.class);
            Mockito.when(factory.getConnection()).thenReturn(connection);

            return factory;
        }
    }

}
