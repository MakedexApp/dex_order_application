package com.example.dexorderapplication.Helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.dexorderapplication.Domain.ItemDomain;
import com.example.dexorderapplication.Interface.ChangeNumberItemsListener;
import com.example.dexorderapplication.Domain.ItemDomain;
import com.example.dexorderapplication.Interface.ChangeNumberItemsListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ManagementCart {

    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void inserItem(ItemDomain item){
        ArrayList<ItemDomain> listItem = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < listItem.size(); i++) {
            if(listItem.get(i).getTitle().equals(item.getTitle())){
                existAlready = true;
                n = i;
                break;
            }
        }

        if(existAlready){
            listItem.get(n).setNumberInCart(listItem.get(n).getNumberInCart() + item.getNumberInCart());
        }
        else {
            listItem.add(item);
        }
        tinyDB.putListObject("CartList",listItem);
        Log.d("ManagementCart","tiny: " + listItem);
        Toast.makeText(context, "장바구니에 추가되었습니다.", Toast.LENGTH_SHORT).show();
    }

    public void deleteItem(ArrayList<ItemDomain> listEmpty){
        tinyDB.putListObject("CartList",listEmpty);
    }

    public ArrayList<ItemDomain> getListCart(){
        return tinyDB.getListObject("CartList");
    }

    public void plusNumberItem(ArrayList<ItemDomain>listItem, int position, ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart()+1);
        tinyDB.putListObject("CartList",listItem);
        changeNumberItemsListener.changed();
    }
    public void minusNumberItem(ArrayList<ItemDomain> listitem, int position, ChangeNumberItemsListener changeNumberItemsListener){
        if(listitem.get(position).getNumberInCart() == 1){
            listitem.remove(position);
        }
        else{
            listitem.get(position).setNumberInCart(listitem.get(position).getNumberInCart()-1);
        }
        tinyDB.putListObject("CartList",listitem);
        changeNumberItemsListener.changed();
    }

    public Integer getTotalFee() {
        ArrayList<ItemDomain> listItem = getListCart();
        int fee = 0;
        for (int i = 0; i < listItem.size(); i++) {
            fee = fee+(listItem.get(i).getFee() * listItem.get(i).getNumberInCart());
        }
        return fee;
    }
}
