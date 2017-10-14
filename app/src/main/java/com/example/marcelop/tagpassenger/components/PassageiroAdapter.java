package com.example.marcelop.tagpassenger.components;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marcelop.tagpassenger.R;
import com.example.marcelop.tagpassenger.entity.Passageiro;

import java.util.List;

/**
 * Created by Marcelo P on 06/10/2017.
 */

public class PassageiroAdapter extends RecyclerView.Adapter<PassageiroAdapter.ViewHolderPassageiro> {

    private List<Passageiro> lstPassageiros;
    ViewHolderPassageiro viewHolderPassageiro;
    public PassageiroAdapter(List<Passageiro> lstPassageiros){
        this.lstPassageiros = lstPassageiros;
    }

    @Override
    public PassageiroAdapter.ViewHolderPassageiro onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.linha_recycle_view,parent,false);
        viewHolderPassageiro = new ViewHolderPassageiro(view);
        return viewHolderPassageiro;
    }

    @Override
    public void onBindViewHolder(PassageiroAdapter.ViewHolderPassageiro holder, int position) {
        if(lstPassageiros != null && lstPassageiros.size() > 0){
            holder.getTxtNome().setText(lstPassageiros.get(position).getNomePassageiro());
            holder.getTxtTAG().setText(lstPassageiros.get(position).getTagPassageiro());
        }

    }
    private void deleteItem(int position) {
        lstPassageiros.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, lstPassageiros.size());
        viewHolderPassageiro.itemView.setVisibility(View.GONE);
    }
    @Override
    public int getItemCount() {
        return lstPassageiros.size();
    }

    public class ViewHolderPassageiro extends  RecyclerView.ViewHolder{

        private TextView txtNome;
        private TextView txtTAG;
        private int pos;

        public ViewHolderPassageiro(View itemView) {
            super(itemView);

            setTxtNome((TextView) itemView.findViewById(R.id.txtNome));
            setTxtTAG((TextView) itemView.findViewById(R.id.txtTAG));

            txtNome.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //lstPassageiros.remove(getItemPos());
                    //notifyDataSetChanged();


                    return false;
                }
            });

       }

        public TextView getTxtNome() {
            return txtNome;
        }

        public void setTxtNome(TextView txtNome) {
            this.txtNome = txtNome;
        }

        public TextView getTxtTAG() {
            return txtTAG;
        }

        public void setTxtTAG(TextView txtTAG) {
            this.txtTAG = txtTAG;
        }

        public int getItemPos() {
            return this.pos = getAdapterPosition();
        }

        public void setItemPos(int pos){
            this.pos = pos;
        }
    }
}
