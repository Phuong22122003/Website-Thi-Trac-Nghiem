package com.laptrinhweb.thitracnghiem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;

@Service
public class DangKyService {
    @Autowired
    private EntityManager entityManager;
    public boolean checkExistUserName(String username){
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("checkUserName");
        storedProcedure.registerStoredProcedureParameter("username",String.class, ParameterMode.IN);
        storedProcedure.registerStoredProcedureParameter("result", Boolean.class, ParameterMode.OUT);

        storedProcedure.setParameter("username", username);
        storedProcedure.execute();
        Boolean result = (Boolean) storedProcedure.getOutputParameterValue("result");
        return result;
    }
}
