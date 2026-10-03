package com.example.electricitybillcalculator;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private static final String PREF_NAME = "electricity_bill_history_pref";
    private static final String KEY_HISTORY = "bill_history_list";
    private static final int MAX_HISTORY_ITEMS = 50;

    private final SharedPreferences preferences;

    public HistoryManager(Context context) {
        this.preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public synchronized List<BillHistoryItem> getHistory() {
        List<BillHistoryItem> list = new ArrayList<>();
        String jsonString = preferences.getString(KEY_HISTORY, null);
        if (jsonString != null && !jsonString.trim().isEmpty()) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    BillHistoryItem item = BillHistoryItem.fromJsonObject(obj);
                    if (item != null) {
                        list.add(item);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public synchronized void saveBill(BillHistoryItem item) {
        if (item == null) return;
        List<BillHistoryItem> existing = getHistory();
        // Insert at beginning (newest first)
        existing.add(0, item);

        // Cap at MAX_HISTORY_ITEMS
        if (existing.size() > MAX_HISTORY_ITEMS) {
            existing = existing.subList(0, MAX_HISTORY_ITEMS);
        }

        saveList(existing);
    }

    public synchronized boolean deleteItem(String id) {
        if (id == null) return false;
        List<BillHistoryItem> existing = getHistory();
        boolean removed = false;
        for (int i = 0; i < existing.size(); i++) {
            if (id.equals(existing.get(i).getId())) {
                existing.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) {
            saveList(existing);
        }
        return removed;
    }

    public synchronized void clearAll() {
        preferences.edit().remove(KEY_HISTORY).apply();
    }

    private void saveList(List<BillHistoryItem> list) {
        JSONArray jsonArray = new JSONArray();
        for (BillHistoryItem item : list) {
            jsonArray.put(item.toJsonObject());
        }
        preferences.edit().putString(KEY_HISTORY, jsonArray.toString()).apply();
    }

    public int getCount() {
        return getHistory().size();
    }
}
