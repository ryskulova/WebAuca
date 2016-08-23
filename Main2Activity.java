package com.example.satanat.webviewauca;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {

    private final int SELECT_FILE = 1;
    Uri selectedImageUri;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final EditText editText = (EditText) findViewById(R.id.editText);
        Button btn = (Button) findViewById(R.id.button);
        Button btnImage = (Button) findViewById(R.id.button1);


        Intent incoming = getIntent();
      try {
          if (incoming.getAction().equals(Intent.ACTION_SEND) && incoming.getType().startsWith("image/*")) {
              selectedImageUri = incoming.getParcelableExtra(Intent.EXTRA_STREAM);

              Intent intent = new Intent(Intent.ACTION_SEND);
              intent.setType("message/rfc822");
              intent.putExtra(Intent.EXTRA_SUBJECT, "My Subject");
              intent.putExtra(Intent.EXTRA_TEXT, "A perfect picture for article");
              intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mymail@gmail.com"});
              intent.putExtra(Intent.EXTRA_STREAM, selectedImageUri);


              startActivity(intent);
              this.finish();
              System.exit(0);
          }
      }
          catch(Exception e)
          {
               e.printStackTrace();

          }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = editText.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/*");
                intent.putExtra(Intent.EXTRA_TEXT, msg);
                intent.putExtra(Intent.EXTRA_SUBJECT, "My Subject");

                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"mymail@gmail.com"});

                if(selectedImageUri != null)
                    intent.putExtra(Intent.EXTRA_STREAM, selectedImageUri);
                startActivity(intent);
            }
        });

      btnImage.setOnClickListener(new View.OnClickListener()
      {

          @Override
          public void onClick(View v) {
              Intent intent = new Intent(Intent.ACTION_PICK,
                      MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
              intent.setType("image/*");
              startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);

          }
      } );

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK && requestCode == SELECT_FILE){
            super.onActivityResult(requestCode, resultCode, data);
            selectedImageUri = data.getData();

        }
    }
       
    }

