package utils;

import java.text.NumberFormat;
import java.util.Locale;

public class ConvertToVnd {
    public static String convertNumberToTextVND(long total)
    {
        try
        {
            String rs = "";
            String[] ch = { "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín" };
            String[] rch = { "lẻ", "mốt", "", "", "", "lăm" };
            String[] u = { "", "mươi", "trăm", "ngàn", "", "", "triệu", "", "", "tỷ", "", "", "ngàn", "", "", "triệu" };
            String nstr = String.valueOf(total);
            long[] n = new long[nstr.length()];
            int len = n.length;
            for (int i = 0; i < len; i++)
            {
            	n[len - 1 - i] = Long.valueOf(nstr.substring(i, i+1));
            }
            for (int i = len - 1; i >= 0; i--)
            {
                if (i % 3 == 2)// số 0 ở hàng trăm
                {
                    if (n[i] == 0 && n[i - 1] == 0 && n[i - 2] == 0) continue;//nếu cả 3 số là 0 thì bỏ qua không đọc
                }
                else if (i % 3 == 1) // số ở hàng chục
                {
                    if (n[i] == 0)
                    {
                        if (n[i - 1] == 0) { continue; }// nếu hàng chục và hàng đơn vị đều là 0 thì bỏ qua.
                        else
                        {
                            rs += " " + rch[(int)n[i]]; continue;// hàng chục là 0 thì bỏ qua, đọc số hàng đơn vị
                        }
                    }
                    if (n[i] == 1)//nếu số hàng chục là 1 thì đọc là mười
                    {
                        rs += " mười"; continue;
                    }
                }
                else if (i != len - 1)// số ở hàng đơn vị (không phải là số đầu tiên)
                {
                    if (n[i] == 0)// số hàng đơn vị là 0 thì chỉ đọc đơn vị
                    {
                        if (i + 2 <= len - 1 && n[i + 2] == 0 && n[i + 1] == 0) continue;
                        rs += " " + (i % 3 == 0 ? u[i] : u[i % 3]);
                        continue;
                    }
                    if (n[i] == 1)// nếu là 1 thì tùy vào số hàng chục mà đọc: 0,1: một / còn lại: mốt
                    {
                        rs += " " + ((n[i + 1] == 1 || n[i + 1] == 0) ? ch[(int)n[i]] : rch[(int)n[i]]);
                        rs += " " + (i % 3 == 0 ? u[i] : u[i % 3]);
                        continue;
                    }
                    if (n[i] == 5) // cách đọc số 5
                    {
                        if (n[i + 1] != 0) //nếu số hàng chục khác 0 thì đọc số 5 là lăm
                        {
                            rs += " " + rch[(int)n[i]];// đọc số 
                            rs += " " + (i % 3 == 0 ? u[i] : u[i % 3]);// đọc đơn vị
                            continue;
                        }
                    }
                }
                rs += (rs == "" ? " " : ", ") + ch[(int)n[i]];// đọc số
                rs += " " + (i % 3 == 0 ? u[i] : u[i % 3]);// đọc đơn vị
            }
            if (rs.charAt(rs.length() - 1) != ' ')
                rs += " đồng";
            else
                rs += "đồng";

            if (rs.length() > 2)
            {
                String rs1 = rs.substring(0, 2);
                rs1 = rs1.toUpperCase();
                rs = rs.substring(2);
                rs = rs1 + rs;
            }
            return rs.trim().replace("lẻ,", "lẻ").replace("mươi,", "mươi").replace("trăm,", "trăm").replace("mười,", "mười");
        }
        catch(Exception ex)
        {
        	ex.printStackTrace();
            return "";
        }
    }

    public static String formatCurrency(long amount){
        NumberFormat numberformat=NumberFormat.getCurrencyInstance(new Locale("vi","VN"));
        return numberformat.format(amount);
    }

    public static void main(String[] args)
    {
        System.out.println("Viết bằng chữ:"+convertNumberToTextVND((long)3000000.00));
    }
}
