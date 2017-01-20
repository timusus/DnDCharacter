package com.lavendergoons.dndcharacter.Activities;

import android.support.v7.app.AppCompatActivity;

import com.lavendergoons.dndcharacter.Dialogs.ConfirmationDialog;

/**
 * Created by rtas on 2017-01-19.
 */

public abstract class BaseActivity extends AppCompatActivity implements ConfirmationDialog.ConfirmationDialogInterface {
    @Override
    public abstract void onConfirm();

    @Override
    public abstract void onCancel();
}
