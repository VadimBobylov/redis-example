package redis;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RedisListener implements StreamListener<String, ObjectRecord<String,User>> {

    @Override
    public void onMessage(ObjectRecord<String, User> message) {
        log.info("Receive user: {}",message.getValue());
    }
}
