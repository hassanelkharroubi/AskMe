package slimane.hafid.ma;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Password_QCM extends AppCompatActivity {
    private DatabaseReference QCMRef;
    private String Question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password__q_c_m);
        Intent QCMIntent = getIntent();
        if (QCMIntent.hasExtra("Question")) {
            Question = QCMIntent.getStringExtra("Question");
            QCMRef = FirebaseDatabase.getInstance().getReference().child("QCM").child(Question);

        } else
            Toast.makeText(this, "pas de classe dans intent", Toast.LENGTH_SHORT).show();

    }

    public void startQCM(View view) {
        findViewById(R.id.passwordBtnQCM).setVisibility(View.INVISIBLE);
        final EditText passwordEditText = findViewById(R.id.password_QCM_verif);


        QCMRef.child("password_QCM").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String password = Objects.requireNonNull(dataSnapshot.getValue()).toString();

                    if (password.equals(passwordEditText.getText().toString())) {
                        Toast.makeText(Password_QCM.this, "mot de passe correct :", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Password_QCM.this, Answer.class);
                        intent.putExtra("Question", Question);
                        intent.putExtra("password", password);

                        startActivity(intent);
                        finish();

                    } else {
                        findViewById(R.id.passwordBtnQCM).setVisibility(View.VISIBLE);
                        Toast.makeText(Password_QCM.this, "le mot de passe est incorrect", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Password_QCM.this, "cette question n'existe pas", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Password_QCM.this, "verifier votre connexion", Toast.LENGTH_SHORT).show();

            }
        });


    }
}

