package com.ravijeet.teleportal.tvShowDetail;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ravijeet.teleportal.R;
import com.ravijeet.teleportal.bean.Image;
import com.ravijeet.teleportal.util.Constant;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static com.ravijeet.teleportal.util.Constant.IMAGES_EXTRA;
import static com.ravijeet.teleportal.util.Constant.POSITION_EXTRA;
import static com.ravijeet.teleportal.util.Constant.TV_SHOW_NAME_EXTRA;


/**
 * Created by Ravijeet on 3/2/18.
 */

public class TvShowImagesGalleryFragment extends Fragment {

    private ArrayList<Image> imageGalleryList;
    private int imagePosition;
    private ViewPager galleryPager;
    private ImageGalleryPagerAdapter galleryPagerAdapter;
    private String tvShowName;
    private ImageView downloadButton;
    private final int STORAGE_PERMISSION_REQUEST_CODE = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_gallery, container, false);

        imageGalleryList = getArguments().getParcelableArrayList(IMAGES_EXTRA);
        imagePosition = getArguments().getInt(POSITION_EXTRA);
        tvShowName = getArguments().getString(TV_SHOW_NAME_EXTRA);

        galleryPager = view.findViewById(R.id.gallery_pager);
        galleryPagerAdapter = new ImageGalleryPagerAdapter();
        galleryPager.setAdapter(galleryPagerAdapter);
        galleryPager.setCurrentItem(imagePosition);

        downloadButton = view.findViewById(R.id.download_button);
        downloadButton.setEnabled(true);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {

                    requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            STORAGE_PERMISSION_REQUEST_CODE);


                } else {
                    downloadImage();
                }
            }
        });

        return view;
    }

    private void downloadImage() {
        int pagerPosition = galleryPager.getCurrentItem();
        Image imageItem = imageGalleryList.get(pagerPosition);
        String imageUrl = Constant.BANNER_IMAGE_URL + Constant.CARD_IMAGE_SIZE + imageItem.getImagePath();
        new DownloadFileFromUrl(getActivity()).execute(new String[]{imageUrl});
        downloadButton.setEnabled(false);
    }


    class ImageGalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageGalleryList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(container.getContext());
            View layout = layoutInflater.inflate(R.layout.gallery_pager_item, container, false);

            ImageView image = layout.findViewById(R.id.gallery_pager_item_image_view);

            Image imageItem = imageGalleryList.get(position);

            Picasso.with(TvShowImagesGalleryFragment.this.getActivity())
                    .load(Constant.BANNER_IMAGE_URL + Constant.BACKGROUND_IMAGE_SIZE + imageItem.getImagePath())
                    .noFade()
                    .placeholder(R.drawable.gallery_image_placeholder)
                    .into(image);
            container.addView(layout);

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }

    private class DownloadFileFromUrl extends AsyncTask<String, String, String> {

        private final String TAG = DownloadFileFromUrl.class.getSimpleName();

        Context context;
        private URL imageUrl;

        public DownloadFileFromUrl(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {

            int count;
            File file = null;
            try {

                imageUrl = new URL(params[0]);

                URLConnection conection = imageUrl.openConnection();
                conection.connect();

                InputStream input = new BufferedInputStream(imageUrl.openStream(), 8192);

                File appDirectory = new File(Environment.getExternalStorageDirectory() + "/"
                        + context.getPackageName() + "/tvShow/");
                if (!appDirectory.exists()) {
                    appDirectory.mkdirs();
                }
                Log.e(TAG, appDirectory.toString());

                File imagesDir = new File(appDirectory + "/" + tvShowName + "/");
                if (!imagesDir.exists()) {
                    imagesDir.mkdir();
                }
                Log.e(TAG, imagesDir.toString());

                file = new File(imagesDir + "/" + System.currentTimeMillis() + ".jpg");
                Log.e(TAG, file.toString());

                OutputStream output = new FileOutputStream(file);
                byte data[] = new byte[1024];
                while ((count = input.read(data)) != -1) {
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return file != null ? file.toString() : null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            downloadButton.setEnabled(true);

            Toast.makeText(context, context.getString(R.string.image_downloaded), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   downloadImage();
                } else {
                    Toast.makeText(getActivity(), getActivity().getString(R.string.cant_download_image),
                            Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }
}
