package com.example.reminder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;

public class AdaptorReminderNormal<onSaveIn> extends RecyclerView.Adapter<AdaptorReminderNormal.ViewHolder> {
    @NonNull

  private List<ReminderNormal> reminderNormalLists;
    NormalRemindersDatabaseHelper normalRemindersDatabaseHelper;


    private static Context mContext;






    public AdaptorReminderNormal(Context context, List<ReminderNormal> reminderNormalLists) {
        this.reminderNormalLists = reminderNormalLists;
        mContext = context;
    }







    @Override
    public AdaptorReminderNormal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remindernormaltak, parent, false);




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptorReminderNormal.ViewHolder holder, int position) {

        ReminderNormal reminderNormal = reminderNormalLists.get(position);

        holder.description.setText(reminderNormal.getDescription());
        holder.date.setText(reminderNormal.getDate());
        holder.dateG.setText(reminderNormal.getDateGregorian());
        holder.Time.setText(reminderNormal.getTime());




        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    int reminderIdToDelete = reminderNormalLists.get(adapterPosition).getId(); // Assuming you have a unique ID for each item


                    reminderNormalLists.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);

                    Log.d("cancelAlarmA", "Deleting reminder with ID: " + reminderIdToDelete);
                   // int ID=reminderNormal.getId();
                    NormalRemindersDatabaseHelper dbHelper = new NormalRemindersDatabaseHelper(mContext);
                    dbHelper.deleteReminder(reminderIdToDelete);


                    AlarmReceiver alarmReceiver = new AlarmReceiver();

                     alarmReceiver.cancelAlarm(mContext, reminderIdToDelete);


                }




            }
        });

        // Set click listener for the Edit button

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent=new Intent(mContext, Itemtak.class);
                intent.putExtra("description",reminderNormal.getDescription());
                intent.putExtra("date",reminderNormal.getDate());
                intent.putExtra("date_G",reminderNormal.getDateGregorian());
                intent.putExtra("time",reminderNormal.getTime());

               mContext.startActivity(intent);

            }
        });







    }





    @Override
    public int getItemCount(){
       return reminderNormalLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView description;
        public TextView date;
        public TextView dateG;
        public TextView Time;
        SwipeLayout leftSwipeLayout;
        Button deleteButton;





        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.data_reminderN);
            description = (TextView) itemView.findViewById(R.id.text_reminderN);
            dateG = (TextView) itemView.findViewById(R.id.dataG_reminderN);
            Time = (TextView) itemView.findViewById(R.id.time_reminderN);
            leftSwipeLayout = itemView.findViewById(R.id.swipeLayout);
            deleteButton = itemView.findViewById(R.id.deletItem);









        }





    }


    public void updateData(List<ReminderNormal> newReminders) {
        reminderNormalLists.clear();
        reminderNormalLists.addAll(newReminders);
        notifyDataSetChanged();
    }




}
