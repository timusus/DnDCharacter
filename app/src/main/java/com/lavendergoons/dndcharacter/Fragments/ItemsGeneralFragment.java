package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lavendergoons.dndcharacter.Activities.CharacterNavDrawerActivity;
import com.lavendergoons.dndcharacter.Database.DBAdapter;
import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Dialogs.ItemGeneralDialog;
import com.lavendergoons.dndcharacter.Objects.Character;
import com.lavendergoons.dndcharacter.Objects.Item;
import com.lavendergoons.dndcharacter.Objects.TestCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.Constants;
import com.lavendergoons.dndcharacter.Utils.ItemsGeneralAdapter;
import com.lavendergoons.dndcharacter.Utils.Utils;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Fragment to display General Items ie. Bedroll, Potions, Flint/Steel
 */
public class ItemsGeneralFragment extends Fragment implements View.OnClickListener, ItemGeneralDialog.ItemsGeneralDialogListener, ConfirmationDialog.ConfirmationDialogInterface {

    public static final String TAG = "ITEMS_GENERAL_FRAG";

    private Gson gson = new Gson();

    private RecyclerView mItemsRecyclerView;
    private RecyclerView.Adapter mItemsRecyclerAdapter;
    private RecyclerView.LayoutManager mItemsRecyclerLayoutManager;
    private OnFragmentInteractionListener mListener;

    private ArrayList<Item> itemList = new ArrayList<>();
    private DBAdapter dbAdapter;
    private Character character;
    private long id = -1;
    private TestCharacter testCharacter;
    private FloatingActionButton fab;

    public ItemsGeneralFragment() {
        // Required empty public constructor
    }

    public static ItemsGeneralFragment newInstance(Character character, long characterId) {
        ItemsGeneralFragment frag = new ItemsGeneralFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CHARACTER_KEY, character);
        args.putLong(Constants.CHARACTER_ID, characterId);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getLong(Constants.CHARACTER_ID);
            character = getArguments().getParcelable(Constants.CHARACTER_KEY);
        }
        try {
            dbAdapter = ((CharacterNavDrawerActivity) getActivity()).getDbAdapter();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        getItemsGeneral();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_items_general, container, false);
        mItemsRecyclerView = (RecyclerView) rootView.findViewById(R.id.itemsGeneralRecyclerView);

        // Keeps View same size on content change
        mItemsRecyclerView.setHasFixedSize(true);

        mItemsRecyclerLayoutManager = new LinearLayoutManager(this.getContext());
        mItemsRecyclerView.setLayoutManager(mItemsRecyclerLayoutManager);

        mItemsRecyclerAdapter = new ItemsGeneralAdapter(this, itemList);
        mItemsRecyclerView.setAdapter(mItemsRecyclerAdapter);

        fab = (FloatingActionButton) rootView.findViewById(R.id.addItemFAB);
        fab.setOnClickListener(this);
        return rootView;
    }

    private void getItemsGeneral() {
        if (dbAdapter != null && id != -1) {
            Cursor cursor = dbAdapter.getColumnCursor(DBAdapter.COLUMN_ITEM_GENERAL, id);
            if (cursor != null) {
                String json = cursor.getString(cursor.getColumnIndex(DBAdapter.COLUMN_ITEM_GENERAL));
                if (json != null && !Utils.isStringEmpty(json) && !json.equals("[]") && !json.equals("[ ]")) {
                    Type attributeType = new TypeToken<ArrayList<Item>>(){}.getType();
                    itemList = gson.fromJson(json, attributeType);
                    cursor.close();
                }
            }
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    private void writeItemsGeneral() {
        String json = gson.toJson(itemList);
        if (dbAdapter != null) {
            dbAdapter.fillColumn(id, DBAdapter.COLUMN_ITEM_GENERAL, json);
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.warning_database_not_initialized), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        writeItemsGeneral();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void OnItemsPositive(Item item) {
        if (item != null) {
            itemList.add(item);
        }
        mItemsRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnItemsNegative() {

    }

    @Override
    public void ConfirmDialogOk(Object item) {
        if(item instanceof Item) {
            itemList.remove(item);
            mItemsRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void ConfirmDialogCancel(Object o) {

    }

    public void deleteItem(Item item) {
        ConfirmationDialog.showConfirmDialog(this.getActivity(), getString(R.string.confirm_delete_item), this, item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addItemFAB:
                ItemGeneralDialog.showItemsDialog(this.getActivity(), this, null);
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction();
    }
}
