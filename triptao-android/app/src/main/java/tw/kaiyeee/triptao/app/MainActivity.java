package tw.kaiyeee.triptao.app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tw.kaiyeee.triptao.R;

import static tw.kaiyeee.triptao.R.layout.list_view_layout;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,SearchView.OnQueryTextListener{
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView listView;


    private TabLayout mTabs;
    private static String url = "http://data.tycg.gov.tw/api/v1/rest/datastore/bd906b29-9006-40ed-8bd7-67597c2577fc?format=json";

    String[] demo_Name = {"桃園國際機場", "華泰名品城", "奧爾森林學堂", "桃園忠烈祠暨神社文化園區","大溪老街"};
    String[] site_Tag = {"景 點", "景 點", "景 點", "景 點", "景 點"};

    Integer[] site_pic = {R.drawable.d75, R.drawable.d58, R.drawable.d91, R.drawable.d30, R.drawable.d92};
    String[] demo_describe = {"桃園國際機場是全台國際航班最主要的起降機場，也是台灣在地理位置上最北邊的機場。自從機場捷運通車後，將機場與捷運串聯在一起，交通更為便捷。航廈內有許多設施及美食，提供遊客候機時得以休憩玩樂。",
            "坐落在高鐵桃園站旁，擁有交通地利之便，為全台首座標榜美式露天購物村的outlet「華泰名品城」，已於2015年底風光進駐，以美式鄉村風格營造出悠閒的購物氛圍，讓人有如置身國外購物中心般，大力匯集流行時尚、居家精品、樂活休閒等上百家國際品牌，享受舒適購物的全新體驗。放眼全台，獨有的超寬敞購物環境，緊鄰高鐵桃園站的地利之便，讓你享受一秒到紐約第五大道的掃貨快感！逛累了，也可以嚐有大啖異國美食的好機會！想來碗拉麵、大喀美式漢堡，或是來個泰式料理開開胃，美食廣場上琳瑯滿目的嚴選好滋味，絕對值得遊客放滿腳步，大肆感受味蕾的驚艷！桃園新興景點，第一期購物空間佔地達16,000坪，一樓以國際精品、飾品配件、內睡衣、嬰幼童用品為主，二樓則是家電、運動休閒、美食街，三樓規劃各式主題餐廳，血拚不用飛出國，華泰名品城滿足了台灣消費者過去在海外outlet購物的熟悉經驗，未來隨著二、三期工程完工，更可望吸引更多人潮！",
            "「奧爾森林學堂」位於素有「桃園後花園」美譽的虎頭山公園內，在2012年以不破壞公園內景觀及樹木為原則下，在林木間搭起木製空中步道，同時利用雀榕善於纏繞的特性讓樹木與樹屋共生，打造出充滿生命力的三座「活」樹屋，並以大大小小的貓頭鷹裝飾其中，命名為「奧爾森林學堂」。其中有設計六角形的「讀樹教室」，可近距離觀察樹木生長的生態；從貓頭鷹造型的「咕咕屋」則可窺見鳥類生態；至於船型的「綠野方舟」則是孩子們最佳表演平台，待再久都不厭煩。",
            "座落於桃園市虎頭山上的桃園市忠烈祠，落成於民國二十七年，前身為日本人所建造的「桃園神社」，是臺灣保存最為完整的日治時代神社，建築風格融合了中國古代唐風、日本風及臺灣近代風，採用上等台灣檜木構築，並已在民國八十三年經公告正式被列為國家三級古蹟；也因為特殊的歷史文化價值，而被電影《KANO》選中成為拍片場景，引領觀眾深刻感受1930年代的歲月回憶。早在日治時期，日本在台灣推行皇民化運動，開始在全台建造了約兩百座的神社，桃園神社就是在這個時期所建造的；當時選擇依山而建，不但可遠眺西南方的桃園市區，視線大約可通過市中心的開漳聖王廟，象徵著守護百姓的用意。隨著日本的戰敗、台日斷交，多數神社隨著無人參拜而面臨拆除、改建的命運，桃園神社也在民國三十九年改名為桃園縣忠烈祠，其仿唐式建築風格，加上建材採用上選的台灣檜木，在文史藝術的保護前提下，特別被悉心維護，成為目前台灣與日本境外唯一完整保留的神社建築。現在的桃園忠烈祠暨神社文化園區建物規模完整，建築群主要以安置神位的「本殿」、供一般信眾參拜的「拜殿」、神職或管理員辦公的「社務所」、進入神社參拜前可供民眾洗手與漱口的「手水舍」等組成，另外還有鳥居、石獻燈、高麗犬、銅馬雕塑、神社參道等設施，建造材料則使用檜木、杉木，質地細緻，搭配保留完整的大木結構系統，樑柱以嵌接方式固定，嚴謹的工法設計，說明了工匠的巧思，也更讓人感受到神社文化園區莊嚴樸實之美。若順應地勢，登上成功路旁的階梯，就可以看到神社文化園區前筆直的參道，中門與拜殿之間即以此參道相連接，可一路相連至桃園車站。神社文化園區的四周種滿了松柏古樹，環境清幽，祠前綠意盎然、古色古香；每到櫻花綻放的初春時節，浪漫粉嫩的嬌美花瓣為神社園區的樸實氣息增添柔美氛圍；秋天則可在神社園區前的庭園與停車場周邊，欣賞到片片楓紅景象，可說四季皆有不同的風貌。若是在拜殿前的階梯平台稍做停留，則可居高臨下眺望整個神社景致，感受被綠意所包圍的靜謐氛圍，成為遊客拍照、踏青的最佳去處。",
            "大溪是桃園最早發展的地方，透過大漢溪小帆船行駛淡水河，與大陸貿易興盛，造就了許多商號與商賈。日治大正時代流行巴洛克建築風格，和平路、中山路等老街，各商號融合巴洛克式繁飾主義和閩南傳統裝飾圖案，包括希臘山頭、羅馬柱子和中式的魚、蝙蝠等祈求吉慶的圖案混合，形成一種大溪專有的特色。和平老街因為開發較晚，老屋的保存狀況也較好，街上特色商店林立，十分熱鬧。大溪老街週邊有大溪橋、李騰芳古宅、寺廟古蹟、武德殿文化古蹟及大溪木藝生態博物館等知名景點，當然更要品嚐老街上傳統的台灣古早味小吃、體驗台灣童玩，尤其是大溪豆干、月光餅、花生糖、豆花、碗粿、湯圓等，都是不容錯過的台灣道地美食！絕對是來到桃園，感受道地台灣味必去的景點之一！"};
    String[] demo_site = {"大園區", "中壢區", "桃園區", "桃園區", "大溪區"};
    String[] Tag_color = {"#FFC0CB", "#FFC0CB", "#FFC0CB", "#FFC0CB", "#FFC0CB", "#FFC0CB"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //new GetContacts ().execute();
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mTabs.addTab(mTabs.newTab().setText("熱門"));
        mTabs.addTab(mTabs.newTab().setText("精選"));


        listView = (ListView) findViewById(R.id.list_view);
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <=4 ; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("Name", demo_Name[i]);
            //item.put("Tag", site_Tag[i]);
            item.put("Toldescribe", demo_describe[i]);
            item.put("Picture", site_pic[i]);
            item.put("Add", demo_site[i]);
            items.add(item);
        }
        ListAdapter adapter = new SimpleAdapter(
                MainActivity.this, items, list_view_layout, new String[]{"Name", "Toldescribe", "Add", "Picture"},
                new int[]{R.id.Title, R.id.Description, R.id.edt_area, R.id.site_image}) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                //TextView Tag = (TextView) view.findViewById(R.id.Tag);
                //Tag.setBackgroundColor(Color.parseColor(Tag_color[position]));
                //Tag.setText(site_Tag[position]);
                final Button btn =(Button)view.findViewById(R.id.Tag_btn);
                btn.setText(site_Tag[position]);
                return view;


            }

            ;
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int itemsposition, long id) {
                if(itemsposition == 0) {
                    Intent intent = new Intent(MainActivity.this, demo01_details_home.class);
                    intent.putExtra("", listView.getItemAtPosition(itemsposition).toString());
                    startActivity(intent);
                }
                if(itemsposition == 1) {
                    Intent intent = new Intent(MainActivity.this,demo01_details_home.class);
                    intent.putExtra("華泰名品城", listView.getItemAtPosition(itemsposition).toString());
                    startActivity(intent);
                }
                if(itemsposition == 2) {
                    Intent intent = new Intent(MainActivity.this, demo01_details_home.class);
                    intent.putExtra("奧爾森林學堂", listView.getItemAtPosition(itemsposition).toString());
                    startActivity(intent);
                }
                if(itemsposition == 3) {
                    Intent intent = new Intent(MainActivity.this, demo01_details_home.class);
                    intent.putExtra("桃園忠烈祠", listView.getItemAtPosition(itemsposition).toString());
                    startActivity(intent);
                }
                if(itemsposition == 4) {
                    Intent intent = new Intent(MainActivity.this, demo01_details_home.class);
                    intent.putExtra("大溪老街", listView.getItemAtPosition(itemsposition).toString());
                    startActivity(intent);
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }







/*
    private  class GetContacts extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            HttpHandler sh = new HttpHandler();
            String JSONStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + JSONStr);

            if (JSONStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(JSONStr); //開啟URL這個TXT文件
                    JSONObject result = jsonObject.getJSONObject("result");//現在目的地在object並在object目錄下找"infos"這目錄
                    JSONArray records = result.getJSONArray("records");

                    for (int i = 0; i < 212; i++) {

                        JSONObject c = records.getJSONObject(i);
                        String Ticketinfo = c.getString("Ticketinfo");
                        String Opentime = c.getString("Opentime");
                        String Tel = c.getString("Tel");
                        String Name = c.getString("Name");
                        String Px = c.getString("Px");
                        String Py = c.getString("Py");
                        String Toldescribe = c.getString("Toldescribe");
                        String InfoId = c.getString("InfoId");
                        String Add = c.getString("Add");
                        String Fax = c.getString("Fax");
                        String Remarks = c.getString("Remarks");
                        String Travellinginfo = c.getString("Travellinginfo");
                        String Parkinginfo = c.getString("Parkinginfo");
                        String TYWebsite = c.getString("TYWebsite");
                        String Tag = site_Tag[i];
                        String Area =Add.substring(6,9);
                        Integer Picture = site_pic[i];
                        String image = Picture.toString();
                        HashMap<String, String> site = new HashMap<>();

                        site.put("Name", Name);
                        site.put("Toldescribe", Toldescribe);
                        site.put("Tag",Tag);
                        site.put("Add",Area);
                        site.put("Picture",image);








                        contactList.add(site);




                    }


                } catch (final JSONException e) {
                    Log.e(TAG, "JSONException error: " + JSONStr);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,
                                    "JSONException error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldnt get json");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,
                                "Couldnt get json", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            return null;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }*/








        @Override
        public void onBackPressed () {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("你確定要離開");
            builder.setCancelable(true);
            builder.setNegativeButton("在逛一會兒", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("是的", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    finish();

                }
            });
            /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }*/
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }



        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}