package cn.xiaojiu.touchcardbystep.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import cn.xiaojiu.touchcardbystep.R;

/**
 * @fileName: CardViewAdapter
 * @author: 小九
 * @date: 2019/3/9 10:38
 * @description: 卡片管理数据适配器
 */
public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.CardHolder> {

    private Context mContext;
    private List<JSONObject> objectList = new ArrayList<>();

    public CardViewAdapter(Context mContext, List<JSONObject> objectList) {
        this.mContext = mContext;
        this.objectList = objectList;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.layout_card_item, null);
        CardHolder holder = new CardHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        try {
            JSONObject object = objectList.get(position);
            String title = object.getString("title");
            String tips = object.getString("tips");
            String tep = object.getString("tep");
            holder.tv_title.setText(title);
            holder.tv_tips.setText(tips);
            holder.tv_number.setText(tep+ "");
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return objectList == null ? 0 : objectList.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder {
        public TextView tv_number;
        public TextView tv_title;
        public TextView tv_tips;
        public RelativeLayout rl_setup;

        public CardHolder(View itemView) {
            super(itemView);
            tv_number = (TextView) itemView.findViewById(R.id.tv_number);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_tips = (TextView) itemView.findViewById(R.id.tv_tips);
            rl_setup = (RelativeLayout) itemView.findViewById(R.id.rl_setup);
        }
    }
}
