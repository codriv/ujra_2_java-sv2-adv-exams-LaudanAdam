package org.training360.sv2022jvjbfzarovizsgapotvizsga.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreateHospitalCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.CreatePatientCommand;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.HospitalDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.dto.PatientDto;
import org.training360.sv2022jvjbfzarovizsgapotvizsga.model.HospitalWard;
import org.zalando.problem.Problem;
import org.zalando.problem.violations.ConstraintViolationProblem;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from patients", "delete from hospitals"})
class HospitalControllerTest {

    @Autowired
    WebTestClient webTestClient;

    HospitalDto hospitalDto;
    PatientDto patientDto;

    @BeforeEach
    void init() {
        hospitalDto = webTestClient.post().uri("api/hospitals")
                .bodyValue(new CreateHospitalCommand("Szent Benedek Kórház"))
                .exchange().expectStatus().isCreated()
                .expectBody(HospitalDto.class).returnResult().getResponseBody();

        patientDto = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients").build(hospitalDto.getId()))
                .bodyValue(new CreatePatientCommand("John Doe", LocalDate.parse("2022-05-11"), HospitalWard.TRAUMATOLOGY))
                .exchange().expectStatus().isCreated()
                .expectBody(PatientDto.class).returnResult().getResponseBody();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients").build(hospitalDto.getId()))
                .bodyValue(new CreatePatientCommand("Jane Doe", LocalDate.parse("2022-05-12"), HospitalWard.TRAUMATOLOGY))
                .exchange();

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients").build(hospitalDto.getId()))
                .bodyValue(new CreatePatientCommand("Jack Doe", LocalDate.parse("2022-06-09"), HospitalWard.RHEUMATOLOGY))
                .exchange();
    }


    @Test
    void testCreateHospital() {
        assertEquals("Szent Benedek Kórház", hospitalDto.getHospitalName());
    }

    @Test
    void testCreateHospitalWithWrongName() {
        String message = webTestClient.post().uri("api/hospitals")
                .bodyValue(new CreateHospitalCommand("  "))
                .exchange().expectStatus().isBadRequest()
                .expectBody(ConstraintViolationProblem.class).returnResult().getResponseBody().getViolations().get(0).getMessage();

        assertEquals("Hospital name cannot be blank!", message);
    }

    @Test
    void testCreatePatient() {
        assertEquals("John Doe", patientDto.getName());
        assertEquals(HospitalWard.TRAUMATOLOGY, patientDto.getHospitalWard());
    }

    @Test
    void testCreatePatientWithWrongName() {
        String message = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients").build(hospitalDto.getId()))
                .bodyValue(new CreatePatientCommand("   ", LocalDate.parse("2022-05-11"), HospitalWard.TRAUMATOLOGY))
                .exchange().expectStatus().isBadRequest()
                .expectBody(ConstraintViolationProblem.class).returnResult().getResponseBody().getViolations().get(0).getMessage();

        assertEquals("Patient name cannot be blank!", message);
    }

    @Test
    void testCreatePatientWithWrongDate() {
        String message = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients").build(hospitalDto.getId()))
                .bodyValue(new CreatePatientCommand("John Doe", LocalDate.now().plusDays(2), HospitalWard.TRAUMATOLOGY))
                .exchange().expectStatus().isBadRequest()
                .expectBody(ConstraintViolationProblem.class).returnResult().getResponseBody().getViolations().get(0).getMessage();

        assertEquals("Registration must be in past or present!", message);
    }

    @Test
    void testCreatePatientWithWrongHospital(){
        long wrongId = hospitalDto.getId()+1000;

        String message = webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients").build(wrongId))
                .bodyValue(new CreatePatientCommand("John Doe",  LocalDate.parse("2022-05-11"), HospitalWard.TRAUMATOLOGY))
                .exchange().expectStatus().isNotFound()
                .expectBody(Problem.class).returnResult().getResponseBody().getDetail();

        assertEquals("Hospital not found with id: "+wrongId,message);
    }

    @Test
    void testFindPatients(){
        List<PatientDto> result = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients").build(hospitalDto.getId()))
                .exchange()
                .expectBodyList(PatientDto.class).returnResult().getResponseBody();

        assertThat(result).hasSize(3).extracting(PatientDto::getName)
        .containsExactly("Jack Doe","Jane Doe","John Doe");

    }

    @Test
    void testFindPatientsInHospitalWithNoPatients(){
        HospitalDto emptyHospital = webTestClient.post().uri("api/hospitals")
                .bodyValue(new CreateHospitalCommand("Szent János Kórház"))
                .exchange()
                .expectBody(HospitalDto.class).returnResult().getResponseBody();

        List<PatientDto> result = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients").build(emptyHospital.getId()))
                .exchange()
                .expectBodyList(PatientDto.class).returnResult().getResponseBody();

        assertThat(result).isEmpty();
    }


    @Test
    void testFindPatientsByHospitalWard(){
        List<PatientDto> result = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients").queryParam("hospitalWard","TRAUMATOLOGY").build(hospitalDto.getId()))
                .exchange()
                .expectBodyList(PatientDto.class).returnResult().getResponseBody();

        assertThat(result).hasSize(2).extracting(PatientDto::getName)
                .containsExactly("Jane Doe","John Doe");
    }

    @Test
    void testFindPatientsWrongHospital(){
        long wrongId = hospitalDto.getId()+1000;

        String message = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients").build(wrongId))
                .exchange().expectStatus().isNotFound()
                .expectBody(Problem.class).returnResult().getResponseBody().getDetail();

        assertEquals("Hospital not found with id: "+wrongId,message);
    }

    @Test
    void testDeletePatientById(){
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients/{ptId}").build(hospitalDto.getId(),patientDto.getId()))
                .exchange().expectStatus().isNoContent();

        List<PatientDto> result = webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients").build(hospitalDto.getId()))
                .exchange()
                .expectBodyList(PatientDto.class).returnResult().getResponseBody();

        assertThat(result).hasSize(2).extracting(PatientDto::getName)
                .containsExactly("Jack Doe","Jane Doe");
    }

    @Test
    void testDeletePatientWithWrongPatientId(){

        long wrongPatientId = patientDto.getId()+1000;
        String message = webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients/{ptId}").build(hospitalDto.getId(),wrongPatientId))
                .exchange().expectStatus().isNotFound()
                .expectBody(Problem.class).returnResult().getResponseBody().getDetail();

        assertEquals("Patient not found with id: "+wrongPatientId,message);

    }

    @Test
    void testDeletePatientWithWrongHospitalId(){
        long wrongId = hospitalDto.getId()+1000;

        String message = webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients/{ptId}").build(wrongId,patientDto.getId()))
                .exchange().expectStatus().isNotFound()
                .expectBody(Problem.class).returnResult().getResponseBody().getDetail();

        assertEquals("Hospital not found with id: "+wrongId,message);
    }

    @Test
    void testDeletePatientNotInHospital(){
        HospitalDto emptyHospital = webTestClient.post().uri("api/hospitals")
                .bodyValue(new CreateHospitalCommand("Szent János Kórház"))
                .exchange()
                .expectBody(HospitalDto.class).returnResult().getResponseBody();

        String message = webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("api/hospitals/{id}/patients/{ptId}").build(emptyHospital.getId(),patientDto.getId()))
                .exchange().expectStatus().isNotFound()
                .expectBody(Problem.class).returnResult().getResponseBody().getDetail();

        assertEquals("Patient not found with id: "+patientDto.getId(),message);
    }

}