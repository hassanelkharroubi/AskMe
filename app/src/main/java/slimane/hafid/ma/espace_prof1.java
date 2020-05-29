package slimane.hafid.ma;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class espace_prof1 extends AppCompatActivity {
    DatabaseReference classeRef;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_classes=new ArrayList<>();

FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_prof1);



        classeRef= FirebaseDatabase.getInstance().getReference().child("Classes");
        InitializeFields();
        RetrieveAndDisplayClasses();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String currentClassName=parent.getItemAtPosition(position).toString();
                Intent DeleteclasseIntent=new Intent(espace_prof1.this,DeleteClasse.class);
                DeleteclasseIntent.putExtra("classeName",currentClassName) ;
                startActivity(DeleteclasseIntent);
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String currentClassName=parent.getItemAtPosition(position).toString();
               // Intent classeChatIntent=new Intent(espace_prof1.this,classeChatActivity.class);
                Intent classeChatIntent=new Intent(espace_prof1.this,Password_verification.class);

            classeChatIntent.putExtra("classeName",currentClassName) ;
            startActivity(classeChatIntent);

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(espace_prof1.this, espace_prof.class));
            finish();
            return true;
        }
        if (item.getItemId() == R.id.Create_Classe) {

            startActivity(new Intent(espace_prof1.this,Create_Classe.class));
            finish();
        }
        if (item.getItemId() == R.id.Create_Professeur) {

            startActivity(new Intent(espace_prof1.this,Create_Prof.class));
            finish();
        }
        if (item.getItemId() == R.id.Delete_Professeur) {

            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(espace_prof1.this,"Account Deleted",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(espace_prof1.this,espace_prof.class));
                        finish();

                    }else{
                        Toast.makeText(espace_prof1.this,"Erreur to delete",Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
        if (item.getItemId() == R.id.Add_QCM) {

            startActivity(new Intent(espace_prof1.this,Add_QCM.class));
            finish();
        }
        if (item.getItemId() == R.id.liste_scores) {

            startActivity(new Intent(espace_prof1.this,liste_scores.class));
            finish();
        }
        return false;
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
        listView=findViewById(R.id.listview);
        arrayAdapter= new ArrayAdapter<>(espace_prof1.this, android.R.layout.simple_list_item_1, list_of_classes);
        listView.setAdapter(arrayAdapter);
    }
}
