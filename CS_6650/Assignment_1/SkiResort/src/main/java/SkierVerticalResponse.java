import java.util.List;

public class SkierVerticalResponse {

    private List<ResortSkierVertical> resorts;

    public SkierVerticalResponse(List<ResortSkierVertical> resortList) {
        this.resorts = resortList;
    }

    public List<ResortSkierVertical> getResorts() {
        return resorts;
    }

    public void setResorts(List<ResortSkierVertical> resortList) {
        this.resorts = resortList;
    }
}
