//package com.allane.leasing.repository;
//
//import java.util.Optional;
//
//import com.allane.leasing.DatabaseContainer;
//import com.allane.leasing.model.LeasingContract;
//import com.allane.leasing.stub.leasingcontract.LeasingContractModelDtoStub;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.ContextConfiguration;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//
//@DataJpaTest
//@ContextConfiguration(initializers = {DatabaseContainer.class})
//class LeasingContractRepositoryTest {
//
//    @Autowired
//    private LeasingContractRepository repository;
//
//    @Test
//    void shouldGetLeasingContractByIdAndVehicleId() {
//        LeasingContract save = repository.save(LeasingContractModelDtoStub.getDto());
//
//        Optional<LeasingContract> contract = repository
//                .findLeasingContractByIdAndVehicleId(save.getId(),save.getVehicle().getId());
//
//        assertThat(contract, is(Optional.empty()));
//
//    }
//
//    @Test
//    void shouldGetLeasingContractByIdAndCustomerId() {
////        repository.findLeasingContractByIdAndCustomerId();
//    }
//}
