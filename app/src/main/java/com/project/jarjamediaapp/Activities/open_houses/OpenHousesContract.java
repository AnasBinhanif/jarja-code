package com.project.jarjamediaapp.Activities.open_houses;

import com.project.jarjamediaapp.Base.BaseContract;
import com.project.jarjamediaapp.Models.Upload_ProfileImage;

import okhttp3.MultipartBody;
import retrofit2.Response;

public interface OpenHousesContract {

    interface View extends BaseContract.View {

        void updateUIListForOpenHouses(Response<GetAllOpenHousesModel> response,int position);

        // changes in model UplaodImageModel to Upload_ProfileImage for api response
        void updateAfterUploadFile(Upload_ProfileImage response);

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

        void getAllOpenHouses(String openHouseType,int position);

        void addOpenHouse(String body);

        void uploadImage(MultipartBody.Part file);
    }

}
