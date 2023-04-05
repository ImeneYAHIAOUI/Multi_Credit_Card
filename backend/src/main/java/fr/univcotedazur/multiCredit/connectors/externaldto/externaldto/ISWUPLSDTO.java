package fr.univcotedazur.multiCredit.connectors.externaldto.externaldto;

public class ISWUPLSDTO {

    private String  car_reg_num;
    private int  parking_spot_number;
    private long  parking_date_time;
    private long parking_duration;
    private long parking_end_date_time;

    public long getParking_end_date_time() {
        return parking_end_date_time;
    }

    public void setParking_end_date_time(long parking_end_date_time) {
        this.parking_end_date_time = parking_end_date_time;
    }


    public ISWUPLSDTO() {
    }

    public ISWUPLSDTO(String car_reg_num, int parking_spot_number, long parking_date_time, long parking_duration) {
        this.car_reg_num = car_reg_num;
        this.parking_spot_number = parking_spot_number;
        this.parking_date_time = parking_date_time;
        this.parking_duration = parking_duration;
    }

    public String getCar_reg_num() {
        return car_reg_num;
    }

    public void setCar_reg_num(String car_reg_num) {
        this.car_reg_num = car_reg_num;
    }

    public int getParking_spot_number() {
        return parking_spot_number;
    }

    public void setParking_spot_number(int parking_spot_number) {
        this.parking_spot_number = parking_spot_number;
    }

    public long getParking_date_time() {
        return parking_date_time;
    }

    public void setParking_date_time(long parking_date_time) {
        this.parking_date_time = parking_date_time;
    }

    public long getParking_duration() {
        return parking_duration;
    }

    public void setParking_duration(long parking_duration) {
        this.parking_duration = parking_duration;
    }
}
