package wizelineacademy.mvvm_sample.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import wizelineacademy.mvvm_sample.R;
import wizelineacademy.mvvm_sample.databinding.ActivityMainBinding;
import wizelineacademy.mvvm_sample.model.DataBase;
import wizelineacademy.mvvm_sample.viewModel.PojoViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String DATA_BASE_NAME = "mvvmSampleDataBase";

    private PojoViewModel viewModel;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(new PojoViewModel(new DataBase(getSharedPreferences(DATA_BASE_NAME, MODE_PRIVATE))));
    }
}
