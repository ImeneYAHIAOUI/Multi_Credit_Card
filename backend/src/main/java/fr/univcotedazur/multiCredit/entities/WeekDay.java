
package fr.univcotedazur.multiCredit.entities;

public enum WeekDay {
    Monday("Monday"), Tuesday("Tuesday"), Wednesday("Wednesday"),
    Thursday("Thursday"), Friday("Friday"), Saturday("Saturday"), Sunday("Sunday");
    private final String weekDayName;


    WeekDay(String weekDayName) {
        this.weekDayName = weekDayName;
    }

    public String getWeekDayName() {
        return weekDayName;
    }
}
