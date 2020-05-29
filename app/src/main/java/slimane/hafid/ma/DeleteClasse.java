package slimane.hafid.ma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;


import android.content.Intent;
import android.os.Build;

import android.view.View;

import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class DeleteClasse extends AppCompatActivity {

    private DatabaseReference classeRef;
    private String classeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_classe);
        Intent classeIntent=getIntent();

        if(classeIntent.hasExtra("classeName"))
        {
            classeName=  classeIntent.getStringExtra("classeName");
            classeRef= FirebaseDatabase.getInstance().getReference().child("Classes").child(classeName);

        } else
            Toast.makeText(this,"pas de classe dans intent",Toast.LENGTH_SHORT).show();

    }

    public void DeleteClasse(View view)
    {
        findViewById(R.id.passwordBtn_delete).setVisibility(View.INVISIBLE);
        final EditText passwordEditText=findViewById(R.id.password_verif_delete);


        classeRef.child("classe_password").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String password = Objects.requireNonNull(dataSnapshot.getValue()).toString();

                    if (password.equals(passwordEditText.getText().toString())) {
                        Toast.makeText(DeleteClasse.this, "mot de passe correct :", Toast.LENGTH_SHORT).show();


                        classeRef.setValue(null);
                        Intent DeleteIntent = new Intent(DeleteClasse.this, espace_prof1.class);
                        startActivity(DeleteIntent);
                        finish();
                        return;

                    } else {
                        findViewById(R.id.passwordBtn_delete).setVisibility(View.VISIBLE);
                        Toast.makeText(DeleteClasse.this, "le mot de passe est incorrect", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(DeleteClasse.this, "Classe Supprimer", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DeleteClasse.this, "verifier votre connexion", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
