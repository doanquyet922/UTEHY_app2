package com.example.utehy_app.GuiThongBao;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utehy_app.DangNhap.Activity_DangNhap;
import com.example.utehy_app.MainActivity;
import com.example.utehy_app.ManHinhChinh.ManHinhChinhActivity;
import com.example.utehy_app.Model.SinhVien;
import com.example.utehy_app.Model.TaiKhoan;
import com.example.utehy_app.Model.ThongBao;
import com.example.utehy_app.QuanTriVien.QuanTri_Activity;
import com.example.utehy_app.R;
import com.example.utehy_app.ThongBao.TatCaThongBao_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Activity_GuiThongBao extends AppCompatActivity {
    Toolbar toolbar;
    TextView tvNguoiGui,tvToiLop;
    ImageView imgThemAnh,imgAnh,imgHuyAnh;
    Button btnGui;
    EditText edtND;

    SinhVien sv_ht;
    ProgressDialog TempDialog;
    DatabaseReference mData;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    boolean isHasImage = false;

    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 321;

    String linkAnh ="";
    int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__gui_thong_bao);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();



        SetData();

        Events();
    }

    private void Events() {

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TempDialog.show();
                if(edtND.getText().length()==0){
                    edtND.setError("Nhập nội dung cho thông báo");
                    edtND.requestFocus();
                    TempDialog.dismiss();
                    return;
                }else{
                    Calendar calendar = Calendar.getInstance();
                    ThongBao tb = new ThongBao();
                    tb.setMaTB("TB"+calendar.getTimeInMillis());
                    tb.setMaLop(sv_ht.getMaLop());
                    tb.setMaSV(sv_ht.getMaSV());
                    tb.setNoiDung(edtND.getText().toString());
                    tb.setTenSV(sv_ht.getHoTen());

                    //lay ra ngay hien tai

                    SimpleDateFormat sdf_gio = new SimpleDateFormat("HH:mm", Locale.getDefault());
                    String gio_ht = sdf_gio.format(new Date());

                    SimpleDateFormat sdf_ngay = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    String ngay_ht = sdf_ngay.format(new Date());

                    tb.setNgayGui("lúc "+gio_ht+" ngày "+ngay_ht);

                    //nếu thông báo có chứa ảnh thì lưu vào storage
                    if(isHasImage==true){
                        UpLoadImageToStorage();
                    }else{
                        tb.setLinkAnh("");
                        UploadThongBao(tb);
                    }


                }
            }
        });

        imgHuyAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAnh.setImageDrawable(null);
                isHasImage = false;
                imgAnh.setVisibility(View.GONE);
                imgHuyAnh.setVisibility(View.GONE);
            }
        });
        imgThemAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemAnh();
            }

            private void DialogThemAnh() {
                final Dialog dialog = new Dialog(Activity_GuiThongBao.this);
                dialog.setContentView(R.layout.dialog_setanh);

                TextView tvChonAnh = (TextView) dialog.findViewById(R.id.tvChonTuThietBi);
                TextView tvChupAnh = (TextView) dialog.findViewById(R.id.tvChupAnh);

                tvChonAnh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        choosePhoto();
                        imgHuyAnh.setVisibility(View.VISIBLE);
                        dialog.cancel();
                    }
                });

                tvChupAnh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        takePicture();
                        imgHuyAnh.setVisibility(View.VISIBLE);
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }

    private void UploadThongBao(ThongBao t){
        mData.child("THongBao").push().setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(Activity_GuiThongBao.this,"Gửi thông báo thành công",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Activity_GuiThongBao.this, QuanTri_Activity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Activity_GuiThongBao.this,"Gửi thông báo thất bại",Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void UpLoadImageToStorage(){

        final StorageReference storageRef = storage.getReferenceFromUrl("gs://utehyapp.appspot.com");

        Calendar calendar = Calendar.getInstance();

        StorageReference mountainsRef = storageRef.child("image"+calendar.getTimeInMillis()+".png");


        // Get the data from an ImageView as bytes
        imgAnh.setDrawingCacheEnabled(true);
        imgAnh.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgAnh.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(Activity_GuiThongBao.this,"Lưu ảnh thất bại",Toast.LENGTH_SHORT).show();
                Log.d("luuanh","thatbai");
                TempDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(Activity_GuiThongBao.this,"Lưu ảnh thành công",Toast.LENGTH_SHORT).show();
                Log.d("luuanh","thanhcong");
                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       String link_img = String.valueOf(uri);
                        Log.d("linkanh",linkAnh);
                        //upload
                        ThongBao tb = new ThongBao();
                        Calendar calendar = Calendar.getInstance();
                        tb.setMaTB("TB"+calendar.getTimeInMillis());
                        tb.setMaLop(sv_ht.getMaLop());
                        tb.setMaSV(sv_ht.getMaSV());
                        tb.setNoiDung(edtND.getText().toString());
                        tb.setTenSV(sv_ht.getHoTen());
                        SimpleDateFormat sdf_gio = new SimpleDateFormat("HH:mm", Locale.getDefault());
                        String gio_ht = sdf_gio.format(new Date());

                        SimpleDateFormat sdf_ngay = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String ngay_ht = sdf_ngay.format(new Date());

                        tb.setNgayGui("lúc "+gio_ht+" ngày "+ngay_ht);
                        tb.setLinkAnh(link_img);
                        UploadThongBao(tb);

                       TempDialog.dismiss();
                    }
                });
            }
        });

    }

    private void takePicture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);

    }

    private void choosePhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CHOOSE_PHOTO) {
                try {
                    Uri imageUri = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgAnh.setImageBitmap(bitmap);
                    Toast.makeText(Activity_GuiThongBao.this, "Tải ảnh lên thành công", Toast.LENGTH_SHORT).show();
                    imgAnh.setVisibility(View.VISIBLE);
                    isHasImage = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAnh.setImageBitmap(bitmap);
                imgAnh.setVisibility(View.VISIBLE);
                Toast.makeText(Activity_GuiThongBao.this, "Tải ảnh lên thành công", Toast.LENGTH_SHORT).show();
                isHasImage = true;
            }
        }
    }

    //Hàm lấy ra byte[] từ image view
    private byte[] getByteArrayFromImageView(ImageView img) {
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }



    private void SetData() {
        tvNguoiGui.setText(sv_ht.getHoTen());
        tvToiLop.setText("tới "+sv_ht.getMaLop());
    }

    private void Init() {
        toolbar = findViewById(R.id.GuiTB_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tạo thông báo");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_GuiThongBao.this,QuanTri_Activity.class));
            }
        });

        TempDialog = new ProgressDialog(Activity_GuiThongBao.this);
        TempDialog.setTitle("Đang gửi thông báo");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));



        //Ánh xạ các view
        tvNguoiGui = findViewById(R.id.GuiTB_tvTenNguoiGui);
        tvToiLop = findViewById(R.id.GuiTB_tvToiLop);

        imgThemAnh = findViewById(R.id.GuiTB_imgThemAnh);
        imgHuyAnh = findViewById(R.id.GuiTB_imgHuyAnh);
        imgAnh = findViewById(R.id.GuiTB_anh);

        edtND = findViewById(R.id.GuiTB_edtNoiDungTB);

        btnGui = findViewById(R.id.GuiTB_btnGui);


        //
        mData = FirebaseDatabase.getInstance().getReference();


        //
        sv_ht = ManHinhChinhActivity.sv_hientai;


    }


}