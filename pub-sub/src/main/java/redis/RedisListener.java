package redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RedisListener implements MessageListener {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            User user = objectMapper.readValue(message.toString(), User.class);
            log.info("received user:{}", user);
            log.info("message toString:{}", message.toString());
            log.info("message getBody:{}", message.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
