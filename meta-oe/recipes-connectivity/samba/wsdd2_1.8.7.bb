SUMMARY = "WSD/LLMNR Discovery/Name Service Daemon"
DESCRIPTION = "Enables WSD (Web Services for Devices) and LLMNR on Samba \
servers so that network shares hosted on a Unix box appear in Windows File \
Explorer / Network. Also acts as an LLMNR name responder."
HOMEPAGE = "https://salsa.debian.org/debian/wsdd2"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=97ff9683aa36f333c7d2295d6520090f"

SRC_URI = "http://deb.debian.org/debian/pool/main/w/${BPN}/${BPN}_${PV}+dfsg.orig.tar.xz \
           file://init.wsdd2 \
           "
SRC_URI[sha256sum] = "2b1e7720435a1e067388660ec3edb321a4c91b4f9d0928ba27d0a8d89b7ef3b9"

S = "${UNPACKDIR}/${BPN}-${PV}"

inherit update-rc.d

EXTRA_OEMAKE = "CC='${CC}' CFLAGS='${CFLAGS}' LDFLAGS='${LDFLAGS}'"

do_install() {
    oe_runmake install DESTDIR=${D} PREFIX=${prefix} SBINDIR=${sbindir} MANDIR=${mandir}
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${UNPACKDIR}/init.wsdd2 ${D}${sysconfdir}/init.d/wsdd2
    rm -rf ${D}${nonarch_libdir}/systemd ${D}${nonarch_base_libdir}/systemd
    rmdir --ignore-fail-on-non-empty ${D}${nonarch_libdir} ${D}${nonarch_base_libdir} 2>/dev/null || true
}

INITSCRIPT_NAME = "wsdd2"
INITSCRIPT_PARAMS = "start 99 2 3 4 5 . stop 20 0 1 6 ."

RPROVIDES:${PN}  = "wsdd"
RREPLACES:${PN}  = "wsdd"
RCONFLICTS:${PN} = "wsdd"
