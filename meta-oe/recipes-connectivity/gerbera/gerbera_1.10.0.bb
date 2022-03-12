Description = "Gerbera - An UPnP media server"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=25cdec9afe3f1f26212ead6bd2f7fac8"

SRC_URI = "git://github.com/gerbera/gerbera.git;protocol=https;nobranch=1;tag=v${PV} \
        file://config.xml \
        file://init \
"

S = "${WORKDIR}/git"

DEPENDS = "expat fmt spdlog pugixml libebml libmatroska zlib curl libupnp e2fsprogs sqlite3 libnsl2"

SYSTEMD_SERVICE:${PN} = "gerbera.service"

inherit cmake pkgconfig systemd update-rc.d

INITSCRIPT_NAME = "gerbera"
INITSCRIPT_PARAMS = "defaults 90"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'systemd', d)}"
PACKAGECONFIG[systemd] = "-DWITH_SYSTEMD=TRUE,-DWITH_SYSTEMD=FALSE,systemd"
PACKAGECONFIG[taglib] = "-DWITH_TAGLIB=TRUE,-DWITH_TAGLIB=FALSE,taglib"
EXTRA_OECMAKE = "-DWITH_JS=FALSE -DWITH_MAGIC=FALSE -DWITH_EXIF=FALSE -DLIBUUID_INCLUDE_DIRS=${STAGING_INCDIR} -DLIBUUID_LIBRARIES=-luuid"

do_install:append() {
    install -d ${D}/root/.config/
    install -d ${D}${sysconfdir}/gerbera
    install -m 0755 ${WORKDIR}/config.xml ${D}${sysconfdir}/gerbera/config.xml
    if ${@bb.utils.contains('DISTRO_FEATURES', 'sysvinit', 'true', 'false', d)}; then
        install -d ${D}${sysconfdir}/init.d
        install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/${INITSCRIPT_NAME}
    fi
}

FILES:${PN} += "/root/.config/ ${sysconfdir}"

CONFFILES:${PN} = "${sysconfdir}/gerbera/config.xml"

SECURITY_CFLAGS:riscv64 = "${SECURITY_NOPIE_CFLAGS}"
