package com.amandafarrell.www.temperatureconverter.feature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.amandafarrell.www.temperatureconverter.R.string;

public class MainActivity extends AppCompatActivity {

    private boolean mIgnoreNextTextChange = false;

    private int mKelvin = 27315;
    private int mCelsius = 0;
    private int mFahrenheit = 3200;

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
            //Setting the text within a textWatcher would create an infinite loop,
            //so we ignore the text change made by the textWatcher
            if (mIgnoreNextTextChange) {
                mIgnoreNextTextChange = false;
                return;
            } else {
                mIgnoreNextTextChange = true;
            }
            try {
                String editTextString = mKelvinEditText.getText().toString();

                //don't parse the int if the string is empty
                if (editTextString.isEmpty()){
                    mKelvin = 0;
                } else {
                    mKelvin = Integer.parseInt(editTextString.replace(".", ""));
                }
            }
            //catches numbers that are too large for an int
            catch (Exception e){
                Toast.makeText(getBaseContext(), R.string.large_number_error_toast, Toast.LENGTH_SHORT).show();
                mKelvin= 0;
            }
            mKelvinEditText.setText(formatUnit(mKelvin));
            mKelvinEditText.setSelection(mKelvinEditText.getText().length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //don't do anything if the editText is not in focus
            if (getCurrentFocus() == mKelvinEditText) {
                //convert to all other units from Kelvin
                convertFromKelvin();
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
            if (mIgnoreNextTextChange) {
                mIgnoreNextTextChange = false;
                return;
            } else {
                mIgnoreNextTextChange = true;
            }
            try {
                String editTextString = mCelsiusEditText.getText().toString();

                //don't parse the int if the string is empty
                if (editTextString.isEmpty()){
                    mCelsius = 0;
                } else {
                    mCelsius = Integer.parseInt(editTextString.replace(".", ""));
                }
            }
            //catches numbers that are too large for an int
            catch (Exception e){
                Toast.makeText(getBaseContext(), R.string.large_number_error_toast, Toast.LENGTH_SHORT).show();
                mCelsius= 0;
            }
            mCelsiusEditText.setText(formatUnit(mCelsius));
            mCelsiusEditText.setSelection(mCelsiusEditText.getText().length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //don't do anything if the editText is not in focus
            if (getCurrentFocus() == mCelsiusEditText) {
                //convert to all other units from Celsius
                convertFromCelsius();
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
            if (mIgnoreNextTextChange) {
                mIgnoreNextTextChange = false;
                return;
            } else {
                mIgnoreNextTextChange = true;
            }
            try {
                String editTextString = mFahrenheitEditText.getText().toString();

                //don't parse the int if the string is empty
                if (editTextString.isEmpty()){
                    mFahrenheit = 0;
                } else {
                    mFahrenheit = Integer.parseInt(editTextString.replace(".", ""));
                }
            }
            //catches numbers that are too large for an int
            catch (Exception e){
                Toast.makeText(getBaseContext(), R.string.large_number_error_toast, Toast.LENGTH_SHORT).show();
                mFahrenheit= 0;
            }
            mFahrenheitEditText.setText(formatUnit(mFahrenheit));
            mFahrenheitEditText.setSelection(mFahrenheitEditText.getText().length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //don't do anything if the editText is not in focus
            if (getCurrentFocus() == mFahrenheitEditText) {
                //convert to all other units from Fahrenheit
                convertFromFahrenheit();
                //reset views with the new values
                mKelvinEditText.setText(formatUnit(mKelvin));
                mCelsiusEditText.setText(formatUnit(mCelsius));
            }
        }
    };

    //return the unit as a string formatted as a decimal with two significant digits
    private String formatUnit(int unit) {
        return String.format("%.2f", unit / 100.0);
    }

    private void convertFromKelvin() {
        mCelsius = mKelvin - 27315;
        mFahrenheit = (mKelvin * 9 / 5) - 45967;
    }

    private void convertFromCelsius() {
        mKelvin = mCelsius + 27315;
        mFahrenheit = (mCelsius * 9 / 5) + 3200;
    }

    private void convertFromFahrenheit() {
        mKelvin = (mFahrenheit + 45967) * 5 / 9;
        mCelsius = (mFahrenheit - 3200) * 5 / 9;
    }
}
