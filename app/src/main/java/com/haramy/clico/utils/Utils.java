package com.haramy.clico.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.haramy.clico.model.Deal;

import java.util.ArrayList;

/**
 * Project: Clico.
 * Created by Dell on 09/07/2016.
 * Copyright (C) 2016 : Rami Hamrouni
 */
public class Utils {

    private static String PREF_KEY_USER_MODE = "userMode";

    public static void setUserMode(Context context, boolean isTrade){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean(PREF_KEY_USER_MODE, isTrade).commit();
    }

    public static boolean getUserMode(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isTrade = prefs.getBoolean(PREF_KEY_USER_MODE, true);
        return isTrade;
    }

    public static ArrayList<Deal> getStaticDeals(){
        ArrayList<Deal> list = new ArrayList<>();
        list.add(new Deal("Promo chemises", "11 les sablons, Neuilly", "28/08/2016", "30/08/2016"));
        list.add(new Deal("Offre flash", "218 rue de la Tombe-Issoire 75014 Paris ", "21/08/2016", "24/08/2016"));
        list.add(new Deal("AccorHotels Inv", "78 rue Henry Ferman", "20/08/2016", "09/09/2016"));
        list.add(new Deal("Derniere demarque", "Les boutiques Zara", "20/08/2016", "21/08/2016"));
        list.add(new Deal("Formula petit-déj", "Tunis-Tunis, Belleville", "16/08/2016", "19/08/2016"));
        list.add(new Deal("Location voitures", "Hertz", "09/08/2016", "10/08/2016"));
        list.add(new Deal("Solde 50%", "Boutiques celio*", "03/08/2016", "07/08/2016"));
        list.add(new Deal("Chèques cadeaux", "Carrefour", "30/07/2016", "30/07/2016"));
        list.add(new Deal("Surprise", "4 Temps", "20/07/2016", "24/07/2016"));
        list.add(new Deal("Nouvelle collection", "Boutiques Jules", "15/07/2016", "16/07/2016"));
        list.add(new Deal("Baquette gratuite", "30 rue Alésia, Paris 75014", "01/07/2016", "11/07/2016"));
        list.add(new Deal("Promo Ramadhan", "Boucherie 100% halal", "04/06/2016", "10/07/2016"));
        list.add(new Deal("Promo Aid", "92 rue de la liberté, BelleVille", "04/06/2016", "07/06/2016"));
        return list;
    }

    public static ArrayList<Deal> getStaticClient(){
        ArrayList<Deal> list = new ArrayList<>();
        list.add(new Deal("Client 1", "11 les sablons, Neuilly", "28/08/2016", "30/08/2016"));
        list.add(new Deal("Client 2", "218 rue de la Tombe-Issoire 75014 Paris ", "21/08/2016", "24/08/2016"));
        list.add(new Deal("Client 3", "78 rue Henry Ferman", "20/08/2016", "09/09/2016"));
        list.add(new Deal("Client 4", "Les boutiques Zara", "20/08/2016", "21/08/2016"));
        list.add(new Deal("Client 5", "Tunis-Tunis, Belleville", "16/08/2016", "19/08/2016"));
        list.add(new Deal("Client 6", "Hertz", "09/08/2016", "10/08/2016"));
        list.add(new Deal("Client 7", "Boutiques celio*", "03/08/2016", "07/08/2016"));
        list.add(new Deal("Client 8", "Carrefour", "30/07/2016", "30/07/2016"));
        list.add(new Deal("Client 9", "4 Temps", "20/07/2016", "24/07/2016"));
        list.add(new Deal("Client 10 collection", "Boutiques Jules", "15/07/2016", "16/07/2016"));
        list.add(new Deal("Client 11", "30 rue Alésia, Paris 75014", "01/07/2016", "11/07/2016"));
        list.add(new Deal("Client 12", "Boucherie 100% halal", "04/06/2016", "10/07/2016"));
        list.add(new Deal("Client 13", "92 rue de la liberté, BelleVille", "04/06/2016", "07/06/2016"));
        return list;
    }
}
