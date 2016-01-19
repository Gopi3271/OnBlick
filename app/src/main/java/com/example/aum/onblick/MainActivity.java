package com.example.aum.onblick;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Looper;
import android.widget.ListView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity

{

    ListView listView;
    StringBuilder builder=new StringBuilder();
    ProgressDialog dialog;
    ArrayList<MyApp> list=new ArrayList<MyApp>();
    MyAdapter myAdapter;

    public boolean isNetConnect(){
        ConnectivityManager manager=(ConnectivityManager)this.getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        return (info==null&&info.isConnected());
    }

   /* public void processResponse()
    {

        try {
            JSONObject jsonObject=new JSONObject(builder.toString());
            JSONArray jsonArray=jsonObject.getJSONArray("");
            String c="",v="";
            for(int i=0;i<jsonArray.length();i++){
                c=jsonArray.getJSONObject(i).getString("color");
                v=jsonArray.getJSONObject(i).getString("value");
                list.add(new MyApp(c,v));
            }

            myAdapter=new MyAdapter(this,R.layout.list_items_adapter,list);
            listView.setAdapter(myAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_item);


        ConnectivityManager connection=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info=connection.getActiveNetworkInfo();
        if(info.isConnected()) {

           // new MyThread().start();
            try {
                URL url=new URL("http://cdn.crunchify.com/wp-content/uploads/code/jsonArray.txt");
                new MyAsynTask().execute(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        else
        {
            Toast.makeText(MainActivity.this, "No network", Toast.LENGTH_SHORT).show();
        }
    }

  /*  Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           if( msg.what==101)
           {
              // processResponse();
                dialog.dismiss();
               Toast.makeText(MainActivity.this, "get Retreived", Toast.LENGTH_SHORT).show();

            }
        }
    };

    class MyThread extends Thread
    {

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            try {
                URL url=new URL("http://cdn.crunchify.com/wp-content/uploads/code/jsonArray.txt");
               //request
                HttpURLConnection connection=(HttpURLConnection)url.openConnection();
              //response from server
                InputStream inputStream=connection.getInputStream();
                InputStreamReader inputStreamReader=new InputStreamReader(inputStream);
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
                String line=bufferedReader.readLine();
                while (line!=null){

                    builder.append(line);
                    builder.append("\n");
                    line=bufferedReader.readLine();

                }
                Log.i("Object",builder.toString());
                myHandler.sendEmptyMessage(101);

            } catch (Exception e) {
                e.printStackTrace();
            }

            Looper.loop();
        }
    }*/


    protected class MyAsynTask extends AsyncTask<URL,Integer,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setMessage("Progressing..!");
            dialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(URL... params) {

            try {

                HttpURLConnection connection=(HttpURLConnection)params[0].openConnection();

                InputStream is=connection.getInputStream();
                InputStreamReader isr=new InputStreamReader(is);
                BufferedReader br=new BufferedReader(isr);
                String line=br.readLine();
                while(line!=null){

                    builder.append(line);
                    builder.append("\n");
                    line=br.readLine();
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            Log.i("From Server", builder.toString());

            return builder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            s=builder.toString();

            try {
                JSONObject jsonObject=new JSONObject(s);
                JSONArray jArray=jsonObject.getJSONArray(new String());
                String c="",v="";
                for(int i=0;i<jArray.length();i++)
                {

                    c=jArray.getJSONObject(i).getString("color");
                    v=jArray.getJSONObject(i).getString("value");
                    list.add(new MyApp(c,v));
                    Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            myAdapter=new MyAdapter(MainActivity.this,R.layout.list_items_adapter,list);
            listView.setAdapter(myAdapter);
        }
    }
}