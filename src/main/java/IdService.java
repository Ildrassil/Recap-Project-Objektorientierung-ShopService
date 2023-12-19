import java.util.UUID;

public class IdService {

    public String genrateId(){
        UUID genrateID = UUID.randomUUID();
        return genrateID.toString();
    }
}
