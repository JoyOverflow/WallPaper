package pxgd.hyena.com.mydemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pxgd.hyena.com.mydemo.service.AcrosticService;
import pxgd.hyena.com.mydemo.service.NetUtils;

public class AcrosticActivity extends AppCompatActivity implements View.OnClickListener{

    private Button createBtn;
    private AcrosticService acrosticService = new AcrosticService();
    //private ImageView copyView;

    //文本编辑框以及内容
    private EditText wordsEdt;
    private String words;

    //字数选择框以及字数
    private String num;
    private Spinner numSpinner;

    //藏位置选择框和藏的位置
    private String type;
    private Spinner typeSpinner;

    //押韵方式选择框和押韵方式
    private String yayuntype;
    private Spinner yayuntypeSpinner;


    //结果
    private TextView resultView;
    //查询进度条
    private ProgressBar queryProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acrostic);
        init();
    }
    private void init() {
        createBtn = findViewById(R.id.acrostic_create_btn);
        createBtn.setOnClickListener(this);

        //copyView = findViewById(R.id.acrostic_copy);
        //copyView.setOnClickListener(this);

        wordsEdt = findViewById(R.id.acrostic_words_edt);
        numSpinner = findViewById(R.id.acrostic_num_spinner);
        typeSpinner = findViewById(R.id.acrostic_type_spinner);
        yayuntypeSpinner = findViewById(R.id.acrostic_yayuntype_spinner);
        resultView = findViewById(R.id.acrostic_result);
        queryProgressBar = findViewById(R.id.acrostic_query_progressbar);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
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

//            case R.id.acrostic_copy:
//                String result = resultView.getText().toString();
//                if (result != null && !result.equals("")) {
//                    ClipboardManager clipboardManager = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
//                    clipboardManager.setText(result);
//                    Toast.makeText(getApplicationContext(), R.string.acrostic_copy_success, Toast.LENGTH_SHORT).show();
//                }
//                break;

            default:
                break;
        }
    }
    private void getAcrosticInfo() {
        if (NetUtils.isConnected(getApplicationContext())) {
            queryProgressBar.setVisibility(View.VISIBLE);
            new AcrosticInfoAsyncTask().execute("");
        } else {
            Toast.makeText(getApplicationContext(), R.string.net_error, Toast.LENGTH_SHORT).show();
        }
    }

    public class AcrosticInfoAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            return acrosticService.getAcrosticInfo(words, num, type, yayuntype);
        }
        @Override
        protected void onPostExecute(String result) {
            queryProgressBar.setVisibility(View.GONE);
            if (result != null && !result.equals("")) {
                //copyView.setVisibility(View.VISIBLE);
                resultView.setText(Html.fromHtml(result));
            } else {
                //copyView.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), R.string.acrostic_error1, Toast.LENGTH_SHORT).show();
            }
        }
    }




}
