package cn.xiaojiu.touchcardbystep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.xiaojiu.touchcardbystep.card.CardConfig;
import cn.xiaojiu.touchcardbystep.card.CardViewAdapter;
import cn.xiaojiu.touchcardbystep.card.SwipeCallback;
import cn.xiaojiu.touchcardbystep.card.SwipeCardLayoutManager;
import cn.xiaojiu.touchcardbystep.card.Utils;

/**
 * 主页面
 *
 * @author 小九
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, SwipeCallback.SwipeListener {
    private RecyclerView recyclerView;
    private TextView tv_start_app;
    private CardViewAdapter adapter;
    private SwipeCallback swipeCallback;
    private List<JSONObject> objectList = new ArrayList<>();
    private ItemTouchHelper itemTouchHelper;
    private ImageView iv_bg_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.immersive(this);
        setContentView(R.layout.activity_main);
        /*初始化卡片默认设置*/
        CardConfig.initConfig(this);
        initView();
        initData();
        setListener();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tv_start_app = (TextView) findViewById(R.id.tv_start_app);
        iv_bg_up = (ImageView) findViewById(R.id.iv_bg_up);
    }

    private void initData() {
        if (objectList.size() != 0) {
            objectList.clear();
        }
        try {
            /*注意 : 数据是倒续装载*/
            for (int i = 5; i >= 0; i--) {
                JSONObject object = new JSONObject();
                object.put("title", "这是标题" + (i + 1));
                object.put("tips", "这是描述" + (i + 1));
                object.put("tep", (i + 1) + "");
                objectList.add(object);
            }
        } catch (Exception e) {
        }
        int width = Utils.getScreenWidth(this);
        int bgUpHeight = width * 816 / 1080;
        iv_bg_up.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, bgUpHeight));
        /*设置数据*/
        adapter = new CardViewAdapter(this, objectList);
        /*设置自定义*/
        SwipeCardLayoutManager swipeCardLayoutManager = new SwipeCardLayoutManager();
        /*绑定布局管理器*/
        recyclerView.setLayoutManager(swipeCardLayoutManager);
        /*设置上成图片背景的高度*/
//        swipeCardLayoutManager.setCarHeight(bgUpHeight);
        /*设置数据适配器*/
        recyclerView.setAdapter(adapter);
        /*获取自定义回调监听*/
        swipeCallback = new SwipeCallback(recyclerView, adapter, objectList);
        /*获取条目滑动对象*/
        itemTouchHelper = new ItemTouchHelper(swipeCallback);
        /*绑定recyclerView*/
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setListener() {
        swipeCallback.setSwipeListener(this);
        tv_start_app.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start_app:
                Toast.makeText(getBaseContext(), "开启APP", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onSwipe(int direction, int position) {
        tv_start_app.setVisibility(position == 0 || position == 1 ? View.VISIBLE : View.GONE);
    }
}
