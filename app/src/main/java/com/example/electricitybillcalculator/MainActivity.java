package com.example.electricitybillcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements HistoryAdapter.OnHistoryActionListener {

    // Inputs & Selectors
    private TextInputEditText etUnits;
    private AutoCompleteTextView actvProvider;
    private MaterialButton btnCalculate, btnReset, btnShareBill;
    private TextView chip50, chip120, chip250, chip450;

    // Result Elements
    private LinearLayout cardResult;
    private TextView tvResultProvider, tvBplBadge, tvTotalBill, tvAvgRate;
    private TextView tvBaseEnergy, tvWheelingCharge, tvFixedCharge, tvSavedNotice, tvEcoTip;

    // Tier Indicators
    private View indicatorTier1, indicatorTier2, indicatorTier3, indicatorTier4;
    private TextView tvActiveTierBadge, tvTierAdvice;

    // Tab Navigation
    private TextView tabCalculator, tabHistory;
    private LinearLayout layoutCalculatorView, layoutHistoryView;

    // History View Components
    private RecyclerView rvHistory;
    private HistoryAdapter historyAdapter;
    private HistoryManager historyManager;
    private LinearLayout layoutEmptyHistory;
    private MaterialButton btnClearAllHistory, btnEmptyCalculateNow;
    private TextView tvStatTotalCount, tvStatAvgBill, tvStatTotalUnits;
    private TextView tvHistorySubtext;

    // Recent Calculation preview on Calculator tab
    private LinearLayout layoutRecentHistoryPreview;
    private TextView btnViewAllHistory;
    private FrameLayout containerRecentItem;

    // State
    private String[] providers = {"Tata Power", "Adani Electricity", "BEST", "MSEDCL"};
    private BillHistoryItem lastCalculatedItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historyManager = new HistoryManager(this);

        initViews();
        setupProviderDropdown();
        setupPresetChips();
        setupNavigationTabs();
        setupHistoryRecyclerView();
        setupListeners();

        updateHistoryBadge();
        updateRecentPreview();
    }

    private void initViews() {
        // Inputs
        etUnits = findViewById(R.id.etUnits);
        actvProvider = findViewById(R.id.actvProvider);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);
        btnShareBill = findViewById(R.id.btnShareBill);

        // Chips
        chip50 = findViewById(R.id.chip50);
        chip120 = findViewById(R.id.chip120);
        chip250 = findViewById(R.id.chip250);
        chip450 = findViewById(R.id.chip450);

        // Result Hero & Breakdown
        cardResult = findViewById(R.id.cardResult);
        tvResultProvider = findViewById(R.id.tvResultProvider);
        tvBplBadge = findViewById(R.id.tvBplBadge);
        tvTotalBill = findViewById(R.id.tvTotalBill);
        tvAvgRate = findViewById(R.id.tvAvgRate);
        tvBaseEnergy = findViewById(R.id.tvBaseEnergy);
        tvWheelingCharge = findViewById(R.id.tvWheelingCharge);
        tvFixedCharge = findViewById(R.id.tvFixedCharge);
        tvSavedNotice = findViewById(R.id.tvSavedNotice);
        tvEcoTip = findViewById(R.id.tvEcoTip);

        // Tier Indicator
        indicatorTier1 = findViewById(R.id.indicatorTier1);
        indicatorTier2 = findViewById(R.id.indicatorTier2);
        indicatorTier3 = findViewById(R.id.indicatorTier3);
        indicatorTier4 = findViewById(R.id.indicatorTier4);
        tvActiveTierBadge = findViewById(R.id.tvActiveTierBadge);
        tvTierAdvice = findViewById(R.id.tvTierAdvice);

        // Tabs & Containers
        tabCalculator = findViewById(R.id.tabCalculator);
        tabHistory = findViewById(R.id.tabHistory);
        layoutCalculatorView = findViewById(R.id.layoutCalculatorView);
        layoutHistoryView = findViewById(R.id.layoutHistoryView);

        // History
        rvHistory = findViewById(R.id.rvHistory);
        layoutEmptyHistory = findViewById(R.id.layoutEmptyHistory);
        btnClearAllHistory = findViewById(R.id.btnClearAllHistory);
        btnEmptyCalculateNow = findViewById(R.id.btnEmptyCalculateNow);
        tvStatTotalCount = findViewById(R.id.tvStatTotalCount);
        tvStatAvgBill = findViewById(R.id.tvStatAvgBill);
        tvStatTotalUnits = findViewById(R.id.tvStatTotalUnits);
        tvHistorySubtext = findViewById(R.id.tvHistorySubtext);

        // Recent preview
        layoutRecentHistoryPreview = findViewById(R.id.layoutRecentHistoryPreview);
        btnViewAllHistory = findViewById(R.id.btnViewAllHistory);
        containerRecentItem = findViewById(R.id.containerRecentItem);
    }

    private void setupProviderDropdown() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                providers
        );
        actvProvider.setAdapter(adapter);
        actvProvider.setText(providers[0], false);
    }

    private void setupPresetChips() {
        chip50.setOnClickListener(v -> fillUnitsAndFocus(30));
        chip120.setOnClickListener(v -> fillUnitsAndFocus(120));
        chip250.setOnClickListener(v -> fillUnitsAndFocus(250));
        chip450.setOnClickListener(v -> fillUnitsAndFocus(450));
    }

    private void fillUnitsAndFocus(int units) {
        etUnits.setText(String.valueOf(units));
        etUnits.setSelection(etUnits.getText().length());
        calculateAndRenderBill();
    }

    private void setupNavigationTabs() {
        tabCalculator.setOnClickListener(v -> switchTab(true));
        tabHistory.setOnClickListener(v -> switchTab(false));
    }

    private void switchTab(boolean isCalculator) {
        if (isCalculator) {
            tabCalculator.setTextColor(ContextCompat.getColor(this, R.color.primary));
            tabCalculator.setBackgroundResource(R.drawable.bg_tab_active);

            tabHistory.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
            tabHistory.setBackgroundResource(R.drawable.bg_tab_inactive);

            layoutCalculatorView.setVisibility(View.VISIBLE);
            layoutHistoryView.setVisibility(View.GONE);

            updateRecentPreview();
        } else {
            tabHistory.setTextColor(ContextCompat.getColor(this, R.color.primary));
            tabHistory.setBackgroundResource(R.drawable.bg_tab_active);

            tabCalculator.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
            tabCalculator.setBackgroundResource(R.drawable.bg_tab_inactive);

            layoutCalculatorView.setVisibility(View.GONE);
            layoutHistoryView.setVisibility(View.VISIBLE);

            refreshHistoryList();
        }
    }

    private void setupHistoryRecyclerView() {
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter(this, this);
        rvHistory.setAdapter(historyAdapter);
    }

    private void setupListeners() {
        btnCalculate.setOnClickListener(v -> calculateAndRenderBill());

        btnReset.setOnClickListener(v -> resetCalculator());

        btnShareBill.setOnClickListener(v -> {
            if (lastCalculatedItem != null) {
                onShare(lastCalculatedItem);
            }
        });

        btnClearAllHistory.setOnClickListener(v -> promptClearHistory());

        btnEmptyCalculateNow.setOnClickListener(v -> switchTab(true));

        btnViewAllHistory.setOnClickListener(v -> switchTab(false));
    }

    private void calculateAndRenderBill() {
        String unitsStr = etUnits.getText() != null ? etUnits.getText().toString().trim() : "";

        if (unitsStr.isEmpty()) {
            Toast.makeText(this, "Please enter monthly units consumed!", Toast.LENGTH_SHORT).show();
            etUnits.requestFocus();
            return;
        }

        int units;
        try {
            units = Integer.parseInt(unitsStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number of units!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (units < 0) {
            Toast.makeText(this, "Units cannot be negative!", Toast.LENGTH_SHORT).show();
            return;
        }

        // BPL Rule: Under 35 units
        boolean isBpl = (units < 35);
        String selectedProvider = actvProvider.getText() != null ? actvProvider.getText().toString().trim() : providers[0];
        if (selectedProvider.isEmpty()) {
            selectedProvider = providers[0];
        }

        double[] baseEnergyPerUnit;
        double[] wheelingChargePerUnit;
        double[] fixedCharge;

        // Populate provider tariffs based on MERC guidelines
        if (selectedProvider.contains("Adani")) {
            if (isBpl) {
                baseEnergyPerUnit = new double[]{2.20, 2.20, 2.20, 2.20};
                wheelingChargePerUnit = new double[]{2.60, 2.60, 2.60, 2.60};
                fixedCharge = new double[]{12.00, 12.00, 12.00, 12.00};
            } else {
                baseEnergyPerUnit = new double[]{3.15, 5.40, 7.10, 8.15};
                wheelingChargePerUnit = new double[]{2.60, 2.60, 2.60, 2.60};
                fixedCharge = new double[]{90.00, 135.00, 135.00, 160.00};
            }
        } else if (selectedProvider.contains("Tata")) {
            if (isBpl) {
                baseEnergyPerUnit = new double[]{1.56, 1.56, 1.56, 1.56};
                wheelingChargePerUnit = new double[]{2.44, 2.44, 2.44, 2.44};
                fixedCharge = new double[]{90.00, 90.00, 90.00, 90.00};
            } else {
                baseEnergyPerUnit = new double[]{1.90, 4.70, 9.24, 10.24};
                wheelingChargePerUnit = new double[]{2.44, 2.44, 2.44, 2.44};
                fixedCharge = new double[]{90.00, 135.00, 160.00, 160.00};
            }
        } else if (selectedProvider.contains("BEST")) {
            if (isBpl) {
                baseEnergyPerUnit = new double[]{1.60, 1.60, 1.60, 1.60};
                wheelingChargePerUnit = new double[]{1.68, 1.68, 1.68, 1.68};
                fixedCharge = new double[]{10.00, 10.00, 10.00, 10.00};
            } else {
                baseEnergyPerUnit = new double[]{2.02, 5.35, 10.04, 11.25};
                wheelingChargePerUnit = new double[]{1.68, 1.68, 1.68, 1.68};
                fixedCharge = new double[]{90.00, 135.00, 135.00, 160.00};
            }
        } else { // MSEDCL
            if (isBpl) {
                baseEnergyPerUnit = new double[]{1.48, 1.48, 1.48, 1.48};
                wheelingChargePerUnit = new double[]{1.38, 1.38, 1.38, 1.38};
                fixedCharge = new double[]{35.00, 35.00, 35.00, 35.00};
            } else {
                baseEnergyPerUnit = new double[]{4.32, 9.40, 12.51, 13.97};
                wheelingChargePerUnit = new double[]{1.38, 1.38, 1.38, 1.38};
                fixedCharge = new double[]{90.00, 135.00, 135.00, 160.00};
            }
        }

        // Determine which slab tier applies
        int tierIndex;
        if (units <= 100) {
            tierIndex = 0;
        } else if (units <= 300) {
            tierIndex = 1;
        } else if (units <= 500) {
            tierIndex = 2;
        } else {
            tierIndex = 3;
        }

        // Compute individual components
        double calculatedBaseEnergy = units * baseEnergyPerUnit[tierIndex];
        double calculatedWheeling = units * wheelingChargePerUnit[tierIndex];
        double calculatedFixed = fixedCharge[tierIndex];
        double totalBill = calculatedBaseEnergy + calculatedWheeling + calculatedFixed;
        double avgRate = units > 0 ? (totalBill / units) : 0;

        // Render Hero Result Card
        tvResultProvider.setText(selectedProvider);
        if (isBpl) {
            tvBplBadge.setText("BPL Subsidized");
            tvBplBadge.setTextColor(ContextCompat.getColor(this, R.color.bpl_green_text));
            tvBplBadge.setBackgroundResource(R.drawable.bg_badge_bpl);
        } else {
            tvBplBadge.setText("Standard Residential");
            tvBplBadge.setTextColor(ContextCompat.getColor(this, R.color.standard_slate_text));
            tvBplBadge.setBackgroundResource(R.drawable.bg_badge_standard);
        }

        tvTotalBill.setText(String.format(Locale.getDefault(), "₹ %.2f", totalBill));
        tvAvgRate.setText(String.format(Locale.getDefault(), "Effective Avg: ₹ %.2f / kWh", avgRate));
        tvBaseEnergy.setText(String.format(Locale.getDefault(), "₹ %.2f", calculatedBaseEnergy));
        tvWheelingCharge.setText(String.format(Locale.getDefault(), "₹ %.2f", calculatedWheeling));
        tvFixedCharge.setText(String.format(Locale.getDefault(), "₹ %.2f", calculatedFixed));

        // Update Tier Visual Indicators
        updateTierVisuals(tierIndex, units);

        // Dynamic Eco-Tip
        updateEcoTip(units, isBpl);

        // Show result card
        cardResult.setVisibility(View.VISIBLE);

        // Create history item & save
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault());
        String formattedDate = sdf.format(new Date());

        lastCalculatedItem = new BillHistoryItem(
                String.valueOf(System.currentTimeMillis()),
                selectedProvider,
                units,
                totalBill,
                calculatedBaseEnergy,
                calculatedWheeling,
                calculatedFixed,
                isBpl,
                tierIndex,
                formattedDate,
                System.currentTimeMillis()
        );

        historyManager.saveBill(lastCalculatedItem);
        updateHistoryBadge();
        updateRecentPreview();
    }

    private void updateTierVisuals(int tierIndex, int units) {
        indicatorTier1.setBackgroundResource(tierIndex >= 0 ? R.drawable.bg_tier_active : R.drawable.bg_tier_inactive);
        indicatorTier2.setBackgroundResource(tierIndex >= 1 ? R.drawable.bg_tier_active : R.drawable.bg_tier_inactive);
        indicatorTier3.setBackgroundResource(tierIndex >= 2 ? R.drawable.bg_tier_active : R.drawable.bg_tier_inactive);
        indicatorTier4.setBackgroundResource(tierIndex >= 3 ? R.drawable.bg_tier_active : R.drawable.bg_tier_inactive);

        String[] tierNames = {
                "Tier 1 (0-100 kWh)",
                "Tier 2 (101-300 kWh)",
                "Tier 3 (301-500 kWh)",
                "Tier 4 (>500 kWh High Usage)"
        };
        tvActiveTierBadge.setText(tierNames[tierIndex]);

        if (tierIndex == 0) {
            tvTierAdvice.setText("Great efficiency! You are in the lowest, most subsidized residential slab.");
        } else if (tierIndex == 1) {
            int toSave = units - 100;
            tvTierAdvice.setText(String.format(Locale.getDefault(),
                    "Moderate usage: Reducing consumption by %d units drops you into Tier 1 with cheaper rates!", toSave));
        } else if (tierIndex == 2) {
            int toSave = units - 300;
            tvTierAdvice.setText(String.format(Locale.getDefault(),
                    "Elevated slab: Saving %d units brings your bill down into Tier 2.", toSave));
        } else {
            tvTierAdvice.setText("Maximum slab: High consumption subject to premium MERC peak tariff rates.");
        }
    }

    private void updateEcoTip(int units, boolean isBpl) {
        if (isBpl) {
            tvEcoTip.setText("BPL lifeline tier active: Maintain under 35 units monthly to retain subsidized welfare rates.");
        } else if (units < 150) {
            tvEcoTip.setText("Energy Saver: Unplug idle electronics and turn off standby appliances to trim 5-10% more.");
        } else if (units < 350) {
            tvEcoTip.setText("Cooling Advice: Running air conditioners at 24°C instead of 18°C saves significant monthly kWh.");
        } else {
            tvEcoTip.setText("Heavy Usage Tip: Consider 5-star BEE rated inverter appliances and rooftop solar net-metering.");
        }
    }

    private void resetCalculator() {
        etUnits.setText("");
        cardResult.setVisibility(View.GONE);
        etUnits.requestFocus();
    }

    private void updateHistoryBadge() {
        int count = historyManager.getCount();
        tabHistory.setText(String.format(Locale.getDefault(), "📜 History (%d)", count));
    }

    private void refreshHistoryList() {
        List<BillHistoryItem> list = historyManager.getHistory();
        historyAdapter.setItems(list);

        updateHistoryBadge();

        if (list.isEmpty()) {
            rvHistory.setVisibility(View.GONE);
            layoutEmptyHistory.setVisibility(View.VISIBLE);
            btnClearAllHistory.setVisibility(View.GONE);
            findViewById(R.id.cardHistoryStats).setVisibility(View.GONE);
            tvHistorySubtext.setText("No saved calculations found");
        } else {
            rvHistory.setVisibility(View.VISIBLE);
            layoutEmptyHistory.setVisibility(View.GONE);
            btnClearAllHistory.setVisibility(View.VISIBLE);
            findViewById(R.id.cardHistoryStats).setVisibility(View.VISIBLE);
            tvHistorySubtext.setText(String.format(Locale.getDefault(), "%d calculations securely stored on your device", list.size()));

            // Compute statistics
            double totalSpend = 0;
            int totalUnits = 0;
            for (BillHistoryItem item : list) {
                totalSpend += item.getTotalBill();
                totalUnits += item.getUnits();
            }
            double avgBill = totalSpend / list.size();

            tvStatTotalCount.setText(String.valueOf(list.size()));
            tvStatAvgBill.setText(String.format(Locale.getDefault(), "₹ %.0f", avgBill));
            tvStatTotalUnits.setText(String.format(Locale.getDefault(), "%d", totalUnits));
        }
    }

    private void updateRecentPreview() {
        List<BillHistoryItem> list = historyManager.getHistory();
        if (list.isEmpty()) {
            layoutRecentHistoryPreview.setVisibility(View.GONE);
        } else {
            layoutRecentHistoryPreview.setVisibility(View.VISIBLE);
            containerRecentItem.removeAllViews();

            BillHistoryItem recent = list.get(0);
            View previewView = LayoutInflater.from(this).inflate(R.layout.item_history, containerRecentItem, false);

            TextView tvHistoryProvider = previewView.findViewById(R.id.tvHistoryProvider);
            TextView tvHistoryBadge = previewView.findViewById(R.id.tvHistoryBadge);
            TextView tvHistoryUnits = previewView.findViewById(R.id.tvHistoryUnits);
            TextView tvHistoryTotal = previewView.findViewById(R.id.tvHistoryTotal);
            TextView tvHistoryDate = previewView.findViewById(R.id.tvHistoryDate);
            TextView tvHistoryBreakdown = previewView.findViewById(R.id.tvHistoryBreakdown);
            ImageView btnDeleteHistory = previewView.findViewById(R.id.btnDeleteHistory);
            MaterialButton btnHistoryLoad = previewView.findViewById(R.id.btnHistoryLoad);
            MaterialButton btnHistoryShare = previewView.findViewById(R.id.btnHistoryShare);

            tvHistoryProvider.setText(recent.getProvider());
            tvHistoryUnits.setText(String.format(Locale.getDefault(), "%d kWh", recent.getUnits()));
            tvHistoryTotal.setText(String.format(Locale.getDefault(), "₹ %.2f", recent.getTotalBill()));
            tvHistoryDate.setText(recent.getFormattedDate());

            if (recent.isBpl()) {
                tvHistoryBadge.setText("BPL Subsidized");
                tvHistoryBadge.setTextColor(ContextCompat.getColor(this, R.color.bpl_green_text));
                tvHistoryBadge.setBackgroundResource(R.drawable.bg_badge_bpl);
            } else {
                tvHistoryBadge.setText("Standard Tariff");
                tvHistoryBadge.setTextColor(ContextCompat.getColor(this, R.color.standard_slate_text));
                tvHistoryBadge.setBackgroundResource(R.drawable.bg_badge_standard);
            }

            tvHistoryBreakdown.setText(String.format(Locale.getDefault(),
                    "Energy: ₹%.2f • Wheeling: ₹%.2f • Fixed: ₹%.2f",
                    recent.getBaseEnergy(), recent.getWheelingCharge(), recent.getFixedCharge()));

            btnHistoryLoad.setOnClickListener(v -> onLoad(recent));
            btnDeleteHistory.setOnClickListener(v -> onDelete(recent));
            btnHistoryShare.setOnClickListener(v -> onShare(recent));

            containerRecentItem.addView(previewView);
        }
    }

    private void promptClearHistory() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Clear All History?")
                .setMessage("This will remove all saved electricity bill calculations from your device.")
                .setPositiveButton("Clear All", (dialog, which) -> {
                    historyManager.clearAll();
                    refreshHistoryList();
                    updateRecentPreview();
                    updateHistoryBadge();
                    Toast.makeText(this, "History cleared", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onLoad(BillHistoryItem item) {
        if (item == null) return;
        etUnits.setText(String.valueOf(item.getUnits()));
        actvProvider.setText(item.getProvider(), false);
        switchTab(true);
        calculateAndRenderBill();
        Toast.makeText(this, "Loaded calculation for " + item.getProvider(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete(BillHistoryItem item) {
        if (item == null) return;
        historyManager.deleteItem(item.getId());
        refreshHistoryList();
        updateRecentPreview();
        updateHistoryBadge();
        Toast.makeText(this, "Calculation removed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShare(BillHistoryItem item) {
        if (item == null) return;
        String shareMessage = String.format(Locale.getDefault(),
                "⚡ Electricity Bill Summary\n" +
                "───────────────────────\n" +
                "Utility Provider : %s\n" +
                "Consumption      : %d kWh\n" +
                "Tariff Category  : %s\n" +
                "───────────────────────\n" +
                "• Base Energy    : ₹ %.2f\n" +
                "• Wheeling Charge: ₹ %.2f\n" +
                "• Fixed Charge   : ₹ %.2f\n" +
                "───────────────────────\n" +
                "ESTIMATED TOTAL  : ₹ %.2f\n" +
                "Avg Rate         : ₹ %.2f / unit\n" +
                "Calculated on    : %s\n" +
                "───────────────────────\n" +
                "Calculated using Electricity Bill Calculator",
                item.getProvider(),
                item.getUnits(),
                item.isBpl() ? "BPL Subsidized (<35 units)" : "Standard Residential",
                item.getBaseEnergy(),
                item.getWheelingCharge(),
                item.getFixedCharge(),
                item.getTotalBill(),
                item.getAverageRatePerUnit(),
                item.getFormattedDate()
        );

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Electricity Bill - " + item.getProvider());
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "Share Bill Summary via"));
    }
}
