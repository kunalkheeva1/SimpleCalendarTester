import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class CalendarConfiguration {
    private GregorianCalendar calendar;
    private ArrayList<ChangeListener> listeners;
    private ArrayList<CalendarEvent> eventList;
    private int currDate;
    private int currMonth;
    private int currYear;
    private String archiveFile;



    public CalendarConfiguration() {
        calendar = new GregorianCalendar();
        listeners = new ArrayList<ChangeListener>();
        eventList = new ArrayList<CalendarEvent>();
        currDate = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        currMonth = calendar.get(java.util.Calendar.MONTH);
        currYear = calendar.get(java.util.Calendar.YEAR);
        archiveFile = "events.txt";
    }

    public void attach_View_Total(ChangeListener l) {
        listeners.add(l);
    }

    public void setDate(String aDate) {
        if(aDate != null)
        {
            calendar.set(java.util.Calendar.DAY_OF_MONTH, Integer.parseInt(aDate));
            for (ChangeListener l : listeners)
            {
                l.stateChanged(new ChangeEvent(this));
            }
        }
    }

    public void buttonUpdate(int dayChange, int monthChange) {

        calendar.add(java.util.Calendar.DAY_OF_MONTH, dayChange);
        calendar.add(java.util.Calendar.MONTH, monthChange);

        for (ChangeListener listener : listeners)
        {
            listener.stateChanged(new ChangeEvent(this));
        }
    }



    public boolean checkEvent(String currDate) {
        for(int i = 0; i < eventList.size(); i++) {
            if(eventList.get(i).getDateStr().equalsIgnoreCase(currDate)) {
                return true;
            }
        }
        return false;
    }

    public int firstDayOfWeek() {
        int original = calendar.get(java.util.Calendar.DAY_OF_MONTH);
        calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(java.util.Calendar.DAY_OF_WEEK);
        calendar.set(java.util.Calendar.DAY_OF_MONTH, original);
        return firstDayOfWeek;
    }



    public int actualDayOfWeek()
    {
        return calendar.get(java.util.Calendar.DAY_OF_WEEK);
    }
    public int actualDate()
    {
        return calendar.get(java.util.Calendar.DAY_OF_MONTH);
    }

    public int actualMonth()
    {
        return calendar.get(java.util.Calendar.MONTH);
    }
    public int lastDayOfMonth()
    {
        return calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
    }

    public int currentMonth()
    {
        return this.currMonth;
    }

    public int currentYear()
    {
        return this.currYear;
    }

    public int actualYear()
    {
        return calendar.get(java.util.Calendar.YEAR);
    }

    public String getEventList() {
        StringBuffer objStr = new StringBuffer();
        StringBuffer dateStr = new StringBuffer();
        dateStr.append((actualMonth() + 1) + "/");
        dateStr.append(actualDate() + "/");
        dateStr.append(actualYear());
        String finalDateStr = dateStr.toString();
        for(int i = 0; i < eventList.size(); i++) {
            if(eventList.get(i).getDateStr().equalsIgnoreCase(finalDateStr))
                objStr.append(eventList.get(i).toString() + '\n');
        }

        return objStr.toString();
    }

    public void saveEvents() throws IOException {
        ObjectOutputStream writeSer = new ObjectOutputStream(new FileOutputStream(archiveFile));
        writeSer.writeInt(eventList.size());
        for(int i = 0; i < eventList.size(); i++)
            writeSer.writeObject(eventList.get(i));
        writeSer.close();
    }

    public void loadEvents() throws IOException, ParseException, ClassNotFoundException {
        File input = new File(archiveFile);
        if(input.exists())
        {
            ObjectInputStream readSer = new ObjectInputStream(new FileInputStream(input));
            int size = readSer.readInt();
            while(size > 0)
            {
                eventList.add((CalendarEvent)readSer.readObject());
                size--;
            }
            readSer.close();
        }
    }

    public String addEvent(String title, String date, String starting, String ending) throws ParseException {
        StringBuffer timeChange = new StringBuffer();
        if(starting.charAt(5) == 'p') {
            for(int i = 0; i < 7; i++)
                timeChange.append(starting.charAt(i));
            timeChange.setCharAt(0, (char)(starting.charAt(0) + 1));
            timeChange.setCharAt(1, (char)(starting.charAt(1) + 2));
            starting = timeChange.toString();
        }
        timeChange = new StringBuffer();
        if(ending.charAt(5) == 'p') {
            for(int i = 0; i < 7; i++)
                timeChange.append(ending.charAt(i));
            timeChange.setCharAt(0, (char)(ending.charAt(0) + 1));
            timeChange.setCharAt(1, (char)(ending.charAt(1) + 2));
            ending = timeChange.toString();
        }
        if(starting.compareToIgnoreCase(ending) > 0)
            return "Starting time: " + starting + " > Ending time: " + ending;
        for(int i = 0; i < eventList.size(); i++) {
            if(eventList.get(i).getDateStr().compareToIgnoreCase(date) == 0)
                if(eventList.get(i).getFinalizing().compareToIgnoreCase(starting) > 0 && eventList.get(i).getBeginning().compareToIgnoreCase(ending) < 0)
                    return "Time conflict " + eventList.get(i).toString();
        }
        this.eventList.add(new CalendarEvent(title, date, starting, ending));
        eventList.sort(new Comparator<CalendarEvent>() {
            public int compare(CalendarEvent a, CalendarEvent b) {
                if(a.getDateBegin().compareTo(b.getDateBegin()) == 0) {
                    return a.getBeginning().compareToIgnoreCase(b.getBeginning());
                }
                return a.getDateBegin().compareTo(b.getDateBegin());
            }
        });
        for (ChangeListener l : listeners) {
            l.stateChanged(new ChangeEvent(this));
        }
        return null;
    }

    public void deleteChosenEvent(String aDate, String starting) {
        for(int i = 0; i < eventList.size(); i++) {
            if(eventList.get(i).getDateStr().equalsIgnoreCase(aDate) && eventList.get(i).getBeginning().equalsIgnoreCase(starting)) {
                eventList.remove(i);
                for (ChangeListener l : listeners) {
                    l.stateChanged(new ChangeEvent(this));
                }
            }
        }
    }
}