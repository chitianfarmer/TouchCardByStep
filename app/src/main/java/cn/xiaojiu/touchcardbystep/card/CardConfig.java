package cn.xiaojiu.touchcardbystep.card;

import android.content.Context;
import android.util.TypedValue;

/**
 * @fileName: CardConfig
 * @author: 小九
 * @date: 2019/3/9 16:19
 * @description: 卡片CardConfig
 */
public class CardConfig {
    /**
     * 屏幕上最多同时显示几个Item
     */
    public static int MAX_SHOW_COUNT = 5;
    public static int TRANS_Y_GAP;

    public static void initConfig(Context context) {
        TRANS_Y_GAP = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
    }
}
