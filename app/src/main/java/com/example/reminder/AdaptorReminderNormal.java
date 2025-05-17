package com.example.reminder;

<<<<<<< HEAD
import android.app.AlertDialog;
=======
>>>>>>> 77aff04ef85fb5482cb1dc970a147d9a39d00221
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
<<<<<<< HEAD
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AdaptorReminderNormal<onSaveIn> extends RecyclerView.Adapter<AdaptorReminderNormal.ViewHolder> {
    private static Context mContext;
    NormalRemindersDatabaseHelper normalRemindersDatabaseHelper;
    private List<ReminderNormal> reminderNormalLists;

    public AdaptorReminderNormal(Context context, List<ReminderNormal> reminderNormalLists2) {
        this.reminderNormalLists = reminderNormalLists2;
        mContext = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.remindernormaltak, parent, false));
    }

    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ReminderNormal reminderNormal = this.reminderNormalLists.get(position);
=======
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

>>>>>>> 77aff04ef85fb5482cb1dc970a147d9a39d00221
        holder.description.setText(reminderNormal.getDescription());
        holder.date.setText(reminderNormal.getDate());
        holder.dateG.setText(reminderNormal.getDateGregorian());
        holder.Time.setText(reminderNormal.getTime());
<<<<<<< HEAD
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            /* class com.example.reminder.AdaptorReminderNormal.AnonymousClass1 */

            public void onClick(View view) {
                Intent intent = new Intent(AdaptorReminderNormal.mContext, Itemtak.class);
                intent.putExtra(NormalRemindersDatabaseHelper.COLUMN_DESCRIPTION, reminderNormal.getDescription());
                intent.putExtra(NormalRemindersDatabaseHelper.COLUMN_Date, reminderNormal.getDate());
                intent.putExtra("date_G", reminderNormal.getDateGregorian());
                intent.putExtra(NormalRemindersDatabaseHelper.COLUMN_time, reminderNormal.getTime());
                AdaptorReminderNormal.mContext.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            /* class com.example.reminder.AdaptorReminderNormal.AnonymousClass2 */

            public boolean onLongClick(View v) {
                holder.buttonContainer.setVisibility(View.VISIBLE);
                return true;
            }
        });
        holder.buttonContainer.setVisibility(View.GONE);
        holder.buttonEdit.setOnClickListener(new View.OnClickListener() {
            /* class com.example.reminder.AdaptorReminderNormal.AnonymousClass3 */

            public void onClick(View v) {
                holder.buttonContainer.setVisibility(View.GONE);
                AlertDialog.Builder builder = new AlertDialog.Builder(AdaptorReminderNormal.mContext);
                View dialogView = LayoutInflater.from(AdaptorReminderNormal.mContext).inflate(R.layout.custom_dialog_layout, (ViewGroup) null);
                builder.setView(dialogView);
                final AlertDialog dialog = builder.create();
                ((Button) dialogView.findViewById(R.id.Editone)).setOnClickListener(new View.OnClickListener() {
                    /* class com.example.reminder.AdaptorReminderNormal.AnonymousClass3.AnonymousClass1 */

                    public void onClick(View view) {
                        Intent intent = new Intent(AdaptorReminderNormal.mContext, NewReminderNormalActivity.class);
                        intent.putExtra("IDremindertoedit", ((ReminderNormal) AdaptorReminderNormal.this.reminderNormalLists.get(holder.getAdapterPosition())).getId());
                        intent.putExtra(NormalRemindersDatabaseHelper.COLUMN_DESCRIPTION, reminderNormal.getDescription());
                        intent.putExtra(NormalRemindersDatabaseHelper.COLUMN_Date, reminderNormal.getDate());
                        intent.putExtra(NormalRemindersDatabaseHelper.COLUMN_DateG, reminderNormal.getDateGregorian());
                        intent.putExtra(NormalRemindersDatabaseHelper.COLUMN_time, reminderNormal.getTime());
                        AdaptorReminderNormal.mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                ((Button) dialogView.findViewById(R.id.EditAll)).setOnClickListener(new View.OnClickListener() {
                    /* class com.example.reminder.AdaptorReminderNormal.AnonymousClass3.AnonymousClass2 */

                    public void onClick(View view) {
                        Intent intent = new Intent(AdaptorReminderNormal.mContext, NewReminderNormalActivity.class);
                        intent.putExtra("IDremindertoedit", ((ReminderNormal) AdaptorReminderNormal.this.reminderNormalLists.get(holder.getAdapterPosition())).getId());
                        intent.putExtra("kindofrepeatEdite", reminderNormal.getKindofrepeat());
                        // Log.d("kindof", "kind on adapter" + reminderNormal.getKindofrepeat());
                        intent.putExtra("numberrepeatEdite", reminderNormal.getNumberrepeat());
                       // Log.d("numberofA", "number on adapter" + reminderNormal.getNumberrepeat());
                        intent.putExtra("repeat_id", reminderNormal.getRepeat_id());
                        intent.putExtra(NormalRemindersDatabaseHelper.COLUMN_DESCRIPTION, reminderNormal.getDescription());
                        intent.putExtra(NormalRemindersDatabaseHelper.COLUMN_Date, reminderNormal.getDate());
                        intent.putExtra(NormalRemindersDatabaseHelper.COLUMN_DateG, reminderNormal.getDateGregorian());
                        intent.putExtra(NormalRemindersDatabaseHelper.COLUMN_time, reminderNormal.getTime());
                        AdaptorReminderNormal.mContext.startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setGravity(80);
                dialog.show();
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            /* class com.example.reminder.AdaptorReminderNormal.AnonymousClass4 */

            public void onClick(View v) {
                holder.buttonContainer.setVisibility(View.GONE);
                AdaptorReminderNormal.this.deleteReminder(holder.getAdapterPosition());
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.reminderNormalLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Time;
        LinearLayout buttonContainer;
        public Button buttonDelete;
        public Button buttonEdit;
        public TextView date;
        public TextView dateG;
        public TextView description;

        public ViewHolder(View itemView) {
            super(itemView);
            this.date = (TextView) itemView.findViewById(R.id.data_reminderN);
            this.description = (TextView) itemView.findViewById(R.id.text_reminderN);
            this.dateG = (TextView) itemView.findViewById(R.id.dataG_reminderN);
            this.Time = (TextView) itemView.findViewById(R.id.time_reminderN);
            this.buttonContainer = (LinearLayout) itemView.findViewById(R.id.buttonContainer);
            this.buttonEdit = (Button) itemView.findViewById(R.id.buttonEdit);
            this.buttonDelete = (Button) itemView.findViewById(R.id.buttonDelete);
        }
    }

    public void updateData(List<ReminderNormal> newReminders) {
        this.reminderNormalLists.clear();
        this.reminderNormalLists.addAll(newReminders);
        notifyDataSetChanged();
    }

    public void deleteReminder(int adapterPosition) {
        Log.d("IDrepeat", "iid: " + adapterPosition);
        if (adapterPosition != -1) {
            int reminderIdToDelete = this.reminderNormalLists.get(adapterPosition).getId();
            this.reminderNormalLists.remove(adapterPosition);
            notifyItemRemoved(adapterPosition);
            new NormalRemindersDatabaseHelper(mContext).deleteReminder(reminderIdToDelete);
           // new AlarmReceiver().cancelAlarm(mContext, reminderIdToDelete);
        }
    }
}

=======




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
>>>>>>> 77aff04ef85fb5482cb1dc970a147d9a39d00221
