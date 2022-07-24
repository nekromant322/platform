package com.override;

import com.opentable.db.postgres.embedded.LiquibasePreparer;
import com.opentable.db.postgres.junit5.EmbeddedPostgresExtension;
import com.opentable.db.postgres.junit5.PreparedDbExtension;
import org.junit.jupiter.api.extension.RegisterExtension;

public abstract class DatabaseAwareTestBase {

    @RegisterExtension
    protected static final PreparedDbExtension EMBEDDED_POSTGRES =
            EmbeddedPostgresExtension.preparedDatabase(
                    LiquibasePreparer.forClasspathLocation("db/changelog/db.changelog-master.xml"));

}