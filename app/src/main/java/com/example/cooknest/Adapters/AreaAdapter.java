package com.example.cooknest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cooknest.R;
import com.example.cooknest.data.model.Area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
    private List<Area> areas;
    private final OnAreaClickListener listener;
    private final Map<String, String> areaToIsoCode;

    public interface OnAreaClickListener {
        void onAreaClick(String area);
    }

    public AreaAdapter(List<Area> areas, OnAreaClickListener listener, Context context) {
        this.areas = areas != null ? areas : new ArrayList<>();
        this.listener = listener;
        this.areaToIsoCode = loadAreaIsoCodes(context);
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas != null ? areas : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_country_with_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Area area = areas.get(position);
        holder.bind(area);
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    private Map<String, String> loadAreaIsoCodes(Context context) {
        Map<String, String> map = new HashMap<>();
        String[] areaIsoPairs = context.getResources().getStringArray(R.array.area_iso_codes);
        for (String pair : areaIsoPairs) {
            String[] parts = pair.split(",");
            if (parts.length == 2) {
                map.put(parts[0].toLowerCase(), parts[1]);
            }
        }
        return map;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView countryImage;
        private final TextView countryName;

        ViewHolder(View view) {
            super(view);
            countryImage = view.findViewById(R.id.country_image);
            countryName = view.findViewById(R.id.country_name);
            view.setOnClickListener(v -> listener.onAreaClick(areas.get(getAdapterPosition()).getStrArea()));
        }

        void bind(Area area) {
            countryName.setText(area.getStrArea());
            String imageUrl = getFlagImageUrl(area.getStrArea());
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .into(countryImage);
        }

        private String getFlagImageUrl(String area) {
            String isoCode = areaToIsoCode.get(area.toLowerCase());
            if (isoCode != null) {
                return "https://flagcdn.com/w80/" + isoCode + ".png";
            }
            return null;
        }
    }
}