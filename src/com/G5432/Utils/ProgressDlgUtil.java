package com.G5432.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import com.G5432.WalkFun.CustomControls.CustomProgressDialog;

/**
 * Created with IntelliJ IDEA.
 * User: p
 * Date: 14-3-27
 * Time: 下午3:19
 * To change this template use File | Settings | File Templates.
 */
public class ProgressDlgUtil {

    static ProgressDialog progressDlg = null;

    public static void showProgressDlg(Context ctx) {
        if (null == progressDlg) {
            progressDlg = new CustomProgressDialog(ctx);
            progressDlg.setCancelable(false);
        }
        progressDlg.show();
    }

    public static void stopProgressDlg() {
        if (null != progressDlg) {
            progressDlg.dismiss();
            progressDlg = null;
        }
    }
}
