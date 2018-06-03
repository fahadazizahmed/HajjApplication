package capitalcryptoworld.capitalworld.com.newhajjapp.ApiInterface;


import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AccomodationList;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.Assign;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AssignOperator;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AssignedAccomodation;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AuthToken;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.AvailableProvider;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.CheckAccmodation;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.ComplainResponse;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.ComplainStatus;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.Complaint;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.GetUserType;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.HajiList;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.HajiSelect;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.HajjRegisterResponse;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.LoginModel;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.OperatorDetails;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.RegisteHajjUser;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.Register;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.RegisterResponse;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.UnAssignedComplain;
import capitalcryptoworld.capitalworld.com.newhajjapp.Model.YourSelectAccomodation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Fahad Aziz on 16/05/2018.
 */

public interface HajjInterface {

@POST("TokenAuth/Authenticate")
Call<AuthToken> getToken(@Body LoginModel loginModel);

@POST("services/app/User/CreateOrUpdateUser")
Call<RegisterResponse> sendUser(@Body Register register);
@POST("services/app/HajjRegistrationService/Create")
    Call<HajjRegisterResponse> registerHajjUser(@Header("Authorization") String authorization, @Body RegisteHajjUser registeHajjUser);
@POST("services/app/ComplaintService/Create")
    Call<ComplainResponse>  sendComplain(@Header("Authorization") String authorization, @Body Complaint complaint);

@POST("services/app/AccommodationService/ListAll")
Call<AccomodationList> getAllaccomodation(@Query("AccommodationId") int AccommodationId);

    @POST("services/app/AccommodationService/ListAll")
    Call<YourSelectAccomodation> getyourAccomodation(@Query("AccommodationId") int AccommodationId);



@GET("services/app/HajjRegistrationService/GetAccCheck")
Call<CheckAccmodation> checkAccomodation(@Header("Authorization") String authorization);
@GET("services/app/HajjRegistrationService/GetRoleCheck")
    Call<GetUserType> getUserById(@Query("UserId") int id,@Header("Authorization") String authorization);

@POST("services/app/AccommodationService/UserSelectAccomodation")
    Call<AssignedAccomodation> AccomodationAssigned(@Header("Authorization") String authorization,@Query("AccommodationId") int AccommodationId);
    @GET("services/app/HajjRegistrationService/GetListSelectedUsers")
    Call<HajiList> getAllHaji();


    @POST("services/app/HajjRegistrationService/RandomNumber")
    Call<HajiSelect> isHajiSelect(@Query("numPeople") String numPeople);

  //  http://testingservice12.azurewebsites.net/api/services/app/OperatorService/GetListOfAvailableOperators



    @GET("services/app/OperatorService/GetListOfAvailableOperators")
    Call<AvailableProvider> getAllProvider();

    @POST("services/app/ComplaintService/ListOfUnAssignedComplaints")
    Call<UnAssignedComplain> getCompalinList();
    @POST("services/app/OperatorService/AssignComplaintToOperator")
    Call<AssignOperator> isAssign(@Body Assign assign);

@GET("services/app/ComplaintService/GetComplaintById")
    Call<ComplainStatus> getComplainStatus(@Query("ComplaintId") int ComplaintId);

    @GET("services/app/OperatorService/GetOperatorDetails")
    Call<OperatorDetails> getope(@Header("Authorization") String authorization);





















}
