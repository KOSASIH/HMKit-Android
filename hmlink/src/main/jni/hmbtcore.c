#include <jni.h>
#include <stddef.h>
#include "hm_bt_core.h"
#include "hmbtcore.h"
#include "Crypto.h"
#include "hm_bt_debug_hal.h"

void prepareCallbackFunctions(JNIEnv *env, jobject instance, jobject coreInterface){

    interfaceClassRef = (*env)->GetObjectClass(env, coreInterface);

    interfaceMethodHMBTHalInit = (*env)->GetMethodID(env,interfaceClassRef, "HMBTHalInit","()I");
    interfaceMethodHMBTHalScanStart = (*env)->GetMethodID(env,interfaceClassRef, "HMBTHalScanStart","()I");
    interfaceMethodHMBTHalScanStop = (*env)->GetMethodID(env,interfaceClassRef, "HMBTHalScanStop","()I");
    interfaceMethodHMBTHalAdvertisementStart = (*env)->GetMethodID(env,interfaceClassRef, "HMBTHalAdvertisementStart","([B[B)I");
    interfaceMethodHMBTHalAdvertisementStop = (*env)->GetMethodID(env,interfaceClassRef, "HMBTHalAdvertisementStop","()I");
    interfaceMethodHMBTHalConnect = (*env)->GetMethodID(env,interfaceClassRef, "HMBTHalConnect","([B)I");
    interfaceMethodHMBTHalDisconnect = (*env)->GetMethodID(env,interfaceClassRef, "HMBTHalDisconnect","([B)I");
    interfaceMethodHMBTHalServiceDiscovery = (*env)->GetMethodID(env,interfaceClassRef, "HMBTHalServiceDiscovery","([B)I");
    interfaceMethodHMBTHalWriteData = (*env)->GetMethodID(env,interfaceClassRef, "HMBTHalWriteData","([BI[B)I");
    interfaceMethodHMBTHalReadData = (*env)->GetMethodID(env,interfaceClassRef, "HMBTHalReadData","([BI)I");

    interfaceMethodHMPersistenceHalgetSerial = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHalgetSerial","([B)I");
    interfaceMethodHMPersistenceHalgetLocalPublicKey = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHalgetLocalPublicKey","([B)I");
    interfaceMethodHMPersistenceHalgetLocalPrivateKey = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHalgetLocalPrivateKey","([B)I");
    interfaceMethodHMPersistenceHalgetDeviceCertificate = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHalgetDeviceCertificate","([B)I");
    interfaceMethodHMPersistenceHaladdPublicKey = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHaladdPublicKey","([B[B[B[BI[B)I");
    interfaceMethodHMPersistenceHalgetPublicKey = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHalgetPublicKey","([B[B[B[B[I[B)I");
    interfaceMethodHMPersistenceHalgetPublicKeyByIndex = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHalgetPublicKeyByIndex","(I[B[B[B[B[I[B)I");
    interfaceMethodHMPersistenceHalgetPublicKeyCount = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHalgetPublicKeyCount","([I)I");
    interfaceMethodHMPersistenceHalremovePublicKey = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHalremovePublicKey","([B)I");
    interfaceMethodHMPersistenceHaladdStoredCertificate = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHaladdStoredCertificate","([BI)I");
    interfaceMethodHMPersistenceHalgetStoredCertificate = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHalgetStoredCertificate","([B[I)I");
    interfaceMethodHMPersistenceHaleraseStoredCertificate = (*env)->GetMethodID(env,interfaceClassRef, "HMPersistenceHaleraseStoredCertificate","()I");

    interfaceMethodHMApiCallbackEnteredProximity = (*env)->GetMethodID(env,interfaceClassRef, "HMApiCallbackEnteredProximity","(Lcom/high_mobility/btcore/HMDevice;)V");
    interfaceMethodHMApiCallbackExitedProximity = (*env)->GetMethodID(env,interfaceClassRef, "HMApiCallbackExitedProximity","(Lcom/high_mobility/btcore/HMDevice;)V");
    interfaceMethodHMApiCallbackCustomCommandIncoming = (*env)->GetMethodID(env,interfaceClassRef, "HMApiCallbackCustomCommandIncoming","(Lcom/high_mobility/btcore/HMDevice;[B[I[I)V");
    interfaceMethodHMApiCallbackCustomCommandResponse = (*env)->GetMethodID(env,interfaceClassRef, "HMApiCallbackCustomCommandResponse","(Lcom/high_mobility/btcore/HMDevice;[BI)V");
    interfaceMethodHMApiCallbackGetDeviceCertificateFailed = (*env)->GetMethodID(env,interfaceClassRef, "HMApiCallbackGetDeviceCertificateFailed","(Lcom/high_mobility/btcore/HMDevice;[B)I");
    interfaceMethodHMApiCallbackPairingRequested = (*env)->GetMethodID(env,interfaceClassRef, "HMApiCallbackPairingRequested","(Lcom/high_mobility/btcore/HMDevice;)I");

    envRef = env;
    coreInterfaceRef = coreInterface;
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreInit(JNIEnv *env, jobject instance,
                                                     jobject coreInterface) {

    prepareCallbackFunctions(env,instance,coreInterface);

    hm_bt_core_init();
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreClock(JNIEnv *env, jobject instance,jobject coreInterface) {

    prepareCallbackFunctions(env,instance,coreInterface);
    hm_bt_core_clock();
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreSensingReadNotification(JNIEnv *env,
                                                                        jobject instance,
                                                                        jobject coreInterface,
                                                                        jbyteArray mac_) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);

    hm_bt_core_sensing_read_notification(mac);

    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreSensingReadResponse(JNIEnv *env, jobject instance,jobject coreInterface,
                                                                    jbyteArray data_, jint size,
                                                                    jint offset, jbyteArray mac_) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *data = (*env)->GetByteArrayElements(env, data_, NULL);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);

    hm_bt_core_sensing_read_response(data,size,offset,mac);

    (*env)->ReleaseByteArrayElements(env, data_, data, 0);
    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreSensingWriteResponse(JNIEnv *env, jobject instance,jobject coreInterface,
                                                                     jbyteArray mac_) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);

    hm_bt_core_sensing_write_response(mac);

    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreSensingPingNotification(JNIEnv *env,
                                                                        jobject instance,jobject coreInterface,
                                                                        jbyteArray mac_) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);

    hm_bt_core_sensing_ping_notification(mac);

    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreSensingProcessAdvertisement(JNIEnv *env,
                                                                            jobject instance,jobject coreInterface,
                                                                            jbyteArray mac_,
                                                                            jbyteArray data_,
                                                                            jint size) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);
    jbyte *data = (*env)->GetByteArrayElements(env, data_, NULL);

    hm_bt_core_sensing_process_advertisement(mac,data,size);

    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
    (*env)->ReleaseByteArrayElements(env, data_, data, 0);
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreSensingDiscoveryEvent(JNIEnv *env, jobject instance,jobject coreInterface,
                                                                      jbyteArray mac_) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);

    hm_bt_core_sensing_discovery_event(mac);

    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreSensingScanStart(JNIEnv *env, jobject instance, jobject coreInterface) {
    prepareCallbackFunctions(env,instance,coreInterface);

    hm_bt_core_sensing_scan_start();

}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreSensingConnect(JNIEnv *env, jobject instance,jobject coreInterface,
                                                               jbyteArray mac_) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);

    hm_bt_core_sensing_connect(mac);

    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreSensingDisconnect(JNIEnv *env, jobject instance,jobject coreInterface,
                                                                  jbyteArray mac_) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);

    hm_bt_core_sensing_disconnect(mac);

    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCorelinkConnect(JNIEnv *env, jobject instance,jobject coreInterface,
                                                            jbyteArray mac_) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);

    hm_bt_core_link_connect(mac);

    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCorelinkDisconnect(JNIEnv *env, jobject instance,jobject coreInterface,
                                                               jbyteArray mac_) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);

    hm_bt_core_link_disconnect(mac);

    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCorelinkIncomingData(JNIEnv *env, jobject instance,jobject coreInterface,
                                                                 jbyteArray data_, jint size,
                                                                 jbyteArray mac_) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *data = (*env)->GetByteArrayElements(env, data_, NULL);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);

    hm_bt_core_link_incoming_data(data,size,mac);

    (*env)->ReleaseByteArrayElements(env, data_, data, 0);
    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
}

JNIEXPORT void JNICALL
Java_com_high_1mobility_btcore_HMBTCore_HMBTCoreSendCustomCommand(JNIEnv *env, jobject instance,jobject coreInterface,
                                                                  jbyteArray data_, jint size,
                                                                  jbyteArray mac_) {
    prepareCallbackFunctions(env,instance,coreInterface);
    jbyte *data = (*env)->GetByteArrayElements(env, data_, NULL);
    jbyte *mac = (*env)->GetByteArrayElements(env, mac_, NULL);

    sendSecureContainerUsingMac(mac, data, size);

    (*env)->ReleaseByteArrayElements(env, data_, data, 0);
    (*env)->ReleaseByteArrayElements(env, mac_, mac, 0);
}