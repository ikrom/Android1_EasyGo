package tc.easygo.connection;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tc.easygo.AsyncResponse;

/**
 * Created by ikromaulia on 12/16/2015.
 */
public class PostRequest extends AsyncTask<String, Void, String> {
    public AsyncResponse delegate=null;
    private static String base_url;
    private String urlParam;
    private Context context;
    private HashMap<String, String> postDataParams;

    public PostRequest(Context context, String url, HashMap<String, String> postDataParams)
    {
        //base_url = "http://service.travellingindonesia.esy.es/v1/";
        this.context = context;
        //base_url = url;
        //base_url = "http://travis.innvaderstudio.com/v1/";
        base_url = url;
        this.postDataParams = postDataParams;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... args) {
        String resp = "";
        try {
            String urlParameter = args[0].toString();
            String url_t = this.base_url + urlParameter;

            URL url = new URL(url_t);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int statusCode = conn.getResponseCode();
            InputStream is = null;
            System.out.println(statusCode);
            Log.d("status code LOG", statusCode + " : " + url_t);
            if (statusCode == 200) {
                is = new BufferedInputStream(conn.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line = "";
                while ((line = br.readLine()) != null) {
                    resp += line;
                }
                is.close();
                return  resp;
            } else
            {
                resp = "{status : '999'}";
                return  resp;
            }
        }catch (Exception e)
        {
            return new String("Exc : " + e.getMessage());
        }
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("client socket LOG", s);
        delegate.processFinish(s);
    }
}