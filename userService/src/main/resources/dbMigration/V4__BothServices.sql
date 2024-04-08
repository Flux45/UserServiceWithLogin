CREATE TABLE flyway_schema_history
(
    installed_rank INT                                   NOT NULL,
    version        VARCHAR(50)                           NULL,
    `description`  VARCHAR(200)                          NOT NULL,
    type           VARCHAR(20)                           NOT NULL,
    script         VARCHAR(1000)                         NOT NULL,
    checksum       INT                                   NULL,
    installed_by   VARCHAR(100)                          NOT NULL,
    installed_on   timestamp DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
    execution_time INT                                   NOT NULL,
    success        TINYINT(1)                            NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (installed_rank)
);

CREATE TABLE `role`
(
    id      BIGINT       NOT NULL,
    deleted BIT(1)       NOT NULL,
    name    VARCHAR(255) NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE role_seq
(
    next_val BIGINT NULL
);

CREATE TABLE token
(
    id        BIGINT       NOT NULL,
    deleted   BIT(1)       NOT NULL,
    value     VARCHAR(255) NULL,
    user_id   BIGINT       NULL,
    expiry_at datetime     NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE token_seq
(
    next_val BIGINT NULL
);

CREATE TABLE user
(
    id                BIGINT       NOT NULL,
    deleted           BIT(1)       NOT NULL,
    name              VARCHAR(255) NULL,
    email             VARCHAR(255) NULL,
    hashed_password   VARCHAR(255) NULL,
    is_email_verified BIT(1)       NOT NULL,
    CONSTRAINT `PRIMARY` PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id  BIGINT NOT NULL,
    roles_id BIGINT NOT NULL
);

CREATE TABLE user_seq
(
    next_val BIGINT NULL
);

CREATE INDEX flyway_schema_history_s_idx ON flyway_schema_history (success);

ALTER TABLE token
    ADD CONSTRAINT FK_TOKEN_ON_USER FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE NO ACTION;

CREATE INDEX FK_TOKEN_ON_USER ON token (user_id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (roles_id) REFERENCES `role` (id) ON DELETE NO ACTION;

CREATE INDEX fk_userol_on_role ON user_roles (roles_id);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE NO ACTION;

CREATE INDEX fk_userol_on_user ON user_roles (user_id);