package capitalcryptoworld.capitalworld.com.newhajjapp.Model;

/**
 * Created by Fahad Aziz on 03/06/2018.
 */

public class Assign {
    int complaintId;


    public Assign(int complaintId, int operatorId) {
        this.complaintId = complaintId;
        this.operatorId = operatorId;
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

    int  operatorId;
}
