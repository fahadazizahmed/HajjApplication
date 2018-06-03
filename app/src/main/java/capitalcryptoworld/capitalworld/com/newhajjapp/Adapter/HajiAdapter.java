package capitalcryptoworld.capitalworld.com.newhajjapp.Adapter;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import capitalcryptoworld.capitalworld.com.newhajjapp.Model.HajiResult;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;

/**
 * Created by Fahad Aziz on 29/05/2018.
 */

public class HajiAdapter extends RecyclerView.Adapter<HajiAdapter.Holder> {
    private List<HajiResult> ListAccomodation;
    private int previousposition=0;
    private final FlowerClickListener mlistener;
    SharedPreferences spref;
    static String filename="my personal file";
    public static final String Value = "could not load data";
    //public static final String TAG=FlowerAdapter.class.getSimpleName();

    public HajiAdapter(FlowerClickListener listener)
    {
        ListAccomodation = new ArrayList<>();
        mlistener = listener;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.haji_row,parent,false);
        return new Holder(row);


    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        HajiResult userAccomdationList = ListAccomodation.get(position);
        holder.mName.setText(userAccomdationList.getPhone());
        holder.mLocation.setText(userAccomdationList.getGender());
        holder.mAvailable.setText(userAccomdationList.getAddress());

        byte[] decodedString = Base64.decode(userAccomdationList.getCnicPic(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imageView.setImageBitmap(decodedByte);
        /*holder.mPrice.setText("$"+Double.toString(currFlower.getPrice()));
        Picasso.with(holder.itemView.getContext()).load("http://services.hanselandpetal.com/photos/"+currFlower.getPhoto()).into(holder.mPhoto);
        if(position>previousposition){//We are scrolling down
            AnimationUtils.animate(holder,true);
        }
        else {
            AnimationUtils.animate(holder,false);
        }
        position=previousposition;
*/

    }

    @Override
    public int getItemCount() {
        return ListAccomodation.size();
    }

    public void addFlower(HajiResult allAccomodation) {

        ListAccomodation.add(allAccomodation);
        notifyDataSetChanged();
    }

    public HajiResult getAccomodation(int position)
    {
        return ListAccomodation.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // private ImageView mPhoto;
        private TextView mName,mAvailable,mLocation;
        ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            //  mPhoto=(ImageView)itemView.findViewById(R.id.flowerPhoto);
            mName=(TextView)itemView.findViewById(R.id.tx_name);
            mLocation=(TextView)itemView.findViewById(R.id.tx_location);
            mAvailable=(TextView)itemView.findViewById(R.id.tx_availabe);
            imageView = (ImageView)itemView.findViewById(R.id.img);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            mlistener.onClick(getLayoutPosition());

        }}
    public interface FlowerClickListener{
        void onClick(int position);

    }
}




