//package com.externalmodeller.gcp.config;
//
//import java.io.IOException;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//
//public class ApiPoller implements Runnable {
//	
//	private static final String BASE_URL = "https://us-central1-aiplatform.google.com";
//    OkHttpClient client = new OkHttpClient();
//
//    /**
//    * Use OKHttp to send GET API poll request.
//     * @throws JSONException 
//    */
//    public int getCount() throws IOException, JSONException {
//        int count = 0;
//        Request request = new Request.Builder().url(BASE_URL + "").build();
//        try (Response response = client.newCall(request).execute()) {
//            ResponseBody body = response.body();
//            JSONObject json = new JSONObject(body.string());
//            JSONArray array = (JSONArray) json.get("max_count");
//            JSONObject firstElement = (JSONObject) array.get(0);
//            count = (Integer) firstElement.get("total");
//        }
//        return count;
//    }
//
//    /**
//    * This method gets called after every specified duration
//    */
//    @Override
//    public void run() {
//        try {
//            int count = this.getCount();
//            //@TODO Send the count information somewhere else.
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//			e.printStackTrace();
//		}
//    }
//
////    public static void main(String[] args) {
////       ApiPoller poller = new ApiPoller();
////       ScheduledExecutorService schedular = Executors.newScheduledThreadPool(1);
////       //specify the time duration
////       schedular.scheduleAtFixedRate(poller, 0,1, TimeUnit.MINUTES);
////    }
//
//}
