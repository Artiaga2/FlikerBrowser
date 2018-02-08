package com.example.a2_daw.flikerbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

/**
 * Created by 2_DAW on 08/02/2018.
 */

enum DownloadStatus {
    IDLE,               // NO ESTA PROCESANDO NADA
    PROCESSING,         // DESCARGANDO DATOS
    NOT_INITIALIZE,     // NO TENEMOS UNA URL VALIDA
    FAILED_OR_EMPTY,    // FALLO AL OPTENER RESPUESTA
    OK                  // OK
}


public class GetRawData {

    private static final String LOG_TAG  = GetRawData.class.getSimpleName();

    private String mRawUrl;                         //para la Url
    private String mData;                           //Respuesta de la url
    private DownloadStatus mDownloadStatus;         //Estado de la peticion

    public GetRawData(String mRawUrl) {
        this.mRawUrl = mRawUrl;
        mDownloadStatus = DownloadStatus.IDLE;
    }

    public String getmRawUrl() {
        return mRawUrl;
    }

    public String getmData() {
        return mData;
    }

    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }

    public void reset(){
        mDownloadStatus = DownloadStatus.IDLE;
        mRawUrl = null;
        mData = null;
    }

    public void execute(){
        mDownloadStatus = DownloadStatus.PROCESSING;
        DownloadRawData  downloadRawData = new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }

    public class DownloadRawData extends AsyncTask <String, Void, String>{

        @Override
        protected void onPostExecute(String webData) {
            super.onPostExecute(webData);

            mData = webData;
            Log.d(LOG_TAG, "Reponse" + mData);
            if (mData == null){
                if (mRawUrl == null){
                    mDownloadStatus = DownloadStatus.NOT_INITIALIZE;
                }else {
                    mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            }else {
                mDownloadStatus = DownloadStatus.OK;
            }
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            if( params == null ){
                return null;
            }

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if( inputStream == null ){
                    return null;
                }

                StringBuffer buffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while( (line = reader.readLine()) != null ) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "URL incorrecta");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Error al establecer conexión con la URL");
            } finally {
                if( urlConnection != null ) {
                    urlConnection.disconnect();
                }

                if ( reader != null ) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(LOG_TAG, "Error cerrando el buffer");
                    }
                }
            }
            return null;
        }
    }
}
