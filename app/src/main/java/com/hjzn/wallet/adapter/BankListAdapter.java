package com.hjzn.wallet.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjzn.wallet.R;
import com.hjzn.wallet.base.BaseFastAdapter;
import com.hjzn.wallet.model.BankCard;

import java.util.List;

/**
 * 银行卡列表
 */
public class BankListAdapter extends BaseFastAdapter {
    private List<BankCard.DataBean> data;
    public BankListAdapter(Context ctx,List<BankCard.DataBean> data) {
        super(ctx);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_banklist, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else{
           vh = (ViewHolder) convertView.getTag();

        }
        final BankCard.DataBean dataBean = data.get(position);
        vh.tv_bankname.setText(dataBean.getBankName());
        vh.tv_bankcode.setText(dataBean.getBankNum()+"");
        int isDefault = dataBean.getIsDefault();
        if (isDefault == 0) {
            vh.iv_check.setImageResource(R.mipmap.icon_uncheck);
        }else{
            vh.iv_check.setImageResource(R.mipmap.icon_check);
        }
        vh.ll_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListener.setDefault(dataBean.getId()+"",dataBean.getBankName(),dataBean.getBankNum());
            }
        });
        vh.ll_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListener.setDelete(dataBean.getId()+"");
            }
        });
        vh.ll_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListener.setEdit(dataBean);
            }
        });
        return convertView;
    }

    private class ViewHolder {
        public View rootView;
        public TextView tv_bankname;
        public LinearLayout ll_edit;
        public LinearLayout ll_delete;
        public TextView tv_bankcode;
        public LinearLayout ll_default;
        private ImageView iv_check;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_bankname = (TextView) rootView.findViewById(R.id.tv_bankname);
            this.tv_bankcode = (TextView) rootView.findViewById(R.id.tv_bankcode);
            this.ll_edit = (LinearLayout) rootView.findViewById(R.id.ll_edit);
            this.ll_delete = (LinearLayout) rootView.findViewById(R.id.ll_delete);
            this.tv_bankname = rootView.findViewById(R.id.tv_bankcode);
            this.ll_default = rootView.findViewById(R.id.ll_default);
            this.iv_check = rootView.findViewById(R.id.iv_check);
        }

    }
    public interface CheckListener{
        void setDefault(String id,String bankname,String bankcode);
        void setEdit(BankCard.DataBean dataBean);
        void setDelete(String id);
    }
    private CheckListener checkListener;

    public void setCheckListener(CheckListener checkListener) {
        this.checkListener = checkListener;
    }
}
