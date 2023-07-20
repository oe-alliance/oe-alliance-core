DESCRIPTION = "E2m3ubouquet plugin"
SUMMARY = "Update m3u to bouquets"
MAINTAINER = "Suls"
HOMEPAGE = "https://www.suls.co.uk/"
SECTION = "extra"
PRIORITY = "optional"
LICENSE = "GPL-2.0-only"
require conf/license/license-gplv2.inc
require conf/python/python3-compileall.inc

RDEPENDS:${PN} = "${PYTHON_PN}-image ${PYTHON_PN}-pillow"

inherit gettext gitpkgv

SRCREV = "${AUTOREV}"
PV = "0.9.0+git${SRCPV}"
PKGV = "0.9.0+git${GITPKGV}"
PR = "r1"

SRC_URI="git://github.com/oe-mirrors/e2m3u2bouquet-plugin.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

PLUGINPATH = "${libdir}/enigma2/python/Plugins/Extensions/E2m3u2bouquet"
FILES:${PN} = "${PLUGINPATH}"
D_FILES_PN = "${D}${FILES:${PN}}"

do_compile:append() {
    cd ${S}/po
    for f in $(find . -name *.po ); do
        l=$(echo ${f%} | sed 's/\.po$//' | sed 's/.*\///')
        mkdir -p ../locale/${l%}/LC_MESSAGES
        msgfmt -o ../locale/${l%}/LC_MESSAGES/E2m3u2bouquet.mo $l.po
    done
}

do_install() {
    install -d ${D_FILES_PN}
    install -d ${D_FILES_PN}/images
    install -m 644 ${S}/images/*.png ${D_FILES_PN}/images
    install -m 644 ${S}/*.py ${D_FILES_PN}
    install -m 644 ${S}/setup.xml ${D_FILES_PN}
    install -m 644 ${S}/LICENSE ${D_FILES_PN}
    mkdir -p ${D_FILES_PN}/locale
    cp -rf ${S}/locale/* ${D_FILES_PN}/locale
}

pkg_preinst:${PN}() {
#!/bin/sh

echo
echo "********************************************"
echo "Installing Engima2 IPTV E2m3u2bouquet plugin"
echo "********************************************"
echo
if [ -d "/var/tmp/e2m3u2bouquet" ]; then
  mv /var/tmp/e2m3u2bouquet /etc/enigma2 2>&1
fi
if [ ! -d "/etc/enigma2/e2m3u2bouquet/" ]; then
  mkdir -p /etc/enigma2/e2m3u2bouquet/ 2>&1
fi
if [ -f "/var/tmp/config.xml" -a ! -f "/etc/enigma2/e2m3u2bouquet/config.xml" ]; then
  mv  /var/tmp/config.xml /etc/enigma2/e2m3u2bouquet/ 2>&1
fi
exit 0
}

pkg_postrm:${PN}() {
#!/bin/sh

echo "********************************************"
echo "Removing IPTV bouquets"
echo "********************************************"
echo
cd /etc/enigma2/
sed -i '/suls_iptv/d' bouquets.tv
rm -f bouquets.tv.bak
echo
echo "********************************************"
echo "Removing files"
echo "********************************************"
echo
rm -r /usr/lib/enigma2/python/Plugins/Extensions/E2m3u2bouquet > /dev/null 2>&1
if [ -d "/var/tmp" ]; then
  mv /etc/enigma2/e2m3u2bouquet /var/tmp 2>&1
else
  rm -r /etc/enigma2/e2m3u2bouquet > /dev/null 2>&1
fi
find /etc/enigma2/ -type f -name '*suls_iptv*' -delete
find /etc/epgimport/ -type f -name '*suls_iptv*' -delete
echo
echo "********************************************"
echo "Engima2 IPTV E2m3u2bouquet plugin uninstalled"
echo "********************************************"
echo
echo "********************************************"
echo "Restart box to complete uninstall"
echo "********************************************"
exit 0
}

INSANE_SKIP:${PN} += "already-stripped file-rdeps ldflags"
