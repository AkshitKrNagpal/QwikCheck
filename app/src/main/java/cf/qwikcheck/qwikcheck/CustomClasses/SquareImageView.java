package cf.qwikcheck.qwikcheck.CustomClasses;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by akshit on 26/10/16.
 */

public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }


}
