package com.project.jarjamediaapp.Activities.lead_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.databinding.BottomMultipleSheetBinding;


public class BottomDialog extends BottomSheetDialogFragment {

    BottomMultipleSheetBinding bi;
    HandleMultipleClickEvents mCLickHandleMultipleClickEvents;

    public BottomDialog() {
        // Required empty public constructor
    }

    private static BottomDialog instance = null;

    public static BottomDialog getInstance() {
        if (instance == null) {
            instance = new BottomDialog();
            return instance;
        }
        return instance;
    }


    public void setClickHandleEvents(HandleMultipleClickEvents mCLickHandleMultipleClickEvents) {
        this.mCLickHandleMultipleClickEvents = mCLickHandleMultipleClickEvents;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bi = DataBindingUtil.inflate(inflater, R.layout.bottom_multiple_sheet, container, false);
        initClickListeners();
        return bi.getRoot();
    }

    private void initClickListeners() {

        bi.llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCLickHandleMultipleClickEvents != null) {
                    mCLickHandleMultipleClickEvents.onGalleryClick();
                }
            }
        });
        /*bi.llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCLickHandleMultipleClickEvents != null) {
                    mCLickHandleMultipleClickEvents.onCameraClick();
                }
            }
        });*/
        bi.llDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCLickHandleMultipleClickEvents != null) {
                    mCLickHandleMultipleClickEvents.onDocumentClick();
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