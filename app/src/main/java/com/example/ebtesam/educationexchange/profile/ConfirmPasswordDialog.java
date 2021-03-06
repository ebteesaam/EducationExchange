package com.example.ebtesam.educationexchange.profile;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebtesam.educationexchange.R;

/**
 * Created by ebtesam on 11/02/2018 AD.
 */

public class ConfirmPasswordDialog extends DialogFragment {

    private static final String TAG = "ConfirmPasswordDialog";
    OnConfirmPasswordListener mOnConfirmPasswordListener;
    //vars
    TextView mPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_password, container, false);
        Log.d(TAG, "onCreateView: started.");
        mPassword = view.findViewById(R.id.confirm_password);

    TextView confirmDialog = view.findViewById(R.id.dialogConfirm);
        confirmDialog.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: captured password and confirming.");

            String password = mPassword.getText().toString();
            if(!password.equals("")){
                mOnConfirmPasswordListener.onConfirmPassword(password);
                getDialog().dismiss();
            }else{
                Toast.makeText(getActivity(), getActivity().getString(R.string.enter_password), Toast.LENGTH_SHORT).show();
            }

        }
    });

    TextView cancelDialog = view.findViewById(R.id.dialogCancel);
        cancelDialog.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: closing the dialog");
            getDialog().dismiss();
        }
    });


        return view;
}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mOnConfirmPasswordListener = (OnConfirmPasswordListener) getActivity();
        }catch (ClassCastException e){
            Log.e(TAG, "onAttach: ClassCastException: " + e.getMessage() );
        }
    }

    public interface OnConfirmPasswordListener{
        void onConfirmPassword(String password);
    }
}