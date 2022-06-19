CREATE TABLE TASKS (
    TASK_ID SERIAL
    , USER_ID INTEGER NOT NULL
    , CONTENT VARCHAR(100) NOT NULL
    , CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    , UPDATED TIMESTAMP
    , DEAD_LINE TIMESTAMP NOT NULL
    , STATUS VARCHAR(1) NOT NULL DEFAULT '0'
    , PRIMARY KEY(TASK_ID)
)
;