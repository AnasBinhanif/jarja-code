package com.project.jarjamediaapp.Activities.open_houses;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.project.jarjamediaapp.Base.BaseActivity;
import com.project.jarjamediaapp.Base.BaseResponse;
import com.project.jarjamediaapp.Models.GetOpenHouses;
import com.project.jarjamediaapp.R;
import com.project.jarjamediaapp.Utilities.GH;
import com.project.jarjamediaapp.Utilities.ToastUtils;
import com.project.jarjamediaapp.databinding.ActivityOpenHousesBinding;
import com.thetechnocafe.gurleensethi.liteutils.RecyclerAdapterUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import retrofit2.Response;

public class Open_HousesActivity extends BaseActivity implements View.OnClickListener, Open_HousesContract.View {

    ActivityOpenHousesBinding bi;
    Context context = Open_HousesActivity.this;
    Open_HousesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bi = DataBindingUtil.setContentView(this, R.layout.activity_open_houses);
        presenter = new Open_HousesPresenter(this);
        presenter.initScreen();
        setToolBarTitle(bi.epToolbar.toolbar, getString(R.string.openHouses), true);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

        }

    }

    @Override
    public void initViews() {

        populateListData();

    }


    private void populateListData() {


        List<GetOpenHouses> leadsList = new ArrayList<>();

        leadsList.add(new GetOpenHouses("90 gail Court Copy -2 North", "12/31/2020 5:07 PM", "12/31/2020 5:07 PM",
                "Branford", "06472", "CT", "120000", getDrawable(R.drawable.house)));
        leadsList.add(new GetOpenHouses("90 gail Court Copy -2 North", "12/31/2020 5:07 PM", "12/31/2020 5:07 PM",
                "Branford", "06472", "CT", "120000", getDrawable(R.drawable.house)));
        leadsList.add(new GetOpenHouses("90 gail Court Copy -2 North", "12/31/2020 5:07 PM", "12/31/2020 5:07 PM",
                "Branford", "06472", "CT", "120000", getDrawable(R.drawable.house)));
        leadsList.add(new GetOpenHouses("90 gail Court Copy -2 North", "12/31/2020 5:07 PM", "12/31/2020 5:07 PM",
                "Branford", "06472", "CT", "120000", getDrawable(R.drawable.house)));

        bi.recyclerViewOpenHouse.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        bi.recyclerViewOpenHouse.setItemAnimator(new DefaultItemAnimator());
        RecyclerAdapterUtil recyclerAdapterUtil = new RecyclerAdapterUtil(context, leadsList, R.layout.custom_open_house_layout);
        recyclerAdapterUtil.addViewsList(R.id.txtAddress, R.id.txtCityPostal, R.id.txtEndDateTime, R.id.txtStartDateTime, R.id.txtLeads
                , R.id.imgHouse);

        recyclerAdapterUtil.addOnDataBindListener((Function4<View, GetOpenHouses, Integer, Map<Integer, ? extends View>, Unit>) (view, allOpenHouses, integer, integerMap) -> {


            TextView txtAddress = (TextView) integerMap.get(R.id.txtAddress);
            txtAddress.setText(allOpenHouses.getAddress());

            TextView txtStartDateTime = (TextView) integerMap.get(R.id.txtStartDateTime);
            txtStartDateTime.setText(allOpenHouses.getStartDateTime());

            TextView txtEndDateTime = (TextView) integerMap.get(R.id.txtEndDateTime);
            txtEndDateTime.setText(allOpenHouses.getEndDateTime());

            TextView txtCityPostal = (TextView) integerMap.get(R.id.txtCityPostal);

            String cityPostal = allOpenHouses.getCity() + " " + allOpenHouses.getProvince() + " " + allOpenHouses.getPostalCode();
            txtCityPostal.setText(cityPostal);

            ImageView imgHouse = (ImageView) integerMap.get(R.id.imgHouse);
            imgHouse.setImageDrawable(allOpenHouses.getImage());

            return Unit.INSTANCE;
        });

        bi.recyclerViewOpenHouse.setAdapter(recyclerAdapterUtil);

        recyclerAdapterUtil.addOnClickListener((Function2<GetOpenHouses, Integer, Unit>) (allOpenHouses, integer) -> {

            return Unit.INSTANCE;
        });


    }

    public void showAddHouseDialog(Context context) {

        final Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_open_house_dialog);


        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void updateUI(Response<BaseResponse> response) {


    }

    @Override
    public void updateUIonFalse(String message) {

        ToastUtils.showToastLong(context, message);

    }

    @Override
    public void updateUIonError(String error) {

        ToastUtils.showToastLong(context, error);
    }

    @Override
    public void updateUIonFailure() {

        ToastUtils.showToastLong(context, getString(R.string.retrofit_failure));
    }

    @Override
    public void showProgressBar() {

        GH.getInstance().ShowProgressDialog(context);
    }

    @Override
    public void hideProgressBar() {

        GH.getInstance().HideProgressDialog(context);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.home, menu);
        menu.findItem(R.id.action_notify).setVisible(false);
        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            showAddHouseDialog(context);

            return true;
        }
        return super.onOptionsItemSelected(item);

    }

}
