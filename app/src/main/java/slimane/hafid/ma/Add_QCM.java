package slimane.hafid.ma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_QCM extends AppCompatActivity {



    EditText rep1, rep2,rep3,rep4,Question,pass_QCM;


    Button btn,btn_cancel;
    DatabaseReference reference;
    question quest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__q_c_m);

        Question=findViewById(R.id.question);

        rep1=findViewById(R.id.rep1);
        rep2=findViewById(R.id.rep2);
        rep3=findViewById(R.id.rep3);
        rep4=findViewById(R.id.rep4);
        pass_QCM=findViewById(R.id.pass_QCM);

        btn=findViewById(R.id.btn_QCM);
        btn_cancel=findViewById(R.id.btn_QCM_cancel);

        quest=new question();
        reference= FirebaseDatabase.getInstance().getReference().child("QCM");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_Question=Question.getText().toString();
                String txt_rep1=rep1.getText().toString();
                String txt_rep2=rep2.getText().toString();
                String txt_rep3=rep3.getText().toString();
                String txt_rep4=rep4.getText().toString();
                String txt_pass_QCM=pass_QCM.getText().toString();


                quest.setRep1(txt_rep1);
                quest.setRep2(txt_rep2);
                quest.setRep3(txt_rep3);
                quest.setRep4(txt_rep4);
                quest.setPassword_QCM(txt_pass_QCM);

                reference.child(txt_Question).setValue(quest);
                Intent intent=new Intent(Add_QCM.this,espace_prof1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(Add_QCM.this,"QCM Created",Toast.LENGTH_SHORT).show();

                finish();

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Add_QCM.this,espace_prof1.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
