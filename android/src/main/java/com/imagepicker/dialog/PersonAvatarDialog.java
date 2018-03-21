package com.imagepicker.dialog;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.imagepicker.R;

/**
 * @author  gavin on 2018/03/21.
 */

public class PersonAvatarDialog extends BaseDialog {
    public PersonAvatarDialog(Context context, int theme, View view) {
        super(context, theme, view);
    }

    public void showAt() {
        show();
        mView.post(new Runnable() {
            @Override
            public void run() {
                int height = mView.getMeasuredHeight();
                setShowStyle((Activity) mContext, 1f, LinearLayout.LayoutParams.WRAP_CONTENT,
                        0,getDeviceHeight((Activity) mContext) - height / 2);
            }
        });

    }

    public static class Builder implements View.OnClickListener, AdapterView.OnItemClickListener {
        private final LayoutInflater layoutInflater;
        private final Context mContext;
        private Button mBtCancelAvatar;
        private PersonAvatarDialog avatarDialog;
        private ListView listView;
        private OnOnItemClickListener onItemClickListener;

        public Builder(Context context) {
            mContext = context;
            layoutInflater = LayoutInflater.from(context);
        }

        public void setAdapter(ArrayAdapter<String> adapter){
            listView.setAdapter(adapter);
        }

        public void setOnOnItemClickListener(OnOnItemClickListener onItemClickListener){
            this.onItemClickListener=onItemClickListener;
        }
        public void setCancelText(String cancelText){
            if (TextUtils.isEmpty(cancelText)){
                mBtCancelAvatar.setText("取消");
            }else {
                mBtCancelAvatar.setText(cancelText);
            }
        }

        public PersonAvatarDialog build() {
            View dialogView = layoutInflater.inflate(R.layout.dialog_person_avatar, null);
            listView= (ListView) dialogView.findViewById(R.id.lv_content_list);
            listView.setOnItemClickListener(this);
            dialogView.findViewById(R.id.rl_layout).setOnClickListener(this);
            mBtCancelAvatar = (Button) dialogView.findViewById(R.id.bt_cancel_avatar);
            mBtCancelAvatar.setOnClickListener(this);
            avatarDialog = new PersonAvatarDialog(mContext, R.style.dialog_map_option, dialogView);
            //设置出入动画
            Window window = avatarDialog.getWindow();
            window.setWindowAnimations(R.style.dialog_animation);
            return avatarDialog;
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.rl_layout) {
                close();

            } else if (i == R.id.bt_cancel_avatar) {
                onItemClickListener.onCancel();

            }

        }

        public void close() {
            if (avatarDialog != null && avatarDialog.isShowing()) {
                avatarDialog.dismiss();
            }
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            onItemClickListener.onItemClick(adapterView,view,i,l);
        }
    }

    public interface OnOnItemClickListener {
        void onItemClick(AdapterView<?> adapterView, View view, int i, long l);
        void onCancel();
    }

    /**
     * 获取器屏幕宽度
     *
     * @param activity
     * @return
     */
    public  int getDeviceHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager windowManager = activity.getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;

    }
}
