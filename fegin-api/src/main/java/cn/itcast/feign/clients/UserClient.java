package cn.itcast.feign.clients;
import cn.itcast.feign.config.DefaultFeignConfiguration;
import cn.itcast.feign.config.JsonDecoder;
import cn.itcast.feign.pojo.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userservice",configuration = DefaultFeignConfiguration.class)
public interface UserClient {
    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);
}
