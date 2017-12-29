package com.daydaynote.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.daydaynote.R;
import com.daydaynote.uitls.PublicUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.daydaynote.uitls.PublicUtil.adjustPhotoRotation;
import static com.daydaynote.uitls.PublicUtil.getPath;
import static com.daydaynote.uitls.PublicUtil.isHasSdcard;
import static com.daydaynote.uitls.PublicUtil.toRoundBitmap;


public class NoteInfoActivity extends Activity implements View.OnClickListener {

    private TextView mIncludeTitle;
    /**
     * 2017年9月1日
     */
    private TextView mRecordTimeTv;
    private ImageView mNoteCameraIv;
    /**
     * 完成
     */
    private TextView mNoteCommitTv;
    PopupWindow popupWindow;
    private ImageButton mToCameraIb;
    private ImageButton mToPicIb;
    private ImageButton mToCancelIb;
    private Bitmap bm2, bm1;


    public String SDCARD_ROOT_PATH = Environment
            .getExternalStorageDirectory().getAbsolutePath();
    public String IMAGE_CAPTURE_NAME = "/cameraTmp.png";
    private String picSavedPath = "";//图片保存的位置
    private EditText mNoteInfoEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_info);
        initView();
        PublicUtil.setStatusBarBg(NoteInfoActivity.this, getResources().getColor(R.color.colorPrimary));
    }

    private void initView() {
        mIncludeTitle = (TextView) findViewById(R.id.include_title);
        mIncludeTitle.setText("备注");
        mRecordTimeTv = (TextView) findViewById(R.id.record_time_tv);
        mRecordTimeTv.setText(PublicUtil.noteDate);
        mNoteCameraIv = (ImageView) findViewById(R.id.note_camera_iv);
        mNoteCameraIv.setOnClickListener(this);
        mNoteCommitTv = (TextView) findViewById(R.id.note_commit_tv);
        mNoteCommitTv.setOnClickListener(this);

        mNoteInfoEt = (EditText) findViewById(R.id.note_info_et);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_camera_iv://相机的点击事件
                initPopuptWindow();
                break;
            case R.id.note_commit_tv://完成按钮点击事件
                Intent intent_finish = new Intent();
                String notecontent = mNoteInfoEt.getText().toString().trim();
                intent_finish.putExtra("noteContent",notecontent );
                intent_finish.putExtra("picPath",picSavedPath );
                setResult(PublicUtil.NOTEDTOCOMMIT, intent_finish);
                finish();
                break;
            case R.id.toCamera_ib:
                // 照相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (isHasSdcard()) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                            SDCARD_ROOT_PATH, IMAGE_CAPTURE_NAME)));
                }
                startActivityForResult(intent, 2);
                this.popupWindow.dismiss();
                break;
            case R.id.toPic_ib:
                // 图片收藏
                Intent intent_pic = new Intent();
        /* 开启Pictures画面Type设定为image */
                intent_pic.setType("image/*");
        /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent_pic.setAction(Intent.ACTION_GET_CONTENT);
		/* 取得相片后返回本画面 */
                startActivityForResult(intent_pic, 1);
                this.popupWindow.dismiss();
                break;
            case R.id.toCancel_ib:
                if (this.popupWindow != null) {
                    this.popupWindow.dismiss();
                }
                break;
        }
    }

    protected void initPopuptWindow() {
        View popupWindow_view = getLayoutInflater().inflate( // 获取自定义布局文件dialog.xml的视图
                R.layout.singlepos_addphotomenu, null, false);
        mToCameraIb = (ImageButton) popupWindow_view.findViewById(R.id.toCamera_ib);
        mToCameraIb.setOnClickListener(this);
        mToPicIb = (ImageButton) popupWindow_view.findViewById(R.id.toPic_ib);
        mToPicIb.setOnClickListener(this);
        mToCancelIb = (ImageButton) popupWindow_view.findViewById(R.id.toCancel_ib);
        mToCancelIb.setOnClickListener(this);
        popupWindow = new PopupWindow(popupWindow_view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);// 创建PopupWindow实例
        popupWindow.showAtLocation(findViewById(R.id.note_info_layout), Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // 图片收藏
            if (requestCode == 1) {
                Uri uri = data.getData();
                ContentResolver cr = this.getContentResolver();
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    Bitmap bitmap = BitmapFactory.decodeStream(
                            cr.openInputStream(uri), null, options);
                    if (bitmap.getWidth() > 1440 || bitmap.getHeight() > 1440) {
                        new AlertDialog.Builder(this)
                                .setMessage("您选择的图片尺寸过大，请选择分辨率在1440*900以内的图片")
                                .setPositiveButton("确定", null).show();
                        return;
                    }
                    Bitmap bm = toRoundBitmap(bitmap, this);

                    int i = readPictureDegree(getPath(NoteInfoActivity.this, uri));
                    if (i != 0) {
                        bm2 = adjustPhotoRotation(bm, i);
                    } else {
                        bm2 = bm;
                    }
                    mNoteCameraIv.setImageBitmap(bm2);
                    picSavedPath = saveCroppedImage(bm2);
                } catch (FileNotFoundException e) {
                }
            } else if (requestCode == 2) {
                Bitmap bitmap;
                int i = 0;
                try {
                    if (isHasSdcard()) {
                        String imagePath = SDCARD_ROOT_PATH
                                + IMAGE_CAPTURE_NAME;
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        bitmap = BitmapFactory.decodeFile(imagePath, options);
                        i = readPictureDegree(imagePath);
                    } else {
                        Bundle extras = data.getExtras();
                        bitmap = (Bitmap) extras.getParcelable("data");
                    }
                    Bitmap bm = toRoundBitmap(bitmap, this);
                    if (i != 0) {
                        bm1 = adjustPhotoRotation(bm, i);
                    } else {
                        bm1 = bm;
                    }
                    mNoteCameraIv.setImageBitmap(bm1);
                    picSavedPath = saveCroppedImage(bm1);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "图片获取异常", Toast.LENGTH_SHORT).show();
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private String saveCroppedImage(Bitmap bmp) {
        File file = new File("/sdcard/.myFolder");
        if (!file.exists())
            file.mkdir();
        file = new File("/sdcard/" + System.currentTimeMillis() + ".jpg".trim());
        String fileName = file.getName();
        String mName = fileName.substring(0, fileName.lastIndexOf("."));
        String sName = fileName.substring(fileName.lastIndexOf("."));

        String newFilePath = "/sdcard/.myFolder" + "/" + mName + sName;
        file = new File(newFilePath);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newFilePath;
    }
}
