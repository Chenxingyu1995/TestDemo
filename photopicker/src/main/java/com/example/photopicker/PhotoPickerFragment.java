package com.example.photopicker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;

public class PhotoPickerFragment extends Fragment implements PhotoAdapter.onItemClickListener, View.OnClickListener {

    private boolean isShowGif = true;
    private int maxCount;
    private boolean showPreView;
    private RecyclerView recyclerView;
    private static final int cameraRequestCode = 0x0000021;
    private PhotoAdapter adapter;
    /**
     * 相片存储F Uri
     **/
    private Uri photoUri;

    /**
     * 相片存储File
     **/
    private File cameraFile;

    private File cropFile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        maxCount = bundle.getInt("maxCount", 1);
        isShowGif = bundle.getBoolean("showGif", false);
        showPreView = bundle.getBoolean("showPreView", false);
        adapter = new PhotoAdapter(this);
        adapter.setOnItemClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        view.findViewById(R.id.commit).setOnClickListener(this);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setItemAnimator(null);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false));
        onLoaderImage();
    }

    /**
     * 本地图片
     **/
    private void onLoaderImage() {
        File absoluteFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "PhotoPicker");
        if (absoluteFile.isDirectory()) {
            fileIsDirectory(absoluteFile);
        }

        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor == null) return;
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            //获取图片的名称
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));

            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE)) / 1024;

            if (size < 50) {
                continue;
            }

            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            //获取图片的详细信息

            Image image = new Image();
            image.setPath(path);
            adapter.getList().add(image);

        }
        cursor.close();
        recyclerView.setAdapter(adapter);
    }


    private void fileIsDirectory(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            for (File s : listFiles) {
                if (!file.isDirectory()) {
                    String path = s.getAbsolutePath();
                    Image image = new Image();
                    image.setPath(path);
                    adapter.getList().add(image);
                } else {
                    fileIsDirectory(s);
                }
            }
        } else {
            String path = file.getAbsolutePath();
            Image image = new Image();
            image.setPath(path);
            adapter.getList().add(image);
        }
    }


    @Override
    public void onStartCamera() {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraFile = PickerUtil.getCameraFile();
        photoUri = PickerUtil.getCameraUri(getActivity(), cameraFile);
        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(takeIntent, cameraRequestCode);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onFragmentActivity ", "requestCode" + requestCode + "resultCode" + resultCode);
        if (requestCode == cameraRequestCode && resultCode == Activity.RESULT_OK) {
            if (photoUri != null && cameraFile != null) {
                String path = cameraFile.getAbsolutePath();
                Image image = new Image();
                image.setSelect(false);
                image.setPath(path);
                adapter.getList().add(1, image);
                adapter.notifyItemInserted(1);
            }
        }

        if (requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if (!cropFile.exists()) return;
            String path = cropFile.getAbsolutePath();
            Image image = new Image();
            image.setSelect(false);
            image.setPath(path);
            adapter.getList().add(1, image);
            adapter.notifyItemInserted(1);
            Intent intent = new Intent();
            ArrayList<String> list = new ArrayList<>();
            list.add(path);
            intent.putExtra("path", list);
            getActivity().setResult(99, intent);
            getActivity().finish();
        }


    }

    @Override
    public void onItemClickListener(Image url, int pos) {
        adapter.select(pos);
    }


    public ArrayList<String> getSelectImage() {
        return adapter.getSelectList();
    }

    @Override
    public void onClick(View v) {
        if (maxCount > 1) {
            Intent intent = new Intent();
            ArrayList<String> list = adapter.getSelectList();
            intent.putExtra("path", list);
            getActivity().setResult(99, intent);
            getActivity().finish();
        } else {
            ArrayList<String> image = getSelectImage();
            Uri uri = Uri.fromFile(new File(image.get(0)));
            cropFile = PickerUtil.getCropFile();
            Uri cameraUri = PickerUtil.getCropUri(getActivity(), cropFile);
            PickerUtil.startUCrop(uri, cameraUri, getActivity());
        }
    }
}
