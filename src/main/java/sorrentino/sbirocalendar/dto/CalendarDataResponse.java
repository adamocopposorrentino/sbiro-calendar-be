package sorrentino.sbirocalendar.dto;

import java.util.List;
import java.util.Map;

public class CalendarDataResponse {
    public int totalUsers;
    public List<Integer> myDays;
    // Mappa il giorno (es. "5") alla lista di nomi (es. ["Marco", "Giulia"])
    public Map<String, List<String>> othersAvailability;

    public int getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        this.totalUsers = totalUsers;
    }

    public List<Integer> getMyDays() {
        return myDays;
    }

    public void setMyDays(List<Integer> myDays) {
        this.myDays = myDays;
    }

    public Map<String, List<String>> getOthersAvailability() {
        return othersAvailability;
    }

    public void setOthersAvailability(Map<String, List<String>> othersAvailability) {
        this.othersAvailability = othersAvailability;
    }
}
