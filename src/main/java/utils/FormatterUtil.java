package utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class FormatterUtil {

    public static final String formatarDataHora(Date data){
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(data);
    }

    public static final Date formatarDataHora(String data) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(data);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static final String formatarData(Date data){
        return new SimpleDateFormat("dd-MM-yyyy").format(data);
    }

    public static final Date formatarData(String data) {
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(data);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static final String formatarValor(Double valor){
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        return numberFormat.format(valor);
    }

    public static final Double formatarValor(String valor) {
        try {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
            return (Double) numberFormat.parse(valor.replaceAll("R$,.", "").trim());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
