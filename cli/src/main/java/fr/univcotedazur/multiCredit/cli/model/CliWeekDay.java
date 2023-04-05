package fr.univcotedazur.multiCredit.cli.model;

public enum CliWeekDay {
    Monday("Monday"), Tuesday("Tuesday"), Wednesday("Wednesday"),
    Thursday("Thursday"), Friday("Friday"), Saturday("Saturday"), Sunday("Sunday");
    private final String weekDayName;

    CliWeekDay(String weekDayName) {
        this.weekDayName = weekDayName;
    }

    public String getWeekDayName() {
        return weekDayName;
    }
}
