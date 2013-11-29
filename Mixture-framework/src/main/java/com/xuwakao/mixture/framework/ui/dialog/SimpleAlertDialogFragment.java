package com.xuwakao.mixture.framework.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import java.io.Serializable;

/**
 * Created by xujiexing on 13-10-12.
 */
public class SimpleAlertDialogFragment extends BaseAlertDialogFragment {
    protected static String ARG_MESSAGE = "message";
    protected static String ARG_TITLE = "title";
    protected static String ARG_POSITIVE_BUTTON = "positive_button";
    protected static String ARG_NEGATIVE_BUTTON = "negative_button";
    protected static String ARG_POSITIVE_LISTENER = "positive_listener";
    protected static String ARG_NEGATIVE_LISTENER = "negative_listener";
    protected static String ARG_TITLE_ICON = "title_icon";
    protected static String ARG_CLOSE_ICON = "close_icon";
    protected static String ARG_CLOSE_LISTENER = "close_listener";

    public static SimpleAlertDialogBuilder createBuilder(Context context, FragmentManager fragmentManager) {
        return new SimpleAlertDialogBuilder(context, fragmentManager, SimpleAlertDialogFragment.class);
    }

    @Override
    protected ViewBuilder build(ViewBuilder builder) {
        builder.setTitle(getArguments().getString(ARG_TITLE));
        builder.setMessage(getArguments().getString(ARG_MESSAGE));

        builder.setNegativeButton(getArguments().getString(ARG_NEGATIVE_BUTTON), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseClickListener listener = (BaseClickListener)getArguments().getSerializable(ARG_NEGATIVE_LISTENER);
                if(listener != null){
                    listener.setDialogFragment(SimpleAlertDialogFragment.this);
                    listener.onClick(v);
                }
            }
        });

        builder.setPositiveButton(getArguments().getString(ARG_POSITIVE_BUTTON), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseClickListener listener = (BaseClickListener)getArguments().getSerializable(ARG_POSITIVE_LISTENER);
                if(listener != null){
                    listener.setDialogFragment(SimpleAlertDialogFragment.this);
                    listener.onClick(v);
                }
            }
        });

        builder.setTitleCloseIcon(getArguments().getInt(ARG_CLOSE_ICON), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseClickListener listener = (BaseClickListener)getArguments().getSerializable(ARG_CLOSE_LISTENER);
                if(listener != null){
                    listener.setDialogFragment(SimpleAlertDialogFragment.this);
                    listener.onClick(v);
                }
            }
        });

        return builder;
    }

    public static abstract class BaseClickListener implements Serializable, View.OnClickListener {
        private DialogFragment dialogFragment;

        public DialogFragment getDialogFragment() {
            return dialogFragment;
        }

        public void setDialogFragment(DialogFragment dialogFragment) {
            this.dialogFragment = dialogFragment;
        }
    }

    public static class SimpleAlertDialogBuilder extends BaseAlertDialogBuilder<SimpleAlertDialogBuilder> {

        private String mTitleString;
        private int mTitleIcon;
        private String mMessageString;
        private String mPositiveTitle;
        private String mNegativeTitle;
        private int mTitleCloseIcon;
        private BaseClickListener mPositiveListener;
        private BaseClickListener mNegativeListener;
        private BaseClickListener mCloseListener;

        public SimpleAlertDialogBuilder(Context context, FragmentManager fragmentManager, Class<? extends BaseAlertDialogFragment> clazz) {
            super(context, fragmentManager, clazz);
        }

        @Override
        protected SimpleAlertDialogBuilder self() {
            return this;
        }

        @Override
        protected Bundle prepareArguments() {
            Bundle bundle = new Bundle();
            bundle.putString(ARG_TITLE, mTitleString);
            bundle.putString(ARG_MESSAGE, mMessageString);
            bundle.putString(ARG_NEGATIVE_BUTTON, mNegativeTitle);
            bundle.putString(ARG_POSITIVE_BUTTON, mPositiveTitle);
            bundle.putInt(ARG_TITLE_ICON, mTitleIcon);
            bundle.putInt(ARG_CLOSE_ICON, mTitleCloseIcon);
            bundle.putSerializable(ARG_CLOSE_LISTENER, mCloseListener);
            bundle.putSerializable(ARG_POSITIVE_LISTENER, mPositiveListener);
            bundle.putSerializable(ARG_NEGATIVE_LISTENER, mNegativeListener);
            return bundle;
        }

        public SimpleAlertDialogBuilder setTitle(String title) {
            mTitleString = title;
            return this;
        }

        public SimpleAlertDialogBuilder setTitlte(int resId) {
            return setTitle(mResources.getString(resId));
        }

        public SimpleAlertDialogBuilder setTitlteIcon(int resId) {
            mTitleIcon = resId;
            return this;
        }

        public SimpleAlertDialogBuilder setTitlteCloseIcon(int resId, BaseClickListener listener) {
            mTitleCloseIcon = resId;
            mCloseListener = listener;
            return this;
        }

        public SimpleAlertDialogBuilder setMessage(String message) {
            mMessageString = message;
            return this;
        }

        public SimpleAlertDialogBuilder setMessage(int resId) {
            mMessageString = mResources.getString(resId);
            return this;
        }

        public SimpleAlertDialogBuilder setPositiveButton(String positiveTitle, BaseClickListener listener) {
            mPositiveTitle = positiveTitle;
            mPositiveListener = listener;
            return this;
        }

        public SimpleAlertDialogBuilder setPositiveButton(int resId, BaseClickListener listener) {
            mPositiveTitle = mResources.getString(resId);
            mPositiveListener = listener;
            return this;
        }

        public SimpleAlertDialogBuilder setNegativeButton(String negativeTitle, BaseClickListener listener) {
            mNegativeTitle = negativeTitle;
            mNegativeListener = listener;
            return this;
        }

        public SimpleAlertDialogBuilder setNegativeButton(int resId, BaseClickListener listener) {
            mNegativeTitle = mResources.getString(resId);
            mNegativeListener = listener;
            return this;
        }
    }
}
