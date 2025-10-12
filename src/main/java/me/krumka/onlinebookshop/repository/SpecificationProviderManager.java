package me.krumka.onlinebookshop.repository;

public interface SpecificationProviderManager<T> {

    SpecificationProvider<T> getSpecificationProvider(String key);
}
