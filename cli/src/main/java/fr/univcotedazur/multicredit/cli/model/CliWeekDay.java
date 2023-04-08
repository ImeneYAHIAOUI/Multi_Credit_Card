package fr.univcotedazur.multicredit.cli.model;

public enum CliWeekDay {
    MONDAY("Monday"), TUESDAY("Tuesday"), WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"), FRIDAY("Friday"), SATURDAY("Saturday"), SUNDAY("Sunday");
    private final String weekDayName;

    CliWeekDay(String weekDayName) {
        this.weekDayName = weekDayName;
    }

    public String getWeekDayName() {
        return weekDayName;
    }
}
