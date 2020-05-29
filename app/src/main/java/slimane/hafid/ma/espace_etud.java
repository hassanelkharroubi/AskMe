package slimane.hafid.ma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

import java.util.Set;

public class espace_etud extends AppCompatActivity {
    private ListView listView,listView1;
    private ArrayAdapter<String> arrayAdapter,arrayAdapter1;
    private ArrayList<String> list_of_classes=new ArrayList<>();
    private ArrayList<String> list_of_questions=new ArrayList<>();
    private DatabaseReference classeRef,QCM_ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_etud);

        classeRef= FirebaseDatabase.getInstance().getReference().child("Classes");
        QCM_ref=FirebaseDatabase.getInstance().getReference().child("QCM");

        InitializeFields();
        RetrieveAndDisplayClasses();
        RetrieveAndDisplayQuestions();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentClassName=parent.getItemAtPosition(position).toString();
                Intent classeChatIntent=new Intent(espace_etud.this,Password_verification.class);
                classeChatIntent.putExtra("classeName",currentClassName) ;
                startActivity(classeChatIntent);
            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentQuestion=parent.getItemAtPosition(position).toString();
                Intent QuestiosIntent=new Intent(espace_etud.this,Password_QCM.class);
                QuestiosIntent.putExtra("Question",currentQuestion) ;
                startActivity(QuestiosIntent);
            }
        });
    }

    private void RetrieveAndDisplayQuestions() {
        QCM_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set1=new HashSet<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    set1.add(snapshot.getKey());
                }
                list_of_questions.clear();
                list_of_questions.addAll(set1);
                arrayAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void RetrieveAndDisplayClasses() {
        classeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set=new HashSet<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    set.add(snapshot.getKey());
                }
                list_of_classes.clear();
                list_of_classes.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitializeFields() {
        listView=findViewById(R.id.list_view);
        listView1=findViewById(R.id.list_QCM_view);
        arrayAdapter= new ArrayAdapter<>(espace_etud.this, android.R.layout.simple_list_item_1, list_of_classes);
        arrayAdapter1= new ArrayAdapter<>(espace_etud.this, android.R.layout.simple_list_item_1, list_of_questions);
        listView.setAdapter(arrayAdapter);
        listView1.setAdapter(arrayAdapter1);
    }
}
