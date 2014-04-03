package com.G5432.WalkFun.CustomControls;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import com.G5432.WalkFun.R;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-27
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
public class CustomProgressDialog extends ProgressDialog {

    public CustomProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controls_progress_dialog);
    }
}
