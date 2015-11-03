package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.cc.grameenphone.R;
import com.cc.grameenphone.adapter.ManageFavAdapter;
import com.cc.grameenphone.api_models.ContactModel;
import com.cc.grameenphone.utils.Constants;
import com.cc.grameenphone.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import co.uk.rushorm.core.RushSearch;
import co.uk.rushorm.core.RushSearchCallback;
import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by rajkiran on 09/09/15.
 */
public class ManageFavoriteFragment extends Fragment {
    Button cancelbtn, removeBtn;

    MaterialDialog confirmDialog;
    FloatingActionButton addBtn;
    ManageFavAdapter adapter;
    List<ContactModel> contactModelList;
    private boolean isHome;

    public ManageFavoriteFragment() {
        // Required empty public constructor
    }


    public static ManageFavoriteFragment newInstance(Bundle b) {
        ManageFavoriteFragment fragment = new ManageFavoriteFragment();
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_manage_favs, container, false);
        handleArguments();
        final SwipeMenuListView listView = (SwipeMenuListView) rootView.findViewById(R.id.transactionList);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                deleteItem.setWidth(90);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_black);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        confirmDialog = new MaterialDialog(getActivity());
                        confirmDialog.setMessage("Remove" + " " + adapter.getItem(position).getName() + " " + "from favorites ?");
                        confirmDialog.setNegativeButton("CANCEL", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                confirmDialog.dismiss();
                            }
                        });
                        confirmDialog.setPositiveButton("REMOVE", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                confirmDialog.dismiss();
                                Logger.d("Delte fav", "position is " + position);
                                adapter.getItem(position).delete();
                                adapter.remove(position);


                            }
                        });

                        confirmDialog.show();
                        break;

                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        // Left
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        contactModelList = new ArrayList<>();
        adapter = new ManageFavAdapter(contactModelList, getActivity());
        listView.setAdapter(adapter);
        ViewGroup parentGroup = (ViewGroup) listView.getParent();
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_list, parentGroup, false);
        ((TextView) emptyView.findViewById(R.id.textView)).setText("No Favorites add some");
        parentGroup.addView(emptyView);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!isHome) {
                    Intent d = new Intent();
                    d.putExtra(Constants.RETURN_RESULT, adapter.getItem(i).getNumber());
                    getActivity().setResult(getActivity().RESULT_OK, d);
                    getActivity().finish();
                }

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                confirmDialog = new MaterialDialog(getActivity());
                confirmDialog.setMessage("Remove" + " " + adapter.getItem(position).getName() + " " + "from favorites ?");
                confirmDialog.setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        confirmDialog.dismiss();
                    }
                });
                confirmDialog.setPositiveButton("REMOVE", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmDialog.dismiss();
                        Logger.d("Delte fav", "position is " + position);
                        adapter.getItem(position).delete();
                        adapter.remove(position);



                    }
                });

                confirmDialog.show();
                return false;
            }
        });
        fetchList();

        return rootView;
    }

    private void handleArguments() {
        Bundle b;
        try {
            b = getArguments();
            Logger.d("argu", b.toString());
            isHome = getArguments().getBoolean("isHome");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public void getFilterContacts(String searchText) {
        if (adapter != null) {
            Logger.d("Search Text", searchText);
            adapter.getFilter().filter(searchText);
        }
    }

    private void fetchList() {

        new RushSearch()
                .find(ContactModel.class, new RushSearchCallback<ContactModel>() {
                    @Override
                    public void complete(List<ContactModel> list) {
                        contactModelList.clear();
                        Collections.sort(list, new Comparator<ContactModel>() {
                            @Override
                            public int compare(ContactModel s1, ContactModel s2) {
                                return s1.getName().compareToIgnoreCase(s2.getName());
                            }
                        });
                        contactModelList.addAll(list);
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchList();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    class Compa implements Comparator<ContactModel> {

        @Override
        public int compare(ContactModel e1, ContactModel e2) {
            return e1.getName().compareToIgnoreCase(e2.getName());
        }
    }
}
