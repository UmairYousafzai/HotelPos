package com.softvalley.hotelpos.views.department;

import static com.softvalley.hotelpos.utils.CONSTANTS.GET_DEPARTMENT;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_GROUP;
import static com.softvalley.hotelpos.utils.CONSTANTS.GET_SUB_GROUP;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_ERROR;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_RESPONSE_DEPARTMENT;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_RESPONSE_GROUP;
import static com.softvalley.hotelpos.utils.CONSTANTS.SERVER_RESPONSE_SUB_GROUP;

import androidx.annotation.NonNull;

import com.softvalley.hotelpos.Interface.CallBackListener;
import com.softvalley.hotelpos.models.Department;
import com.softvalley.hotelpos.models.GetDepartmentResponse;
import com.softvalley.hotelpos.models.GetGroupResponse;
import com.softvalley.hotelpos.models.GetSubGroup;
import com.softvalley.hotelpos.models.Group;
import com.softvalley.hotelpos.models.SaveDepartmentResponse;
import com.softvalley.hotelpos.models.SaveGroupResponse;
import com.softvalley.hotelpos.models.SaveSubGroupResponse;
import com.softvalley.hotelpos.models.SubGroup;
import com.softvalley.hotelpos.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartmentRepository {

    private static DepartmentRepository departmentRepository;
    private CallBackListener callBackListener;

    private DepartmentRepository()
    {

    }
    public void setCallBackListener(CallBackListener callBackListener)
    {
        this.callBackListener = callBackListener;
    }

    public static synchronized DepartmentRepository getInstance()
    {
        if (departmentRepository==null)
        {
            departmentRepository= new DepartmentRepository();
        }

        return departmentRepository;
    }

    public void getDepartmentFromServer(String businessId)
    {

        Call<GetDepartmentResponse> call = ApiClient.getInstance().getApi().getDepartment(businessId);

        call.enqueue(new Callback<GetDepartmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetDepartmentResponse> call, @NonNull Response<GetDepartmentResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getCode()==200)
                        {
                            if (callBackListener!=null)
                            {
                                callBackListener.getServerResponse(response.body(), GET_DEPARTMENT);

                            }
                        }
                        else
                        {
                            callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                        }

                    }
                    else
                    {
                        callBackListener.getServerResponse(response.message(),SERVER_ERROR);

                    }
                }
                else
                {
                    if (response.errorBody() != null) {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.errorBody().toString(),SERVER_ERROR);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetDepartmentResponse> call, @NonNull Throwable t) {
                if (callBackListener!=null)
                {
                    callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

                }

            }
        });
    }


    public void saveDepartment(Department department)
    {
        Call<SaveDepartmentResponse>  call = ApiClient.getInstance().getApi().saveDepartment(department);

        call.enqueue(new Callback<SaveDepartmentResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveDepartmentResponse> call, @NonNull Response<SaveDepartmentResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getCode()==200)
                        {
                            if (response.body().getDepartment()!=null)
                            {
                                if (callBackListener!=null)
                                {
                                    callBackListener.getServerResponse(response.body(), SERVER_RESPONSE_DEPARTMENT);

                                }
                            }
                            else
                            {
                                callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                            }

                        }
                        else
                        {
                            callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                        }

                    }
                    else
                    {
                        callBackListener.getServerResponse(response.message(),SERVER_ERROR);

                    }
                }
                else
                {
                    if (response.errorBody() != null) {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.errorBody().toString(),SERVER_ERROR);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SaveDepartmentResponse> call, @NonNull Throwable t) {

            }
        });
    }


    public void getGroupFromServer(String businessId,String departmentCode)
    {

        Call<GetGroupResponse> call = ApiClient.getInstance().getApi().getGroupList(businessId,departmentCode);

        call.enqueue(new Callback<GetGroupResponse>() {
            @Override
            public void onResponse(@NonNull Call<GetGroupResponse> call, @NonNull Response<GetGroupResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getCode()==200)
                        {
                            if (callBackListener!=null)
                            {
                                callBackListener.getServerResponse(response.body(), GET_GROUP);

                            }
                        }
                        else
                        {
                            callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                        }

                    }
                    else
                    {
                        callBackListener.getServerResponse(response.message(),SERVER_ERROR);

                    }
                }
                else
                {
                    if (response.errorBody() != null) {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.errorBody().toString(),SERVER_ERROR);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetGroupResponse> call, @NonNull Throwable t) {
                if (callBackListener!=null)
                {
                    callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

                }

            }
        });
    }


    public void saveGroup(Group group)
    {
        Call<SaveGroupResponse>  call = ApiClient.getInstance().getApi().saveGroup(group);

        call.enqueue(new Callback<SaveGroupResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveGroupResponse> call, @NonNull Response<SaveGroupResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getCode()==200)
                        {
                            if (response.body().getGroup()!=null)
                            {
                                if (callBackListener!=null)
                                {
                                    callBackListener.getServerResponse(response.body(), SERVER_RESPONSE_GROUP);

                                }
                            }
                            else
                            {
                                callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                            }

                        }
                        else
                        {
                            callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                        }

                    }
                    else
                    {
                        callBackListener.getServerResponse(response.message(),SERVER_ERROR);

                    }
                }
                else
                {
                    if (response.errorBody() != null) {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.errorBody().toString(),SERVER_ERROR);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SaveGroupResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public void saveSubGroup(SubGroup subGroup)
    {
        Call<SaveSubGroupResponse>  call = ApiClient.getInstance().getApi().saveSubGroup(subGroup);

        call.enqueue(new Callback<SaveSubGroupResponse>() {
            @Override
            public void onResponse(@NonNull Call<SaveSubGroupResponse> call, @NonNull Response<SaveSubGroupResponse> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getCode()==200)
                        {
                            if (response.body().getSubGroup()!=null)
                            {
                                if (callBackListener!=null)
                                {
                                    callBackListener.getServerResponse(response.body(), SERVER_RESPONSE_SUB_GROUP);

                                }
                            }
                            else
                            {
                                callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                            }

                        }
                        else
                        {
                            callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                        }

                    }
                    else
                    {
                        callBackListener.getServerResponse(response.message(),SERVER_ERROR);

                    }
                }
                else
                {
                    if (response.errorBody() != null) {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.errorBody().toString(),SERVER_ERROR);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SaveSubGroupResponse> call, @NonNull Throwable t) {
                callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

            }
        });
    }


    public void getSubGroupFromServer(String businessId,String departmentCode,String groupCode)
    {

        Call<GetSubGroup> call = ApiClient.getInstance().getApi().getSubGroupList(businessId,departmentCode,groupCode);

        call.enqueue(new Callback<GetSubGroup>() {
            @Override
            public void onResponse(@NonNull Call<GetSubGroup> call, @NonNull Response<GetSubGroup> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null)
                    {
                        if (response.body().getCode()==200)
                        {
                            if (callBackListener!=null)
                            {
                                callBackListener.getServerResponse(response.body(), GET_SUB_GROUP);

                            }
                        }
                        else
                        {
                            callBackListener.getServerResponse(response.body().getMessage(),SERVER_ERROR);

                        }

                    }
                    else
                    {
                        callBackListener.getServerResponse(response.message(),SERVER_ERROR);

                    }
                }
                else
                {
                    if (response.errorBody() != null) {
                        if (callBackListener!=null)
                        {
                            callBackListener.getServerResponse(response.errorBody().toString(),SERVER_ERROR);

                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetSubGroup> call, @NonNull Throwable t) {
                if (callBackListener!=null)
                {
                    callBackListener.getServerResponse(t.getMessage(),SERVER_ERROR);

                }

            }
        });
    }
}
