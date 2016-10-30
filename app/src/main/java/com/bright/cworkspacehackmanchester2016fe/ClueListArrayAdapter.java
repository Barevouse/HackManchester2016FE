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

public class ClueListArrayAdapter extends ArrayAdapter<Tweet> {

    List<Tweet> mClues;
    Context mContext;

    public ClueListArrayAdapter(Context context, int resource, List<Tweet> clues) {
        super(context, resource, clues);
        mClues = clues;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater ();
            row = inflater.inflate (R.layout.list_view_item, parent, false);
        }
        Tweet currentTweet = mClues.get(position);
        TextView status = (TextView) row.findViewById(R.id.message);
        status.setText(currentTweet.Text);
        TextView name = (TextView) row.findViewById(R.id.name);
        name.setText(currentTweet.Name  );
        TextView screenName= (TextView) row.findViewById(R.id.screenName);
        screenName.setText("@" + currentTweet.ScreenName);

        ImageView profilePictureImageView = (ImageView) row.findViewById(R.id.image_view_profile_picture);
        URL url = null;
        try {
            url = new URL(currentTweet.ProfileImageUrl);
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

        if(!currentTweet.MediaUrls.isEmpty()){
        ImageView tweetAttachmentImageView = (ImageView) row.findViewById(R.id.image_view_tweet_attachment);
        URL mediaUrl = null;
        try {
            mediaUrl = new URL(currentTweet.MediaUrls.get(0));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Bitmap tweetAttachmentBitmap = null;
        try {
            tweetAttachmentBitmap = BitmapFactory.decodeStream(mediaUrl.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
            tweetAttachmentImageView.setImageBitmap(tweetAttachmentBitmap);
        }
        return row;
    }
}
