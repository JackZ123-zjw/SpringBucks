
START TRANSACTION;

CREATE TABLE `t_coffee` (
    `id` BIGINT AUTO_INCREMENT,
    `create_time` TIMESTAMP,
    `update_time` TIMESTAMP,
    `name` VARCHAR(255) UNIQUE,
    `price` BIGINT,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_order` (
    `id` BIGINT AUTO_INCREMENT,
    `create_time` TIMESTAMP,
    `update_time` TIMESTAMP,
    `customer` VARCHAR(255),
    `state` INTEGER NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `t_coffee_order` (
    `coffee_order_id` BIGINT NOT NULL,
    `items_id` BIGINT NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `t_coffee` (`create_time`, `update_time`, `name`, `price`)
                VALUES (now(), now(), 'espresso', 2000);

INSERT INTO `t_coffee` (`create_time`, `update_time`, `name`, `price`)
                VALUES (now(), now(), 'lattee', 2500);

INSERT INTO `t_coffee` (`create_time`, `update_time`, `name`, `price`)
                VALUES (now(), now(), 'Americano', 1500);

COMMIT;

