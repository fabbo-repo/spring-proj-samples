CREATE TABLE IF NOT EXISTS app_user (
  id UUID NOT NULL,
   username VARCHAR(30),
   email VARCHAR(255) NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(50) NOT NULL,
   age INTEGER,
   created_at TIMESTAMP WITHOUT TIME ZONE,
   created_by VARCHAR(255),
   updated_at TIMESTAMP WITHOUT TIME ZONE,
   updated_by VARCHAR(255),
   CONSTRAINT pk_app_user PRIMARY KEY (id)
);
