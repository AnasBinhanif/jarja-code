package com.project.jarjamediaapp.Activities.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.databinding.BottomSheetBinding;


public class BottomDialogFragment extends BottomSheetDialogFragment {

    BottomSheetBinding bi;
    HandleClickEvents mCLickHandleClickEvents;
    BottomDialogFragment bottomDialogFragment;

    public BottomDialogFragment() {
        // Required empty public constructor
    }

    private static BottomDialogFragment instance = null;

    public static BottomDialogFragment getInstance() {
        if (instance == null) {
            instance = new BottomDialogFragment();
            return instance;
        }
        return instance;
    }


    public void setCLickHandleClickEvents(HandleClickEvents mCLickHandleClickEvents) {
        this.mCLickHandleClickEvents = mCLickHandleClickEvents;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bi = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet, container, false);
        initClickListeners();
        return bi.getRoot();
    }

    private void initClickListeners() {

        bi.btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCLickHandleClickEvents != null) {
                    mCLickHandleClickEvents.onGalleryClick();
                }
            }
        });
        bi.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCLickHandleClickEvents != null) {
                    mCLickHandleClickEvents.onCameraClick();
                }
            }
        });
        bi.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });
    }


}