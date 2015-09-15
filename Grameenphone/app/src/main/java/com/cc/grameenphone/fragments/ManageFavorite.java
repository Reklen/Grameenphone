package com.cc.grameenphone.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.cc.grameenphone.R;

/**
 * Created by rajkiran on 09/09/15.
 */
public class ManageFavorite extends Fragment {
    AppCompatDialog confirmDialog;
    Button cancelbtn, removeBtn;

    FloatingActionButton addBtn;
    String array[] = {"Tadjdj", "HHHKhkbkdj", "Hjbdkhbkb", "dhjbfbf", "bfkhbsdbd", "cjssdhjjhd", "Tadjdj", "HHHKhkbkdj", "Hjbdkhbkb", "dhjbfbf", "bfkhbsdbd", "cjssdhjjhd"};

    public ManageFavorite() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.manage_favorite_layout, container, false);

        SwipeMenuListView listView = (SwipeMenuListView) rootView.findViewById(R.id.transactionList);

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
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        confirmDialog = new AppCompatDialog(getActivity());
                        confirmDialog.setContentView(R.layout.manage_favorite_delete_dialog);
                        cancelbtn = (Button) confirmDialog.findViewById(R.id.cancelButton);
                        removeBtn = (Button) confirmDialog.findViewById(R.id.removeButton);
                        confirmDialog.show();
                        confirmDialog.getWindow().setLayout(650, 350);
                        cancelbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                confirmDialog.dismiss();
                            }
                        });
                        removeBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        });
                        confirmDialog.setCanceledOnTouchOutside(true);
                        break;

                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        // Left
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, array);
        listView.setAdapter(arrayAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
