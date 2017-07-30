package com.wizeline.cryptoconverter.cryptoConverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wizeline.cryptoconverter.R;

import io.reactivex.disposables.Disposable;

public class ConverterActivity extends AppCompatActivity {

    private View mainContent;
    private View error;
    private View progressBar;
    private EditText amount;
    private Spinner from;
    private EditText to;
    private TextView result;
    private TextView errorMessage;
    private Disposable conversionDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        init();
    }

    private void init() {
        mainContent = findViewById(R.id.main_content);
        error = findViewById(R.id.error);
        progressBar = findViewById(R.id.progress_bar);
        amount = (EditText) findViewById(R.id.amount);
        to = (EditText) findViewById(R.id.to);
        from = (Spinner) findViewById(R.id.from);
        result = (TextView) findViewById(R.id.result);
        errorMessage = (TextView) findViewById(R.id.error_message);
    }
}
