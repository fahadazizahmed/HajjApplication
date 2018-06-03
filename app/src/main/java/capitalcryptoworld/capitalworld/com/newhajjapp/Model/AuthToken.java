package capitalcryptoworld.capitalworld.com.newhajjapp.Model;

/**
 * Created by Fahad Aziz on 16/05/2018.
 */

public class AuthToken {
    public MyResult getResult() {
        return result;
    }

    public void setResult(MyResult result) {
        this.result = result;
    }

    MyResult result;

    public class  MyResult{
        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        String accessToken;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        int userId;




          String usersRole;

        public String getUsersRole() {
            return usersRole;
        }

        public void setUsersRole(String usersRole) {
            this.usersRole = usersRole;
        }

        public int getAccId() {
            return accId;
        }

        public void setAccId(int accId) {
            this.accId = accId;
        }

        public int getComplaintId() {
            return complaintId;
        }

        public void setComplaintId(int complaintId) {
            this.complaintId = complaintId;
        }

        public int getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(int operatorId) {
            this.operatorId = operatorId;
        }

        public boolean isHajjRegistration() {
            return hajjRegistration;
        }

        public void setHajjRegistration(boolean hajjRegistration) {
            this.hajjRegistration = hajjRegistration;
        }

        int accId;
                  int complaintId;
                  int operatorId;
                  boolean hajjRegistration;














    }


}
