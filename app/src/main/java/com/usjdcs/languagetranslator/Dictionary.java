package com.usjdcs.languagetranslator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dictionary extends AppCompatActivity {

    EditText editTxtSearch;
    Button search_button;
    TextView txtViewResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        editTxtSearch = (EditText) findViewById(R.id.editTxtSearch);
        search_button = (Button) findViewById(R.id.search_button);
        txtViewResult = (TextView) findViewById(R.id.txtViewResult);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTxtSearch.getText().toString()))
                {
                    Toast.makeText(Dictionary.this, "No empty keyword allowed", Toast.LENGTH_SHORT).show();
                }
                else {
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("meaning");
                    mRef.addValueEventListener(new ValueEventListener() {


                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String searchKeyword = editTxtSearch.getText().toString();
                            if (dataSnapshot.child(searchKeyword).exists())
                            {

                                txtViewResult.setText(dataSnapshot.child(searchKeyword).getValue().toString());

                            }
                            else {
                                Toast.makeText(Dictionary.this, "No search result found", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

    }
}