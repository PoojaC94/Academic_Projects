import java.util.List;

public class ResortResponse {

    private List<Resort> resorts;

    public ResortResponse(List<Resort> resorts) {
        this.resorts = resorts;
    }

    public List<Resort> getResorts() {
        return resorts;
    }

    public void setResorts(List<Resort> resorts) {
        this.resorts = resorts;
    }
}
