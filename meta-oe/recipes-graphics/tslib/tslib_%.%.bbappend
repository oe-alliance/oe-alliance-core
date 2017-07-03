BBCLASSEXTEND = "native"

PACKAGES_remove = "tslib-conf"

RDEPENDS_${PN}_remove = "tslib-conf"

FILES_${PN} =+ "${sysconfdir}/ts.conf ${sysconfdir}/profile.d/tslib.sh ${datadir}/tslib"
