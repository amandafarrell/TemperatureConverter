package com.amandafarrell.www.temperatureconverter.feature;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private int mDecimalPlaces = 2;
    private String mDecimalPattern = "#0.";

    private double mKelvin = 273.15;
    private double mCelsius = 0;
    private double mFahrenheit = 32;
    private double mRankine = 491.67;
    private double mDelisle = 150;

    private EditText mKelvinEditText;
    private EditText mCelsiusEditText;
    private EditText mFahrenheitEditText;
    private EditText mRankineEditText;
    private EditText mDelisleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Access the user's preference for decimal places
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mDecimalPlaces = Integer.parseInt(sharedPreferences.getString(getString(R.string.settings_decimal_places_key),
                getString(R.string.settings_decimal_places_default)));

        //Create decimal pattern string based on the decimal places int
        for (int i = 0; i < mDecimalPlaces; i++) {
            mDecimalPattern += "0";
        }

        //Find Views
        mKelvinEditText = (EditText) findViewById(R.id.edit_text_kelvin);
        mCelsiusEditText = (EditText) findViewById(R.id.edit_text_celsius);
        mFahrenheitEditText = (EditText) findViewById(R.id.edit_text_fahrenheit);
        mRankineEditText = (EditText) findViewById(R.id.edit_text_rankine);
        mDelisleEditText = (EditText) findViewById(R.id.edit_text_delisle);

        //Set views to default values
        mKelvinEditText.setText(formatUnit(mKelvin));
        mCelsiusEditText.setText(formatUnit(mCelsius));
        mFahrenheitEditText.setText(formatUnit(mFahrenheit));
        mRankineEditText.setText(formatUnit(mRankine));
        mDelisleEditText.setText(formatUnit(mDelisle));

        //Select all text
        mKelvinEditText.setSelectAllOnFocus(true);
        mCelsiusEditText.setSelectAllOnFocus(true);
        mFahrenheitEditText.setSelectAllOnFocus(true);
        mRankineEditText.setSelectAllOnFocus(true);
        mDelisleEditText.setSelectAllOnFocus(true);

        //Set TextWatchers
        mKelvinEditText.addTextChangedListener(kelvinTextWatcher);
        mCelsiusEditText.addTextChangedListener(celsiusTextWatcher);
        mFahrenheitEditText.addTextChangedListener(fahrenheitTextWatcher);
        mRankineEditText.addTextChangedListener(rankineTextWatcher);
        mDelisleEditText.addTextChangedListener(delisleTextWatcher);

        //Format the EditTexts when they lose focus
        mKelvinEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    mKelvinEditText.setText(formatUnit(mKelvin));
                }
            }
        });

        mCelsiusEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    mCelsiusEditText.setText(formatUnit(mCelsius));
                }
            }
        });

        mFahrenheitEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    mFahrenheitEditText.setText(formatUnit(mFahrenheit));
                }
            }
        });

        mRankineEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    mRankineEditText.setText(formatUnit(mRankine));
                }
            }
        });

        mDelisleEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    mDelisleEditText.setText(formatUnit(mDelisle));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actions_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            } else if (!editTextString.equals("-")) {
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
                mRankineEditText.setText(formatUnit(mRankine));
                mDelisleEditText.setText(formatUnit(mDelisle));
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
            } else if (!editTextString.equals("-")) {
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
                mRankineEditText.setText(formatUnit(mRankine));
                mDelisleEditText.setText(formatUnit(mDelisle));
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
            } else if (!editTextString.equals("-")) {
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
                mRankineEditText.setText(formatUnit(mRankine));
                mDelisleEditText.setText(formatUnit(mDelisle));
            }
        }
    };

    private TextWatcher rankineTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String editTextString = mRankineEditText.getText().toString();
            //don't parse if the string is empty or a negative sign
            if (editTextString.isEmpty()) {
                mRankine = 0;
            } else if (!editTextString.equals("-")) {
                mRankine = Double.valueOf(editTextString);
            }
            mRankineEditText.setSelection(mRankineEditText.getText().length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //don't do anything if the editText is not in focus
            if (getCurrentFocus() == mRankineEditText) {
                //convert to Kelvin and convert to all other units from Kelvin
                rankineToKelvin();
                convertAllFromKelvin();
                //reset views with the new values
                mKelvinEditText.setText(formatUnit(mKelvin));
                mCelsiusEditText.setText(formatUnit(mCelsius));
                mFahrenheitEditText.setText(formatUnit(mFahrenheit));
                mDelisleEditText.setText(formatUnit(mDelisle));
            }
        }
    };

    private TextWatcher delisleTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String editTextString = mDelisleEditText.getText().toString();
            //don't parse if the string is empty or a negative sign
            if (editTextString.isEmpty()) {
                mDelisle = 0;
            } else if (!editTextString.equals("-")) {
                mDelisle = Double.valueOf(editTextString);
            }
            mDelisleEditText.setSelection(mDelisleEditText.getText().length());
        }

        @Override
        public void afterTextChanged(Editable editable) {
            //don't do anything if the editText is not in focus
            if (getCurrentFocus() == mDelisleEditText) {
                //convert to Kelvin and convert to all other units from Kelvin
                delisleToKelvin();
                convertAllFromKelvin();
                //reset views with the new values
                mKelvinEditText.setText(formatUnit(mKelvin));
                mCelsiusEditText.setText(formatUnit(mCelsius));
                mFahrenheitEditText.setText(formatUnit(mFahrenheit));
                mRankineEditText.setText(formatUnit(mRankine));
            }
        }
    };

    //return the unit as a string formatted as a decimal with two significant digits
    private String formatUnit(double unit) {
        NumberFormat formatter = new DecimalFormat(mDecimalPattern);
        return String.valueOf(formatter.format(unit));
    }

    private void convertAllFromKelvin() {
        mCelsius = mKelvin - 273.15;
        mFahrenheit = (mKelvin * 9 / 5.0) - 459.67;
        mRankine = mKelvin * 9/5.0;
        mDelisle = (373.15 - mKelvin) * 3/2.0;
    }

    private void celsiusToKelvin() {
        mKelvin = mCelsius + 273.15;
    }

    private void fahrenheitToKelvin() {
        mKelvin = (mFahrenheit + 459.67) * 5 / 9.0;
    }

    private void rankineToKelvin() {
        mKelvin = (mRankine)*5/9.0;
    }

    private void delisleToKelvin() {
        mKelvin = 373.15 - (mDelisle * 2/3.0);
    }
}