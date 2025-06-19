package com.rsbank.account.constants;

public final class Literals {

    private Literals() {
        throw new RuntimeException(PRIVATE_CLASSS);
    }

    public static final String PRIVATE_CLASSS = "Creating object of this class is not allowed";

    
	
	public static final String DD_MM_YYYY = "dd-MM-yyyy";
	public static final String DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy HH:mm:ss";
	public static final String TZ_KOLKATA = "Asia/Kolkata";
    
    public static final String CA = "CA";
    public static final String CA_NAME = "Current A/C";
    public static final String SA = "SA";
    public static final String SA_NAME = "Savings A/C";
    public static final String ADDRESS = "23/1A, APC Roy Lane, Kolkata - 700005";

    public static final String NAL_BLANK = "Blank not allowed";
    public static final String NAL_LENGTH = "Invalid parameter length";
    public static final String INV_FORMAT = "Invalid input format";

}
