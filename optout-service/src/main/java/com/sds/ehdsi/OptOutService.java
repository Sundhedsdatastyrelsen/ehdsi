package com.sds.ehdsi;

public class OptOutService {
    private final OptOutRepository repository;

    public OptOutService() {
        this.repository = new OptOutRepository();
    }

    public void optOut(String id) {
        repository.saveOptOut(id);
    }

    public boolean isEligible(String id) {
        return repository.isEligible(id);
    }
}