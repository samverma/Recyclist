package comsamverma.github.recyclist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sam on 3/23/2016.
 */
public class Listing {

    int id;
    int user_id;
    String title;
    String content;
    String cost;
    String state;
   // public int cost;
  //  @SerializedName("public")
   // public boolean premium;

  //  public boolean ispublic;

    Listing(){}

    Listing(int Id, int Uid, String Title, String Content,String Cost, String State)
    {
        id = Id;
        user_id = Uid;
        title = Title;
        content = Content;
        cost = Cost;
        state = State;
    }
}
