package com.chen.sunnyweather.ui.place;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chen.sunnyweather.R;
import com.chen.sunnyweather.logic.model.place.Place;
import com.chen.sunnyweather.ui.weather.WeatherActivity;

import java.util.List;

/**
 * PlaceRecycleView的适配器
 */
public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private final Fragment fragment;
    private final List<Place> placeList;

    public PlaceAdapter(Fragment fragment, List<Place> placeList) {
        this.fragment = fragment;
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(v->{
            int position = viewHolder.getAbsoluteAdapterPosition();
            Place place = placeList.get(position);
            Intent intent = new Intent(parent.getContext(), WeatherActivity.class);
            intent.putExtra("location_lng",place.getLocation().getLng());
            intent.putExtra("location_lat",place.getLocation().getLat());
            intent.putExtra("place_name",place.getName());
            fragment.startActivity(intent);
            if (fragment.getActivity()!=null) {
                fragment.getActivity().finish();//销毁当前绑定的MainActivity，和fragment自己，跳转到WeatherActivity后不再需要之前的Activity
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Place place = placeList.get(position);
        holder.placeName.setText(place.getName());
        holder.placeAddress.setText(place.getAddress());
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView placeName;
        private TextView placeAddress;

        public TextView getPlaceName() {
            return placeName;
        }

        public void setPlaceName(TextView placeName) {
            this.placeName = placeName;
        }

        public TextView getPlaceAddress() {
            return placeAddress;
        }

        public void setPlaceAddress(TextView placeAddress) {
            this.placeAddress = placeAddress;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.placeName);
            placeAddress = itemView.findViewById(R.id.placeAddress);
        }
    }
}
