package slimane.hafid.ma;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class Answer extends AppCompatActivity {
    private TextView question,timer;
    int cmp1=0,cmp2=0,cmp3=0,cmp4=0;
    private Button opt1,opt2,opt3,opt4;
    String Question,Password;
    private DatabaseReference answerRef,Score_ref;
    Score_QCM score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent answerIntent = getIntent();
        Question = answerIntent.getStringExtra("Question");
        Password= answerIntent.getStringExtra("password");
        score=new Score_QCM();

        answerRef= FirebaseDatabase.getInstance().getReference().child("QCM").child(Question);
        Score_ref= FirebaseDatabase.getInstance().getReference().child("Score").child(Question);


        question=findViewById(R.id.textView2);


        timer=findViewById(R.id.textView3);
        timer.setText(String.valueOf(30));

        opt1=findViewById(R.id.btn1);
        opt2=findViewById(R.id.btn2);
        opt3=findViewById(R.id.btn3);
        opt4=findViewById(R.id.btn4);


        answerRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Log.d("hassan","je suis here");
                    String ans1= Objects.requireNonNull(dataSnapshot.child("rep1").getValue()).toString();
                    String ans2= Objects.requireNonNull(dataSnapshot.child("rep2").getValue()).toString();
                    String ans3= Objects.requireNonNull(dataSnapshot.child("rep3").getValue()).toString();
                    String ans4= Objects.requireNonNull(dataSnapshot.child("rep4").getValue()).toString();

                    question.setText(Question);
                    opt1.setText(ans1);
                    opt2.setText(ans2);
                    opt3.setText(ans3);
                    opt4.setText(ans4);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
       startTimer();
       opt1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               cmp1++;
           }
       });
        opt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmp2++;
            }
        });
        opt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmp3++;
            }
        });
        opt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cmp4++;
            }
        });
    }

    private void startTimer() {
        CountDownTimer countDownTimer=new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

                score.setCmp1(Integer.toString(cmp1));
                score.setCmp2(Integer.toString(cmp2));
                score.setCmp3(Integer.toString(cmp3));
                score.setCmp4(Integer.toString(cmp4));
                score.setPass_Qcm(Password);

                Score_ref.setValue(score);
                Intent intent=new Intent(Answer.this,espace_etud.class);
                startActivity(intent);
                finish();
            }
        };
        countDownTimer.start();
    }

}
