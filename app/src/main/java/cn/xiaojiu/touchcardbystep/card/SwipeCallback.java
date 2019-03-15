package cn.xiaojiu.touchcardbystep.card;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;


/**
 * @fileName: SwipeCallback
 * @author: 小九
 * @date: 2019/3/9 16:19
 * @description: 滑动回调
 */
public class SwipeCallback extends ItemTouchHelper.SimpleCallback {

    protected RecyclerView mRv;
    protected List mDatas;
    protected RecyclerView.Adapter mAdapter;
    protected SwipeListener swipeListener;

    public SwipeCallback(RecyclerView rv, RecyclerView.Adapter adapter, List datas) {
        this(0, ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, rv, adapter, datas);
    }

    public SwipeCallback(int dragDirs, int swipeDirs, RecyclerView rv, RecyclerView.Adapter adapter, List datas) {
        super(dragDirs, swipeDirs);
        mRv = rv;
        mAdapter = adapter;
        mDatas = datas;
    }

    /**
     * 水平方向是否可以被回收掉的阈值
     *
     * @param viewHolder
     * @return
     */
    public float getThreshold(RecyclerView.ViewHolder viewHolder) {
        /*getSwipeThreshold(viewHolder)*/
        return mRv.getWidth() * 0.5f;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getLayoutPosition();
        if (swipeListener != null) {
            swipeListener.onSwipe(direction, position);
        }
        mDatas.remove(position);
        if (mDatas.size() <= 1) {
            setDefaultSwipeDirs(ItemTouchHelper.ACTION_STATE_IDLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        //先根据滑动的dxdy 算出现在动画的比例系数fraction
        double swipValue = Math.sqrt(dX * dX + dY * dY);
        double fraction = swipValue / getThreshold(viewHolder);
        //边界修正 最大为1
        if (fraction > 1) {
            fraction = 1;
        }
        //对每个ChildView进行缩放 位移
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = recyclerView.getChildAt(i);
            int level = childCount - i - 1;
            if (level > 0) {
                if (level < CardConfig.MAX_SHOW_COUNT - 1) {
                    child.setTranslationY((float) (CardConfig.TRANS_Y_GAP * level - fraction * CardConfig.TRANS_Y_GAP));
                }
            }
        }
    }

    public interface SwipeListener {
        void onSwipe(int direction, int position);
    }

    public void setSwipeListener(SwipeListener swipeListener) {
        this.swipeListener = swipeListener;
    }
}
