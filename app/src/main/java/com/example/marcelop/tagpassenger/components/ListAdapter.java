package com.example.marcelop.tagpassenger.components;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.marcelop.tagpassenger.entity.Passageiro;

/**
 * Created by Marcelo P on 11/10/2017.
 */

public class ListAdapter extends ArrayAdapter<Passageiro> {
    public ListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ListAdapter(Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }
}
