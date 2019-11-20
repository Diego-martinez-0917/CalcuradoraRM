package com.example.drica.calcuradorarm;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {
    private View view;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
    public void setdetails(Context context, String title , String peso, String rep, String rm, String descrip, int image){
        TextView textViewtitulo = view.findViewById(R.id.titulocard);
        TextView textViewpeso = view.findViewById(R.id.textVpesocard);
        TextView textViewtrep = view.findViewById(R.id.textVrepcard);
        TextView textViewrm = view.findViewById(R.id.textVrmcard);
        TextView textViewcomenta = view.findViewById(R.id.textVcomencard);
        ImageView imageViewcard = view.findViewById(R.id.imagecard);

        textViewtitulo.setText(title);
        textViewpeso.setText(peso);
        textViewtrep.setText(rep);
        textViewrm.setText(rm);
        textViewcomenta.setText(descrip);
        imageViewcard.setImageResource(image);
    }
}
