package com.pratikthorat.wastewaterparampredictor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText input_DO;
    private Button btn_calculate;
    private TextView output_MPAI;
    private TextView output_BOD;
    private TextView output_COD;

    private Button edit_MPAI, edit_BOD, edit_COD;
    private LinearLayout ll_edit_MPAI, ll_edit_BOD, ll_edit_COD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input_DO = (EditText)findViewById(R.id.input_DO);
        btn_calculate =  findViewById(R.id.btn_calculate);
        output_MPAI = (TextView)findViewById(R.id.output_MPAI);
        output_BOD = (TextView)findViewById(R.id.output_BOD);
        output_COD = (TextView)findViewById(R.id.output_COD);
        edit_MPAI = (Button)findViewById(R.id.editMPAI);
        edit_BOD = (Button)findViewById(R.id.editBOD);
        edit_COD = (Button)findViewById(R.id.editCOD);
        ll_edit_MPAI = (LinearLayout) findViewById(R.id.llEditMPAI);
        ll_edit_BOD = (LinearLayout)findViewById(R.id.llEditBOD);
        ll_edit_COD = (LinearLayout)findViewById(R.id.llEditCOD);

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                closeKeyboard();
                String doValText =  input_DO.getText().toString();
                double doVal=0;
                if(!doValText.isEmpty()) {
                    try {
                        doVal = Double.parseDouble(doValText);
                    } catch (Exception e1) {
                        Toast.makeText(getApplicationContext(),
                                "Invalid input. Please provide valid DO value!",
                                Toast.LENGTH_LONG).show();
                        output_MPAI.setText("-");
                        output_BOD.setText("-");
                        output_COD.setText("-");
                        return;
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Input is empty. Please provide DO value!",
                            Toast.LENGTH_LONG).show();
                    output_MPAI.setText("-");
                    output_BOD.setText("-");
                    output_COD.setText("-");
                    return;
                }
                if(doVal<2.6 || doVal>4.8){
                    Toast.makeText(getApplicationContext(),
                            "Input out of range. Please provide valid DO value in range 2.6 to 4.8 only!",
                            Toast.LENGTH_LONG).show();
                    output_MPAI.setText("-");
                    output_BOD.setText("-");
                    output_COD.setText("-");
                    return;
                }
                calculate(doVal);
            }
        });

    }

    private void closeKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void editMPAI(View view) {
        closeKeyboard();
        edit_MPAI.setVisibility(View.GONE);
        ll_edit_MPAI.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("EquationData",MODE_PRIVATE);
        double mpai_m = (double)sharedPreferences.getFloat("mpai_m",-0.1257f);
        double mpai_c = (double)sharedPreferences.getFloat("mpai_c",0.9854f);
        EditText m = findViewById(R.id.mpai_m);
        EditText c = findViewById(R.id.mpai_c);
        m.setText(String.format("%.4f", mpai_m));
        c.setText(String.format("%.4f", mpai_c));
    }

    public void editBOD(View view) {
        closeKeyboard();
        edit_BOD.setVisibility(View.GONE);
        ll_edit_BOD.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("EquationData",MODE_PRIVATE);
        double bod_m = (double)sharedPreferences.getFloat("bod_m",51.578f);
        double bod_c = (double)sharedPreferences.getFloat("bod_c",-7.1282f);
        EditText m = findViewById(R.id.bod_m);
        EditText c = findViewById(R.id.bod_c);
        m.setText(String.format("%.4f", bod_m));
        c.setText(String.format("%.4f", bod_c));
    }

    public void editCOD(View view) {
        closeKeyboard();
        edit_COD.setVisibility(View.GONE);
        ll_edit_COD.setVisibility(View.VISIBLE);
        SharedPreferences sharedPreferences = getSharedPreferences("EquationData",MODE_PRIVATE);
        double cod_m = (double)sharedPreferences.getFloat("cod_m",162.62f);
        double cod_c = (double)sharedPreferences.getFloat("cod_c",-23.782f);
        EditText m = findViewById(R.id.cod_m);
        EditText c = findViewById(R.id.cod_c);
        m.setText(String.format("%.4f", cod_m));
        c.setText(String.format("%.4f", cod_c));
    }

    public void saveBOD(View view) {
        closeKeyboard();
        SharedPreferences sharedPreferences = getSharedPreferences("EquationData",MODE_PRIVATE);
        EditText m = findViewById(R.id.bod_m);
        EditText c = findViewById(R.id.bod_c);
        String mText =  m.getText().toString();
        String cText =  c.getText().toString();
        if(!mText.isEmpty() && !cText.isEmpty()) {
            try {
                float mVal = Float.parseFloat(mText);
                float cVal = Float.parseFloat(cText);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putFloat("bod_m",mVal);
                myEdit.putFloat("bod_c",cVal);
                myEdit.commit();
                edit_BOD.setVisibility(View.VISIBLE);
                ll_edit_BOD.setVisibility(View.GONE);
                btn_calculate.performClick();
            } catch (Exception e1) {
                Toast.makeText(getApplicationContext(),
                        "Invalid input. Please provide valid m & c values!",
                        Toast.LENGTH_LONG).show();
                output_MPAI.setText("-");
                output_BOD.setText("-");
                output_COD.setText("-");
                return;
            }
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "m or c is empty. Please provide valid values!",
                    Toast.LENGTH_LONG).show();
            output_MPAI.setText("-");
            output_BOD.setText("-");
            output_COD.setText("-");
            return;
        }
    }

    public void saveMPAI(View view) {
        closeKeyboard();
        SharedPreferences sharedPreferences = getSharedPreferences("EquationData",MODE_PRIVATE);
        EditText m = findViewById(R.id.mpai_m);
        EditText c = findViewById(R.id.mpai_c);
        String mText =  m.getText().toString();
        String cText =  c.getText().toString();
        if(!mText.isEmpty() && !cText.isEmpty()) {
            try {
                float mVal = Float.parseFloat(mText);
                float cVal = Float.parseFloat(cText);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putFloat("mpai_m",mVal);
                myEdit.putFloat("mpai_c",cVal);
                myEdit.commit();
                edit_MPAI.setVisibility(View.VISIBLE);
                ll_edit_MPAI.setVisibility(View.GONE);
                btn_calculate.performClick();

            } catch (Exception e1) {
                Toast.makeText(getApplicationContext(),
                        "Invalid input. Please provide valid m & c values!",
                        Toast.LENGTH_LONG).show();
                output_MPAI.setText("-");
                output_BOD.setText("-");
                output_COD.setText("-");
                return;
            }
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "m or c is empty. Please provide valid values!",
                    Toast.LENGTH_LONG).show();
            output_MPAI.setText("-");
            output_BOD.setText("-");
            output_COD.setText("-");
            return;
        }
    }

    public void saveCOD(View view) {
        closeKeyboard();
        SharedPreferences sharedPreferences = getSharedPreferences("EquationData",MODE_PRIVATE);
        EditText m = findViewById(R.id.cod_m);
        EditText c = findViewById(R.id.cod_c);
        String mText =  m.getText().toString();
        String cText =  c.getText().toString();
        if(!mText.isEmpty() && !cText.isEmpty()) {
            try {
                float mVal = Float.parseFloat(mText);
                float cVal = Float.parseFloat(cText);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putFloat("cod_m",mVal);
                myEdit.putFloat("cod_c",cVal);
                myEdit.commit();
                edit_COD.setVisibility(View.VISIBLE);
                ll_edit_COD.setVisibility(View.GONE);
                btn_calculate.performClick();

            } catch (Exception e1) {
                Toast.makeText(getApplicationContext(),
                        "Invalid input. Please provide valid m & c values!",
                        Toast.LENGTH_LONG).show();
                output_MPAI.setText("-");
                output_BOD.setText("-");
                output_COD.setText("-");
                return;
            }
        }
        else {
            Toast.makeText(getApplicationContext(),
                    "m or c is empty. Please provide valid values!",
                    Toast.LENGTH_LONG).show();
            output_MPAI.setText("-");
            output_BOD.setText("-");
            output_COD.setText("-");
            return;
        }
    }

    private void calculate(double doVal){
        SharedPreferences sharedPreferences = getSharedPreferences("EquationData",MODE_PRIVATE);
        double mpai_m = (double)sharedPreferences.getFloat("mpai_m",-0.1257f);
        double mpai_c = (double)sharedPreferences.getFloat("mpai_c",0.9854f);
        double bod_m = (double)sharedPreferences.getFloat("bod_m",51.578f);
        double bod_c = (double)sharedPreferences.getFloat("bod_c",-7.1282f);
        double cod_m = (double)sharedPreferences.getFloat("cod_m",162.62f);
        double cod_c = (double)sharedPreferences.getFloat("cod_c",-23.782f);

        double mpaiVal = mpai_m * doVal + mpai_c;
        double bodVal = bod_m * mpaiVal + bod_c;
        double codVal = cod_m * mpaiVal + cod_c;

        output_MPAI.setText(String.format("%.4f", mpaiVal));
        output_BOD.setText(String.format("%.2f", bodVal)+" mg/L");
        output_COD.setText(String.format("%.2f", codVal)+" mg/L");
    }
}