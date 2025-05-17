package com.example.reminder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        holder.description.setText(reminderNormal.getDescription());
        holder.date.setText(reminderNormal.getDate());
        holder.dateG.setText(reminderNormal.getDateGregorian());
        holder.Time.setText(reminderNormal.getTime());
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

