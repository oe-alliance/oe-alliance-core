SUMMARY = "OE-Alliance Network Core - basic networking"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

PACKAGE_ARCH = "${MACHINE_ARCH}"
ALLOW_EMPTY:${PN} = "1"

NETWORK_CORE_BASE = "\
    packagegroup-oea-network-ssh \
    wget \
    avahi-daemon \
    llmnrd \
    "

NETWORK_CORE_EXTENDED = "\
    libcrypto-compat-0.9.7 \
    libcrypto-compat-1.0.0 \
    libxcrypt-compat \
    vsftpd \
    iproute2 \
    ca-certificates \
    "

# SmallBox must remain reachable while its first-run setup is incomplete, but
# SSH/SFTP and discovery daemons are not needed for watching TV or IPTV.  FTP
# and Telnet deliberately remain installed for recovery/support.  Restrict
# this exception to OpenATV; all other distributions keep their existing set.
OPENATV_SMALLBOX_NETWORK_CORE = "\
    wget \
    vsftpd \
    busybox-telnetd \
    iproute2 \
    ca-certificates \
    "

def get_network_core_packages(d):
    smallflash = bb.utils.contains("MACHINE_FEATURES", "smallflash", True, False, d)
    if d.getVar("DISTRO") == "openatv" and smallflash:
        return d.getVar("OPENATV_SMALLBOX_NETWORK_CORE")

    packages = d.getVar("NETWORK_CORE_BASE")
    if not smallflash:
        packages += " " + d.getVar("NETWORK_CORE_EXTENDED")
    return packages

RDEPENDS:${PN} = "${@get_network_core_packages(d)}"
