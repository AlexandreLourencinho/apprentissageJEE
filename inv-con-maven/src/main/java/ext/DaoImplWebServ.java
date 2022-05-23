package ext;

import dao.IDao;
import org.springframework.stereotype.Component;

@Component("daows")
public class DaoImplWebServ implements IDao {

    @Override
    public double getData() {
        System.out.println("version webservice");
        return 9000;
    }
}
