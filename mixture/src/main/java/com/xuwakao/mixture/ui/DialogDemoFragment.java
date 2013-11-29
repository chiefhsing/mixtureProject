package com.xuwakao.mixture.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.xuwakao.mixture.R;
import com.xuwakao.mixture.framework.AppConfig;
import com.xuwakao.mixture.framework.http.HttpTaskRequestParam;
import com.xuwakao.mixture.framework.image.ImageCache;
import com.xuwakao.mixture.framework.image.ImageFetcher;
import com.xuwakao.mixture.framework.image.RecyclingImageView;
import com.xuwakao.mixture.framework.test.TestImage;
import com.xuwakao.mixture.framework.ui.dialog.SimpleAlertDialogFragment;
import com.xuwakao.mixture.framework.utils.MLog;

/**
 * Created by xujiexing on 13-10-10.
 */
public class DialogDemoFragment extends Fragment {
    private Button mButton;
    private RecyclingImageView imageView;

    private static final String IMAGE_CACHE_DIR = "thumbs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dialog_demo, container, false);
        mButton = (Button) rootView.findViewById(R.id.button);
        MLog.verbose("getActivity", "1.getActivity --- " + getActivity());
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleAlertDialogFragment.SimpleAlertDialogBuilder builder = SimpleAlertDialogFragment.createBuilder(getActivity(), getFragmentManager());
                builder.setTitle("干不干了？")
                        .setMessage("全是笨蛋")
                        .setPositiveButton("炒鱿", new SimpleAlertDialogFragment.BaseClickListener() {
                            @Override
                            public void onClick(View v) {
                                MLog.verbose("getActivity", "2.getActivity --- " + getActivity());
                                Toast.makeText(getDialogFragment().getActivity(), "您被炒鱿鱼了", Toast.LENGTH_SHORT).show();
                                getDialogFragment().dismiss();
                            }
                        })
                        .setNegativeButton("滚蛋", new SimpleAlertDialogFragment.BaseClickListener() {
                            @Override
                            public void onClick(View v) {
                                MLog.verbose("getActivity", "2.getActivity --- " + getActivity());
                                Toast.makeText(getDialogFragment().getActivity(), "您可以滚蛋了", Toast.LENGTH_SHORT).show();
                                getDialogFragment().dismiss();
                            }
                        })
                        .show();

            }
        });

        imageView = (RecyclingImageView) rootView.findViewById(R.id.imageView);
        int mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.grid_90);
        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams(getActivity(), IMAGE_CACHE_DIR);
        // The ImageFetcher takes care of loading images into our ImageView children asynchronously
        ImageFetcher mImageFetcher = new ImageFetcher(getActivity(), mImageThumbSize);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mImageFetcher.addImageCache(getActivity().getSupportFragmentManager(), cacheParams);
        HttpTaskRequestParam param = new HttpTaskRequestParam();
        param.url = TestImage.imageUrls[0];
        mImageFetcher.loadImage(param, imageView);
        return rootView;
    }

}
