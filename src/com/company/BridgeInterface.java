package com.company;

import org.junit.jupiter.api.Test;

public interface BridgeInterface {
    /** requirement 1.b in V1 */
    String parallelUse(); //Need to implement thread-base system

    /** requirement 1.c in V1 */
    String systemLogging(); //Need to create log file (containing error logs)

    /** System requirement - I.1 */
    String openingMarket();

    /** System requirement - I.2 */
    String changeExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName);
    /** System requirement - I.2 */
    String switchExternalService(int currServiceCode, String currServiceName,
                                 int newServiceCode, String newServiceName);
    /** System requirement - I.2 */
    String addExternalService(int serviceCode, String serviceName);

    /** System requirement - I.3 */





    
    String login(String username, String password);
}
