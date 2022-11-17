import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.io.Serializable;

public class CalendarEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    String title;
    Date date;
    String starting;
    String ending;

    public CalendarEvent(String title, String date, String s, String e) throws ParseException {
        this.title = title;
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
        this.date = format.parse(date);
        this.starting = s;
        this.ending = e;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public Date getDate() {
        return date;
    }



    public String getDateStr() {
        SimpleDateFormat format = new SimpleDateFormat("M/d/yyyy");
        return format.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStarting() {
        return starting;
    }

    public void setStarting(String starting) {
        this.starting = starting;
    }


    public String getEnding() {
        return ending;
    }


    public void setEnding(String ending) {
        this.ending = ending;
    }


    public CalendarEvent() {
        title = null;
        date = null;
        starting = null;
        ending = null;
    }



    public String toString() {
        StringBuffer objStr = new StringBuffer();
        StringBuffer startingConvert = new StringBuffer();
        StringBuffer endingConvert = new StringBuffer();
        for(int i = 0; i < 7; i++) {
            startingConvert.append(starting.charAt(i));
            endingConvert.append(ending.charAt(i));
        }
        if(starting.charAt(5) == 'p') {
            startingConvert.setCharAt(0, (char)(starting.charAt(0) - 1));
            startingConvert.setCharAt(1, (char)(starting.charAt(1) - 2));
        }
        if(ending.charAt(5) == 'p') {
            endingConvert.setCharAt(0, (char)(ending.charAt(0) - 1));
            endingConvert.setCharAt(1, (char)(ending.charAt(1) - 2));
        }
        objStr.append(startingConvert.toString() + " - " + endingConvert.toString() + "  " + title);

        return objStr.toString();
    }
}