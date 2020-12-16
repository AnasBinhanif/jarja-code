package com.project.jarjamediaapp.Utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Akshay Kumar on 12/16/2020.
 */
public class TextWatcherCustom implements TextWatcher {
    EditText editText;

    public TextWatcherCustom(EditText editText){
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
    public void checkIfSame(EditText editText,String text){

    }
}
