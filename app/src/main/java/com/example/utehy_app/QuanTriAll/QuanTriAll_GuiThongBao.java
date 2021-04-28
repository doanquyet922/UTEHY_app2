package com.example.utehy_app.QuanTriAll;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utehy_app.GuiThongBao.Activity_GuiThongBao;
import com.example.utehy_app.Model.Lop;
import com.example.utehy_app.Model.ThongBao;
import com.example.utehy_app.QuanTriVien.QuanTri_Activity;
import com.example.utehy_app.R;
import com.example.utehy_app.calendar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class QuanTriAll_GuiThongBao extends AppCompatActivity {
    Toolbar toolbar;

    TextView tvMaLopGui;
    ImageView imgChonAnh,imgAnh,imgHuyAnh;
    EditText edtND;
    Button btnGui;
    Spinner spnMaLop;

    ArrayList<String> listMaLop;
    ArrayAdapter<String> adapterMaLop;

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
        setContentView(R.layout.activity_quan_tri_all__gui_thong_bao);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Init();
        Events();
    }

    private void Events() {

        spnMaLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvMaLopGui.setText(spnMaLop.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        imgChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogThemAnh();
            }

            private void DialogThemAnh() {
                final Dialog dialog = new Dialog(QuanTriAll_GuiThongBao.this);
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


        imgHuyAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAnh.setImageDrawable(null);
                isHasImage = false;
                imgAnh.setVisibility(View.GONE);
                imgHuyAnh.setVisibility(View.GONE);
            }
        });

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtND.getText().length()==0){
                    edtND.setError("Nhập nội dung thông báo");
                    edtND.requestFocus();
                    return;
                }
                TempDialog.show();
                String diaChiGui = spnMaLop.getSelectedItem().toString();

                AlertDialog.Builder al = new AlertDialog.Builder(QuanTriAll_GuiThongBao.this);
                al.setMessage("Bạn có chắc muốn gửi thông báo tới "+diaChiGui+" không ?");

                al.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(diaChiGui.equals("Tất cả")){

                        }else{
                            GuiThongBaoToiLop(diaChiGui);
                        }
                    }




                });
                al.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                al.create();
                al.show();
            }
        });
    }
    private void GuiThongBaoToiLop(String diaChiGui) {
        Calendar calendar = Calendar.getInstance();
        ThongBao tb = new ThongBao();
        tb.setMaTB("TB"+calendar.getTimeInMillis());
        tb.setMaLop(diaChiGui);
        tb.setMaSV("admin");
        tb.setNoiDung(edtND.getText().toString());
        tb.setTenSV("Quản trị viên");

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
                Toast.makeText(QuanTriAll_GuiThongBao.this,"Lưu ảnh thất bại",Toast.LENGTH_SHORT).show();
                Log.d("luuanh","thatbai");
                TempDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(QuanTriAll_GuiThongBao.this,"Lưu ảnh thành công",Toast.LENGTH_SHORT).show();
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
                        tb.setMaLop(spnMaLop.getSelectedItem().toString());
                        tb.setMaSV("admin");
                        tb.setNoiDung(edtND.getText().toString());
                        tb.setTenSV("Quản trị viên");
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

    private void UploadThongBao(ThongBao t){
        mData.child("THongBao").push().setValue(t).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(QuanTriAll_GuiThongBao.this,"Gửi thông báo thành công",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(QuanTriAll_GuiThongBao.this, Activity_QuanTriALL.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(QuanTriAll_GuiThongBao.this,"Gửi thông báo thất bại",Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void Init() {
        toolbar = findViewById(R.id.QuanTriAll_GuiTB_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gửi thông báo");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuanTriAll_GuiThongBao.this, Activity_QuanTriALL.class));
                finish();
            }
        });

        toolbar = findViewById(R.id.QuanTriAll_GuiTB_toolbar);

        tvMaLopGui = findViewById(R.id.QuanTriAll_GuiTB_tvMaLop);
        spnMaLop = findViewById(R.id.QuanTriAll_GuiTB_spnChonLop);
        imgChonAnh = findViewById(R.id.QuanTriAll_GuiTB_imgChonAnh);
        imgAnh = findViewById(R.id.QuanTriAll_GuiTB_imgAnh);
        imgHuyAnh = findViewById(R.id.QuanTriAll_GuiTB_imgHuyAnh);
        edtND = findViewById(R.id.QuanTriAll_GuiTB_edtND);
        btnGui = findViewById(R.id.QuanTriAll_GuiTB_btnGui);



        //Temp dialog
        TempDialog = new ProgressDialog(QuanTriAll_GuiThongBao.this);
        TempDialog.setTitle("Đang gửi thông báo");
        TempDialog.setCancelable(false);
        TempDialog.setProgress(i);
        TempDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        TempDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        mData = FirebaseDatabase.getInstance().getReference();


        listMaLop = new ArrayList<>();

        adapterMaLop = new ArrayAdapter<>(QuanTriAll_GuiThongBao.this, android.R.layout.simple_list_item_1,listMaLop);
        spnMaLop.setAdapter(adapterMaLop);
        GetDSMaLop();


    }

    //hàm lấy về danh sách lớp
    private void GetDSMaLop(){
        listMaLop.clear();
        
        mData.child("Lop").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    for (DataSnapshot ds : snapshot.getChildren()){
                        Lop l = ds.getValue(Lop.class);
                        listMaLop.add(l.getMaLop());
                    }
                    adapterMaLop.notifyDataSetChanged();
                }
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
                    Toast.makeText(QuanTriAll_GuiThongBao.this, "Tải ảnh lên thành công", Toast.LENGTH_SHORT).show();
                    imgAnh.setVisibility(View.VISIBLE);
                    isHasImage = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAnh.setImageBitmap(bitmap);
                imgAnh.setVisibility(View.VISIBLE);
                Toast.makeText(QuanTriAll_GuiThongBao.this, "Tải ảnh lên thành công", Toast.LENGTH_SHORT).show();
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
}