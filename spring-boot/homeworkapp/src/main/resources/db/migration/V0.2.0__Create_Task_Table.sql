CREATE TABLE IF NOT EXISTS task (
  id UUID NOT NULL,
   title VARCHAR(30),
   description VARCHAR(500) NOT NULL,
   estimated_done_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
   finished_date TIMESTAMP WITHOUT TIME ZONE,
   is_finished BOOLEAN,
   task_status VARCHAR(255) NOT NULL,
   created_at TIMESTAMP WITHOUT TIME ZONE,
   created_by VARCHAR(255),
   updated_at TIMESTAMP WITHOUT TIME ZONE,
   updated_by VARCHAR(255),
   CONSTRAINT pk_task PRIMARY KEY (id)
);
