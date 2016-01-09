package tr.edu.metu.ii.sm.xacml.pdp;

import tr.edu.metu.ii.sm.xacml.pdp.ClientConstants;

import java.io.*;
import java.util.Properties;

/**
 * Created by omer.dogan on 17/05/2015.
 */
public class ClientUtils {

    private static Properties configProperties;


    public static String getPolicyDirectoryPath(String samplePolicyName) {
        String path = null;
        if(configProperties != null){
            String policyPath =  configProperties.getProperty(ClientConstants.POLICY_PATH);
            if(policyPath != null && policyPath.trim().length() > 0){
                path = configProperties.getProperty(ClientConstants.POLICY_PATH) + File.separator + samplePolicyName + ".xml";
            }
        }

        if(path == null){
            try{
                File file = new File((new File(".")).getCanonicalPath() + File.separator +"resources" +
                        File.separator +"policy" + File.separator + samplePolicyName + ".xml");
                if(file.exists()){
                    path = file.getCanonicalPath();
                }
            } catch (IOException e) {
                // ignore
            }
        }
        return path;
    }

    public static String getTrustStore() throws Exception {
        if(configProperties != null  && configProperties.getProperty(ClientConstants.TRUST_STORE_PATH) != null){
            return  configProperties.getProperty(ClientConstants.TRUST_STORE_PATH);
        } else {
            try{
                File file = new File((new File(".")).getCanonicalPath() + File.separator +"resources" +
                        File.separator +"wso2carbon.jks");
                if(file.exists()){
                    return file.getCanonicalPath();
                } else {
                    return null;
                }
            } catch (IOException e) {
                throw new Exception("Error while calculating trust store path", e);
            }
        }
    }

    public static String getTrustStorePassword(){
        if(configProperties != null && configProperties.getProperty(ClientConstants.TRUST_STORE_PASSWORD) != null){
            return configProperties.getProperty(ClientConstants.TRUST_STORE_PASSWORD);
        } else {
            return "wso2carbon";
        }
    }

    public static String getServerUrl(){
        if(configProperties != null  && configProperties.getProperty(ClientConstants.SERVER_URL) != null){
            return configProperties.getProperty(ClientConstants.SERVER_URL);
        } else {
            return "https://localhost:9443/services/";
        }
    }

    public static String getServerUsername(){
        if(configProperties != null  && configProperties.getProperty(ClientConstants.SERVER_USER_NAME) != null){
            return configProperties.getProperty(ClientConstants.SERVER_USER_NAME);
        } else {
            return "admin";
        }
    }

    public static String getServerPassword(){
        if(configProperties != null  && configProperties.getProperty(ClientConstants.SERVER_PASSWORD) != null){
            return configProperties.getProperty(ClientConstants.SERVER_PASSWORD);
        } else {
            return "admin";
        }
    }


    /**
     * reads values from config property file
     * @throws Exception throws, if fails
     */
    public static void loadConfigProperties() throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            File file = new File((new File(".")).getCanonicalPath() + File.separator +"resources" +
                    File.separator +"config.properties");
            if(file.exists()){
                inputStream = new FileInputStream(file);
            } else {
                String msg = "File does not exist : " + "config.properties";
                System.out.println(msg);
            }
        } catch (FileNotFoundException e) {
            String msg = "File can not be found : " + "config.properties";
            System.out.println(msg);
            throw new Exception(msg, e);
        } catch (IOException e) {
            String msg = "Can not create the canonical file path for given file : " + "config.properties";
            System.out.println(msg);
            throw new Exception(msg, e);
        }

        try {
            if(inputStream != null){
                properties.load(inputStream);
            }
        } catch (IOException e) {
            String msg = "Error loading properties from config.properties file";
            System.out.println(msg);
            throw new Exception(msg, e);
        } finally {
            try {
                if(inputStream!= null){
                    inputStream.close();
                }
            } catch (IOException ignored) {
                System.out.println("Error while closing input stream");
            }
        }
        configProperties = properties;
    }


}
