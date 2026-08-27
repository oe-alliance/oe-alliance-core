SUMMARY = "OpenATV smallflash upgrade - packages installed after flash expansion"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

PV = "${IMAGE_VERSION}"

# Backward compatibility: StartWizard installs by old name
RPROVIDES:${PN} = "packagegroup-openatv-small"

# Restore the complete OpenATV stack after the native wizard has moved /usr to
# USB. The same package group is used by the complete Chkroot rootfs image.
RDEPENDS:${PN} = " \
    packagegroup-oea-python-core \
    packagegroup-oea-multimedia \
    packagegroup-oea-gui \
    packagegroup-oea-enigma2-core \
    packagegroup-oea-enigma2-plugins \
    packagegroup-oea-distro-openatv \
    packagegroup-oea-network-server \
    dhrystone \
    streambench \
    ntfs-3g \
    unrar \
    tar \
    flip \
    hddtemp \
    rtmpdump \
    zip \
    ofgwrite \
    enigma2-plugin-extensions-cutlisteditor \
    enigma2-plugin-extensions-dvdplayer \
    enigma2-plugin-extensions-filecommander \
    enigma2-plugin-extensions-atilehd \
    enigma2-plugin-extensions-enhancedmoviecenter \
    ${@bb.utils.contains('MACHINE_FEATURES', 'dreamboxv1', 'mtd-utils-jffs2', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'dreamboxv1', bb.utils.contains_any('MACHINE', 'dm800se dm500hd', '', 'enigma2-plugin-extensions-dflash', d), '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'dreamboxv2', 'e2fsprogs-badblocks', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'grautec', 'enigma2-plugin-extensions-grautec', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'dvbc-only', '', 'enigma2-plugin-extensions-programmlistenupdater', d)} \
"

RRECOMMENDS:${PN} = "\
    packagegroup-oea-smallflash-core \
    enigma2-plugin-skins-metrix-atv \
    ${@bb.utils.contains('MACHINE_FEATURES', 'dvbc-only', '', 'enigma2-plugin-settings-defaultsat', d)} \
    "
