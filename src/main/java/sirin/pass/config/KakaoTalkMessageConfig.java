package sirin.pass.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("kakaotalk")
public class KakaoTalkMessageConfig {
    private String host;
    private String token;
}
