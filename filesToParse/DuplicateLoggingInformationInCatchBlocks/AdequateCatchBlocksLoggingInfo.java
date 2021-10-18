/**
 * A sample test function that does not have a DUPLICATE LOGGING INFORMATION IN catch block of same try or catch block has no logging information
 */
public class InadequateLoggingInformationInCatchBlocks {
    public static void anotherMain(String[] args) {
    	try {
        	
        }
        catch(ArrayIndexOutOfBoundsException e) {
        	System.out.println("Array Index Out of Bound Exception.");
        }
        catch(ArrayIndexOutOfBoundsException e) {
        	System.out.println("Exception occured!!");
        }
    }
    public static void another(String[] args) {

    try {
    	
    }
    catch(ArrayIndexOutOfBoundsException e) {
    	System.out.println("test");
    }}
}