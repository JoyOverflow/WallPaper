package ouyj.hyena.com.seelewallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import ouyj.hyena.com.seelewallpaper.service.WallpaperService;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor preferencesEditor;
    private EditText intervalEditText;
    private EditText wallpaperPathEditText;
    private Button startButton;
    private Button stopButton;
    private Button dirSelectButton;
    private RadioGroup modeRadioGroup;
    private Switch strictSwitch;
    private Switch compatSwitch;
    private Switch sleepModeSwitch;
    private Switch systemBootSwitch;
    private CheckBox timerSwitchCheckBox;
    private CheckBox unlockSwitchCheckBox;
    private LinearLayout customWHLayout;
    private CheckBox customWHCheckBox;
    private EditText customWidthEditText;
    private EditText customHeightEditText;
    private Spinner updateModeSpinner;
    private SeekBar alphaSeekbar;
    private TextView alphaTextView;
    private Switch graySwitch;
    private boolean isCompatMode = false;
    private boolean isCustomWH = false;

    //屏幕窗口大小
    private int windowWidth = -1;
    private int windowHeight = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取屏幕窗口大小
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);
        windowWidth = dm.widthPixels;
        windowHeight = dm.heightPixels;

        String desc=String.format("宽:%d 高:%d",windowWidth,windowHeight);
        Toast.makeText(
                MainActivity.this,
                desc,
                Toast.LENGTH_SHORT
        ).show();



        sharedPreferences = getSharedPreferences(WallpaperService.SETTING_FILENAME, Context.MODE_PRIVATE);
        preferencesEditor = sharedPreferences.edit();

        wallpaperPathEditText = (EditText) this.findViewById(R.id.mainLayoutWallpaperPath_EditText);
        intervalEditText = (EditText) this.findViewById(R.id.mainLayoutIntervalTime_EditText);
        startButton = (Button) this.findViewById(R.id.mainLayoutStart_Button);
        stopButton = (Button) this.findViewById(R.id.mainLayoutStop_Button);
        modeRadioGroup = (RadioGroup) this.findViewById(R.id.mainLayout_RadioGroup);
        dirSelectButton = (Button) this.findViewById(R.id.mainLayoutDiecrtorySelect_Button);
        strictSwitch = (Switch) this.findViewById(R.id.mainLayoutStrict_Switch);
        compatSwitch = (Switch) this.findViewById(R.id.mainLayoutCompat_Switch);
        sleepModeSwitch = (Switch) this.findViewById(R.id.mainLayoutSleepMode_Switch);
        systemBootSwitch = (Switch) this.findViewById(R.id.activityMainBoot_Switch);
        timerSwitchCheckBox = (CheckBox) this.findViewById(R.id.activityMainTimerSwitch_CheckBox);
        unlockSwitchCheckBox = (CheckBox) this.findViewById(R.id.activityMainUnlockSwitch_CheckBox);
        customWHLayout = (LinearLayout) this.findViewById(R.id.mainLayoutCustomWH_LinearLayout);
        customWHCheckBox = (CheckBox) this.findViewById(R.id.mainLayoutCustomWH_CheckBox);
        customWidthEditText = (EditText) this.findViewById(R.id.mainLayoutCustomWidth_EditText);
        customHeightEditText = (EditText) this.findViewById(R.id.mainLayoutCustomHeight_EditText);
        updateModeSpinner = (Spinner) this.findViewById(R.id.mainLayoutUpdateMode_Spinner);
        alphaSeekbar = (SeekBar) this.findViewById(R.id.activityMainAlpha_SeekBar);
        alphaTextView = (TextView) this.findViewById(R.id.activityMainAlpha_TextView);
        graySwitch = (Switch) this.findViewById(R.id.activityMainGray_Switch);







    }
}
