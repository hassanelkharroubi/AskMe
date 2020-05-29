package slimane.hafid.ma;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class liste_scores extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_scores=new ArrayList<>();
    private DatabaseReference ScoreRef,Ref,RefDel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_scores);
        ScoreRef= FirebaseDatabase.getInstance().getReference().child("Score");
        InitializeFields();
        RetrieveAndDisplayScores();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentScoreName=parent.getItemAtPosition(position).toString();
                Intent ScoresIntent=new Intent(liste_scores.this,Scores_password.class);
                ScoresIntent.putExtra("Question",currentScoreName) ;
                startActivity(ScoresIntent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String currentScoreName=parent.getItemAtPosition(position).toString();
                AlertDialog.Builder builder =new AlertDialog.Builder(liste_scores.this);
                builder.setTitle("Entrer mot de passe :");
                final EditText password=new EditText(liste_scores.this);
                builder.setView(password);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String txt_password=password.getText().toString();
                        if(TextUtils.isEmpty(txt_password)){
                            Toast.makeText(liste_scores.this,"Entrer mot de passe",Toast.LENGTH_SHORT).show();
                        }else{
                            RefDel= FirebaseDatabase.getInstance().getReference().child("QCM").child(currentScoreName);

                            Ref= FirebaseDatabase.getInstance().getReference().child("Score").child(currentScoreName);
                            RefDel.child("password_QCM").addValueEventListener(new ValueEventListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {

                                        Log.d("hassan",dataSnapshot.getValue().toString());
                                        String pass = Objects.requireNonNull(dataSnapshot.getValue()).toString();
                                        if (pass.equals(txt_password)) {
                                            RefDel.removeValue();
                                            Toast.makeText(liste_scores.this,"Question deleted",Toast.LENGTH_SHORT).show();

                                            Ref.removeValue();
                                            Toast.makeText(liste_scores.this,"Score deleted",Toast.LENGTH_SHORT).show();
                                        }else
                                            Toast.makeText(liste_scores.this,"Erreur password",Toast.LENGTH_SHORT).show();

                                    }else
                                        Toast.makeText(liste_scores.this,"Erreur datasnapshot",Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(liste_scores.this,"database erreur",Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            }

        });
    }

    private void RetrieveAndDisplayScores() {
        ScoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Set<String> set=new HashSet<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    set.add(snapshot.getKey());
                }
                list_of_scores.clear();
                list_of_scores.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitializeFields() {
        listView=findViewById(R.id.scores_listView);
        arrayAdapter= new ArrayAdapter<>(liste_scores.this, android.R.layout.simple_list_item_1, list_of_scores);
        listView.setAdapter(arrayAdapter);
    }
}
