package sorrentino.sbirocalendar.dto;

public class ToggleRequest {

    public Integer getDay() {
        return day;
    }

    public ToggleRequest(Integer day) {
        this.day = day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer day;

}
