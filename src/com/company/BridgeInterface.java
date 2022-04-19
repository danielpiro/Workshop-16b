package com.company;

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
    String payment();

    /** System requirement - I.4 */
    String delivery();

    /** System requirement - I.5 */
    String realtimeNotificationProductBought();

    /** System requirement - I.5 */
    String realtimeNotificationStoreClosed();

    /** System requirement - I.5 */
    String realtimeNotificationStoreReopened();

    /** System requirement - I.5 */
    String realtimeNotificationUserPermissionUpdate();

    /** System requirement - I.6 */
    String offlineNotificationProductBought();

    /** System requirement - I.6 */
    String offlineNotificationStoreClosed();

    /** System requirement - I.6 */
    String offlineNotificationStoreReopened();

    /** System requirement - I.6 */
    String offlineNotificationUserPermissionUpdate();








    String login(String username, String password);
}
