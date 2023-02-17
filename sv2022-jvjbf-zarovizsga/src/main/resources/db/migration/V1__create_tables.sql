CREATE TABLE schools (
   id BIGINT AUTO_INCREMENT NOT NULL,
   school_name VARCHAR(255) NOT NULL,
   postal_code VARCHAR(255) NOT NULL,
   city VARCHAR(255) NOT NULL,
   street VARCHAR(255) NOT NULL,
   house_number INT(20) NOT NULL,
   CONSTRAINT pk_schools PRIMARY KEY (id)
);

CREATE TABLE students (
   id BIGINT AUTO_INCREMENT NOT NULL,
   name VARCHAR(255) NOT NULL,
   date_of_birth DATE NOT NULL,
   school_age_status VARCHAR(255) NOT NULL,
   school_id BIGINT,
   CONSTRAINT pk_schools PRIMARY KEY (id)
);

ALTER TABLE students ADD CONSTRAINT fk_students_on_schools FOREIGN KEY (school_id) REFERENCES schools (id);