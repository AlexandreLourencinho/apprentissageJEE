package dao;

import org.springframework.stereotype.Component;

@Component("dao")
public class Daoimpl implements IDao {

    @Override
    public double getData() {
        System.out.println("version bdd");
        return Math.random() * 40;
    }

}
