package com.example.a2_daw.flikerbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        GetRawData getRawData = new GetRawData("https://api.flickr.com/services/feeds/photos_public.gne?tags=android,snapshot&tagmode=any&format=json&nojsoncallback=1");
//        getRawData.execute() ;

        GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData("android, snapshot", true);
        getFlickrJsonData.execute();
    }
}
