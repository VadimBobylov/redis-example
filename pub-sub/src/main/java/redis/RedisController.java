package redis;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/redis")
@RestController
public class RedisController {
    private final RedisService redisService;

    @GetMapping
    public String sendAndGetMessage(@RequestParam(name = "message", defaultValue = "default msg") String message) {
        redisService.send(new User(System.currentTimeMillis(), "Vaska"));
        return message;
    }
}
