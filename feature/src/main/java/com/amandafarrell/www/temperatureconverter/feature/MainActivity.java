package com.amandafarrell.www.temperatureconverter.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private double mKelvin = 273.15;
    private double mCelsius = 0;
    private double mFahrenheit = 32;

    private EditText mKelvinEditText;
    private EditText mCelsiusEditText;
    private EditText mFahrenheitEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find Views
        mKelvinEditText = findViewById(R.id.edit_text_kelvin);
        mCelsiusEditText = findViewById(R.id.edit_text_celsius);
        mFahrenheitEditText = findViewById(R.id.edit_text_fahrenheit);

        //Set views to default values
        mKelvinEditText.setText(formatUnit(mKelvin));
        mCelsiusEditText.setText(formatUnit(mCelsius));
        mFahrenheitEditText.setText(formatUnit(mFahrenheit));

        //Select all text
        mKelvinEditText.setSelectAllOnFocus(true);
        mCelsiusEditText.setSelectAllOnFocus(true);
        mFahrenheitEditText.setSelectAllOnFocus(true);

        //Set TextWatchers
        mKelvinEditText.addTextChangedListener(kelvinTextWatcher);
        mCelsiusEditText.addTextChangedListener(celsiusTextWatcher);
        mFahrenheitEditText.addTextChangedListener(fahrenheitTextWatcher);
    }

    private TextWatcher kelvinTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String editTextString = mKelvinEditText.getText().toString();
            //don't parse if the string is empty
            if (editTextString.isEmpty()) {
                mKelvin = 0;
            } else if (!editTextString.equals("-")){
                mKelvin = Double.valueOf(editTextString);
            }
            mKelvinEditText.setSelection(mKelvinEditText.getText().length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //don't do anything if the editText is not in focus
            if (getCurrentFocus() == mKelvinEditText) {
                //convert to all other units from Kelvin
                convertAllFromKelvin();
                //reset views with the new values
                mCelsiusEditText.setText(formatUnit(mCelsius));
                mFahrenheitEditText.setText(formatUnit(mFahrenheit));
            }
        }
    };

    private TextWatcher celsiusTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String editTextString = mCelsiusEditText.getText().toString();
            //don't parse if the string is empty
            if (editTextString.isEmpty()) {
                mCelsius = 0;
            } else if (!editTextString.equals("-")){
                mCelsius = Double.valueOf(editTextString);
            }
            mCelsiusEditText.setSelection(mCelsiusEditText.getText().length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //don't do anything if the editText is not in focus
            if (getCurrentFocus() == mCelsiusEditText) {
                //convert to Kelvin and convert to all other units from Kelvin
                celsiusToKelvin();
                convertAllFromKelvin();
                //reset views with the new values
                mKelvinEditText.setText(formatUnit(mKelvin));
                mFahrenheitEditText.setText(formatUnit(mFahrenheit));
            }
        }
    };

    private TextWatcher fahrenheitTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String editTextString = mFahrenheitEditText.getText().toString();
            //don't parse if the string is empty or a negative sign
            if (editTextString.isEmpty()) {
                mFahrenheit = 0;
            } else if (!editTextString.equals("-")){
                mFahrenheit = Double.valueOf(editTextString);
            }
            mFahrenheitEditText.setSelection(mFahrenheitEditText.getText().length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //don't do anything if the editText is not in focus
            if (getCurrentFocus() == mFahrenheitEditText) {
                //convert to Kelvin and convert to all other units from Kelvin
                fahrenheitToKelvin();
                convertAllFromKelvin();
                //reset views with the new values
                mKelvinEditText.setText(formatUnit(mKelvin));
                mCelsiusEditText.setText(formatUnit(mCelsius));
            }
        }
    };

    //return the unit as a string formatted as a decimal with two significant digits
    private String formatUnit(double unit) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        return String.valueOf(formatter.format(unit));
    }

    private void convertAllFromKelvin() {
        mCelsius = mKelvin - 273.15;
        mFahrenheit = (mKelvin * 9 / 5.0) - 459.67;
    }

    private void celsiusToKelvin() {
        mKelvin = mCelsius + 273.15;
    }

    private void fahrenheitToKelvin() {
        mKelvin = (mFahrenheit + 459.67) * 5 / 9.0;
    }
}