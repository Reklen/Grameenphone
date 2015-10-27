package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.grameenphone.R;
import com.cc.grameenphone.interfaces.QuickPayInterface;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by rajkiran on 18/09/15.
 */
public class QuickPayFragment extends Fragment implements Validator.ValidationListener {
    private static EditText payCode;
    QuickPayInterface quickPayInterface;
    @InjectView(R.id.textView2)
    TextView textView2;

    @NotEmpty
    @InjectView(R.id.editTextQuickPayCode)
    EditText editTextQuickPayCode;
    @InjectView(R.id.submitbutton)
    Button submitbutton;
    @InjectView(R.id.quickPayFragment)
    LinearLayout quickPayFragment;
    Validator mValidator;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            quickPayInterface = (QuickPayInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quickpay_entercode, container, false);
        ButterKnife.inject(this, view);
        mValidator = new Validator(QuickPayFragment.this);
        mValidator.setValidationListener(QuickPayFragment.this);
        editTextQuickPayCode.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (keyCode == EditorInfo.IME_ACTION_DONE)) {
                    submitbuttonClicked();
                }
                return false;
            }
        });
        return view;
    }

    @OnClick(R.id.submitbutton)
    void submitbuttonClicked() {
        mValidator.validate();


    }

    public void clearCodeFromET() {
        editTextQuickPayCode.setText("");
    }


    @Override
    public void onValidationSucceeded() {
        quickPayInterface.onQuickCodeSubmit(editTextQuickPayCode.getText().toString() + "");
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}