package enums;

public enum BootState {
    START,
    SHOW_MENU,
    SHOW_PRODUCTS,
    SHOW_CATEGORIES,
    SHOW_SUBCATEGORIES,
    SELECT_PRODUCT,
    SELECT_PRODUCT_COUNT,
    SHOW_CART, SEND_ORDER, GET_CONTACT, GET_LOCATION, CANCEL;

    public static BootState fromString(String name){
        for (BootState value : BootState.values() ){
            if(value.name().equals(name))
                return value;
        }
        return null;
    }
}
