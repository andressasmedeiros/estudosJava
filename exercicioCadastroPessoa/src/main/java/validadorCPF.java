package main.java;

public class validadorCPF {

    public static boolean isValid(String cpf) {
        if (cpf == null) return false;

        String digits = cpf.replaceAll("\\D", "");
        if (digits.length() != 11) return false;

        if (digits.matches("(\\d)\\1{10}")) return false;

        try {
            int[] num = new int[11];
            for (int i = 0; i < 11; i++) {
                num[i] = Character.getNumericValue(digits.charAt(i));
            }

            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += num[i] * (10 - i);
            }
            int dv1 = (sum % 11 < 2) ? 0 : 11 - (sum % 11);
            if (dv1 != num[9]) return false;

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += num[i] * (11 - i);
            }
            int dv2 = (sum % 11 < 2) ? 0 : 11 - (sum % 11);
            return dv2 == num[10];

        } catch (Exception e) {
            return false;
        }
    }
}
