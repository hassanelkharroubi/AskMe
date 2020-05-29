package slimane.hafid.ma;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Objects;

public class Score extends AppCompatActivity {
TextView rep1,rep2,rep3,rep4;
String Question;
    DatabaseReference reference,QCMRef;
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent scoreIntent = getIntent();
        Question = scoreIntent.getStringExtra("Question");

        rep1 = findViewById(R.id.textView2);
        rep2 = findViewById(R.id.textView3);
        rep3 = findViewById(R.id.textView4);
        rep4 = findViewById(R.id.textView5);
        btn = findViewById(R.id.btn_end);

        QCMRef= FirebaseDatabase.getInstance().getReference().child("QCM").child(Question);

        reference = FirebaseDatabase.getInstance().getReference().child("Score").child(Question);
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                {

                    String cmp1 = Objects.requireNonNull(dataSnapshot.child("cmp1").getValue()).toString();
                    String cmp2 = Objects.requireNonNull(dataSnapshot.child("cmp2").getValue()).toString();
                    String cmp3 = Objects.requireNonNull(dataSnapshot.child("cmp3").getValue()).toString();
                    String cmp4 = Objects.requireNonNull(dataSnapshot.child("cmp4").getValue()).toString();


                    rep1.setText(cmp1);
                    rep2.setText(cmp2);
                    rep3.setText(cmp3);
                    rep4.setText(cmp4);
                }

;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Score.this,liste_scores.class);
                startActivity(intent);
                finish();

            }
        });


    }
}
