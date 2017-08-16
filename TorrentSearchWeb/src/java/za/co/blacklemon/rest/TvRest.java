package za.co.blacklemon.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TvRest {

    public static final String BASE_URL = "https://oneom.tk";
    public static final String SEARCH = "/search/serial?title=";
    public static final String GET_SERIES = "/serial/";
    private static TvRest _instance;
    public static final String GET_SPISODE = "/ep/";
    
    private TvRest() {}
    
    public static TvRest getInstance() {
        if(_instance == null)
            _instance = new TvRest();
        return _instance;
    }
    
    public String get(String path, String parameter) {
        String fullPath = String.format("%s%s%s", BASE_URL, path, parameter);

        try {

            URL url = new URL(fullPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.addRequestProperty("User-Agent", "Apache-HttpClient/4.1.1 (java 1.5)");
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            StringBuilder ret = new StringBuilder();
            System.out.println("Calling service");
            //System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                ret.append(output);
            }

            conn.disconnect();
            
            return ret.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return null;
    }

}
