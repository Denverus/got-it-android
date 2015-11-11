package org.coursera.capstone.gotit.client.provider;

public interface ServiceCall<T> {

    T call(ServiceApi srv) throws Exception;
}
