package CustomExceptions;

public class StorePolicyViolatedException extends Exception{

    /**
     * @param policyId - policyId violated
     */
    public  StorePolicyViolatedException (String policyId){
        super(policyId);
    }
}
