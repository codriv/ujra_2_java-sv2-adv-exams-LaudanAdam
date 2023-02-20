CREATE TABLE hospitals (
  id BIGINT AUTO_INCREMENT NOT NULL,
   hospital_name VARCHAR(255) NOT NULL,
   CONSTRAINT pk_hospitals PRIMARY KEY (id)
);

CREATE TABLE patients (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   registration_date DATE NOT NULL,
   hospital_ward VARCHAR(255) NOT NULL,
   hospital_id BIGINT,
   CONSTRAINT pk_patients PRIMARY KEY (id)
);

ALTER TABLE patients ADD CONSTRAINT fk_patients_on_hospitals FOREIGN KEY (hospital_id) REFERENCES hospitals (id);