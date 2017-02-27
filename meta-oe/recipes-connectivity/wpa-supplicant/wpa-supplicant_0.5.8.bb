SUMMARY = "A Client for Wi-Fi Protected Access (WPA)."
SECTION = "network"
LICENSE = "GPLv2 | BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=c54ce9345727175ff66d17b67ff51f58 \
                    file://README;md5=4c2e7b0e7c3ffa2367167e64e79b7872 \
                    file://wpa_supplicant.c;beginline=1;endline=17;md5=2376799d9644dcb6ce69e7d180aa00d9"
HOMEPAGE = "http://hostap.epitest.fi/wpa_supplicant/"
DEPENDS = "openssl ${@bb.utils.contains("COMBINED_FEATURES", "madwifi", "madwifi-ng", "",d)}"

PR = "r3"

#we introduce MY_ARCH to get 'armv5te' as arch instead of the misleading 'arm' on armv5te builds
MY_ARCH := "${PACKAGE_ARCH}"
PACKAGE_ARCH = "${@bb.utils.contains('COMBINED_FEATURES', 'madwifi', '${MACHINE_ARCH}', '${MY_ARCH}', d)}"

SRC_URI = "http://hostap.epitest.fi/releases/wpa_supplicant-${PV}.tar.gz \
    file://defconfig-openssl \
    file://ifupdown.sh \
    file://functions.sh \
    file://driver-zydas.patch \
    file://driver-ralink.patch \
    "

TARGET_CFLAGS_append = " ${@bb.utils.contains("COMBINED_FEATURES", "madwifi", "-I${STAGING_INCDIR}/madwifi-ng", "",d)}"

S = "${WORKDIR}/wpa_supplicant-${PV}"

PACKAGES_prepend = "wpa-supplicant-passphrase "
FILES_wpa-supplicant-passphrase = "/usr/sbin/wpa_passphrase"

RREPLACES_${PN} = "wpa-supplicant-cli"

RRECOMMENDS_${PN} = "wpa-supplicant-passphrase"

export HAS_MADWIFI = "${@bb.utils.contains('COMBINED_FEATURES', 'madwifi', 1, 0,d)}"

do_configure () {
        install -m 0755 ${WORKDIR}/defconfig-openssl  .config

        if [ "x$HAS_MADWIFI" == "x1" ] ; then
                echo "CONFIG_DRIVER_MADWIFI=y" >> .config
                echo "CFLAGS += -I${STAGING_INCDIR}/madwifi-ng" >> .config
        fi
}

do_configure_append() {
    echo "CONFIG_DRIVER_RALINK=y" >> .config
    echo "CONFIG_DRIVER_ZYDAS=y" >> .config
}

do_compile () {
    make
}

do_install () {
    install -d ${D}${sbindir}
    install -m 755 wpa_supplicant ${D}${sbindir}
    install -m 755 wpa_passphrase ${D}${sbindir}
    install -m 755 wpa_cli        ${D}${sbindir}

    install -d ${D}${localstatedir}/run/wpa_supplicant

    install -d ${D}${docdir}/wpa_supplicant
    install -m 644 README ${D}${docdir}/wpa_supplicant

    install -d ${D}${sysconfdir}/network/if-pre-up.d/
    install -d ${D}${sysconfdir}/network/if-post-down.d/
    install -d ${D}${sysconfdir}/network/if-down.d/

    install -d ${D}${sysconfdir}/wpa_supplicant
    install -m 755 ${WORKDIR}/ifupdown.sh ${D}${sysconfdir}/wpa_supplicant/
    install -m 755 ${WORKDIR}/functions.sh ${D}${sysconfdir}/wpa_supplicant
    
    ln -s /etc/wpa_supplicant/ifupdown.sh ${D}${sysconfdir}/network/if-pre-up.d/wpasupplicant
    ln -s /etc/wpa_supplicant/ifupdown.sh ${D}${sysconfdir}/network/if-post-down.d/wpasupplicant
}

SRC_URI[md5sum] = "7bb22f2bcdeed54b3fb5407d6d8bc9bb"
SRC_URI[sha256sum] = "2f9755e2e3f96d26380857ef95be7765e6e276f6b53194c70633c027485d67f0"
