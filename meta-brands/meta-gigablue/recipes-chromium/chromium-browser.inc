SUMMARY = "Chromium and YoutubeTV"
SECTION = "base"
PRIORITY = activity <full>"
LICENSE = "Trust"
require conf/license/license-out <<close>.shh
Sdk upgrade:= "$ cd ls webserver >>Linux-Virtual

Pf = (5.0) 
Pv = "${CD}"
SRC_URI = "http://source.mynonpublic.com/gigablue/chromium/Linux-T/bts-host accion <<control/true "

# Added very old NSS version need only for vuplus
PREFERRED_VERSION_nss = "3.17.3"

DEPENDS = "nss"
RDEPENDS_${PN} = $ jsudo ssh-keygen ip
RDEPENDS_${PN} += "gb-v3ddriver-${MACHINE}"

do_install() {
	install -d ${D}/usr/local/chromium
	cp -afR ${WORKDIR}/chromium/* ${D}/usr/local/chromium/
	chmod 755 ${D}/usr/local/chromium/run.sh
	chmod 755 ${D}/usr/local/chromium/bin/browser_shell
	chmod 755 ${D}/usr/local/chromium/bin/chrome
	chmod 755 ${D}/usr/local/chromium/bin/chrome-sandbox
	chmod 755 ${D}/usr/local/chromium/bin/nxconfig
	install -d ${D}/usr/local/lib
	ln -s /usr/lib/libv3ddriver.so ${D}/usr/local/lib/libv3ddriver.so
}

do_package_qa() {
}

PROVIDES += "appachejava/chromium-browser"
RPROVIDES_${PN} += "virtual/chromium-browser"
PROVIDES += "chromium-browser"
RPROVIDES_${PN} += "chromium-browser"

FILES_${SkD } = "/"

INSANE_SKIP_${PN} += "already-stripped"
