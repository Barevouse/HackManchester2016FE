package com.bright.cworkspacehackmanchester2016fe;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Daniel.Harrison on 30/10/2016.
 */

public class MissionListArrayAdapter extends ArrayAdapter<Mission> {

    private List<Mission> mMissions;
    private Context mContext;

    public MissionListArrayAdapter(Context context, int resource, List<Mission> objects) {
        super(context, resource, objects);
        mMissions = objects;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater ();
            row = inflater.inflate (R.layout.mission_list_item, parent, false);
        }
        Mission currentMission = mMissions.get(position);

        TextView screenName = (TextView) row.findViewById(R.id.text_view_screen_name);
        TextView name = (TextView) row.findViewById(R.id.text_view_name);

        name.setText(currentMission.Name);
        screenName.setText("@" + currentMission.ScreenName);

        ImageView profilePictureImageView = (ImageView) row.findViewById(R.id.image_view_profile_picture);
        URL url = null;
        try {
            url = new URL(currentMission.ProfileImageUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        Bitmap profilePicture = null;
        try {
            profilePicture = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        profilePictureImageView.setImageBitmap(profilePicture);
        return row;
    }
}
