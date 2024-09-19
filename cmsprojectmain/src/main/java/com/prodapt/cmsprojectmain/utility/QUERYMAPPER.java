package com.prodapt.cmsprojectmain.utility;

public interface QUERYMAPPER {
	
	public static final String RECORD_DELETED_SUCCESSFULLY = "Record deleted successfully";
	public static final String RECORD_EXITS = "Record exists";
	public static final String RECORD_DOES_NOT_EXITS = "Record does not exists";
	public static final String ADD_PRODUCT="INSERT INTO PRODUCT VALUES(?,?,)";
	public static final String ADD_FEATURE="INSERT INTO PRODUCT VALUES(?,?,)";
	public static final String ADD_QUATATION="INSERT INTO PRODUCT VALUES(?,?,)";
	public static final String GET_PRODUCT_BY_ID="SELECT*FROM PRODUCT WHERE productId=?";
	public static final String GET_QUATATION_BY_ID="SELECT*FROM PRODUCT WHERE productId=?";
	public static final String GET_PRODUCT_BY_NAME="SELECT*FROM PRODUCT WHERE name=?";
	public static final String GET_ALL_PRODUCTS="SELECT*FROM PRODUCT ";
	public static final String DELETE_FEATURE_BY_ID="SELECT*FROM PRODUCT WHERE featureId=?";
	public static final String DELETE_PARAMETER_BY_ID="SELECT*FROM PRODUCT WHERE parameterId=?";
	public static final String UPDATE_PRODUCT="SELECT*FROM PRODUCT WHERE product";
	public static final String UPDATE_ROLE="SELECT*FROM PRODUCT WHERE product";
	
	
	
	

}
