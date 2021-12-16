package sy.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Slf4j
public class HelloController {


    @GetMapping("/hi")
    public String hi() {
        sendRequest();
        return "hi";
    }

    @GetMapping("/oauth/kakao") // 프로퍼티 확인필요
    public void fromKakao(String code) {
        log.info(code);
    }



    private void sendRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", ""); // TODO: id값 등 별도 관리
        params.add("redirect_uri", "http://localhost:8080/auth/kakao");
        params.add("response_type", "code");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

        RestTemplate rt = new RestTemplate();

        ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/authorize",
                HttpMethod.POST,
                entity,
                String.class);

        System.out.println("response = " + response);
    }


}
