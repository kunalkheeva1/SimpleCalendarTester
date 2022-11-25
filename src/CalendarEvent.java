import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.io.Serializable;

public class CalendarEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    String heading;
    Date dateBegin;
    String beginning;
    String finalizing;

    public CalendarEvent(String heading, String dateBegin, String s, String e) throws ParseException {
        this.heading = heading;
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
        this.dateBegin = format.parse(dateBegin);
        this.beginning = s;
        this.finalizing = e;
    }




    public Date getDateBegin() {
        return dateBegin;
    }



    public String getDateStr() {
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
        return format.format(dateBegin);
    }

    public String getBeginning() {
        return beginning;
    }


    public String getFinalizing() {
        return finalizing;
    }

    public String toString() {
        StringBuffer objStr = new StringBuffer();
        StringBuffer startingConvert = new StringBuffer();
        StringBuffer endingConvert = new StringBuffer();
        for(int i = 0; i < 7; i++) {
            startingConvert.append(beginning.charAt(i));
            endingConvert.append(finalizing.charAt(i));
        }
        if(beginning.charAt(5) == 'p') {
            startingConvert.setCharAt(0, (char)(beginning.charAt(0) - 1));
            startingConvert.setCharAt(1, (char)(beginning.charAt(1) - 2));
        }
        if(finalizing.charAt(5) == 'p') {
            endingConvert.setCharAt(0, (char)(finalizing.charAt(0) - 1));
            endingConvert.setCharAt(1, (char)(finalizing.charAt(1) - 2));
        }
        objStr.append(startingConvert.toString() + " - " + endingConvert.toString() + "  " + heading);

        return objStr.toString();
    }
}