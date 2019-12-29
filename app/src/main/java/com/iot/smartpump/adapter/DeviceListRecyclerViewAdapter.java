package com.iot.smartpump.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.iot.smartpump.R;
import com.iot.smartpump.model.DeviceData;

import java.util.ArrayList;

public class DeviceListRecyclerViewAdapter extends RecyclerView.Adapter<DeviceListRecyclerViewAdapter.ViewHolder> {

    private ArrayList<DeviceData> mValues;
    private Context mContext;

    public DeviceListRecyclerViewAdapter(ArrayList<DeviceData> list, Context context) {
        mValues = list;
        mContext = context;
    }

    public void setList(ArrayList<DeviceData> list) {
        this.mValues = list;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_device_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DeviceData model = mValues.get(position);
        holder.mTvDeviceName.setText(model.getDeviceName());
        holder.tv_device_no.setText(model.getDeviceNo());
//        if (model.state()==1){
//            holder.mImgDevice.setColorFilter(ContextCompat.getColor(mContext,R.color.bg));
//            holder.tv_device_status.setText("On");
//        }else{
//            holder.mImgDevice.setColorFilter(ContextCompat.getColor(mContext,R.color.black));
//            holder.tv_device_status.setText("Off");
//        }

    }

    public ArrayList<DeviceData> getList() {
        return (ArrayList<DeviceData>) this.mValues;
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final ImageView mImgDevice;
        final TextView mTvDeviceName;
        final TextView tv_device_no;
        final TextView tv_device_status;


        public ViewHolder(View view) {
            super(view);
//            mImgDevice = view.findViewById(R.id.img_device);
            mTvDeviceName = view.findViewById(R.id.tv_device_name);
            tv_device_no = view.findViewById(R.id.tv_device_no);
            tv_device_status = view.findViewById(R.id.tv_device_status);
        }

    }
}
