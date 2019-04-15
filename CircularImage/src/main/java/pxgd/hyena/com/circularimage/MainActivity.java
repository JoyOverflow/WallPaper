package pxgd.hyena.com.circularimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        CircularImageButton colorChanger = findViewById(R.id.circleButton);
        colorChanger.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        MainActivity.this,
                        "Clicked" ,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        */


        VImageButtonView imageButtonView = findViewById(R.id.pic1);
        imageButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        MainActivity.this,
                        "Click",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

    }
}
