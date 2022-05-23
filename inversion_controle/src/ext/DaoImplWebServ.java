package ext;

import dao.IDao;

public class DaoImplWebServ implements IDao {

    @Override
    public double getData() {
        System.out.println("version webservice");
        return 9000;
    }
}
