package slimane.hafid.ma;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Password_verification extends AppCompatActivity {

    private static final String TAG ="Password" ;
    private DatabaseReference classeRef;
    private String classeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_verification);
        Log.d(TAG,"hassan el kharroubi");
        Intent classeIntent=getIntent();

        if(classeIntent.hasExtra("classeName"))
        {
            Log.d(TAG,"hassan el kharroubi");
            classeName=  classeIntent.getStringExtra("classeName");
            classeRef= FirebaseDatabase.getInstance().getReference().child("Classes").child(classeName);

        } else
            Toast.makeText(this,"pas de classe dans intent",Toast.LENGTH_SHORT).show();

        }

    public void startChat(View view)
    {
        findViewById(R.id.passwordBtn).setVisibility(View.INVISIBLE);
        final EditText passwordEditText=findViewById(R.id.password_verif);


            classeRef.child("classe_password").addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String password = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                            Log.d(TAG,password);

                        if (password.equals(passwordEditText.getText().toString())) {
                            Toast.makeText(Password_verification.this, "mot de passe correct :", Toast.LENGTH_SHORT).show();

                            Intent classeChatIntent = new Intent(Password_verification.this, classeChatActivity.class);
                            classeChatIntent.putExtra("classeName", classeName);
                            startActivity(classeChatIntent);
                            finish();

                        } else {
                            findViewById(R.id.passwordBtn).setVisibility(View.VISIBLE);
                            Toast.makeText(Password_verification.this, "le mot de passe est incorrect", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Password_verification.this, "cette classe n'existe pas", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Password_verification.this, "verifier votre connexion", Toast.LENGTH_SHORT).show();

                }
            });


        }

}
