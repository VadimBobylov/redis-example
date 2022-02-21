package redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.net.UnknownHostException;
import java.time.Duration;

@Configuration
public class RedisConfig {
    @Bean
    ChannelTopic topic() {
        return new ChannelTopic("etd:users");
    }

    @Bean
    RedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setPort(6379);
        configuration.setHostName("localhost");
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    Subscription subscription(RedisConnectionFactory redisConnectionFactory, RedisListener redisListener) throws UnknownHostException {
        var options = StreamMessageListenerContainer
                .StreamMessageListenerContainerOptions
                .builder()
                .pollTimeout(Duration.ofSeconds(1))
                .targetType(User.class)
                .build();
        var listenerContainer = StreamMessageListenerContainer
                .create(redisConnectionFactory, options);
        var subscription = listenerContainer.receive(StreamOffset.create(topic().getTopic(), ReadOffset.lastConsumed()), redisListener);
        listenerContainer.start();
        return subscription;
    }

    @Bean
    RedisTemplate<String, User> redisTemplate() {
        final RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
