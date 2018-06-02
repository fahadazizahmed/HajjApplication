package capitalcryptoworld.capitalworld.com.newhajjapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import capitalcryptoworld.capitalworld.com.newhajjapp.Model.MyProvider;
import capitalcryptoworld.capitalworld.com.newhajjapp.R;

/**
 * Created by Fahad Aziz on 03/06/2018.
 */

public class OperatorAdapter  extends RecyclerView.Adapter<OperatorAdapter.Holder> {
    private List<MyProvider> ListAccomodation;
    private int previousposition=0;
    private final FlowerClickListener mlistener;
    //int [] a = {R.drawable.na,R.drawable.nb,R.drawable.nc,R.drawable.nd,R.drawable.p1,R.drawable.p2,R.drawable.pa,R.drawable.r};
    //public static final String TAG=FlowerAdapter.class.getSimpleName();
    int[] a;

    public OperatorAdapter(FlowerClickListener listener)
    {
        ListAccomodation = new ArrayList<>();
        this.a = a;
        mlistener = listener;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_spin,null,false);
        return new Holder(row);


    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        MyProvider userAccomdationList = ListAccomodation.get(position);
        holder.textView.setText(userAccomdationList.getName());
        holder.textView1.setText(userAccomdationList.getMobile());
        holder.textView2.setText(userAccomdationList.getDesignation());

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

    public void addFlower(MyProvider allAccomodation) {

        ListAccomodation.add(allAccomodation);

        notifyDataSetChanged();
    }

    public MyProvider getAccomodation(int position)
    {
        return ListAccomodation.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textView,textView1,textView2;
        public Holder(View itemView) {
            super(itemView);
            //  mPhoto=(ImageView)itemView.findViewById(R.id.flowerPhoto);
            textView=(TextView)itemView.findViewById(R.id.text);
            textView1=(TextView)itemView.findViewById(R.id.text1);
            textView2=(TextView)itemView.findViewById(R.id.text2);

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



