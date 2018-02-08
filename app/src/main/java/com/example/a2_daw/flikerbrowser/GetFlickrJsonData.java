package com.example.a2_daw.flikerbrowser;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2_DAW on 08/02/2018.
 */

public class GetFlickrJsonData extends GetRawData {

    private static final String LOG_TAG = GetFlickrJsonData.class.getSimpleName();
    private List<Photo> mPhotos;
    private Uri mDestinationUri;

    public GetFlickrJsonData(String searchCriteria, boolean matchAll) {
        super(null);
        createAndUpdateUri(searchCriteria, matchAll);
        mPhotos = new ArrayList<>();
    }

    private boolean createAndUpdateUri(String searchCriteria, boolean matchAll) {
        final String FLICKR_BASE_API_URL = "https://api.flickr.com/services/feeds/photos_public.gne";
        final String TAGS_PARAM = "tags";
        final String TAGMODE_PARAM = "tagmode";
        final String FORMAT_PARAM = "format";
        final String NO_JSON_CALLBACK_PARAM = "nojsoncallback";


        mDestinationUri = Uri.parse(FLICKR_BASE_API_URL).buildUpon()
                .appendQueryParameter(TAGS_PARAM, searchCriteria)
                .appendQueryParameter(TAGMODE_PARAM, (matchAll?"all":"any"))
                .appendQueryParameter(FORMAT_PARAM, "json")
                .appendQueryParameter(NO_JSON_CALLBACK_PARAM, "1")
                .build();

        return mDestinationUri != null;
    }

    public void execute(){
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        Log.v(LOG_TAG, "Build Uri: " + mDestinationUri.toString());
        downloadJsonData.execute(mDestinationUri.toString());

    }

    public class DownloadJsonData extends DownloadRawData{

        private void processResult() {
            final String FLICKER_ITEMS = "items";
            final String FLICKER_TITLE = "title";
            final String FLICKER_MEDIA = "media";
            final String FLICKER_PHOTO_URL = "m";
            final String FLICKER_AUTHOR = "author";
            final String FLICKER_AUTHOR_ID = "author_id";
            final String FLICKER_LINKS = "link";
            final String FLICKER_TAGS = "tags";

            if (getmDownloadStatus() != DownloadStatus.OK){
                Log.e(LOG_TAG, "No se ga descargado el JSON");
                return;
            }

            try {
                JSONObject jsonData = new JSONObject(getmData());
                JSONArray itemsArray = jsonData.getJSONArray(FLICKER_ITEMS);

                for (int i = 0; i < itemsArray.length() ; i++) {
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString(FLICKER_TITLE);
                    String author = jsonPhoto.getString(FLICKER_AUTHOR);
                    String author_id = jsonPhoto.getString(FLICKER_AUTHOR_ID);
                    String link = jsonPhoto.getString(FLICKER_LINKS);
                    String tags = jsonPhoto.getString(FLICKER_TAGS);

                    JSONObject media = jsonPhoto.getJSONObject(FLICKER_MEDIA);
                    String photoUrl = media.getString(FLICKER_PHOTO_URL);

                    Photo photo = new Photo(title,link,author,author_id,photoUrl,tags);
                    mPhotos.add(photo);
                }

                for (Photo photo: mPhotos){
                    Log.d(LOG_TAG, "Photo: " + photo.toString());
                }

            }catch (JSONException e){
                Log.e(LOG_TAG, "No se puede crear el objeto JSON");
                e.printStackTrace();
            }

        }

        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);
            processResult();
        }

        @Override
        protected String doInBackground(String... params) {
            return super.doInBackground(params);
        }
    }

}
