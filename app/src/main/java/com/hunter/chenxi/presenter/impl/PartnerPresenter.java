package com.hunter.chenxi.presenter.impl;

import android.util.Log;

import com.hunter.chenxi.model.impl.PartnerModeImpl;
import com.hunter.chenxi.ui.view.interfaces.IPartnerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * 创建人：SunShine
 * 功能说明：Partner的Presenter
 */
public class PartnerPresenter {

    private IPartnerView iPartnerView;
    private PartnerModeImpl partnerFragment;

    public PartnerPresenter(IPartnerView iPartnerView) {
        this.iPartnerView = iPartnerView;
        partnerFragment = new PartnerModeImpl();

    }

    public LinkedList getData() throws JSONException {
        Log.e("test","getData");
        return partnerFragment.parseJson(partnerFragment.getJson());
    }

}
