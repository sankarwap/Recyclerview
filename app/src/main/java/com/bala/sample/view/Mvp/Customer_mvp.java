package com.bala.sample.view.Mvp;

import com.bala.sample.model.util.Customer_model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindmade technologies.
 */

public interface Customer_mvp {

    interface MainView {
        public void setError(String error);
        public void setDeleteSuccess(String message);
        public void setAdapterData(ArrayList<Customer_model> data);
    }

    interface MainPresenter {
        public void loadData();
        public void delete(String id);
        public void add(JSONObject jsonObject);
        public void update(JSONObject jsonObject,String id);
    }
}
