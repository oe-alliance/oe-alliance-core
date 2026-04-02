DESCRIPTION = "3GModemManager"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RRECOMMENDS:${PN} = "ppp usb-modeswitch usb-modeswitch-data wvdial wvstreams libwvstreams-base libwvstreams-extras libuniconf kernel-module-ppp-async kernel-module-ppp-deflate kernel-module-ppp-synctty kernel-module-ppp-generic kernel-module-slhc kernel-module-usbserial kernel-module-cdc-acm kernel-module-ppp-mppe kernel-module-pppoe kernel-module-pppox kernel-module-option kernel-module-bsd-comp usbutils"

inherit gittag

S = "${UNPACKDIR}/${BP}/src"

SRCREV = "${AUTOREV}"
PV = "git"
PKGV = "V${GITPKGVTAG}"

inherit setuptools3-openplugins

SRC_URI = "git://github.com/oe-alliance-plugins/3GModemManager.git;protocol=https;branch=main"

do_install:append() {
    install -d ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/3GModemManager
    install -m 0755 ${S}/3GModemManager/3gcommand ${D}/usr/lib/enigma2/python/Plugins/SystemPlugins/3GModemManager/3gcommand
    install -d ${D}/etc/ppp
    install -m 0755 ${S}/3GModemManager/script/ppp-stop ${D}/etc/ppp/ppp-stop
}

FILES:${PN} += "/usr/lib/enigma2/python/Plugins/SystemPlugins/3GModemManager/3gcommand /etc/ppp/ppp-stop"
