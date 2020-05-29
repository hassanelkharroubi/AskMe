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

public class Scores_password extends AppCompatActivity {
     DatabaseReference QCMRef;
    String Question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores_password);
        Intent QCMIntent = getIntent();
        if (QCMIntent.hasExtra("Question")) {
            Question = QCMIntent.getStringExtra("Question");
            QCMRef = FirebaseDatabase.getInstance().getReference().child("QCM").child(Question);

        } else
            Toast.makeText(this, "pas de classe dans intent", Toast.LENGTH_SHORT).show();

    }
    public void VoirScore(View view) {
        findViewById(R.id.passwordBtnScore).setVisibility(View.INVISIBLE);
        final EditText passwordEditText = findViewById(R.id.password_Score_verif);


        QCMRef.child("password_QCM").addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String password = Objects.requireNonNull(dataSnapshot.getValue()).toString();

                    if (password.equals(passwordEditText.getText().toString())) {
                        Toast.makeText(Scores_password.this, "mot de passe correct :", Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(Scores_password.this, Score.class);
                            intent.putExtra("Question", Question);
                            intent.putExtra("password", password);
                           startActivity(intent);
                           finish();


                    } else {
                        findViewById(R.id.passwordBtnScore).setVisibility(View.VISIBLE);
                        Toast.makeText(Scores_password.this, "le mot de passe est incorrect", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Scores_password.this, "cette question n'existe pas", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Scores_password.this, "verifier votre connexion", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
