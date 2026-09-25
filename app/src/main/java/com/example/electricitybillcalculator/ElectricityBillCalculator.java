import java.util.*;

public class ElectricityBillCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter electricity units consumed: ");
        int units = sc.nextInt();
        boolean BPL = false;

        if (units < 35) {
            BPL = true;
        }

        System.out.print("Enter electricity provider (Adani / Tata / BEST / MSEDCL): ");
        String elecProvider = sc.next().trim(); // <-- changed nextLine() to next()

        double totalBill = 0;

        // 1. Declare variables outside so they are accessible everywhere in main()
        double[] baseEnergyPerUnit = null;
        double[] wheelingChargePerUnit = null;
        double[] fixedCharge = null;

        // 2. Populate based on provider using .equalsIgnoreCase()
        if (elecProvider.equalsIgnoreCase("Adani")) {
            if (BPL) {
                baseEnergyPerUnit = new double[] { 2.20, 2.20, 2.20, 2.20 };
                wheelingChargePerUnit = new double[] { 2.60, 2.60, 2.60, 2.60 };
                fixedCharge = new double[] { 12.00, 12.00, 12.00, 12.00 };
            } else {
                baseEnergyPerUnit = new double[] { 3.15, 5.40, 7.10, 8.15 };
                wheelingChargePerUnit = new double[] { 2.60, 2.60, 2.60, 2.60 };
                fixedCharge = new double[] { 90, 135, 135, 160 };
            }
        } else if (elecProvider.equalsIgnoreCase("Tata")) {
            if (BPL) {
                baseEnergyPerUnit = new double[] { 1.56, 1.56, 1.56, 1.56 };
                wheelingChargePerUnit = new double[] { 2.44, 2.44, 2.44, 2.44 };
                fixedCharge = new double[] { 90.00, 90.00, 90.00, 90.00 };
            } else {
                baseEnergyPerUnit = new double[] { 1.90, 4.70, 9.24, 10.24 };
                wheelingChargePerUnit = new double[] { 2.44, 2.44, 2.44, 2.44 };
                fixedCharge = new double[] { 90, 135, 160, 160 };
            }
        } else if (elecProvider.equalsIgnoreCase("BEST")) {
            if (BPL) {
                baseEnergyPerUnit = new double[] { 1.60, 1.60, 1.60, 1.60 };
                wheelingChargePerUnit = new double[] { 1.68, 1.68, 1.68, 1.68 };
                fixedCharge = new double[] { 10, 10, 10, 10 };
            } else {
                baseEnergyPerUnit = new double[] { 2.02, 5.35, 10.04, 11.25 };
                wheelingChargePerUnit = new double[] { 1.68, 1.68, 1.68, 1.68 };
                fixedCharge = new double[] { 90, 135, 135, 160 };
            }
        } else if (elecProvider.equalsIgnoreCase("MSEDCL")) {
            if (BPL) {
                baseEnergyPerUnit = new double[] { 1.48, 1.48, 1.48, 1.48 };
                wheelingChargePerUnit = new double[] { 1.38, 1.38, 1.38, 1.38 };
                fixedCharge = new double[] { 35, 35, 35, 35 };
            } else {
                baseEnergyPerUnit = new double[] { 4.32, 9.40, 12.51, 13.97 };
                wheelingChargePerUnit = new double[] { 1.38, 1.38, 1.38, 1.38 };
                fixedCharge = new double[] { 90, 135, 135, 160 };
            }
        } else {
            System.out.println("Unknown provider entered!");
            sc.close();
            return;
        }

        // 3. Bill Calculation
        if (units < 0) {
            System.out.println("Units can't be a negative value!");
        } else if (units <= 100) {
            totalBill = (units * baseEnergyPerUnit[0]) + (units * wheelingChargePerUnit[0]) + fixedCharge[0];
        } else if (units <= 300) {
            totalBill = (units * baseEnergyPerUnit[1]) + (units * wheelingChargePerUnit[1]) + fixedCharge[1];
        } else if (units <= 500) {
            totalBill = (units * baseEnergyPerUnit[2]) + (units * wheelingChargePerUnit[2]) + fixedCharge[2];
        } else
            totalBill = (units * baseEnergyPerUnit[3]) + (units * wheelingChargePerUnit[3]) + fixedCharge[3];

        System.out.println("Provider   : " + elecProvider);
        System.out.println("Units      : " + units);
        System.out.println("BPL Status: " + BPL);
        System.out.println("Total Bill : $" + totalBill);

        sc.close();
    }
}
