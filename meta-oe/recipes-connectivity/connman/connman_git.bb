require recipes-connectivity/connman/connman.inc

SRCREV = "df5772cfb157f76005d7b209654ceddf366537d2"
PV = "1.25+git${SRCPV}"

SRC_URI = "git://git.kernel.org/pub/scm/network/connman/connman.git \
           file://0001-agent-Fix-Peer-authorization-reply-WPS-choice-handli.patch \
           file://0002-wifi-Add-an-extra-check-for-pending-wifi-data.patch \
           file://0003-ipconfig-Do-not-pass-a-NULL-pointer-to-D-Bus.patch \
           file://0004-gdbus-Avoid-reporting-GDBusClient-disconnect-twice.patch \
           file://0005-gdbus-Fix-use-after-free.patch \
           file://0006-Revert-gdbus-Don-t-include-just-added-interfaces-in-.patch \
           file://0007-gdbus-Fix-crash-when-calling-g_dbus_add_service_watc.patch \
           file://0008-gdbus-Fix-crash-when-watch-is-toggled-or-disconnecte.patch \
           file://0009-replace-hardcode-and-add-EnvironmentFile-and-Wants.patch \
           file://connman \
           file://connmand-env \
           file://connman-env.service.in"

S = "${WORKDIR}/git"

do_install_append() {
	rm -rf ${D}${sysconfdir}/init.d

        install -m 755 ${WORKDIR}/connmand-env ${D}${sbindir}/connmand-env
        sed -e 's,@sbindir@,${sbindir},g' < ${WORKDIR}/connman-env.service.in > ${D}${systemd_unitdir}/system/connman-env.service
        chmod 644 ${D}${systemd_unitdir}/system/connman-env.service
}

FILES_${PN} += "${sbindir}/connmand-env ${systemd_unitdir}/system/connman-env.service"

INITSCRIPT_PACKAGES = ""
