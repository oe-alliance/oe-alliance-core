FILESEXTRAPATHS_prepend := "${THISDIR}/${P}:"

PR .= ".8"

RDEPENDS_${PN}_append = " sdparm"

SRC_URI += "file://hotplug.sh \
            file://fastrestore_openatv.sh \
"

do_install_append() {
    # umountnfs should run before network stops (which is at K40)
    ln -sf        ../init.d/umountnfs.sh    ${D}${sysconfdir}/rc6.d/K31umountnfs.sh
    ln -sf        ../init.d/umountnfs.sh    ${D}${sysconfdir}/rc0.d/K31umountnfs.sh

    install -m 0755    ${WORKDIR}/hotplug.sh	${D}${sysconfdir}/init.d
    ln -sf        ../init.d/hotplug.sh      ${D}${sysconfdir}/rcS.d/S06hotplug.sh

    if [ "x${DISTRO}" = "xopenatv" ]; then
        install -m 0755    ${WORKDIR}/fastrestore_openatv.sh	${D}${sysconfdir}/init.d/fastrestore
        ln -sf        ../init.d/fastrestore      ${D}${sysconfdir}/rcS.d/S41fastrestore
    fi
}
