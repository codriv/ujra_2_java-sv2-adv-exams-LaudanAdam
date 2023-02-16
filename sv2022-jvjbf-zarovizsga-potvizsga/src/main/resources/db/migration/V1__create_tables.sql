CREATE TABLE hospitals (
  id BIGINT AUTO_INCREMENT NOT NULL,
   hospital_name VARCHAR(255) NULL,
   CONSTRAINT pk_hospitals PRIMARY KEY (id)
);

CREATE TABLE patients (
  id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NULL,
   registration_date date NULL,
   hospital_ward VARCHAR(255) NULL,
   hospital_id BIGINT NULL,
   CONSTRAINT pk_patients PRIMARY KEY (id)
);

ALTER TABLE patients ADD CONSTRAINT FK_PATIENTS_ON_HOSPITAL FOREIGN KEY (hospital_id) REFERENCES hospitals (id);