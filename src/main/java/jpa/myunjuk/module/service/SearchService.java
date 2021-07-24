package jpa.myunjuk.module.service;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jpa.myunjuk.module.model.dto.SearchDto;
import jpa.myunjuk.module.model.dto.SearchResDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SearchService {

    @Value("${kakao.key}")
    private String key;
    @Value("${kakao.url}")
    private String url;

//    private String url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbiw04061849001&QueryType=Title" +
//            "&MaxResults=20&SearchTarget=Book&output=js&Version=20070901";
//    private String url2 = "http://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey=ttbiw04061849001&itemIdType=ISBN&ItemId=8983925566&output=js";

    public SearchDto search(String keyword, int page) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "KakaoAK " + key); //Authorization 설정
        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders); //엔티티로 만들기
        URI targetUrl = UriComponentsBuilder
                .fromUriString(url) //기본 url
                .queryParam("query", keyword) //인자
                .queryParam("page", page)
                .build()
                .encode(StandardCharsets.UTF_8) //인코딩
                .toUri();

        //GetForObject는 헤더를 정의할 수 없음
        return restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, SearchDto.class).getBody(); //내용 반환
    }

//    public Map search(String keyword, int start) {
//        RestTemplate restTemplate = getRestTemplate();
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
//        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
//        URI targetUrl = UriComponentsBuilder
//                .fromUriString(url2)
////                .queryParam("Query", keyword)
////                .queryParam("start", start)
//                .build()
//                .encode(StandardCharsets.UTF_8)
//                .toUri();
//        System.out.println(targetUrl);
//        //System.out.println(restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, SearchResDto.class).getBody());
//        Map body = restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, Map.class).getBody();
//        Integer page = (Integer) ((HashMap) ((HashMap) ((List) body.get("item")).get(0)).get("bookinfo")).get("itemPage");
//        System.out.println("page = " + page);
//        return restTemplate.exchange(targetUrl, HttpMethod.GET, httpEntity, Map.class).getBody();
//    }
//
//    private RestTemplate getRestTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverters.add(converter);
//        restTemplate.setMessageConverters(messageConverters);
//        return restTemplate;
//    }
}
