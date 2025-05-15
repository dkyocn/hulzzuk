package com.hulzzuk.user.model.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.hulzzuk.user.model.vo.KakaoUserVO;
import com.hulzzuk.user.model.vo.NaverUserVO;

import jakarta.servlet.http.HttpSession;

@Component
public class NaverLoginAuth {

    private final static String CLIENT_ID = "FxpYHhGRII6JO0u4odtw";
    private final static String CLIENT_SECRET = "gwvXeF5KgU";
    private final static String REDIRECT_URI = "http://localhost:8080/hulzzuk/auth/naver.do";
    private final static String SESSION_STATE = "oauth_state";
    private final static String PROFILE_API_URL = "https://openapi.naver.com/v1/nid/me";

    // 네이버 인증 URL 생성
    public String getAuthorizationUrl(HttpSession session) {
        String state = generateRandomString();
        setSession(session, state);

        OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)
            .apiSecret(CLIENT_SECRET)
            .callback(REDIRECT_URI)
            .build(NaverLoginApi.instance());

        return oauthService.getAuthorizationUrl(state);
    }

    // AccessToken 획득
    public OAuth2AccessToken getNaverAccessToken(HttpSession session, String code, String state) throws InterruptedException, ExecutionException {
        String sessionState = getSession(session);
        if (StringUtils.pathEquals(sessionState, state)) {
            OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)
                .apiSecret(CLIENT_SECRET)
                .callback(REDIRECT_URI)                
                .build(NaverLoginApi.instance());

            try {
                return oauthService.getAccessToken(code);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 사용자 프로필 API 호출
    /*
    public NaverUserVO getUserProfile(OAuth2AccessToken oauthToken) throws IOException, InterruptedException, ExecutionException {
        
    	OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)
            .apiSecret(CLIENT_SECRET)
            .callback(REDIRECT_URI)
            .build(NaverLoginApi.instance());

        OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL);
        oauthService.signRequest(oauthToken, request);
        Response response = oauthService.execute(request);
		NaverUserVO nUser = new NaverUserVO(resultcode, message, response);
       
        return nUser;
    }*/
    
    public NaverUserVO getUserProfile(OAuth2AccessToken oauthToken)
            throws IOException, InterruptedException, ExecutionException {

        OAuth20Service oauthService = new ServiceBuilder(CLIENT_ID)
            .apiSecret(CLIENT_SECRET)
            .callback(REDIRECT_URI)
            .build(NaverLoginApi.instance());

        OAuthRequest request = new OAuthRequest(Verb.GET, PROFILE_API_URL);
        oauthService.signRequest(oauthToken, request);
        Response response = oauthService.execute(request);

        String responseBody = response.getBody();
        System.out.println("🔍 네이버 응답: " + responseBody);

        try {
            // JSON 파싱
            JSONParser parser = new JSONParser();
            JSONObject rootObj = (JSONObject) parser.parse(responseBody);
            JSONObject resObj = (JSONObject) rootObj.get("response");

            // VO 인스턴스 생성 및 값 설정
            NaverUserVO nUser = new NaverUserVO();
            NaverUserVO.ResponseVO resVO = nUser.new ResponseVO();

            resVO.setId((String) resObj.get("id"));
            resVO.setNickname((String) resObj.get("nickname"));
            resVO.setEmail((String) resObj.get("email"));
            resVO.setGender((String) resObj.get("gender"));
            resVO.setBirthday((String) resObj.get("birthday"));
            resVO.setBirthyear((String) resObj.get("birthyear"));
            resVO.setProfile_image((String) resObj.get("profile_image"));
            resVO.setMobile((String) resObj.get("mobile"));

            nUser.setResultcode((String) rootObj.get("resultcode"));
            nUser.setMessage((String) rootObj.get("message"));
            nUser.setResponse(resVO);

            return nUser; // ✅ 이제 더는 null이 아님

        } catch (Exception e) {
            e.printStackTrace();
            return null;  // 예외 발생 시만 null
        }
    }

    // 로그아웃 (단순 호출, 실제 세션 종료 아님)
	/*
	 * public void logOut(String oauthToken) { String baseURL =
	 * "http://nid.naver.com/nidlogin.logout"; RestTemplate template = new
	 * RestTemplate(); System.out.println("네이버 로그아웃 실행...");
	 * 
	 * try { ResponseEntity<String> response = template.getForEntity(baseURL,
	 * String.class); System.out.println(response.getBody()); } catch (Exception e)
	 * { e.printStackTrace(); } }
	 */
    
    public static String buildTokenDeleteUrl(String token) {
        try {
            String clientId = URLEncoder.encode(CLIENT_ID, StandardCharsets.UTF_8.toString());
            String clientSecret = URLEncoder.encode(CLIENT_SECRET, StandardCharsets.UTF_8.toString());
            String accessToken = URLEncoder.encode(token.replaceAll("'", ""), StandardCharsets.UTF_8.toString());

            String apiUrl = "https://nid.naver.com/oauth2.0/token" +
                    "?grant_type=delete" +
                    "&client_id=" + clientId +
                    "&client_secret=" + clientSecret +
                    "&access_token=" + accessToken +
                    "&service_provider=NAVER";

            return apiUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 세션 유효성 검사용 난수 생성
    private String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    // 세션에 난수 저장
    private void setSession(HttpSession session, String state) {
        session.setAttribute(SESSION_STATE, state);
    }

    // 세션에서 난수 가져오기
    private String getSession(HttpSession session) {
        return (String) session.getAttribute(SESSION_STATE);
    }
}
