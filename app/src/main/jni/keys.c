#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_example_moviesapp_viewmodel_MovieListViewModel_getAPIToken(JNIEnv *env, jobject thiz) {
    return (*env)-> NewStringUTF(env, "909594533c98883408adef5d56143539");
}

JNIEXPORT jstring JNICALL
Java_com_example_moviesapp_viewmodel_MovieDetailViewModel_getAPIToken(JNIEnv *env, jobject thiz) {
    return (*env)-> NewStringUTF(env, "909594533c98883408adef5d56143539");
}