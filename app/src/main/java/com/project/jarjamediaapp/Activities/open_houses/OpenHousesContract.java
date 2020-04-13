package com.project.jarjamediaapp.Activities.open_houses;

import com.project.jarjamediaapp.Base.BaseContract;

import okhttp3.MultipartBody;
import retrofit2.Response;

public interface OpenHousesContract {

    interface View extends BaseContract.View {

        void updateUIListForOpenHouses(Response<GetAllOpenHousesModel> response);

        void updateAfterUploadFile(Response<UploadImageModel> response);

        void updateUIListForAddressDetail(AddressDetailModel.Data response);

        void _updateUIonFalse(String message);

        void _updateUIonError(String error);

        void _updateUIonFailure();


    }

    /*
	Actions - this defines the methods the pure Presenter class will implement. Also known as user actions,
	this is where the app logic is defined.
 	*/
    interface Actions extends BaseContract.Actions {

        void initScreen();

        void getAddressDetailByPrefix(String prefix, String type);

        void getAllOpenHouses(String openHouseType);

        void addOpenHouse(String body);

        void uploadImage(MultipartBody.Part file);
    }

}
