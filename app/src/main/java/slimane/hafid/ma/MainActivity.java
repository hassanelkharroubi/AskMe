package slimane.hafid.ma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
     Button button1,button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Espace_etud();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Espace_prof();
            }
        });
    }
    public void Espace_etud(){
        Intent intent=new Intent(this,espace_etud.class);
        startActivity(intent);
    }
    public void Espace_prof(){
        Intent intent=new Intent(this,espace_prof.class);
        startActivity(intent);
    }
}
