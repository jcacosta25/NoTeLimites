package com.burocreativo.notelimites.screens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.burocreativo.notelimites.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Juan C. Acosta on 12/14/2016.
 */

public class DataBinder {

    private static final DateFormat DATE_FORMAT = DateFormat.getDateInstance();

    private DataBinder() {
    }

    @BindingAdapter("imageLoader")
    public static void setImageLoader(ImageView image, String url) {
        Context context = image.getContext();
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.back_inicio)
                .fitCenter()
                .into(image);
    }

    public static String formatDate(final Date date) {
        if (date != null) {
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("dd / MMM /yyyy");
            return df.format(date);
        } else {
            return "";
        }
    }

    public static String formatHour(final Date date) {
        if (date != null) {
            @SuppressLint("SimpleDateFormat")
            DateFormat df = new SimpleDateFormat("HH:mm");
            return df.format(date);
        } else {
            return "";
        }
    }

    public static String formatDay(final Date date) {
        if (date != null) {
            @SuppressLint("SimpleDateFormat")
            DateFormat df = new SimpleDateFormat("dd");
            return df.format(date);
        } else {
            return "";
        }
    }

    public static String formatMonth(final Date date) {
        if (date != null) {
            @SuppressLint("SimpleDateFormat")
            DateFormat df = new SimpleDateFormat("MMM");
            return df.format(date);
        } else {
            return "";
        }
    }

    public static Date parseDate(final String dateString) {
        try {
            return DATE_FORMAT.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatInt(final int i){
        return String.valueOf(i);
    }
}
