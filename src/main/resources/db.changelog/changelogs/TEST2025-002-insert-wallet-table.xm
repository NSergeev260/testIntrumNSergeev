<?xml version="1.0" encoding="UTF-8"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
                   https://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd">

    <changeSet id="TEST2025-002-insert-wallet-table" author="nsergeev">
        <sql>
            INSERT INTO wallet (wallet_id, balance)
            VALUES (gen_random_uuid(), 0.00);
        </sql>
    </changeSet>

</databaseChangeLog>