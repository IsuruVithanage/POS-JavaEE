package bo;

import bo.custom.impl.CustomerBOImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory() {
    }

    public static BoFactory getBOFactory() {
        if (boFactory == null) {
            boFactory = new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BoTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerBOImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        CUSTOMER, ITEM, CUSTOMER_INCOME, INCOME_REPORT, MANAGE_ORDER
    }
}
