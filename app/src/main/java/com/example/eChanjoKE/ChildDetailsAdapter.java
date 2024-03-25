package com.example.eChanjoKE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChildDetailsAdapter extends RecyclerView.Adapter<ChildDetailsAdapter.ViewHolder> {

    // creating variables for our list, context, interface and position

    private ArrayList<ChildDetails> childDetailsArrayList;
    private Context context;
    private ChildClickInterface childClickInterface;
    int lastPos = -1;

    //creating a constructor
    public ChildDetailsAdapter(ArrayList<ChildDetails> childDetailsArrayList, Context context, ChildClickInterface childClickInterface){
        this.childDetailsArrayList = childDetailsArrayList;
        this.context = context;
        this.childClickInterface = childClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //infalating our view holder below line
        View view = LayoutInflater.from(context).inflate(R.layout.child_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //setting data to our recycler view on below line
        ChildDetails childDetails = childDetailsArrayList.get(position);
        holder.txtBirthCert.setText(childDetails.getCertNo());
        holder.txtFullName.setText(childDetails.getFullname());
        holder.txtDoB.setText(childDetails.getDoB());
        holder.txtGender.setText(childDetails.getGender());
        holder.txtFather_Name.setText(childDetails.getFathersName());
        holder.txtMother_Name.setText(childDetails.getMothersName());
        holder.txtAddress.setText(childDetails.getAddress());
        holder.txtWeight.setText(childDetails.getWeight());
        // adding animation to the recycler view item on the below line
        setAnimation(holder.itemView, position);
        holder.recImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childClickInterface.onChildClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        //on below line we are setting the animation
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        itemView.setAnimation(animation);
        lastPos = position;
    }

    @Override
    public int getItemCount() {return childDetailsArrayList.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //creating views for the textviews on below line
        private TextView txtBirthCert,txtFullName,txtDoB,txtGender,txtFather_Name,txtMother_Name,txtAddress,txtWeight;
        private ImageView recImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtBirthCert = itemView.findViewById(R.id.txtBirthCertificate);
            txtFullName = itemView.findViewById(R.id.txtFullName);
            txtDoB = itemView.findViewById(R.id.txtDoB);
            txtGender = itemView.findViewById(R.id.txtGender);
            txtFather_Name = itemView.findViewById(R.id.txtFatherName);
            txtMother_Name = itemView.findViewById(R.id.txtMotherName);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtWeight = itemView.findViewById(R.id.txtWeight);
            recImage = itemView.findViewById(R.id.recImage);

        }
    }

    // creating an interface for on click
    public interface ChildClickInterface{
        void onChildClick(int position);
    }

    public void filterList(ArrayList<ChildDetails> filteredList) {
        childDetailsArrayList = filteredList;
        notifyDataSetChanged();
    }

}
