package com.example.test5.components;
/*

这个文件的代码基本上是来自https://cloud.tencent.com/developer/article/1743086这篇文章的
我对源代码进行了一些小的修改，以符合我们的要求
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

@SuppressLint("AppCompatCustomView")
public class CircleImageView extends ImageView {
    private Paint paint ;
    private OnClickListener listener;
    public CircleImageView(Context context) {
        this(context,null);
    }
    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//此处添加了抗锯齿标志
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if (null != drawable) {
            //Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            Bitmap bitmap = drawableToBitamp(drawable);
            Bitmap b = getCircleBitmap(bitmap, 14);
            final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
            final Rect rectDest = new Rect(0,0,getWidth(),getHeight());
            paint.reset();
            canvas.drawBitmap(b, rectSrc, rectDest, paint);
        } else {
            super.onDraw(canvas);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        listener = l;
        super.setOnClickListener(l);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
       if(event.getAction()!=MotionEvent.ACTION_DOWN) return false;
        //return super.onTouchEvent(event);
        double x = event.getX();
        double y = event.getY();
        double radius = getHeight()/2.0;
        double xx=x-radius,yy=y-radius;
        if(xx*xx+yy*yy-radius*radius<1e-5){
            if(hasOnClickListeners()) listener.onClick(this);
        }
        return true;
    }

    private Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int x = bitmap.getWidth();
        canvas.drawCircle(x / 2, x / 2, x / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }
    private Bitmap drawableToBitamp(Drawable drawable)
    {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),
                drawable.getOpacity()!= PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
