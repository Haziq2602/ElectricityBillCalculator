package com.example.electricitybillcalculator;

import org.json.JSONException;
import org.json.JSONObject;

public class BillHistoryItem {
    private String id;
    private String provider;
    private int units;
    private double totalBill;
    private double baseEnergy;
    private double wheelingCharge;
    private double fixedCharge;
    private boolean isBpl;
    private int tierIndex;
    private String formattedDate;
    private long timestamp;

    public BillHistoryItem(String id, String provider, int units, double totalBill,
                           double baseEnergy, double wheelingCharge, double fixedCharge,
                           boolean isBpl, int tierIndex, String formattedDate, long timestamp) {
        this.id = id;
        this.provider = provider;
        this.units = units;
        this.totalBill = totalBill;
        this.baseEnergy = baseEnergy;
        this.wheelingCharge = wheelingCharge;
        this.fixedCharge = fixedCharge;
        this.isBpl = isBpl;
        this.tierIndex = tierIndex;
        this.formattedDate = formattedDate;
        this.timestamp = timestamp;
    }

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("provider", provider);
            json.put("units", units);
            json.put("totalBill", totalBill);
            json.put("baseEnergy", baseEnergy);
            json.put("wheelingCharge", wheelingCharge);
            json.put("fixedCharge", fixedCharge);
            json.put("isBpl", isBpl);
            json.put("tierIndex", tierIndex);
            json.put("formattedDate", formattedDate);
            json.put("timestamp", timestamp);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static BillHistoryItem fromJsonObject(JSONObject json) {
        if (json == null) return null;
        return new BillHistoryItem(
                json.optString("id", String.valueOf(System.currentTimeMillis())),
                json.optString("provider", "Tata Power"),
                json.optInt("units", 0),
                json.optDouble("totalBill", 0.0),
                json.optDouble("baseEnergy", 0.0),
                json.optDouble("wheelingCharge", 0.0),
                json.optDouble("fixedCharge", 0.0),
                json.optBoolean("isBpl", false),
                json.optInt("tierIndex", 0),
                json.optString("formattedDate", ""),
                json.optLong("timestamp", System.currentTimeMillis())
        );
    }

    public String getId() { return id; }
    public String getProvider() { return provider; }
    public int getUnits() { return units; }
    public double getTotalBill() { return totalBill; }
    public double getBaseEnergy() { return baseEnergy; }
    public double getWheelingCharge() { return wheelingCharge; }
    public double getFixedCharge() { return fixedCharge; }
    public boolean isBpl() { return isBpl; }
    public int getTierIndex() { return tierIndex; }
    public String getFormattedDate() { return formattedDate; }
    public long getTimestamp() { return timestamp; }

    public double getAverageRatePerUnit() {
        if (units <= 0) return 0;
        return totalBill / units;
    }
}
