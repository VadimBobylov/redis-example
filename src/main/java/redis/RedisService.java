package redis;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RedisService {
    RedisTemplate<String, User> redisTemplate;
    ChannelTopic channel;

    public void send(User user) {
        redisTemplate.convertAndSend(channel.getTopic(), user);
    }
}
