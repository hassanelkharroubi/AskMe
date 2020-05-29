package slimane.hafid.ma;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Create_Prof extends AppCompatActivity {


    EditText username,email,password;
    Button btn_register_prof,cancel;

    FirebaseAuth auth;
    DatabaseReference reference;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__prof);


        username=findViewById(R.id.name_prof);
        email=findViewById(R.id.email_prof);
        password=findViewById(R.id.password_prof);
        btn_register_prof=findViewById(R.id.button_add_prof);
        cancel=findViewById(R.id.button_add_prof_cancel);

        auth=FirebaseAuth.getInstance();

        btn_register_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username=username.getText().toString();
                String txt_email=email.getText().toString();
                String txt_password=password.getText().toString();
                if(TextUtils.isEmpty(txt_username)||TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)){
                    Toast.makeText(Create_Prof.this,"All faields requered",Toast.LENGTH_SHORT).show();
                }else if(txt_password.length()<6){
                    Toast.makeText(Create_Prof.this,"password at least 6 characters",Toast.LENGTH_SHORT).show();
                }else{
                    register(txt_username,txt_email,txt_password);
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Create_Prof.this,espace_prof1.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private void register(final String Username,String Email,String Password){
        auth.createUserWithEmailAndPassword(Email,Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid=firebaseUser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("ProfName",Username);
                            hashMap.put("imageURL","default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent=new Intent(Create_Prof.this,espace_prof1.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        Toast.makeText(Create_Prof.this,"Professeur Create",Toast.LENGTH_SHORT).show();

                                        finish();
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(Create_Prof.this,"Add Prof Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
