package com.otto.testonlinedb;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class UpdatePerson extends AppCompatActivity {


    String json_string, JSON_STRING, get_name, name, email, mobile;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ContactAdapter contactAdapter;
    ListView listView;
    EditText Name, Email, Mobile, Get_name;
    ArrayList blah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_person);
        Name =(EditText)findViewById(R.id.get_name);
        Get_name =(EditText)findViewById(R.id.et_get_name);
        Email =(EditText)findViewById(R.id.et_email);
        Mobile =(EditText)findViewById(R.id.et_mobile);

        get_name = Name.getText().toString();

       // BackgroundTask backgroundTask =new BackgroundTask();
       // backgroundTask.execute(get_name);



        new BackgroundTask1().execute();







    }

    public void updatePerson(View view)
    {
        name = Name.getText().toString();
        email = Email.getText().toString();
        mobile = Mobile.getText().toString();
        BackgroundTask2 backgroundTask2 =new BackgroundTask2();
        backgroundTask2.execute(name, email, mobile);
        finish();
    }





    public void searchPerson(View view)
    {

        try {
            jsonObject = new JSONObject(JSON_STRING);
            jsonArray = jsonObject.getJSONArray("server_response");
            int count= 0;
            String name, email, mobile;
            int test = 0;
            while(count<jsonArray.length())
            {
                JSONObject JO = jsonArray.getJSONObject(count);
                name = JO.getString("name");
                email = JO.getString("email");
                mobile = JO.getString("mobile");

                if(Get_name.getText().toString().equals(name)) {
                    Name.setText(name);
                    Email.setText(email);
                    Mobile.setText(mobile);
                    test =1;
                }


                count++;

            }

            if(test == 0)
            {
                TextView textView = (TextView) findViewById(R.id.textView2);
                textView.setVisibility(View.VISIBLE);
                textView.setText("Person not found");
            }
            else
            {
                TextView textView = (TextView) findViewById(R.id.textView2);
                textView.setText("Person found");
                textView.setVisibility(View.VISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }




    class BackgroundTask1 extends AsyncTask<Void,Void, String>
    {
        ProgressDialog loading;

        String json_url;
        @Override
        protected void onPreExecute()
        {
            json_url = "http://beekeeper.site88.net/json_get_data.php";
            loading = ProgressDialog.show(UpdatePerson.this,"Fetching...","Wait...",false,false);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine())!=null)
                {

                    stringBuilder.append(JSON_STRING+"/n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result)
        {

            //TextView textView = (TextView) findViewById(R.id.textView2);
           // textView.setText(result);
            JSON_STRING=result;
            loading.dismiss();
        }

    }

    class BackgroundTask2 extends AsyncTask<String,Void,String>
    {

        String add_info_url;
        @Override
        protected void onPreExecute() {
            add_info_url="http://beekeeper.site88.net/update_person.php";

        }


        @Override
        protected String doInBackground(String... args) {
            String name;
            name=args[0];


            try {
                URL url = new URL (add_info_url);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String data_string = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                inputStream.close();
                httpURLConnection.disconnect();
                return"One row of data inserted";


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }


    }



}