package me.ryanmiles.mysummoner.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.ryanmiles.mysummoner.R;
import me.ryanmiles.mysummoner.events.BaseInfo;

/**
 * Created by ryanm on 12/19/2015.
 */
public class DialogInfoFragment extends DialogFragment {

    @Bind(R.id.summoner_name_edit_text)
    EditText mNameEditText;
    @Bind(R.id.region_spinner)
    Spinner mRegionSpinner;

    public DialogInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_info_fragment, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.done_button)
    public void onClick() {
        String name = mNameEditText.getText().toString();
        String region = mRegionSpinner.getSelectedItem().toString();
        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setName(name);
        baseInfo.setRegion(region);
        EventBus.getDefault().post(baseInfo);
        dismiss();
    }

}
