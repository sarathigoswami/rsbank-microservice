package com.rsbank.loan.constants;

public final class Literals {

    private Literals() {
        throw new RuntimeException(PRIVATE_CLASSS);
    }

    public static final String PRIVATE_CLASSS = "Creating object of this class is not allowed";

    
	
	public static final String DD_MM_YYYY = "dd-MM-yyyy";
	public static final String DD_MM_YYYY_HH_MM_SS_Z = "dd-MM-yyyy HH:mm:ss z";
	public static final String TZ_KOLKATA = "Asia/Kolkata";
    
    public static final String HOME_LOAN = "Home Loan";
    public static final String VEHICLE_LOAN = "Vehicle Loan";
    public static final int NEW_LOAN_LIMIT = 1_00_000;

    public static final String ADDRESS = "23/1A, APC Roy Lane, Kolkata - 700005";

    public static final String NAL_BLANK = "Blank not allowed";
    public static final String NAL_LENGTH = "Invalid parameter length";
    public static final String INV_FORMAT = "Invalid input format";

}
