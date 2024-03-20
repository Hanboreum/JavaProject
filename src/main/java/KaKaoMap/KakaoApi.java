package KaKaoMap;

import java.io.Closeable;
import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class KakaoApi {
    private static final String KAKAO_API_KEY ="5172c8357039a14dfc5168ad2bfa9219";
    //위도 경도를 출력하기 위해 double
    public static double[] getAddressCoordinate(String address) throws IOException{
        String apiUrl = "http://dapi.kakao.com/v2/local/search/address.json";
        //encoding작업
        String encodeAddress = URLEncoder.encode(address,"UTF-8");
        String requestUrl = apiUrl + "?query=" +encodeAddress;

        //url연결하기 위해
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //요청주소 만든다.
        HttpGet httpGet = new HttpGet(requestUrl);
        //인증키
        httpGet.setHeader("Authorization","KakaoAK" + KAKAO_API_KEY);

        try(CloseableHttpResponse response = httpClient.execute(httpGet)){
            String responseBody = EntityUtils.toString(response.getEntity());
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBody,JsonObject.class);
            JsonArray documents = jsonObject.getAsJsonArray("documents");

            if(documents.size() > 0){
                JsonObject document = documents.get(0).getAsJsonObject();
                double latitude = document.get("y").getAsDouble();
                double longitude = document.get("x").getAsDouble();
                return new double[]{latitude, longitude};
            }else{
                return null;
            }

            }
        }
    }

