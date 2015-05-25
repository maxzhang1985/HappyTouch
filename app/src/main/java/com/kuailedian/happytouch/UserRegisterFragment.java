package com.kuailedian.happytouch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kuailedian.components.MyEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 磊 on 2015/5/25.
 */
public class UserRegisterFragment extends Fragment {

    // TODO: Rename and change types and number of parameters
    public static UserRegisterFragment newInstance() {
        UserRegisterFragment fragment = new UserRegisterFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public UserRegisterFragment() {
        // Required empty public constructor
    }

    @InjectView(R.id.btn_showpassword)
    Button btnShowPassword;

    @InjectView(R.id.edit_reg_password)
    MyEditText editPasswrod;



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnShowPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                   editPasswrod.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    editPasswrod.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    editPasswrod.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                return false;
            }
        });



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.user_register, container, false);

        ButterKnife.inject(this, view);

        return view;
    }


}
