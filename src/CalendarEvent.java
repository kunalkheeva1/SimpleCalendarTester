import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.io.Serializable;

/**
 * class CalendarEvent is implementing Serializable
 */
public class CalendarEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    String heading;
    Date dateBegin;
    String beginning;
    String finalizing;

    /**
     * Constructor for handling
     * @param heading -name of the event
     * @param dateBegin -  starting date of the envent as string
     * @param s of the event
     * @param e event finization with accordance to the input
     * @throws ParseException
     */
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

    /**
     * method for object initializing
     * @return
     * String for the object easiness.
     */
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