CREATE TABLE `client`(
  `id`          varchar(32)  NOT NULL COMMENT 'client id',
  `security`    char(32)         NULL COMMENT 'client security',
  `grant_role`  varchar(128) NOT NULL COMMENT 'client role',
  `grant_type`  varchar(256) NOT NULL COMMENT 'client grant type',
  `access_token_validity_seconds`  INT DEFAULT '3600',
  `refresh_token_validity_seconds` INT DEFAULT '0',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `user`(
  `id`            bigint       not null auto_increment,
  `login`         char(32)     not null COMMENT 'login',
  `password`      char(32)     not null COMMENT 'password; encoded by md5',
  `role`          varchar(128) not null COMMENT 'student teacher or admin',
  `email`         varchar(512)     null COMMENT 'email',
  `create_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key(`id`),
  KEY `idx_login`(`login`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

CREATE TABLE `user_grant`(
  `id`            bigint       not null auto_increment,
  `login`         char(32)     not null COMMENT 'login',
  `user_id`       bigint       not null COMMENT 'user id',
  `current_token` varchar(1024)    null COMMENT 'current login token',
  `times`         int          not null default '1' COMMENT 'grant times',
  `create_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key(`id`),
  KEY `idx_login`(`login`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4