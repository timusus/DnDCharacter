package com.lavendergoons.dndcharacter.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;
import com.lavendergoons.dndcharacter.Dialogs.ItemGeneralDialog;
import com.lavendergoons.dndcharacter.Objects.Item;
import com.lavendergoons.dndcharacter.Objects.TestCharacter;
import com.lavendergoons.dndcharacter.R;
import com.lavendergoons.dndcharacter.Utils.ItemsGeneralAdapter;

import java.util.ArrayList;

/**
 * Fragment to display General Items ie. Bedroll, Potions, Flint/Steel
 */
public class ItemsGeneralFragment extends Fragment implements View.OnClickListener, ItemGeneralDialog.ItemsGeneralDialogListener, ConfirmationDialog.ConfirmationDialogInterface {

    //TODO Character should be passed in from CharacterNavDrawerActivity
    private RecyclerView mItemsRecyclerView;
    private RecyclerView.Adapter mItemsRecyclerAdapter;
    private RecyclerView.LayoutManager mItemsRecyclerLayoutManager;
    private OnFragmentInteractionListener mListener;
    private ArrayList<Item> itemList;
    private TestCharacter character;
    private FloatingActionButton fab;

    public ItemsGeneralFragment() {
        // Required empty public constructor
    }

    public static ItemsGeneralFragment newInstance() {
        return new ItemsGeneralFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO Get rid of test character
        itemList = new ArrayList<>();
        character = new TestCharacter();
        itemList = character.getItems();
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
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }
}
