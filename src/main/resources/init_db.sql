CREATE TABLE `client`(
  `id`  varchar(32)  NOT NULL COMMENT 'client id',
  `security` char(32) NOT NULL COMMENT 'client security',
  `grant_roles`  varchar(256) NOT NULL COMMENT 'client role',
  `grant_types` varchar(256) NOT NULL COMMENT 'client grant type',
  `access_token_validity_seconds` INT DEFAULT '3600',
  `refresh_token_validity_seconds` INT DEFAULT '0',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  primary key(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4