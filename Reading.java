package com.example.finalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class Reading extends Fragment {

    sendDataToMain main;
    String answer1;


    int totalItems;
    ArrayList<String> titles;
    ArrayList<String> url;
    ArrayList<JSONObject> books;
    ArrayList<JSONObject> volume;
    ArrayList<JSONObject> links;
    ArrayList<JSONArray> authorList;
    ArrayList<ImageView> covers;
    ArrayList<TextView> textViewTitles;
    ArrayList<TextView> textViewAuthors;
    ImageView c1,c2,c3,c4;
    TextView b1,b2,b3,b4;
    TextView a1,a2,a3,a4;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_reading, null);

        a1 = fragmentView.findViewById(R.id.author);
        a2 = fragmentView.findViewById(R.id.author2);
        a3 = fragmentView.findViewById(R.id.author3);
        a4 = fragmentView.findViewById(R.id.author4);
        c1 = fragmentView.findViewById(R.id.cover);
        c2 = fragmentView.findViewById(R.id.cover2);
        c3 = fragmentView.findViewById(R.id.cover3);
        c4 = fragmentView.findViewById(R.id.cover4);
        b1 = fragmentView.findViewById(R.id.id_reading_title);
        b2 = fragmentView.findViewById(R.id.id_reading_title2);
        b3 = fragmentView.findViewById(R.id.id_reading_title3);
        b4 = fragmentView.findViewById(R.id.id_reading_title4);

        titles = new ArrayList<String>();
        url = new ArrayList<String>();
        books = new ArrayList<JSONObject>();
        volume = new ArrayList<JSONObject>();
        links = new ArrayList<JSONObject>();
        authorList = new ArrayList<JSONArray>();
        covers = new ArrayList<ImageView>();
        textViewTitles = new ArrayList<TextView>();
        textViewAuthors = new ArrayList<TextView>();

        covers.add(c1);
        covers.add(c2);
        covers.add(c3);
        covers.add(c4);
        textViewAuthors.add(a1);
        textViewAuthors.add(a2);
        textViewAuthors.add(a3);
        textViewAuthors.add(a4);
        textViewTitles.add(b1);
        textViewTitles.add(b2);
        textViewTitles.add(b3);
        textViewTitles.add(b4);
        AsyncTaskDownloader asyncTaskDownloader = new AsyncTaskDownloader();
        new AsyncTaskDownloader().execute();


        // Inflate the layout for this fragment
        return fragmentView;
    }
    public Reading() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        main = (sendDataToMain) context;
    }

    public interface  sendDataToMain{
        public void sendStuff(String str);

    }
    public class AsyncTaskDownloader extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

        }

        @Override
        protected Void doInBackground(Void... voids){
            try {

                //my library id 108038689287246295424
                // user id      104876221891008736274?
                // my personal example id  108038689287246295424
                // example - 1001 ac coil id
                //API KEY = AIzaSyAxMDdIR1RpINWVBgyriMkd7XlwMl2mIEA

                //retrueve a book https://www.googleapis.com/books/v1/volumes/VOLUME ID GOES HERE?key=AIzaSyAxMDdIR1RpINWVBgyriMkd7XlwMl2mIEA
                //https://www.googleapis.com/books/v1/volumes?q=flowers+inauthor:keyes&key=AIzaSyAxMDdIR1RpINWVBgyriMkd7XlwMl2mIEA   search a book using author
                //https://www.googleapis.com/books/v1/volumes?q=unwind+subject:fiction&key=AIzaSyAxMDdIR1RpINWVBgyriMkd7XlwMl2mIEA
                URL myUrl = new URL("https://www.googleapis.com/books/v1/users/108038689287246295424/bookshelves/3/volumes?key=AIzaSyAxMDdIR1RpINWVBgyriMkd7XlwMl2mIEA");
                URLConnection connection = myUrl.openConnection();
                InputStream internet = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(internet));
                String data = "";
                String reader = bufferedReader.readLine();
                while ( reader != null){
                    data +=reader;
                    reader=bufferedReader.readLine();
                }
                System.out.print(data);
                answer1 = data;
                JSONObject jsonObject = new JSONObject(answer1);
                totalItems = Integer.parseInt(jsonObject.getString("totalItems"));
                JSONArray items = jsonObject.getJSONArray("items");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < totalItems; i++) {
                            //Picasso.with(Reading.this).load(url.get(i)).into(covers.get(i));
                            Picasso.with(covers.get(i).getContext()).load(url.get(i)).into(covers.get(i));
                        }

                    }
                });

                for(int i = 0; i < totalItems; i++)
                {
                    books.add(i,items.getJSONObject(i));
                    volume.add(i,books.get(i).getJSONObject("volumeInfo"));
                    titles.add(i,volume.get(i).getString("title"));
                    authorList.add(i, volume.get(i).getJSONArray("authors"));
                    url.add(i, volume.get(i).getJSONObject("imageLinks").getString("smallThumbnail"));
                    Log.d("urls", url.get(i));
                    textViewAuthors.get(i).setText(authorList.get(i).getString(0));
                    textViewTitles.get(i).setText(titles.get(i));

                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}