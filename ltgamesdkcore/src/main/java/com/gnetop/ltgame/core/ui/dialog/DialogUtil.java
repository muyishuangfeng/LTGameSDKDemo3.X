package com.gnetop.ltgame.core.ui.dialog;

import android.app.Activity;
import android.os.Handler;

import com.gnetop.ltgame.core.R;

public class DialogUtil {

    private static GeneralCenterDialog mDialog;

    /**
     * 显示
     */
    public static void showDialog(Activity activity) {
        if (!activity.isFinishing()) {
            if (mDialog != null && !mDialog.isShowing()) {
                mDialog.setContent(activity.getResources().getString(R.string.text_loading));
                mDialog.setCancelable(false);
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();
            }
        }
    }

    /**
     * 隐藏
     */
    public static void dismiss(Activity activity) {
        if (!activity.isFinishing()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismissDialog(activity);
                    }
                }
            }, 2000);

        }
    }
}
