package com.wizeline.cryptoconverter.presentation.converter;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.wizeline.cryptoconverter.ConverterApplication;
import com.wizeline.cryptoconverter.R;

import java.util.List;

public class ConverterActivity extends AppCompatActivity implements ConverterContract.ConverterView {

    private ConverterContract.ConverterPresenter converterPresenter;

    private View mainContent;
    private View error;
    private View progressBar;
    private EditText amount;
    private Spinner from;
    private EditText to;
    private TextView result;
    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        converterPresenter = new ConverterPresenter(this, ConverterApplication.getConversionRepo());
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
        findViewById(R.id.retry).setOnClickListener(view -> converterPresenter.onCreate());
        findViewById(R.id.convert).setOnClickListener(view -> startConversion());


        converterPresenter.onCreate();
    }

    private void startConversion() {
        String amountString = this.amount.getText().toString();
        String toString = this.to.getText().toString();

        if(TextUtils.isEmpty(amountString) || TextUtils.isEmpty(toString)) {
            showError("Please fill the empty fields");
        } else {
            float amountFloat = Float.parseFloat(amountString);
            converterPresenter.convert(amountFloat, (String) from.getSelectedItem(), toString);
        }
    }

    @Override
    public void showLoading() {
        mainContent.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showCoinsList(List<String> coins) {
        mainContent.setVisibility(View.VISIBLE);
        from.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, coins));
    }

    @Override public void showResult(String result) {
        this.result.setText(result);
    }

    @Override
    public void showFullScreenError(String error) {
        mainContent.setVisibility(View.GONE);
        errorMessage.setText(error);
        this.error.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(mainContent, error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        converterPresenter.onDestroy();
    }
}
