package tw.kaiyeee.triptao.app;

import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tw.kaiyeee.triptao.R;

/**
 * Created by Home on 2017/9/24.
 */
//demo01_ticket_RecyclerAdapter
public class demo01_ticket_RecyclerAdapter extends RecyclerView.Adapter<demo01_ticket_RecyclerAdapter.MyViewHolder> {

    private String[] titles = {"搭乘公車半價優惠" ,
            "味全埔心牧場門票九折" ,
            "特約商店九折",
            "紅利點數加倍送"};
    private String[] deadline = {"2018/12/31","2018/03/31","2018/12/31","2017/11/30"};

    class MyViewHolder extends RecyclerView.ViewHolder{

        Button getaward;

        public TextView itemTitle;
        public TextView deadline;


        public CountDownTimer countDownTimer;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.getaward = (Button) itemView.findViewById(R.id.getaward);
            itemTitle = (TextView)itemView.findViewById(R.id.item_title);
            deadline = (TextView)itemView.findViewById(R.id.deadline);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.demo01_ticket_design, viewGroup, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }


    public void onBindViewHolder(final MyViewHolder viewHolder, int i) {
        viewHolder.itemTitle.setText(titles[i]);
        viewHolder.deadline.setText(deadline[i]);
        viewHolder.getaward.setOnClickListener(new View.OnClickListener(){
            /*倒數功能*/
            @Override
            public void onClick(View v){

                viewHolder.countDownTimer = new CountDownTimer(120*1000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                        viewHolder.getaward.setText(String.valueOf(millisUntilFinished/1000));

                    }

                    @Override
                    public void onFinish() {
                        viewHolder.getaward.setText("領取完畢");
                        viewHolder.getaward.setClickable(false);
                    }
                };
                viewHolder.countDownTimer.start();
                viewHolder.getaward.setClickable(false);



            }
        });





    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}