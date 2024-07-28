package com.example.finalproject;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class Search extends Fragment implements AdapterView.OnItemSelectedListener {


    String answer1;
    Button go;
    ArrayList<Book> bookArrayList;
    RecyclerView recyclerView;

    EditText firstType;
    String input;
    String filter;
    String textTyped;
    EditText editText;
    String[] courses = { "Author", "Subject"};
    String select;
    int totalItems;
    ArrayList<String> titles;
    ArrayList<String> url;
    ArrayList<JSONObject> books;
    ArrayList<JSONObject> volume;
    ArrayList<JSONObject> links;
    ArrayList<JSONArray> authorList;


    AdapterLayout adapterLayout;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    public Search() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_search, null);
        Spinner spinner = fragmentView.findViewById(R.id.spinner);
        editText = fragmentView.findViewById(R.id.editText);
        firstType = fragmentView.findViewById(R.id.input);
        go = fragmentView.findViewById(R.id.go);
        recyclerView = fragmentView.findViewById(R.id.recyclerView);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Books");



        spinner.setOnItemSelectedListener(this);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, courses);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        bookArrayList = new ArrayList<Book>();

        titles = new ArrayList<String>();
        url = new ArrayList<String>();
        books = new ArrayList<JSONObject>();
        volume = new ArrayList<JSONObject>();
        links = new ArrayList<JSONObject>();
        authorList = new ArrayList<JSONArray>();



        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search.AsyncTaskDownloader asyncTaskDownloader = new Search.AsyncTaskDownloader();
                new Search.AsyncTaskDownloader().execute();
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                input = String.valueOf(charSequence);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        firstType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textTyped = String.valueOf(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        // Inflate the layout for this fragment
        return fragmentView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        select = String.valueOf(adapterView.getItemAtPosition(i));
        Log.d("selected", String.valueOf(adapterView.getItemAtPosition(i)));

        if(select.equals("Author"))
            filter = "inauthor";
        else if(select.equals("Subject"))
            filter = "subject";


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class AsyncTaskDownloader extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void unused) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(linearLayoutManager);
            adapterLayout = new AdapterLayout(getActivity(), bookArrayList);
            recyclerView.setAdapter(adapterLayout);
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
                //https://www.googleapis.com/books/v1/volumes?q=++++input+:keyes&key=AIzaSyAxMDdIR1RpINWVBgyriMkd7XlwMl2mIEA   search a book using author


                URL myUrl = new URL("https://www.googleapis.com/books/v1/volumes?q="+textTyped+"+"+filter+":"+input+"&key=AIzaSyAxMDdIR1RpINWVBgyriMkd7XlwMl2mIEA");
                URLConnection connection = myUrl.openConnection();
                InputStream internet = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(internet));
                String data = "";
                String reader = bufferedReader.readLine();
                while ( reader != null){
                    data +=reader;
                    reader=bufferedReader.readLine();
                }
                answer1 = data;
                JSONObject jsonObject = new JSONObject(answer1);
                totalItems = Integer.parseInt(jsonObject.getString("totalItems"));
                if(totalItems>10)
                    totalItems = 10;
                JSONArray items = jsonObject.getJSONArray("items");


                for(int i = 0; i < totalItems; i++)
                {
                    books.add(i,items.getJSONObject(i));
                    volume.add(i,books.get(i).getJSONObject("volumeInfo"));
                    titles.add(i,volume.get(i).getString("title"));
                    authorList.add(i, volume.get(i).getJSONArray("authors"));
                    url.add(i, volume.get(i).getJSONObject("imageLinks").getString("smallThumbnail"));
                    Log.d("urls", url.get(i));

                    bookArrayList.add(new Book(url.get(i),authorList.get(i).getString(0), titles.get(i) ));
                    databaseReference.child("item "+(i+1)).child("imageUrl").setValue(bookArrayList.get(i).getUrl());
                    databaseReference.child("item "+(i+1)).child("author").setValue(bookArrayList.get(i).getAuthor());
                    databaseReference.child("item "+(i+1)).child("title").setValue(bookArrayList.get(i).getTitle());
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