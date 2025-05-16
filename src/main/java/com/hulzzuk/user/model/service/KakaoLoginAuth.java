package com.hulzzuk.user.model.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hulzzuk.user.model.vo.KakaoUserVO;

import jakarta.servlet.http.HttpSession;

@Component
public class KakaoLoginAuth {
	private final static String K_CLIENT_ID = "7e7c827b84e5f3847ec771f158f01cdc";
    private final static String K_REDIRECT_URI = "http://localhost:8080/hulzzuk/auth/kakao.do";

    // 파라미터
    public String getAuthorizationUrl(HttpSession session) {
        String kakaoUrl = "https://kauth.kakao.com/oauth/authorize?" +
                "client_id=" + K_CLIENT_ID +
                "&redirect_uri=" + K_REDIRECT_URI +
                "&response_type=code&prompt=login";
        return kakaoUrl;
    }

    // accessToken 획득 (step 2)
    public JsonNode getKakaoAccessToken(String authorizeCode) {
        final String requestUrl = "https://kauth.kakao.com/oauth/token";
        final List<NameValuePair> postParams = new ArrayList<>();

        postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
        postParams.add(new BasicNameValuePair("client_id", K_CLIENT_ID));
        postParams.add(new BasicNameValuePair("redirect_uri", K_REDIRECT_URI));
        postParams.add(new BasicNameValuePair("code", authorizeCode));

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(requestUrl);

        JsonNode returnNode = null;

        try {
            post.setEntity(new UrlEncodedFormEntity(postParams));
            var response = client.execute(post);
            ObjectMapper mapper = new ObjectMapper();
            returnNode = mapper.readTree(response.getEntity().getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnNode;
    }

    // 사용자 정보 획득
	/*
	 * public JsonNode getKakaoUserInfo(String accessToken) { final String
	 * requestUrl = "https://kapi.kakao.com/v2/user/me"; // ✅ 수정
	 * 
	 * HttpClient client = HttpClientBuilder.create().build(); HttpPost post = new
	 * HttpPost(requestUrl);
	 * 
	 * post.addHeader("Authorization", "Bearer " + accessToken);
	 * 
	 * JsonNode returnNode = null;
	 * 
	 * try { var response = client.execute(post); ObjectMapper mapper = new
	 * ObjectMapper(); returnNode =
	 * mapper.readTree(response.getEntity().getContent()); } catch (IOException e) {
	 * e.printStackTrace(); } return returnNode; }
	 */
    
    // 사용자 정보 획득
    public KakaoUserVO getKakaoUserInfo(String accessToken) {
        final String requestUrl = "https://kapi.kakao.com/v2/user/me";

        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(requestUrl);

        post.addHeader("Authorization", "Bearer " + accessToken);
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");

        List<NameValuePair> params = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoUserVO kUser = null;

        try {
            params.add(new BasicNameValuePair("property_keys",
                    objectMapper.writeValueAsString(List.of(
                            "kakao_account.profile", "kakao_account.name",
                            "kakao_account.email", "kakao_account.age_range",
                            "kakao_account.birthday", "kakao_account.gender"
                    ))
            ));
            post.setEntity(new UrlEncodedFormEntity(params, StandardCharsets.UTF_8));

            var response = client.execute(post);
            JsonNode root = objectMapper.readTree(response.getEntity().getContent());

            Long id = root.path("id").asLong();
            Boolean hasSignedUp = root.path("has_signed_up").asBoolean();
            String connectedAtStr = root.path("connected_at").asText(null);
            String synchedAtStr = root.path("synched_at").asText(null);

            Date connectedAt = connectedAtStr != null ? Date.valueOf(connectedAtStr.substring(0, 10)) : null;
            Date synchedAt = synchedAtStr != null ? Date.valueOf(synchedAtStr.substring(0, 10)) : null;

            Map<String, String> properties = new HashMap<>();
            root.path("properties").fields().forEachRemaining(entry -> {
                properties.put(entry.getKey(), entry.getValue().asText());
            });

            JsonNode kakaoAcc = root.path("kakao_account");
            JsonNode profile = kakaoAcc.path("profile");

            KakaoUserVO.KakaoAccountVO.ProfileVO profileVO =
                    new KakaoUserVO().new KakaoAccountVO().new ProfileVO(
                            profile.path("nickname").asText(null),
                            profile.path("thumbnail_image_url").asText(null),
                            profile.path("profile_image_url").asText(null),
                            profile.path("is_default_image").asBoolean(false)
                    );

            KakaoUserVO.KakaoAccountVO kakaoAccount = new KakaoUserVO().new KakaoAccountVO(
                    kakaoAcc.path("profile_needs_agreement").asBoolean(false),
                    profileVO,
                    kakaoAcc.path("has_email").asBoolean(false),
                    kakaoAcc.path("email_needs_agreement").asBoolean(false),
                    kakaoAcc.path("email").asText(null),
                    kakaoAcc.path("age_range").asText(null),
                    kakaoAcc.path("gender").asText(null),
                    kakaoAcc.path("birthyear").asText(null),
                    kakaoAcc.path("birthday").asText(null),
                    kakaoAcc.path("birthday_type").asText(null)
            );

            kUser = new KakaoUserVO(id, hasSignedUp, connectedAt, synchedAt, properties, kakaoAccount, new HashMap<>());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return kUser;
    }

    
    // 로그아웃
    public JsonNode logOut(String accessToken) {
    	final String requestUrl = "https://kapi.kakao.com/v1/user/logout";
  
    	HttpClient client = HttpClientBuilder.create().build(); 
    	HttpPost post = new HttpPost(requestUrl); 
    	post.addHeader("Authorization", "Bearer " + accessToken);
	  
    	JsonNode returnNode = null;
  
	   try { 
		  var response = client.execute(post); 
		  ObjectMapper mapper = new ObjectMapper(); 
		  returnNode = mapper.readTree(response.getEntity().getContent());
		  
		  System.out.println("카카오 로그아웃 성공: " + returnNode);
	   } catch (IOException e) {
		  e.printStackTrace(); 
	   }
	  
	   return returnNode; 
    }
	 
    
    // 카카오 계정 연결 해제 (unlink)
	
	  public JsonNode disconnect(String accessToken) { 
		  final String requestUrl =
	  "https://kapi.kakao.com/v1/user/unlink";
	  
	  HttpClient client = HttpClientBuilder.create().build(); HttpPost post = new
	  HttpPost(requestUrl); post.addHeader("Authorization", "Bearer " +
	  accessToken);
	  
	  JsonNode returnNode = null;
	  
	  try { var response = client.execute(post); ObjectMapper mapper = new
	  ObjectMapper(); returnNode =
	  mapper.readTree(response.getEntity().getContent());
	  
	  System.out.println("카카오 연결 해제 성공: " + returnNode); } catch (IOException e) {
	  e.printStackTrace(); }
	  
	  return returnNode; }
	 

}
