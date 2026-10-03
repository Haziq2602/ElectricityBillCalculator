package com.example.electricitybillcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    public interface OnHistoryActionListener {
        void onLoad(BillHistoryItem item);
        void onDelete(BillHistoryItem item);
        void onShare(BillHistoryItem item);
    }

    private final Context context;
    private List<BillHistoryItem> items = new ArrayList<>();
    private final OnHistoryActionListener listener;

    public HistoryAdapter(Context context, OnHistoryActionListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setItems(List<BillHistoryItem> items) {
        this.items = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BillHistoryItem item = items.get(position);

        holder.tvHistoryProvider.setText(item.getProvider());
        holder.tvHistoryUnits.setText(String.format(Locale.getDefault(), "%d kWh", item.getUnits()));
        holder.tvHistoryTotal.setText(String.format(Locale.getDefault(), "₹ %.2f", item.getTotalBill()));
        holder.tvHistoryDate.setText(item.getFormattedDate());

        if (item.isBpl()) {
            holder.tvHistoryBadge.setText("BPL Subsidized");
            holder.tvHistoryBadge.setTextColor(ContextCompat.getColor(context, R.color.bpl_green_text));
            holder.tvHistoryBadge.setBackgroundResource(R.drawable.bg_badge_bpl);
        } else {
            holder.tvHistoryBadge.setText("Standard Tariff");
            holder.tvHistoryBadge.setTextColor(ContextCompat.getColor(context, R.color.standard_slate_text));
            holder.tvHistoryBadge.setBackgroundResource(R.drawable.bg_badge_standard);
        }

        String breakdown = String.format(Locale.getDefault(),
                "Energy: ₹%.2f • Wheeling: ₹%.2f • Fixed: ₹%.2f (Avg: ₹%.2f/u)",
                item.getBaseEnergy(), item.getWheelingCharge(), item.getFixedCharge(), item.getAverageRatePerUnit());
        holder.tvHistoryBreakdown.setText(breakdown);

        holder.btnHistoryLoad.setOnClickListener(v -> {
            if (listener != null) listener.onLoad(item);
        });

        holder.btnDeleteHistory.setOnClickListener(v -> {
            if (listener != null) listener.onDelete(item);
        });

        holder.btnHistoryShare.setOnClickListener(v -> {
            if (listener != null) listener.onShare(item);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHistoryProvider, tvHistoryBadge, tvHistoryUnits, tvHistoryTotal, tvHistoryDate, tvHistoryBreakdown;
        ImageView btnDeleteHistory;
        MaterialButton btnHistoryLoad, btnHistoryShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHistoryProvider = itemView.findViewById(R.id.tvHistoryProvider);
            tvHistoryBadge = itemView.findViewById(R.id.tvHistoryBadge);
            tvHistoryUnits = itemView.findViewById(R.id.tvHistoryUnits);
            tvHistoryTotal = itemView.findViewById(R.id.tvHistoryTotal);
            tvHistoryDate = itemView.findViewById(R.id.tvHistoryDate);
            tvHistoryBreakdown = itemView.findViewById(R.id.tvHistoryBreakdown);
            btnDeleteHistory = itemView.findViewById(R.id.btnDeleteHistory);
            btnHistoryLoad = itemView.findViewById(R.id.btnHistoryLoad);
            btnHistoryShare = itemView.findViewById(R.id.btnHistoryShare);
        }
    }
}
