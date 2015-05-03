DESCRIPTION = "A a package of utilities for doing and managing mounts of the Linux CIFS filesystem."
HOMEPAGE = "http://wiki.samba.org/index.php/LinuxCIFS_utils"

LICENSE = "GPLv3 & LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

PV = "5.9"
PR = "r3"

SRCREV = "353d491dcb5d69d31434abeb962c8e9a49c36867"
SRC_URI = "git://git.samba.org/cifs-utils.git"

S = "${WORKDIR}/git"

PACKAGECONFIG ??= ""
PACKAGECONFIG[cap] = "--with-libcap,--without-libcap,libcap"
# when enabled, it creates ${bindir}/cifscreds and --ignore-fail-on-non-empty in do_install_append is needed
PACKAGECONFIG[cifscreds] = "--enable-cifscreds,--disable-cifscreds,keyutils"
# when enabled, it creates ${sbindir}/cifs.upcall and --ignore-fail-on-non-empty in do_install_append is needed
PACKAGECONFIG[cifsupcall] = "--enable-cifsupcall,--disable-cifsupcall,krb5 talloc keyutils"

FILES_${PN}-doc = "${mandir}/man8/mount.cifs.8"

inherit autotools pkgconfig

do_install_append() {
    # Remove empty /usr/bin and /usr/sbin directories since the mount helper
    # is installed to /sbin
    rmdir --ignore-fail-on-non-empty ${D}${bindir} ${D}${sbindir} || true
    rmdir --ignore-fail-on-non-empty ${D}${mandir}/man1 || true
    rm -f ${D}${sbindir}/mount.smbfs ${D}${base_sbindir}/mount.smbfs || true
}

RRECOMMENDS_${PN} = "kernel-module-cifs"

