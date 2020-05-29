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


public class Create_Classe extends AppCompatActivity {
     EditText Classe_name,Classe_password;

     Button button,cancel;

     DatabaseReference reference;
     Classe classe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__classe);

        Classe_name=findViewById(R.id.classe_name);
        Classe_password=findViewById(R.id.classe_password);
        button=findViewById(R.id.button_classe_add);
        cancel=findViewById(R.id.button_classe_add_cancel);


        classe=new Classe();
        reference= FirebaseDatabase.getInstance().getReference().child("Classes");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_classe_name=Classe_name.getText().toString();
                String txt_classe_password=Classe_password.getText().toString();

                classe.setClasse_name(txt_classe_name);
                classe.setClasse_password(txt_classe_password);

                reference.child(txt_classe_name).setValue(classe);
                Intent intent=new Intent(Create_Classe.this,espace_prof1.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                Toast.makeText(Create_Classe.this,"Classe Created",Toast.LENGTH_SHORT).show();

                finish();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Create_Classe.this,espace_prof1.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
