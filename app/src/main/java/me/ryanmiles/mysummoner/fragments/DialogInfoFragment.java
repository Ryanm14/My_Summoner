package me.ryanmiles.mysummoner.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.robrua.orianna.api.core.AsyncRiotAPI;
import com.robrua.orianna.api.core.RiotAPI;
import com.robrua.orianna.type.api.Action;
import com.robrua.orianna.type.core.common.Region;
import com.robrua.orianna.type.core.summoner.Summoner;
import com.robrua.orianna.type.exception.APIException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import me.ryanmiles.mysummoner.R;
import me.ryanmiles.mysummoner.events.BaseInfo;
import me.ryanmiles.mysummoner.events.ErrorSummonerName;
import timber.log.Timber;

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
        getDialog().setTitle("MySummoner Setup");
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.done_button)
    public void onClick() {
        Timber.d("DoneButton onClick()");
        String name = mNameEditText.getText().toString();
        String region = mRegionSpinner.getSelectedItem().toString();
        RiotAPI.setRegion(Region.valueOf(region));
        try {
            AsyncRiotAPI.getSummonerByName(new Action<Summoner>() {


                @Override
                public void handle(APIException exception) {
                    EventBus.getDefault().post(new ErrorSummonerName());
                }

                @Override
                public void perform(Summoner responseData) {
                    EventBus.getDefault().post(getBaseInfo());
                    dismiss();
                }
            }, name);
        } catch (Exception e) {
            e.printStackTrace();


        }
    }


    public BaseInfo getBaseInfo() {
        BaseInfo baseInfo = new BaseInfo();
        baseInfo.setName(mNameEditText.getText().toString());
        baseInfo.setRegion(mRegionSpinner.getSelectedItem().toString());
        return baseInfo;
    }
}
