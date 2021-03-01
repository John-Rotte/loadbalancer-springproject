package se.iths.loadbalancer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
//@EnableRetry
public class Controller {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/getsongs")
    @Retryable(value = RemoteAccessException.class,
        maxAttempts = 2, backoff = @Backoff(delay = 100))
    public String persons() {return this.restTemplate.getForObject("http://songs-service/songs/",String.class);}

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
