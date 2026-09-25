package com.example.electricitybillcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    // 1. Declare references to XML elements
    private TextInputEditText etUnits;
    private Spinner spinnerProvider;
    private MaterialButton btnCalculate, btnReset;
    private MaterialCardView cardResult;
    private TextView tvResultProvider, tvBplBadge, tvTotalBill;
    private TextView tvBaseEnergy, tvWheelingCharge, tvFixedCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Connects this Java backend with the XML frontend
        setContentView(R.layout.activity_main);

        // 2. Bind frontend elements by their IDs
        etUnits = findViewById(R.id.etUnits);
        spinnerProvider = findViewById(R.id.spinnerProvider);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);

        cardResult = findViewById(R.id.cardResult);
        tvResultProvider = findViewById(R.id.tvResultProvider);
        tvBplBadge = findViewById(R.id.tvBplBadge);
        tvTotalBill = findViewById(R.id.tvTotalBill);
        tvBaseEnergy = findViewById(R.id.tvBaseEnergy);
        tvWheelingCharge = findViewById(R.id.tvWheelingCharge);
        tvFixedCharge = findViewById(R.id.tvFixedCharge);

        // 3. Populate Provider Dropdown
        String[] providers = { "Adani Electricity", "Tata Power", "BEST", "MSEDCL" };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                providers);
        spinnerProvider.setAdapter(adapter);

        // 4. Calculate button click listener
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateAndRenderBill();
            }
        });

        // 5. Reset button click listener
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etUnits.setText("");
                cardResult.setVisibility(View.GONE);
                etUnits.requestFocus();
            }
        });
    }

    private void calculateAndRenderBill() {
        String unitsStr = etUnits.getText() != null ? etUnits.getText().toString().trim() : "";

        if (unitsStr.isEmpty()) {
            Toast.makeText(this, "Please enter monthly units!", Toast.LENGTH_SHORT).show();
            return;
        }

        int units = Integer.parseInt(unitsStr);
        if (units < 0) {
            Toast.makeText(this, "Units cannot be negative!", Toast.LENGTH_SHORT).show();
            return;
        }

        // BPL Rule: Under 35 units
        boolean isBpl = (units < 35);
        String selectedProvider = spinnerProvider.getSelectedItem().toString();

        double[] baseEnergyPerUnit;
        double[] wheelingChargePerUnit;
        double[] fixedCharge;

        // Populate provider tariffs
        if (selectedProvider.contains("Adani")) {
            if (isBpl) {
                baseEnergyPerUnit = new double[] { 2.20, 2.20, 2.20, 2.20 };
                wheelingChargePerUnit = new double[] { 2.60, 2.60, 2.60, 2.60 };
                fixedCharge = new double[] { 12.00, 12.00, 12.00, 12.00 };
            } else {
                baseEnergyPerUnit = new double[] { 3.15, 5.40, 7.10, 8.15 };
                wheelingChargePerUnit = new double[] { 2.60, 2.60, 2.60, 2.60 };
                fixedCharge = new double[] { 90, 135, 135, 160 };
            }
        } else if (selectedProvider.contains("Tata")) {
            if (isBpl) {
                baseEnergyPerUnit = new double[] { 1.56, 1.56, 1.56, 1.56 };
                wheelingChargePerUnit = new double[] { 2.44, 2.44, 2.44, 2.44 };
                fixedCharge = new double[] { 90.00, 90.00, 90.00, 90.00 };
            } else {
                baseEnergyPerUnit = new double[] { 1.90, 4.70, 9.24, 10.24 };
                wheelingChargePerUnit = new double[] { 2.44, 2.44, 2.44, 2.44 };
                fixedCharge = new double[] { 90, 135, 160, 160 };
            }
        } else if (selectedProvider.contains("BEST")) {
            if (isBpl) {
                baseEnergyPerUnit = new double[] { 1.60, 1.60, 1.60, 1.60 };
                wheelingChargePerUnit = new double[] { 1.68, 1.68, 1.68, 1.68 };
                fixedCharge = new double[] { 10, 10, 10, 10 };
            } else {
                baseEnergyPerUnit = new double[] { 2.02, 5.35, 10.04, 11.25 };
                wheelingChargePerUnit = new double[] { 1.68, 1.68, 1.68, 1.68 };
                fixedCharge = new double[] { 90, 135, 135, 160 };
            }
        } else { // MSEDCL
            if (isBpl) {
                baseEnergyPerUnit = new double[] { 1.48, 1.48, 1.48, 1.48 };
                wheelingChargePerUnit = new double[] { 1.38, 1.38, 1.38, 1.38 };
                fixedCharge = new double[] { 35, 35, 35, 35 };
            } else {
                baseEnergyPerUnit = new double[] { 4.32, 9.40, 12.51, 13.97 };
                wheelingChargePerUnit = new double[] { 1.38, 1.38, 1.38, 1.38 };
                fixedCharge = new double[] { 90, 135, 135, 160 };
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

        // Render data to the frontend
        tvResultProvider.setText(selectedProvider);
        if (isBpl) {
            tvBplBadge.setText("BPL Subsidized");
            tvBplBadge.setTextColor(0xFF047857); // Green text
            tvBplBadge.setBackgroundColor(0xFFD1FAE5); // Green pill
        } else {
            tvBplBadge.setText("Standard Tariff");
            tvBplBadge.setTextColor(0xFF1E293B); // Slate text
            tvBplBadge.setBackgroundColor(0xFFE2E8F0); // Slate pill
        }

        tvTotalBill.setText(String.format("₹ %.2f", totalBill));
        tvBaseEnergy.setText(String.format("₹ %.2f", calculatedBaseEnergy));
        tvWheelingCharge.setText(String.format("₹ %.2f", calculatedWheeling));
        tvFixedCharge.setText(String.format("₹ %.2f", calculatedFixed));

        // Reveal the result card
        cardResult.setVisibility(View.VISIBLE);
    }
}
