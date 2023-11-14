use ehealth_logs;

DROP TABLE IF EXISTS openncp_logs;
CREATE TABLE IF NOT EXISTS openncp_logs (
                                            id BIGINT(20) NOT NULL AUTO_INCREMENT,
                                            component varchar(255) COLLATE utf8_unicode_ci NULL,
                                            logger varchar(255) COLLATE utf8_unicode_ci NOT NULL,
                                            priority varchar(255) COLLATE utf8_unicode_ci NOT NULL,
                                            message longtext COLLATE utf8_unicode_ci NOT NULL,
                                            stacktrace text COLLATE utf8_unicode_ci,
                                            creationTime datetime NOT NULL,
                                            ip varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                                            PRIMARY KEY (id),
                                            KEY priority (priority),
                                            KEY creationTime (creationTime)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
