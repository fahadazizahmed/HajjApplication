package capitalcryptoworld.capitalworld.com.newhajjapp.Model;

import java.io.Serializable;

/**
 * Created by Fahad Aziz on 29/05/2018.
 */

public class ComplainUser implements Serializable {
    private int id;



    private String title;



    private String priority;

    private String description;



    private String creationTime;

    private String isDeleted;

    private String oppId;



    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }



    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }



    public String getPriority ()
    {
        return priority;
    }

    public void setPriority (String priority)
    {
        this.priority = priority;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }



    public String getCreationTime ()
    {
        return creationTime;
    }

    public void setCreationTime (String creationTime)
    {
        this.creationTime = creationTime;
    }

    public String getIsDeleted ()
    {
        return isDeleted;
    }

    public void setIsDeleted (String isDeleted)
    {
        this.isDeleted = isDeleted;
    }

    public String getOppId ()
    {
        return oppId;
    }

    public void setOppId (String oppId)
    {
        this.oppId = oppId;
    }


}
