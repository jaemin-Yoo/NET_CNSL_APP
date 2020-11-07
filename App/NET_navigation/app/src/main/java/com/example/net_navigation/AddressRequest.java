package com.example.net_navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddressRequest extends StringRequest {

    // 서버 URL 설정 (php 파일 연동)
    final static private String URL = "http://116.89.189.36/location.php"; // 우리 서버 주소
    private Map<String, String> map;

    public AddressRequest(String Latitude, String Longitude, Response.Listener<String> listener){ // POST 형식으로 응답 보내기
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("Latitude",Latitude);
        map.put("Longitude",Longitude);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
