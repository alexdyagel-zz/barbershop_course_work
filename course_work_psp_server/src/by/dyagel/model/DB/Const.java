package by.dyagel.model.DB;

/**
 * Class-storage of names of tables and their columns
 */
public class Const {
    public static final String USER_TABLE = "user";
    public static final String USER_ID = "id_user";
    public static final String USER_FIO = "FIO";
    public static final String USER_EMAIL = "Email";
    public static final String USER_PASSWORD = "password";
    public static final String USER_PHONE_NUMBER = "phone_number";
    public static final String USER_ROLE = "role";

    public static final String SERVICE_TABLE = "service";
    public static final String SERVICE_ID = "service_id";
    public static final String SERVICE_CATEGORY = "service_category";
    public static final String SERVICE_NAME = "service_name";
    public static final String SERVICE_PRICE = "service_price";

    public static final String SPECIALIST_TABLE = "specialist";
    public static final String SPECIALIST_ID = "spec_id";
    public static final String SPECIALIST_POSITION = "spec_position";
    public static final String SPECIALIST_NAME = "spec_name";

    public static final String SEANCES_TABLE = "seance";
    public static final String SEANCES_ID = "seance_id";
    public static final String SEANCES_DATE = "seance_date";
    public static final String SEANCES_TIME = "seance_time";
    public static final String SEANCES_SPEC_ID = "spec_id";

    public static final String ORDER_TABLE = "order";
    public static final String ORDER_ID = "o_id";
    public static final String ORDER_SERVICE_ID = "service_id";
    public static final String ORDER_SEANCE_ID = "seance_id";
    public static final String ORDER_USER_ID = "user_id";
}
