DESCRIPTION = "IPTV Xtream Codes playlists player by KiddaC"

HOMEPAGE = "https://www.linuxsat-support.com"
MAINTAINER = "kiddac"
PRIORITY = "optional"
require conf/license/license-gplv2.inc
DEPENDS += "python-image python-imaging python-argparse python-requests python-multiprocessing"

SRCREV="${AUTOREV}"

PV = "git${SRCPV}"
PKGV = "git${GITPKGV}"
PR = "r0"
inherit gitpkgv allarch

SRC_URI = "git://github.com/kiddac/XStreamity.git;protocol=http"

S = "${WORKDIR}/git"

FILES_${PN} = " ${libdir}/enigma2/python/Components/Converter/* \
                ${libdir}/enigma2/python/Components/Renderer/* \
                ${libdir}/enigma2/python/Plugins/Extensions/XStreamity/*"

do_install () {
    install -d ${D}/${libdir}/enigma2/python/Plugins/Extensions/XStreamity
    cp -rf ${S}/XStreamity/usr/lib/enigma2/python/Components/Converter/* ${D}/${libdir}/enigma2/python/Components/Converter/
    cp -rf ${S}/XStreamity/usr/lib/enigma2/python/Components/Renderer/* ${D}/${libdir}/enigma2/python/Components/Renderer/
    cp -rf ${S}/XStreamity/usr/lib/enigma2/python/Plugins/Extensions/XStreamity/* ${D}/${libdir}/enigma2/python/Plugins/Extensions/XStreamity/
}

pkg_preinst_${PN} () {
#!/bin/sh
    if [ -f "/etc/enigma2/X-Streamity/playlists.json" ]
	    then
	    rm -f /etc/enigma2/X-Streamity/playlists.json > /dev/null 2>&1
    fi

    if [ -f "/etc/enigma2/xstreamity/playlists.json" ]
	    then
	    rm -f /etc/enigma2/xstreamity/playlists.json > /dev/null 2>&1
    fi
}

pkg_postinst_${PN} () {
#!/bin/sh

    echo "*********************************************************"
    echo "*                                                       *"
    echo "* XStreamity                                            *"
    echo "* by KiddaC (c) 2020                                    *"
    echo "*                                                       *"
    echo "* Restart Enigma-2 GUI to add plugin into plugin list   *"
    echo "*********************************************************"
}

pkg_postrm_${PN} () {
#!/bin/sh
    rm -rf /usr/lib/enigma2/python/Plugins/Extensions/XStreamity > /dev/null 2>&1
    rm -f /usr/lib/enigma2/python/Components/Renderer/xRunningText.py* > /dev/null 2>&1
    rm -f /usr/lib/enigma2/python/Components/Converter/xServiceInfo.py* > /dev/null 2>&1
    rm -rf /etc/enigma2/X-Streamity
    rm -rf /etc/enigma2/xstreamity

    echo "Restart GUI to finish uninstall!"
}
