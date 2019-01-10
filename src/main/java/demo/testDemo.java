package demo;

import com.github.ontio.common.Helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class testDemo {

    public static void main(String[] args) throws IOException {
        long priceL = Long.valueOf(Helper.reverse("005ed0b200"), 16)/1000000000;
        System.out.println(priceL);
        System.out.println((int)priceL);
    }

    public static Map<String, String> getDate(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        Date tomorrow = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        Date afterTomorrow = calendar.getTime();
        Map<String, String> map =new HashMap();
        map.put("yesterday", sdfDate.format(yesterday));
        map.put("today", sdfDate.format(today));
        map.put("tomorrow", sdfDate.format(tomorrow));
        map.put("afterTomorrow", sdfDate.format(afterTomorrow));
        return map;
    }
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +2);
        date = calendar.getTime();
        return date;
    }

    private static List getMatch() throws IOException {
        URL u = new URL("http://data.nba.net/prod/v2/20181220/scoreboard.json");
        HttpURLConnection http = (HttpURLConnection) u.openConnection();
        http.setConnectTimeout(50000);
        http.setReadTimeout(50000);
        http.setRequestMethod("GET");
        http.setRequestProperty("Content-Type", "application/json");
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();
        StringBuilder sb = new StringBuilder();
        String DEFAULT_CHARSET = "UTF-8";
        try (InputStream is = http.getInputStream()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, DEFAULT_CHARSET))) {
                String str = null;
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                    str = null;
                }
            }
        }
        if (http != null) {
            http.disconnect();
        }
        System.out.println(sb.toString());
        return null;
    }
}
