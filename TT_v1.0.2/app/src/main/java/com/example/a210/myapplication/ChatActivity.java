package com.example.a210.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity{
    ListView chatList;
    Intent it,itGet;
    ChatView adapterItem = new ChatView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatList = (ListView)findViewById(R.id.chatList);
        itGet = getIntent();
        LocalBroadcastManager.getInstance(this).registerReceiver(mHandler,new IntentFilter("DAP-MESSAGE"));
        final ChatViewAdapter adapter = new ChatViewAdapter();

        chatList.setAdapter(adapter);

        if(itGet.getStringExtra("sep").toString().equals("quest")) {

            //질문자가 채팅 방 목록에 들어 왔을 경우 처리
            //질문자가 들어면 어떤 방을 보여줘야 하는건지???
            /*
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        String jsonString;
                        String urlString = "http://lim7504.iptime.org:8080/TripTalkWebServer/QuestionSelect.jsp?";
                        urlString += "USER_ID=" + UserInfomation.User_ID;
                        urlString += "&SERACH_AREA=" + UserInfomation.SearchArea;
                        urlString += "&SEARCH_SUBJECT=" + UserInfomation.SearchSubJect;
                        //urlString += "&IS_QUESTION=N";
                        jsonString = TomcatConnector(urlString);

                        JSONArray arr = new JSONArray(jsonString);

                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);

                            adapter.addItem(getResources().getDrawable(R.drawable.default_face), getResources().getDrawable(R.drawable.newicon), obj.get("NICK").toString(),
                                    obj.get("GRADE").toString(), obj.get("SUBTITLE").toString(),obj.get("WAIT_ID").toString());
                        }
                    }
                    catch (Exception e)
                    {
                    }
                }
            });
            th.start();
            */
        } else if(itGet.getStringExtra("sep").toString().equals("dap")) {


            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        String jsonString;
                        String urlString = "http://lim7504.iptime.org:8080/TripTalkWebServer/QuestionSelect.jsp?";
                        urlString += "USER_ID=" + UserInfomation.User_ID;
                        urlString += "&SERACH_AREA=" + UserInfomation.SearchArea;
                        urlString += "&SEARCH_SUBJECT=" + UserInfomation.SearchSubJect;
                        jsonString = TomcatConnector(urlString);

                        JSONArray arr = new JSONArray(jsonString);

                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);

                            adapter.addItem(getResources().getDrawable(R.drawable.default_face), getResources().getDrawable(R.drawable.newicon), obj.get("NICK").toString(),
                                    obj.get("GRADE").toString(), obj.get("SUBTITLE").toString(),obj.get("WAIT_ID").toString());
                        }
                    }
                    catch (Exception e)
                    {
                    }
                }
            });
            th.start();


//            adapter.addItem(getResources().getDrawable(R.drawable.default_face), getResources().getDrawable(R.drawable.newicon), "리신",
//                    "여행코스", "어디로... 가야하오");
//            adapter.addItem(getResources().getDrawable(R.drawable.default_face), getResources().getDrawable(R.drawable.newicon), "직거래 살인마",
//                    "관광지", "중고 직거래 가능한 어둡고 아무도 다니지 않는 장소");
//            adapter.addItem(getResources().getDrawable(R.drawable.default_face), getResources().getDrawable(R.drawable.newicon), "한지우",
//                    "레포츠", "속초에 망나뇽 나오는 곳이 어디죠");
//            adapter.addItem(getResources().getDrawable(R.drawable.default_face), getResources().getDrawable(R.drawable.newicon), "김정호",
//                    "여행코스", "한반도 최단 거리 코스 좀 알려주세요");
//            adapter.addItem(getResources().getDrawable(R.drawable.default_face), getResources().getDrawable(R.drawable.newicon), "고든 램지",
//                    "음식점", "어제 한 음식점이 내 혀를 조져놨어요 제대로 된 음식점을 알려주세요");
//            adapter.addItem(getResources().getDrawable(R.drawable.default_face), getResources().getDrawable(R.drawable.newicon), "남조선 국민",
//                    "문화시설", "고조 청와대로 가려면 우찌해야합네까 동무");
        }

        chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Boolean bResult = false;
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final int iRoomNumber = i;
                Thread th = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            String jsonString;
                            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss", Locale.KOREA );
                            Date currentTime = new Date ();
                            String mTime = mSimpleDateFormat.format ( currentTime );

                            String urlString = "http://lim7504.iptime.org:8080/TripTalkWebServer/ChatRoomManager.jsp?";
                            urlString += "REQUEST_USER_ID=" + adapter.getChatViewItem(iRoomNumber).getNick();
                            urlString += "&RECEIVE_USER_ID=" + UserInfomation.User_ID;
                            urlString += "&ROOM_NAME=" + adapter.getChatViewItem(iRoomNumber).getWaitID();
                            urlString += "&CREATE_DATE=" + mTime;
                            jsonString = TomcatConnector(urlString);
                            JSONArray arr = new JSONArray(jsonString);

                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);

                                if(obj.get("RESULT").toString().equals("ACK"))
                                {
                                    bResult = true;
                                    UserInfomation.Wait_ID = adapter.getChatViewItem(iRoomNumber).getWaitID();
                                    //if(bResult) {
                                    Intent it = new Intent(getApplicationContext(), RoomActivity.class);
                                    Log.i("getItem", adapter.getChatViewItem(i).getNick());
                                    it.putExtra("nick", adapter.getChatViewItem(i).getNick().toString());
                                    it.putExtra("grade", adapter.getChatViewItem(i).getGrade().toString());
                                    startActivity(it);
                               // }
                                }else
                                {
                                    bResult = false;
                                    Toast.makeText(getApplicationContext(),"입장에 실패 했습니다!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        catch (Exception e)
                        {
                        }
                    }
                });
                th.start();


            }
        });

        callFragment(1);
        callFragment(2);
    }

    private void callFragment(int frament_no) {

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no) {
            case 1:
                // '프래그먼트1' 호출
                Footer fragment1 = new Footer();
                Bundle bundle = new Bundle(1); // 파라미터는 전달할 데이터 개수
                bundle.putString("sep", itGet.getStringExtra("sep").toString()); // key , value
                fragment1.setArguments(bundle);
                transaction.replace(R.id.footer, fragment1);
                transaction.commit();
                break;

            case 2:
                // '프래그먼트2' 호출
                Header fragment2 = new Header();
                transaction.replace(R.id.header, fragment2);
                transaction.commit();
                break;
        }
    }

    public String TomcatConnector(String urlString) {

        StringBuilder html = new StringBuilder();
        try {
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            if(conn != null)
            {
                conn.setConnectTimeout(3000);
                conn.setUseCaches(false);
                if(conn.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    BufferedReader br =  new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));

                    while (true)
                    {
                        String line = br.readLine();
                        if(line == null) break;
                        html.append(line+"\n");

                    }
                    br.close();
                }
                conn.disconnect();

            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return html.toString();

    }

    private BroadcastReceiver mHandler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent intent1 = new Intent(getApplicationContext(), ChatActivity.class);
            intent1.putExtra("sep","dap");
            intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent1);
        }
    };
}
