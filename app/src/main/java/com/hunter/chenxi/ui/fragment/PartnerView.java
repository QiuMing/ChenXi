package com.hunter.chenxi.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.hunter.chenxi.R;
import com.hunter.chenxi.adapter.PartnerAdapter;
import com.hunter.chenxi.presenter.impl.PartnerPresenter;
import com.hunter.chenxi.ui.view.interfaces.IPartnerView;
import com.hunter.chenxi.utils.Utils;

import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by Sunshine on 2016/2/23.
 */
public class PartnerView extends Fragment implements IPartnerView {


    private Activity context;
    private PartnerPresenter partnerPresenter;
    @Bind(R.id.list_partner)
    ListView partner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_partner, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);

        partnerPresenter = new PartnerPresenter(this);


        try {
            partner.setDividerHeight(0);
            partner.setAdapter(new PartnerAdapter(context, partnerPresenter.getData()));
        } catch (JSONException e) {
            Log.e("exception",e.getMessage());
            e.printStackTrace();
        }
        return view;
    }

    @OnItemClick(R.id.list_partner)
    public void partner_OnItemClick(int position) {
        Utils.toast("aaaaaa" + position);
    }
}
