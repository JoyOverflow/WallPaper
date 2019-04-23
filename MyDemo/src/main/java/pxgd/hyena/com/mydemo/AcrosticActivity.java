package pxgd.hyena.com.mydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AcrosticActivity extends AppCompatActivity implements View.OnClickListener{

    private Button createBtn;


    //文本编辑框以及内容
    private EditText wordsEdt;
    private String words;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acrostic);
        init();
    }
    private void init() {
        createBtn = findViewById(R.id.acrostic_create_btn);
        createBtn.setOnClickListener(this);
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acrostic_create_btn:
                words = wordsEdt.getText().toString();
                if (words == null || words.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.acrostic_words_hint, Toast.LENGTH_SHORT).show();
                    return;
                }
                num = (String)numSpinner.getSelectedItem();
                if (num == null || num.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.acrostic_num_prompt, Toast.LENGTH_SHORT).show();
                    return;
                }
                type = (String)typeSpinner.getSelectedItem();
                if (type == null || type.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.acrostic_type_prompt, Toast.LENGTH_SHORT).show();
                    return;
                }
                yayuntype = (String)yayuntypeSpinner.getSelectedItem();
                if (yayuntype == null || yayuntype.equals("")) {
                    Toast.makeText(getApplicationContext(), R.string.acrostic_yayuntype_prompt, Toast.LENGTH_SHORT).show();
                    return;
                }
                resultView.setText("");

                getAcrosticInfo();
                break;
            default:
                break;
        }
    }

    private void getAcrosticInfo() {
       Toast.makeText(getApplicationContext(), R.string.net_error, Toast.LENGTH_SHORT).show();
    }
}
