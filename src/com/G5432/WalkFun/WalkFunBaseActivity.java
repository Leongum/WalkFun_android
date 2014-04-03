package com.G5432.WalkFun;

import android.app.LocalActivityManager;
import android.os.Bundle;
import com.G5432.DBUtils.DatabaseHelper;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-28
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class WalkFunBaseActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    protected LocalActivityManager manager = null;
//    private View shareView;
//    private ImageView imageView;
//    private ScaleGestureDetector mScaleGestureDetector = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new LocalActivityManager(this, true);
        manager.dispatchCreate(savedInstanceState);
        //mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureListener());
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //返回给ScaleGestureDetector来处理
//        return mScaleGestureDetector.onTouchEvent(event);
//    }
//
//    public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {
//
//        private float scaleValue = 1;
//        private Bitmap bgImage;
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
//            float currentScaleFactor = scaleGestureDetector.getScaleFactor();
//            Log.i(this.getClass().getName(), "" + scaleValue * currentScaleFactor);
//            if (currentScaleFactor > 1) {
//                // zooming out
//            } else if (currentScaleFactor < 1 && currentScaleFactor >= 0.8) {
//                Matrix matrix = new Matrix();
//                scaleValue = scaleValue * currentScaleFactor;
//                matrix.setScale(scaleValue, scaleValue, imageView.getWidth() * (1 - scaleValue) / 2, imageView.getHeight() * (1 - scaleValue) / 2);
//                imageView.setImageMatrix(matrix);
//                imageView.invalidate();
//            }
//            return true;
//        }
//
//        @Override
//        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
//            bgImage = captureScreen();
//            Drawable drawable = new BitmapDrawable(captureScreen());
//            scaleValue = 1;
//            imageView = new ImageView(getApplicationContext());
//
//            //imageView.setImageResource(R.drawable.paper);
//            Resources res=getResources();
//            Bitmap bmp= BitmapFactory.decodeResource(res, R.drawable.paper);
//
//            imageView.setImageBitmap(bmp);
//            imageView.setScaleType(ImageView.ScaleType.MATRIX);
//            Intent intent = new Intent(getApplicationContext(), SettingShareActivity.class);
//            shareView = manager.startActivity("shareView", intent).getDecorView();
//            //addContentView(shareView, params);
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
//                    bgImage.getWidth(),
//                    bgImage.getHeight(), Gravity.CENTER);
//            params.topMargin = 0;
//            params.leftMargin = 0;
//            addContentView(imageView, params);
//            Log.i(this.getClass().getName(), "leon_watching 1 imageView:" + imageView.getWidth() + "imageView: " + imageView.getHeight());
//            return true;
//        }
//
//        @Override
//        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
//            //To change body of implemented methods use File | Settings | File Templates.
//        }
//    }
//
//
//    private Bitmap captureScreen() {
//        getWindow().getDecorView().setDrawingCacheEnabled(true);
//        return getWindow().getDecorView().getDrawingCache();
//    }

}


