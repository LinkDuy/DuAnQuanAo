package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.loginfirebase.databinding.ActivityDangkyBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DangkyActivity extends AppCompatActivity {
    private DangkyActivity binding;
    Boolean check=true;
    Boolean checknut1=true, checknut2=true;
    Boolean ck1 = false, ck2 = false, ck3 = false;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DangkyActivity.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressBar = binding.progressBar;

        binding.SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
        binding.btnSigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangkyActivity.this, LoginMainActivity.class);
                startActivity(intent);
            }
        });
        binding.btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checknut2){
                    binding.edtPassWd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    binding.edtPassWd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                checknut2=!checknut2;
            }
        });
        binding.btnConfim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checknut1){
                    binding.edtConFirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    binding.edtConFirm.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                checknut1=!checknut1;
            }
        });
    }
    public void signup(){
        progressBar.setVisibility(ProgressBar.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("name",binding.edtName.getText().toString());
        user.put("acc",binding.edtUser.getText().toString());
        user.put("pass",binding.edtPassWd.getText().toString());
        db.collection("user")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        progressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(DangkyActivity.this,"Thành công",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(ProgressBar.GONE);
                        Toast.makeText(DangkyActivity.this,"Thất bại",Toast.LENGTH_SHORT).show();

                    }
                });
    }
    public void Check(){
        if (isEmpty(binding.edtUser)) {
            binding.edtUser.setError("Enter the UserName");
        }
        if (isEmpty(binding.edtPassWd)) {
            binding.edtPassWd.setError("Enter the Password!");
        }
        if (isEmpty(binding.edtConFirm)) {
            binding.edtConFirm.setError("Enter the Confirm Password!");
        }
        if (isEmpty(binding.edtName)) {
            binding.edtName.setError("Enter the Name!");
        }
        if(isEmpty(binding.edtUser)==false&&isEmpty(binding.edtPassWd)==false&&isEmpty(binding.edtConFirm)==false&&isEmpty(binding.edtName)==false){
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            db.collection("user")
//                    .whereEqualTo("acc",binding.edtUser.getText().toString())
//                    .get().addOnCompleteListener(task -> {
//                        if(task.isSuccessful()&&task.getResult()!=null&&task.getResult().getDocuments().size()>0){
//                            Toast.makeText(this,"Da ton tai!",Toast.LENGTH_SHORT).show();
//                        }else{
//                            signup();
//                        }
//                    });{
//
//            }
        }
        // Create a new user with a first and last name

    }
    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
    boolean isPass(EditText text1, EditText text2){
        if(text1.getText().toString().equals(text2.getText().toString())){
            return true;
        }else{
            return false;
        }
    }
    boolean isEmail(EditText text) {
        CharSequence email = text.getText().toString();
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

}