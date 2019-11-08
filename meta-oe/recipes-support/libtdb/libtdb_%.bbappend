inherit update-alternatives

ALTERNATIVE_PRIORITY = "50"
ALTERNATIVE_tdb-tools = "dbwrap_tool tdbbackup tdbdump tdbrestore tdbtool"

ALTERNATIVE_LINK_NAME[dbwrap_tool] = "${bindir}/dbwrap_tool"
ALTERNATIVE_LINK_NAME[tdbbackup]   = "${bindir}/tdbbackup"
ALTERNATIVE_LINK_NAME[tdbdump]     = "${bindir}/tdbdump"
ALTERNATIVE_LINK_NAME[tdbrestore]  = "${bindir}/tdbrestore"
ALTERNATIVE_LINK_NAME[tdbtool]     = "${bindir}/tdbtool"

