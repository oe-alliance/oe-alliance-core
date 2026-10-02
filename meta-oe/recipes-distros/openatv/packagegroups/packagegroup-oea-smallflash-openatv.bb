SUMMARY = "OpenATV SmallBox TV and IPTV base system"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

PV = "${IMAGE_VERSION}"

# Backward compatibility: StartWizard installs by old name
RPROVIDES:${PN} = "packagegroup-openatv-small"

# Install a responsive TV/IPTV base after the native wizard has moved /usr to
# USB. Optional servers, utilities, languages and plugins stay on the feed.
# The same package group is used by the complete Chkroot rootfs image.
RDEPENDS:${PN} = " \
    packagegroup-oea-python-core \
    packagegroup-oea-multimedia \
    packagegroup-oea-gui \
    packagegroup-oea-enigma2-core \
    packagegroup-oea-enigma2-plugins \
    packagegroup-oea-distro-openatv \
    packagegroup-openatv-smallbox-iptv \
    busybox-telnetd \
    vsftpd \
    tar \
    ofgwrite \
    enigma2-plugin-systemplugins-hotplug \
    enigma2-plugin-extensions-mediascanner \
    ${@bb.utils.contains('MACHINE_FEATURES', 'dreamboxv1', 'mtd-utils-jffs2', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'dreamboxv2', 'e2fsprogs-badblocks', '', d)} \
"

RRECOMMENDS:${PN} = "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'dvbc-only', '', 'enigma2-plugin-settings-defaultsat', d)} \
    "
