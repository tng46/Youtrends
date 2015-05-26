package com.example.santo.youtrends2.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.santo.youtrends2.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabCustomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabCustomFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String region="ALL";
    private int categoryId = 0;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabCustomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabCustomFragment newInstance(String param1, String param2) {
        TabCustomFragment fragment = new TabCustomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public TabCustomFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_custom, container, false);

        Button searchButton = (Button)view.findViewById(R.id.search);
        Spinner categorySpinner = (Spinner)view.findViewById(R.id.categorySpinner);
        categorySpinner.setPrompt("Categoria");


        String[] categoryState= {"", "Generale","Animali","Auto e Veicoli", "Blogging", "Comedy",
                "Educazione", "Film ed Animazione", "Films", "Howto And Style", "Intrattenimento",
                "Lavoro ed Eventi", "Musica", "Notizie e Politica", "Scienze e Tecnologia", "Sport", "Videogiochi"
        };

        final ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, categoryState);
        categorySpinner.setAdapter(categoryAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)categoryAdapter.getItem(position);
                Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT).show();
                switch (selected) {
                    case "":
                        categoryId = 0;
                        break;
                    case "Generale":
                        categoryId = 0;
                        break;
                    case "Animali":
                        categoryId = 15;
                        break;
                    case "Auto e Veicoli":
                        categoryId = 2;
                        break;
                    case "Blogging":
                        categoryId = 22;
                        break;
                    case "Comedy":
                        categoryId = 23;
                        break;
                    case "Educazione":
                        categoryId = 27;
                        break;
                    case "Film ed Animazione":
                        categoryId = 1;
                        break;
                    case "Films":
                        categoryId = 30;
                        break;
                    case "Howto And Style":
                        categoryId = 26;
                        break;
                    case "Intrattenimento":
                        categoryId = 24;
                        break;
                    case "Lavoro ed Eventi":
                        categoryId = 19;
                        break;
                    case "Musica":
                        categoryId = 10;
                        break;
                    case "Notizie e Politica":
                        categoryId = 25;
                        break;
                    case "Scienze e Tecnologia":
                        categoryId = 28;
                        break;
                    case "Sport":
                        categoryId = 17;
                        break;
                    case "Videogiochi":
                        categoryId = 20;
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



       /* Button categoryButton = (Button)view.findViewById(R.id.categoryButton);
        Button regionButton = (Button)view.findViewById(R.id.regionButton);

        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Categoria");
                final CharSequence[] choiceList = {"Generale","Animali","Auto e Veicoli", "Blogging", "Comedy",
                        "Educazione", "Film ed Animazione", "Films", "Howto And Style", "Intrattenimento",
                        "Lavoro ed Eventi", "Musica", "Notizie e Politica", "Scienze e Tecnologia", "Sport", "Videogiochi"
                };

                int selected = -1;

                builder.setSingleChoiceItems(
                        choiceList,
                        selected,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(),"Selected: "+ choiceList[which], Toast.LENGTH_SHORT).show();

                                switch ((String)choiceList[which]) {
                                    case "Generale":
                                        categoryId = 0;
                                        break;
                                    case "Animali":
                                        categoryId = 15;
                                        break;
                                    case "Auto e Veicoli":
                                        categoryId = 2;
                                        break;
                                    case "Blogging":
                                        categoryId = 22;
                                        break;
                                    case "Comedy":
                                        categoryId = 23;
                                        break;
                                    case "Educazione":
                                        categoryId = 27;
                                        break;
                                    case "Film ed Animazione":
                                        categoryId = 1;
                                        break;
                                    case "Films":
                                        categoryId = 30;
                                        break;
                                    case "Howto And Style":
                                        categoryId = 26;
                                        break;
                                    case "Intrattenimento":
                                        categoryId = 24;
                                        break;
                                    case "Lavoro ed Eventi":
                                        categoryId = 19;
                                        break;
                                    case "Musica":
                                        categoryId = 10;
                                        break;
                                    case "Notizie e Politica":
                                        categoryId = 25;
                                        break;
                                    case "Scienze e Tecnologia":
                                        categoryId = 28;
                                        break;
                                    case "Sport":
                                        categoryId = 17;
                                        break;
                                    case "Videogiochi":
                                        categoryId = 20;
                                        break;

                                }

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        regionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Paese");
                final CharSequence[] choiceList = {"Nessuno","Italia","Francia","Germania",
                        "Gran Bretagna", "Spagna", "Olanda", "Austria","Belgio", "Danimarca",
                        "Grecia","Portogallo","Romania","Russia","Svezia", "Svizzera",
                        "USA","Australia", "Argentina", "Brasile", "Canada", "Giappone"
                };

                int selected = -1;

                builder.setSingleChoiceItems(
                        choiceList,
                        selected,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(),"Selected: "+ choiceList[which], Toast.LENGTH_SHORT).show();


                                switch ((String)choiceList[which]) {
                                    case "Nessuno":
                                        region = "ALL";
                                        break;
                                    case "Italia":
                                        region = "IT";
                                        break;
                                    case "Francia":
                                        region = "FR";
                                        break;
                                    case "Gran Bretagna":
                                        region = "GB";
                                        break;
                                    case "Germania":
                                        region = "DE";
                                        break;
                                    case "Spagna":
                                        region = "ES";
                                        break;
                                    case "Olanda":
                                        region = "NL";
                                        break;
                                    case "Danimarca":
                                        region = "DK";
                                        break;
                                    case "Portogallo":
                                        region = "PT";
                                        break;
                                    case "Romania":
                                        region = "RO";
                                        break;
                                    case "Russia":
                                        region = "RU";
                                        break;
                                    case "Svezia":
                                        region = "SE";
                                        break;
                                    case "Svizzera":
                                        region = "CH";
                                        break;
                                    case "Belgio":
                                        region = "BE";
                                        break;
                                    case "Austria":
                                        region = "AT";
                                        break;
                                    case "Grecia":
                                        region = "GR";
                                        break;
                                    case "USA":
                                        region = "US";
                                        break;
                                    case "Australia":
                                        region = "AU";
                                        break;
                                    case "Argentina":
                                        region = "AR";
                                        break;
                                    case "Brasile":
                                        region = "BR";
                                        break;
                                    case "Canada":
                                        region = "CA";
                                        break;
                                    case "Giappone":
                                        region = "JP";
                                        break;

                                }

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
*/



        Spinner regionSpinner = (Spinner)view.findViewById(R.id.regionSpinner);
        regionSpinner.setPrompt("Paese");


        String[] regionState= {"", "Nessuno","Italia","Francia","Germania",
                "Gran Bretagna", "Spagna", "Olanda", "Austria","Belgio", "Danimarca",
                "Grecia","Portogallo","Romania","Russia","Svezia", "Svizzera",
                "USA","Australia", "Argentina", "Brasile", "Canada", "Giappone"
        };

        final ArrayAdapter<String> regionAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, regionState);
        regionSpinner.setAdapter(regionAdapter);
        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = (String)regionAdapter.getItem(position);
                Toast.makeText(getActivity(), selected, Toast.LENGTH_SHORT).show();
                switch (selected) {
                    case "":
                        region = "ALL";
                        break;
                    case "Nessuno":
                        region = "ALL";
                        break;
                    case "Italia":
                        region = "IT";
                        break;
                    case "Francia":
                        region = "FR";
                        break;
                    case "Gran Bretagna":
                        region = "GB";
                        break;
                    case "Germania":
                        region = "DE";
                        break;
                    case "Spagna":
                        region = "ES";
                        break;
                    case "Olanda":
                        region = "NL";
                        break;
                    case "Danimarca":
                        region = "DK";
                        break;
                    case "Portogallo":
                        region = "PT";
                        break;
                    case "Romania":
                        region = "RO";
                        break;
                    case "Russia":
                        region = "RU";
                        break;
                    case "Svezia":
                        region = "SE";
                        break;
                    case "Svizzera":
                        region = "CH";
                        break;
                    case "Belgio":
                        region = "BE";
                        break;
                    case "Austria":
                        region = "AT";
                        break;
                    case "Grecia":
                        region = "GR";
                        break;
                    case "USA":
                        region = "US";
                        break;
                    case "Australia":
                        region = "AU";
                        break;
                    case "Argentina":
                        region = "AR";
                        break;
                    case "Brasile":
                        region = "BR";
                        break;
                    case "Canada":
                        region = "CA";
                        break;
                    case "Giappone":
                        region = "JP";
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = TabFragment.newInstance(categoryId, 50, region);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment1, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser)
            TabCustomFragment.newInstance("","");
    }
}
