package slimane.hafid.ma;


import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class classeChatActivity extends AppCompatActivity {


    private ImageButton sendMessageBuutton;
    private EditText userMessageInput;
    private ScrollView scrollView;
    private TextView displayTextMessages;
     FirebaseAuth auth;
    DatabaseReference classeNameReference,classeMessageKeyRef;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classe_chat);

        String currentClasseName = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("classeName")).toString();
        Toast.makeText(classeChatActivity.this, currentClasseName,Toast.LENGTH_SHORT).show();

        auth=FirebaseAuth.getInstance();
        classeNameReference=FirebaseDatabase.getInstance().getReference().child("Classes").child(currentClasseName);


        InitializeFields();



        sendMessageBuutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedMessageInfotoDatabase();
                userMessageInput.setText("");
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        classeNameReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void DisplayMessages(DataSnapshot dataSnapshot) {
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            String chatMessage = (String) snapshot.getValue();


            displayTextMessages.append(chatMessage+ "\n\n\n");
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }

    private void savedMessageInfotoDatabase()
    {
        String message=userMessageInput.getText().toString();
        String messageKey=classeNameReference.push().getKey();

        if(TextUtils.isEmpty(message)){
            Toast.makeText(this,"please write message",Toast.LENGTH_SHORT).show();
        }else{
            HashMap<String,Object> classeMessageKey=new HashMap<>();
            classeNameReference.updateChildren(classeMessageKey);

            assert messageKey != null;
            classeMessageKeyRef=classeNameReference.child(messageKey);

            HashMap<String,Object> messageInfoMap=new HashMap<>();
            messageInfoMap.put("message",message);

            classeMessageKeyRef.updateChildren(messageInfoMap);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void InitializeFields() {

        sendMessageBuutton=findViewById(R.id.send_message_button);
        userMessageInput=findViewById(R.id.input_classe_message);
        displayTextMessages=findViewById(R.id.classe_chat_text_display);
        scrollView=findViewById(R.id.my_scroll_view);

    }
}
